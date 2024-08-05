package sn.sensoft.springbatch

import org.springframework.batch.admin.service.JobService
import org.springframework.batch.core.*
import springbatch.model.JobExecutionModel
import springbatch.model.JobInstanceModel
import springbatch.model.JobModel
import springbatch.model.StepExecutionModel
import springbatch.ui.NaturalSort
import springbatch.ui.PagedResult
import springbatch.ui.SpringBatchUiUtilities

/**
 * Manage UI model
 */
class SpringBatchUiService {
    SpringBatchService springBatchService
    JobService jobService

    private final NaturalSort naturalSort = new NaturalSort()

    /**
     * Populate the jobModel from the jobService
     * @param jobName
     * @param fullVersion - defaults to true for the full Version of the model,
     * otherwise generates a lite version of the model
     */
    JobModel jobModel(String jobName, boolean fullVersion = true) {
        if (!jobName) {
            log.debug("No jobName submitted")
            return null
        }

        Integer countJobExecutions = jobService.countJobExecutionsForJob(jobName)
        Integer countJobInstances = jobService.countJobInstances(jobName)
        Boolean launchable = jobService.isLaunchable(jobName)
        Boolean incrementable = jobService.isIncrementable(jobName)
        Boolean currentlyRunning = springBatchService.hasRunningExecutions(jobName)
        Collection stepNames = null

        List<JobExecution> mostRecentJobExecutions = jobService.listJobExecutionsForJob(jobName, 0, 1)
        JobExecutionModel mostRecentJobExecutionModel = null
        if (mostRecentJobExecutions) {
            mostRecentJobExecutionModel = jobExecutionModel(mostRecentJobExecutions[0])
        }

        if (fullVersion) {
            stepNames = jobService.getStepNamesForJob(jobName)
        }

        new JobModel(name: jobName,
            executionCount: countJobExecutions,
            jobInstanceCount: countJobInstances,
            stepNames: stepNames,
            launchable: launchable,
            incrementable: incrementable,
            currentlyRunning: currentlyRunning,
            mostRecentJobExecution: mostRecentJobExecutionModel)
    }

    PagedResult<JobModel> getJobModels(Map params) {
        int jobCount = jobService.countJobs()

        initPaging(params, jobCount)

        Collection<String> jobs = jobService.listJobs(params.offset, params.max)
        Collection<JobModel> jobModels = jobs.collect { String name ->
            jobModel(name, false)
        }

        jobModels.sort(true) { JobModel a, JobModel b ->
            naturalSort.compare(a.name, b.name)
        }

        new PagedResult<JobModel>(resultsTotalCount: jobCount, results: jobModels)
    }


    JobParameters buildJobParametersFromRequest(Map reqParams) {
        Map entries = [:]
        if (reqParams.jobParams) {
            reqParams.jobParams.collectEntries { k, v ->
                entries.put(k, new JobParameter(v))
            }
        }

        entries = entries ?: springBatchService.defaultJobParameters()
        springBatchService.mapToJobParameters(entries)
    }


    JobInstanceModel jobInstanceModel(Long jobInstanceId) {
        jobInstanceModel(springBatchService.jobInstance(jobInstanceId))
    }

    JobInstanceModel jobInstanceModel(JobInstance jobInstance) {
        Collection<JobExecution> jobExecutions = jobService.getJobExecutionsForJobInstance(jobInstance.jobName, jobInstance.id)
        if (!jobExecutions) {
            return new JobInstanceModel(
                id: jobInstance.id, jobName: jobInstance.jobName,
                jobExecutionCount: 0, lastJobExecutionStatus: null, executions: []
            )
        }
        Collection<JobExecutionModel> jobExecutionModels = jobExecutions.collect { JobExecution jobExecution ->
            jobExecutionModel(jobExecution)
        }
        return new JobInstanceModel(
            id: jobInstance.id,
            jobName: jobInstance.jobName,
            jobExecutionCount: jobExecutions.size(),
            lastJobExecutionStatus: jobExecutions.first().status,
            executions: jobExecutionModels
        )
    }

    PagedResult<JobInstanceModel> getJobInstanceModels(String jobName, Map params) {
        Integer jobInstanceCount = jobService.countJobInstances(jobName)

        initPaging(params, jobInstanceCount)

        Collection<JobInstance> jobInstances = jobService.listJobInstances(
            jobName, params.offset, params.max
        )
        Collection<JobInstanceModel> jobInstanceModels = jobInstances.collect { JobInstance jobInstance ->
            jobInstanceModel(jobInstance)
        }

        new PagedResult<JobInstanceModel>(resultsTotalCount: jobInstanceCount, results: jobInstanceModels)
    }

    JobExecutionModel jobExecutionModel(Long jobExecutionId) {
        jobExecutionModel(springBatchService.jobExecution(jobExecutionId))
    }

