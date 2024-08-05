package springbatch.batch.synchroneResponse

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import io.micronaut.http.HttpResponse
import org.apache.http.HttpStatus
import org.grails.web.json.JSONObject
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.StepExecutionListener
import org.springframework.batch.item.ItemWriter
import org.springframework.beans.factory.annotation.Autowired
import sn.sensoft.springbatch.CommittedItem
import sn.sensoft.springbatch.ItemToProcess
import sn.sensoft.springbatch.ToolsBatchService
import sn.sensoft.springbatch.api.AichaClient
import sn.sensoft.springbatch.api.ApiManagerService
import sn.sensoft.springbatch.apiRequest.aicha.ProcessRequest
import sn.sensoft.springbatch.apiResponse.aicha.SimpleResponse
import sn.sensoft.springbatch.utils.Constantes

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Slf4j
public class ItemWriterSync implements ItemWriter<ItemToProcess> , StepExecutionListener{

    ApiManagerService apiManagerService
    ToolsBatchService toolsBatchService

    String programCode
    String batchId
    String objet

    JobParameters jobParameters

    @Autowired
    final AichaClient aichaClient

    ExecutorService executor = Executors.newSingleThreadExecutor()

    @Override
    public void write(List<? extends ItemToProcess> items) throws Exception {
        if (items != null){
            items.each {ItemToProcess itemToProcess ->

                executor.execute(){
                    ProcessRequest processRequest = new ProcessRequest()
                    processRequest.batch = batchId
                    processRequest.program = programCode
                    processRequest.itemId = itemToProcess.objetId

                    JSONObject data
                    if(itemToProcess.jsonString != null){
                        def jsonSlurper = new JsonSlurper()
                         data = jsonSlurper.parseText(itemToProcess.jsonString) as JSONObject
                    }
                    else {
                        data = new JSONObject()
                    }

                    if(jobParameters != null) {
                        jobParameters.parameters.each { key, jobParameter ->
                            data.put("${key}", jobParameter.value)
                        }
                    }

                    processRequest.data = data



                    CommittedItem committedItem = new CommittedItem(
                            batchName: batchId,
                            stepName: programCode,
                            objet: objet,
                            objetId: itemToProcess.objetId
                    )


                    HttpResponse<SimpleResponse> httpResponse  = aichaClient.processItem(apiManagerService.aichaBearerToken(), processRequest)


                    if(httpResponse.code() == HttpStatus.SC_OK){
                        SimpleResponse simpleResponse = httpResponse.body()
                        if(simpleResponse.status == Constantes.STATUT_SUCCESS){
                            committedItem.responseCode = HttpStatus.SC_OK
                            toolsBatchService.committedItem(committedItem)
                        }
                        else {
                            committedItem.responseCode = HttpStatus.SC_BAD_REQUEST
                            committedItem.responseMessage = simpleResponse.message
                            toolsBatchService.committedItem(committedItem)
                            log.error(JsonOutput.toJson([batch: batchId, program: programCode, objet: objet, method: "write", code: HttpStatus.SC_BAD_REQUEST,  message: simpleResponse.message]))
                        }
                    }
                    else {
                        committedItem.responseCode = httpResponse.code()
                        committedItem.responseMessage = httpResponse.body().toString()
                        toolsBatchService.committedItem(committedItem)
                        log.error(JsonOutput.toJson([batch: batchId, program: programCode, objet: objet, method: "write", code: HttpStatus.SC_BAD_REQUEST,  message: httpResponse.body().toString()]))
                    }
                }
            }
        }
    }

    @Override
    void beforeStep(StepExecution stepExecution) {
        if(toolsBatchService.isEnabled(programCode)){
            jobParameters = stepExecution.jobParameters
        }
    }

    @Override
    ExitStatus afterStep(StepExecution stepExecution) {
        return stepExecution.exitStatus
    }
}
