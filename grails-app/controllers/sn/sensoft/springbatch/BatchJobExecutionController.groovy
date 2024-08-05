package sn.sensoft.springbatch

import org.apache.http.HttpStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionException
import sn.sensoft.springbatch.utils.BatchList
import sn.sensoft.springbatch.utils.ConstantesBatch
import springbatch.model.JobExecutionModel
import springbatch.model.StepExecutionModel
import springbatch.ui.PagedResult

class BatchJobExecutionController {

    def springBatchUiService
    def springBatchService

    static defaultAction = 'show'
    def toolsBatchService

    static String MODULE_NAME = "batch"

    def show(String id) {
        if (id) {
            PagedResult<StepExecutionModel> stepExecutionList = springBatchUiService.getStepExecutionModels(id.toLong(), params)
            Integer numberCommittedItemWithError = CommittedItem.countByResponseCodeNotEqual(HttpStatus.SC_OK)
            def batchListWithOptions = [BatchList.BatchTFJ.id, BatchList.BatchRecuperationProvison.id]
            JobExecutionModel jobExecutionModel = springBatchUiService.jobExecutionModel(id.toLong())

            [
                    jobExecution  : springBatchUiService.jobExecutionModel(id.toLong()),
                    modelInstances: toolsBatchService.getEnabledList(stepExecutionList, jobExecutionModel.jobName),
                    numberCommittedItemWithError: numberCommittedItemWithError,
                    batchListWithOptions: batchListWithOptions
            ]
        }
        else {
            flash.error = "Please supply a job execution id"
            redirect(controller: "batch", action: "list")
        }
    }

    def restart(String id) {
        if (id) {
            try {
                JobExecution jobExecution = springBatchService.restart(id.toLong())
                flash.message = "Restarted Job Execution"
                redirect(controller: "batchJobExecution", action: "show", id: jobExecution.id)
            }
            catch (JobExecutionException jee) {
                flash.error = jee.message
                redirect(controller: "batch", action: "list")
            }
        }
        else {
            flash.error = "Please supply a job execution id"
            redirect(controller: "batch", action: "list")
        }
    }

    def continuer(String id) {
        if (id) {
            try {
                def listErrorCode = [HttpStatus.SC_BAD_REQUEST, HttpStatus.SC_INTERNAL_SERVER_ERROR]
                CommittedItem.executeUpdate("update CommittedItem set responseCode = :code where responseCode in :listCode", [code: HttpStatus.SC_OK, listCode: listErrorCode])

                JobExecution jobExecution = springBatchService.restart(id.toLong())
                flash.message = "continuity Job Execution"
                redirect(controller: "batchJobExecution", action: "show", id: jobExecution.id)
            }
            catch (JobExecutionException jee) {
                flash.error = jee.message
                redirect(controller: "batch", action: "list")
            }
        }
        else {
            flash.error = "Please supply a job execution id"
            redirect(controller: "batch", action: "list")
        }
    }

    def stop(String id) {
        if (id) {
            try {
                springBatchService.stop(id.toLong())
                flash.message = "Stopped Job Execution"
            }
            catch (JobExecutionException jee) {
                flash.error = jee.message
            }
            redirect(action: "show", id: id)
        }
        else {
            flash.error = "Please supply a job execution id"
            redirect(controller: "batch", action: "list")
        }
    }
}
