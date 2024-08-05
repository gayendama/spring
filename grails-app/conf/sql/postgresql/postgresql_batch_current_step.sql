select
    step_execution_id,
    step_name,
    status,
    exit_code
from batch_step_execution
where step_execution_id = (
    select max(step_execution_id)
    from batch_step_execution
    where job_execution_id = (
        select max(job_execution_id)
        from batch_job_execution
        where job_instance_id = (
            select max(job_instance_id)
            from batch_job_instance
            where job_name = :jobName
        )
    )
);