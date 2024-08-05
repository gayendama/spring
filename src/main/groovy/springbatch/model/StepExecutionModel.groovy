package springbatch.model

import groovy.transform.EqualsAndHashCode
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.ExitStatus

@EqualsAndHashCode
class StepExecutionModel {

    Long id
    Long jobExecutionId
    String jobName
    String name
    Date startDateTime
    Date endTime
    Long duration
    BatchStatus status
    Integer reads
    Integer writes
    Integer skips
    Integer readSkipCount
    Integer writeSkipCount
    Integer rollbackCount
    Integer commitCount
    Integer filterCount
    Integer processSkipCount
    
    ExitStatus exitStatus

    List<Throwable> failureExceptions
    Date lastUpdated
    String summary

    Integer sequenceNumber
}
