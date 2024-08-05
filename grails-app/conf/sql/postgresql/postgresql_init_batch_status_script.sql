UPDATE batch_job_execution
set
    status = :status,
    exit_code = :status,
    exit_message = 'org.springframework.batch.core.JobInterruptedException'
where job_execution_id = :jobExecutionId;

UPDATE batch_step_execution
set
    status = :status,
    exit_code = :status,
    exit_message = 'org.springframework.batch.core.JobInterruptedException'
where job_execution_id = :jobExecutionId;