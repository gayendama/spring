package springbatch.batch.synchroneResponse

import groovy.util.logging.Slf4j
import io.micronaut.http.HttpResponse
import org.apache.http.HttpStatus
import org.grails.web.json.JSONObject
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.Transaction
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.StepExecutionListener
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.NonTransientResourceException
import org.springframework.batch.item.ParseException
import org.springframework.batch.item.UnexpectedInputException
import org.springframework.beans.factory.annotation.Autowired
import sn.sensoft.springbatch.CommittedItem
import sn.sensoft.springbatch.ItemToProcess
import sn.sensoft.springbatch.ToolsBatchService
import sn.sensoft.springbatch.api.AichaClient
import sn.sensoft.springbatch.api.ApiManagerService
import sn.sensoft.springbatch.apiRequest.aicha.ItemsToProcessRequest
import sn.sensoft.springbatch.apiResponse.aicha.ItemToProcessResponse
import sn.sensoft.springbatch.apiResponse.aicha.SimpleResponse
import sn.sensoft.springbatch.exception.SpringbatchException
import sn.sensoft.springbatch.utils.Constantes

@Slf4j
public class ItemReaderRepriseSync implements ItemReader<ItemToProcess>, StepExecutionListener{

    ToolsBatchService toolsBatchService
    ApiManagerService apiManagerService
    SessionFactory sessionFactory

    Boolean notBeforStep
    int offset=0;

    String programCode
    String batchId

    @Autowired
    final AichaClient aichaClient

    @Override
    public ItemToProcess read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException, SpringbatchException {

        if(!toolsBatchService.isEnabled(programCode)){
            return null
        }

        ItemToProcess.withNewSession{
            List<ItemToProcess> itemToProcessList = ItemToProcess.findAll([max: 1, offset: offset])
            if(itemToProcessList.size() > 0){
                sleep(250)
                offset++;
                return itemToProcessList.first()
            }
            else {
                Integer nbItem = ItemToProcess.count()
                Integer nbProcessItem = CommittedItem.count()

                while (nbItem > nbProcessItem){
                    sleep(5000)
                    nbProcessItem = CommittedItem.count()
                }

                Integer processedItemsWithError = CommittedItem.countByResponseCodeNotEqual(HttpStatus.SC_OK)
                if (processedItemsWithError > 0){
                    throw new SpringbatchException("${ExitStatus.FAILED.exitCode}: ${processedItemsWithError} item(s) traité(s) avec erreur (voir détails)")
                }
                else {
                    return null
                }
            }
        }
    }

    @Override
    void beforeStep(StepExecution stepExecution) {
        if(notBeforStep){
            return
        }

        if (!toolsBatchService.isEnabled(programCode)){
            return
        }

        log.debug("[Batch debut step: ${stepExecution.stepName}, parameters: ${stepExecution.properties.toString()}]")
        ItemToProcess.withNewSession{
            ItemToProcess.executeUpdate("delete from ItemToProcess")
            ItemsToProcessRequest itemsToProcessRequest = new ItemsToProcessRequest()
            itemsToProcessRequest.max = 10000
            itemsToProcessRequest.batch = batchId
            itemsToProcessRequest.program = programCode

            JSONObject data = new JSONObject()
            stepExecution.jobParameters.parameters.each {key, jobParameter ->
                data.put("${key}", jobParameter.value)
            }

            Integer nbItemTraite = CommittedItem.count
            if(nbItemTraite == 0){
                Boolean getItems = true
                Integer offsetItem = 0
                while (getItems){
                    itemsToProcessRequest.offset = offsetItem
                    HttpResponse<ItemToProcessResponse> httpResponse = aichaClient.getItemsToPrecessReprise(apiManagerService.aichaBearerToken(), itemsToProcessRequest)
                    if(httpResponse.code() == HttpStatus.SC_OK){
                        ItemToProcessResponse itemToProcessResponse = httpResponse.body()
                        if(itemToProcessResponse?.data != null && itemToProcessResponse?.data?.size() > 0){
                            Session session = sessionFactory.openSession()
                            Transaction tx = session.beginTransaction();
                            itemToProcessResponse.data.eachWithIndex { itemToProcess, i ->
                                session.save(itemToProcess)
                                if(i.mod(100)==0) {
                                    session.flush();
                                    session.clear();
                                }
                            }
                            tx.commit();
                            session.close();

                            offsetItem++
                        }
                        else {
                            getItems = false
                        }
                    }
                    else {
                        throw new SpringbatchException("${Constantes.APP_PARTNER_AICHA}: ${httpResponse.body()}")
                    }
                }
            }
        }
    }

    @Override
    ExitStatus afterStep(StepExecution stepExecution) {
        offset=0;
        ExitStatus exitStatus =  stepExecution.exitStatus
        JobParameters jobParameters = stepExecution.getJobExecution().getJobParameters()

        if(exitStatus.exitCode.equals(ExitStatus.COMPLETED.exitCode)){
            CommittedItem.withNewSession {
                if(toolsBatchService.isEnabled(programCode)){
                    HttpResponse<SimpleResponse> httpResponse = aichaClient.updatesAfterStep(apiManagerService.aichaBearerToken(), batchId, programCode)
                    if(httpResponse.code() != HttpStatus.SC_OK){
                        throw new SpringbatchException("${Constantes.APP_PARTNER_AICHA}: {code: ${httpResponse.code()}, body: ${httpResponse.body()}}")
                    }
                }

                CommittedItem.executeUpdate('delete from CommittedItem')
                ItemToProcess.executeUpdate('delete from ItemToProcess')
            }
        }

        if(stepExecution.jobExecution.exitStatus.exitCode == ExitStatus.COMPLETED.exitCode){
            toolsBatchService.sendBatchnotification(ExitStatus.COMPLETED.exitCode, jobParameters, stepExecution.stepName, exitStatus.exitDescription, batchId)
        }
        else if(exitStatus.exitCode.equals(ExitStatus.FAILED.exitCode)){
            toolsBatchService.sendBatchnotification(ExitStatus.FAILED.exitCode, jobParameters, stepExecution.stepName, exitStatus.exitDescription, batchId)
        }

        log.debug("[Batch fin step: ${stepExecution.stepName}, parameters: ${stepExecution.properties.toString()}]")

        return exitStatus
    }
}
