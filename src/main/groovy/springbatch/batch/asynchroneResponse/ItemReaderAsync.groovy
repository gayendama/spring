package springbatch.batch.asynchroneResponse

import groovy.json.JsonOutput
import groovy.util.logging.Slf4j
import io.micronaut.http.HttpResponse
import org.apache.http.HttpStatus
import org.grails.web.json.JSONObject
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.StepExecutionListener
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.NonTransientResourceException
import org.springframework.batch.item.ParseException
import org.springframework.batch.item.UnexpectedInputException
import org.springframework.beans.factory.annotation.Autowired
import sn.sensoft.springbatch.ToolsBatchService
import sn.sensoft.springbatch.api.AichaClient
import sn.sensoft.springbatch.api.ApiManagerService
import sn.sensoft.springbatch.apiRequest.aicha.ProcessRequest
import sn.sensoft.springbatch.apiResponse.aicha.SimpleResponse
import sn.sensoft.springbatch.exception.SpringbatchException
import sn.sensoft.springbatch.parametrage.Batch
import sn.sensoft.springbatch.parametrage.ProgrammeBatch
import sn.sensoft.springbatch.utils.BatchStepStatus
import sn.sensoft.springbatch.utils.Constantes
import sn.sensoft.springbatch.utils.ConstantesBatch


@Slf4j
public class ItemReaderAsync implements ItemReader<ProgrammeBatch>, StepExecutionListener {

    ToolsBatchService toolsBatchService
    ApiManagerService apiManagerService
    int offset = 0

    String programCode
    String batchId

    @Autowired
    final AichaClient aichaClient

    @Override
    public ProgrammeBatch read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException, SpringbatchException {

        if (!toolsBatchService.isEnabled(programCode)) {
            return null
        }

        Batch.withNewSession {
            log.debug("[Batch deb parameters: ${this.toString()}]")

            ProgrammeBatch programmeBatch = ProgrammeBatch.findByCodeProgrammeAndProgramStatus(programCode, Constantes.STATUT_STARTED)
            log.debug("[Batch get parameters: ${programmeBatch.toString()}]")
            if (programmeBatch == null && offset == 0) {
                offset++
                programmeBatch = ProgrammeBatch.findByCodeProgramme(programCode)
            }
            log.info("[Batch after parameters: ${programmeBatch.toString()}]")
            return programmeBatch
        }
    }

    @Override
    void beforeStep(StepExecution stepExecution) {
        if (!toolsBatchService.isEnabled(programCode)) {
            log.warn(JsonOutput.toJson([method: "beforeStep", message: "${programCode} is disabled..."]))
            return
        }

        log.info(JsonOutput.toJson([method     : "beforeStep",
                                    batchId    : batchId,
                                    programCode: programCode,
                                    stepName   : stepExecution?.stepName,
                                    parameters : stepExecution?.properties?.toString()]))
        ProgrammeBatch.withNewSession {
            ProgrammeBatch.executeUpdate("update ProgrammeBatch set programStatus = :status where codeProgramme = :code", [status: Constantes.STATUT_STARTED, code: programCode])

            JSONObject data = new JSONObject()
            stepExecution.jobParameters.parameters.each { key, jobParameter ->
                data.put("${key}", jobParameter.value)
            }

            ProcessRequest processRequest = new ProcessRequest()
            processRequest.batch = batchId
            processRequest.program = programCode
            processRequest.data = data

            log.info(JsonOutput.toJson([method     : "beforeStep",
                                        batchId    : batchId,
                                        programCode: programCode,
                                        message    : "before call",
                                        data: data]))
            HttpResponse<SimpleResponse> httpResponse = aichaClient.processItem(apiManagerService.aichaBearerToken(), processRequest)

            if (httpResponse.code() == HttpStatus.SC_OK) {
                SimpleResponse simpleResponse = httpResponse.body()
                if (simpleResponse.status != Constantes.STATUT_SUCCESS) {
                    log.error(JsonOutput.toJson([method : "beforeStep",
                                                 status : simpleResponse?.status,
                                                 message: simpleResponse?.message]))
                    throw new SpringbatchException("${Constantes.APP_PARTNER_AICHA}: ${simpleResponse.message}")
                }
                else {
                    log.info(JsonOutput.toJson([method      : "beforeStep",
                                                batchId     : batchId,
                                                programCode : programCode,
                                                message     : "after call",
                                                status      : simpleResponse.status,
                                                httpResponse: httpResponse.code()]))
                }
            }
            else {
                log.error(JsonOutput.toJson([method            : "beforeStep",
                                             batchId           : batchId,
                                             programCode       : programCode,
                                             httpResponseCode  : httpResponse?.code(),
                                             httpResponseReason: httpResponse?.reason(),
                                             httpResponseBody  : "${httpResponse.body()}",
                                             status            : simpleResponse?.status,
                                             message           : simpleResponse?.message
                ]))
                throw new SpringbatchException("${Constantes.APP_PARTNER_AICHA}: ${httpResponse.body()}")
            }
        }

    }

    @Override
    ExitStatus afterStep(StepExecution stepExecution) {
        log.debug(JsonOutput.toJson([method: "ItemReaderAsync  after after step", stepExecution: stepExecution.toString()]))

        offset = 0
        ExitStatus exitStatus = stepExecution.exitStatus
        JobParameters jobParameters = stepExecution.getJobExecution().getJobParameters()

        if (exitStatus.exitCode == ExitStatus.COMPLETED.exitCode && stepExecution.stepName == ConstantesBatch.Programme.SAUVB.name) {
            toolsBatchService.sendBatchnotification(ExitStatus.COMPLETED.exitCode, jobParameters, stepExecution.stepName, exitStatus.exitDescription, batchId)

            ProgrammeBatch.withNewSession {
                ProgrammeBatch.executeUpdate("update ProgrammeBatch set batchStepStatus = :batchStepStatus, indReactiverApresBatch = false where batchId = :jobName and indReactiverApresBatch = true", [batchStepStatus: BatchStepStatus.ACTIVER, jobName: stepExecution.jobExecution.jobInstance.jobName])
            }
        }
        else if (exitStatus.exitCode.equals(ExitStatus.FAILED.exitCode)) {
            toolsBatchService.sendBatchnotification(ExitStatus.FAILED.exitCode, jobParameters, stepExecution.stepName, exitStatus.exitDescription, batchId)
        }
        log.info(JsonOutput.toJson([method         : "afterStep",
                                    message        : "Fin step",
                                    jobName        : stepExecution?.jobExecution?.jobInstance?.jobName,
                                    stepName       : stepExecution?.stepName,
                                    parameters     : stepExecution?.properties?.toString(),
                                    exitCode       : exitStatus?.exitCode,
                                    exitDescription: exitStatus?.exitDescription
        ]))

        return exitStatus
    }
}
