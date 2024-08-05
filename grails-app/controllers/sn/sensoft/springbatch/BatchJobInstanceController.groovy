package sn.sensoft.springbatch

class BatchJobInstanceController {

    def springBatchUiService

    static defaultAction = 'show'

    static String MODULE_NAME = "batch"

    def show(String jobName, String id) {
        if (!jobName) {
            flash.error = "Please supply a job name"
            redirect(controller: "springBatchJob", action: "list")
            return
        }
        if (!id) {
            flash.error = "Please supply a job instance id"
            redirect(controller: "springBatchJob", action: "show", id: jobName)
            return
        }

        Long lId = Long.parseLong(id)

        [jobInstance   : springBatchUiService.jobInstanceModel(lId),
         modelInstances: springBatchUiService.getJobExecutionModels(jobName, lId, params),
         jobName       : jobName]
    }
}