    JobExecutionModel jobExecutionModel(JobExecution jobExecution) {
        new JobExecutionModel(
            id: jobExecution.id,
            startDateTime: jobExecution.startTime,
            duration: SpringBatchUiUtilities.getDuration(jobExecution.startTime, jobExecution.endTime),
            status: jobExecution.status,
            exitStatus: jobExecution.exitStatus,
            jobName: jobExecution.jobInstance.jobName,
            instanceId: jobExecution.jobInstance.id,
            jobParameters: jobExecution.jobParameters.parameters
        )
    }

    PagedResult<JobExecutionModel> getJobExecutionModels(String jobName, Long jobInstanceId, Map params = [:]) {
        def jobExecutions
        try {
            jobExecutions = jobService.getJobExecutionsForJobInstance(jobName, jobInstanceId)
        }
        catch (e) {
            log.warn("Unable to get job executions for $jobName", e)
            return []
        }

        int jobExecutionCount = jobExecutions.size()
        initPaging(params, jobExecutionCount)

        Collection<JobExecutionModel> jobExecutionModels = SpringBatchUiUtilities.paginate(params.offset, params.max) {
            jobExecutions.collect { JobExecution jobExecution ->
                jobExecutionModel(jobExecution)
            }
        }

        new PagedResult<JobExecutionModel>(resultsTotalCount: jobExecutionCount, results: jobExecutionModels)
    }


    StepExecutionModel stepExecutionModel(Long jobExecutionId, Long stepExecutionId) {
        stepExecutionModel(springBatchService.stepExecution(jobExecutionId, stepExecutionId))
    }

    StepExecutionModel stepExecutionModel(StepExecution stepExecution) {
        log.debug(" jobExecutionId: ${stepExecution.jobExecutionId},  jobExecution: ${stepExecution.jobExecution}")

        new StepExecutionModel(
            id: stepExecution.id,
            jobExecutionId: stepExecution.jobExecutionId,
            name: stepExecution.stepName,
            startDateTime: stepExecution.startTime,
            duration: SpringBatchUiUtilities.getDuration(stepExecution.startTime, stepExecution.endTime),
            status: stepExecution.status,
            reads: stepExecution.readCount,
            writes: stepExecution.writeCount,
            skips: stepExecution.skipCount,
            exitStatus: stepExecution.exitStatus,
            jobName: stepExecution.jobExecution?.jobInstance?.jobName,
            commitCount: stepExecution.commitCount,
            endTime: stepExecution.endTime,
            failureExceptions: stepExecution.failureExceptions,
            filterCount: stepExecution.filterCount,
            lastUpdated: stepExecution.lastUpdated,
            processSkipCount: stepExecution.processSkipCount,
            readSkipCount: stepExecution.readSkipCount,
            rollbackCount: stepExecution.rollbackCount,
            summary: stepExecution.summary,
            writeSkipCount: stepExecution.writeSkipCount
        )
    }

    PagedResult<StepExecutionModel> getStepExecutionModels(Long jobExecutionId, Map params) {
        try {
            Collection<StepExecution> stepExecutions = jobService.getStepExecutions(jobExecutionId) ?: []
            int stepExecutionCount = stepExecutions ? stepExecutions.size() : 0
            initPaging(params, stepExecutionCount)

            Collection<StepExecutionModel> stepExecutionModels = SpringBatchUiUtilities.paginate(params.offset, params.max) {
                stepExecutions.collect { stepExecutionModel(it) }
            }

            return new PagedResult<StepExecutionModel>(resultsTotalCount: stepExecutionCount, results: stepExecutionModels)
        }
        catch (e) {
            log.error("Failed to fetch Step Executions for JobExecution: $jobExecutionId", e)
            return new PagedResult<StepExecutionModel>(results: [])
        }
    }

    PagedResult<StepExecutionModel> previousStepExecutionModels(StepExecutionModel stepExecution, Map params) {
        if (!stepExecution) {
            return new PagedResult<StepExecutionModel>(results: [], resultsTotalCount: 0)
        }

        int stepExecutionCount = jobService.countStepExecutionsForStep(stepExecution.jobName, stepExecution.name)
        initPaging(params, stepExecutionCount)

        Collection<StepExecutionModel> stepExecutionModels = []

        Collection<StepExecution> previousSteps = springBatchService.previousStepExecutions(
            stepExecution.jobName, stepExecution.name, params.offset as Integer, params.max as Integer
        )

        if (previousSteps) {
            stepExecutionModels = previousSteps.collect {
                stepExecutionModel(it)
            }
        }

        new PagedResult<StepExecutionModel>(resultsTotalCount: stepExecutionCount, results: stepExecutionModels)
    }

    private void initPaging(Map params, Integer maxElementsInQuery) {
        //int max = defaultMax
        int max = maxElementsInQuery
        if (params.max) {
            if (params.max instanceof String) {
                if (params.max.isInteger()) {
                    max = Integer.valueOf(params.max)
                }
            }
            else {
                max = params.max
            }
        }
        params.max = Math.min(max, maxElementsInQuery)

        if (params.offset) {
            if (params.offset instanceof String) {
                if (params.offset.isInteger()) {
                    params.offset = Integer.valueOf(params.offset)
                }
            }
        }
        else {
            params.offset = 0
        }
    }
}
