package sn.sensoft.springbatch

import grails.converters.JSON
import org.apache.http.HttpStatus
import springbatch.model.StepExecutionModel


class BatchStepExecutionController {

    def springBatchUiService

    static defaultAction = 'show'

    static String MODULE_NAME = "batch"

    def show(String id) {
        Long jobExecutionId

        if (params.jobExecutionId && params.jobExecutionId.isLong()) {
            jobExecutionId = params.jobExecutionId.toLong()
        }

        if (!id || !jobExecutionId) {
            flash.error = "Please supply a Step execution id and a Job execution id"
            redirect(controller: "springBatchJob", action: "list")
        }
        else {
            Long lId = Long.parseLong(id)
            StepExecutionModel stepExecution = springBatchUiService.stepExecutionModel(jobExecutionId, lId)
            def modelInstances = []
            if (stepExecution) {
                modelInstances = springBatchUiService.previousStepExecutionModels(
                        stepExecution, params)
            }

            Integer numberItemsWithException = CommittedItem.count()

            [stepExecution: stepExecution, modelInstances: modelInstances, numberItemsWithException: numberItemsWithException]
        }
    }

    def loadError() {

        def sortIndex = params.sort ? params.sort : 'objetId'
        def sortOrder = params.order ? params.order : 'desc'
        def maxRows = params.limit ? Integer.valueOf(params.limit) : 10
        def offset = params.offset ? Integer.valueOf(params.offset) : 0


        def committedItemList = CommittedItem.createCriteria().list(max: maxRows, offset: offset) {
            ne("responseCode", HttpStatus.SC_OK)

            if (params.search != null && !"".equals(params.search)) {
                or {
                    ilike("objetId", "%${params.search}%")
                    //eq("responseCode", Integer.parseInt(params.search))
                    ilike("responseMessage", "%${params.search}%")
                }
            }

            order(sortIndex, sortOrder)
        }


        def totalRows = committedItemList.totalCount
        def collection = committedItemList.collect {CommittedItem committedItem->
            [
                    objet            : committedItem.objet,
                    objetId        : committedItem.objetId,
                    responseCode   : committedItem.responseCode,
                    responseMessage: committedItem.responseMessage
            ]
        }

        def result = [total: totalRows, rows: collection]


        render result as JSON
    }
}
