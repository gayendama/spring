<%@ page import="sn.sensoft.springbatch.parametrage.ProgrammeBatch" %>
<%@ page import="sn.sensoft.springbatch.utils.ConstantesBatch" %>

<div class="page-content-inner">
    <div class="row">
        <div class="col-md-12">
            <div class="portlet light portlet-fit portlet-datatable ">

                <div class="report" style="margin-left: 10px;">
                    <div class="panel panel-default report-content">
                        <div class="panel-heading"><g:message
                                code="batch.jobExecution.startDateTime.label"/></div>

                        <div class="panel-body">
                            <g:formatDate type="datetime" style="LONG" timeStyle="MEDIUM"
                                          date="${jobExecution.startDateTime}"/>
                        </div>
                    </div>

                    <div class="panel panel-default report-content">
                        <div class="panel-heading"><g:message
                                code="batch.jobExecution.duration.label"/></div>

                        <div class="panel-body"><batch:durationPrint
                                duration="${jobExecution.duration}"/></div>
                    </div>

                    <div class="panel panel-default report-content">
                        <div class="panel-heading"><g:message
                                code="batch.jobExecution.status.label"/>

                        </div>

                        <div class="panel-body">
                            <g:if test="${jobExecution.status.toString().equals(sn.sensoft.springbatch.utils.ConstantesBatch.BATCH_JOB_INSTANCE_STARTED_STATUS)}">
                                <span class="batch-result status-background-white">${jobExecution.status}</span>
                            </g:if>
                            <g:elseif
                                    test="${jobExecution.status.toString().equals(ConstantesBatch.BATCH_JOB_INSTANCE_COMPLETED_STATUS)}">
                                <span class="batch-result status-background-green">${jobExecution.status}</span>
                            </g:elseif>
                            <g:elseif
                                    test="${jobExecution.status.toString().equals(ConstantesBatch.BATCH_JOB_INSTANCE_FAILED_STATUS)}">
                                <span class="batch-result status-background-red">${jobExecution.status}</span>
                            </g:elseif>
                            <g:elseif
                                    test="${jobExecution.status.toString().equals(ConstantesBatch.BATCH_JOB_INSTANCE_STOPPING_STATUS)}">
                                <span class="batch-result status-background-grey">
                                    <i class="fa fa-spinner fa-spin fa-lg load-step"></i> ${jobExecution.status}
                                </span>
                            </g:elseif>
                            <g:else>
                                <span class="batch-result status-background-orange">${jobExecution.status}</span>
                            </g:else>
                        </div>
                    </div>

                    <div class="panel panel-default report-content">
                        <div class="panel-heading"><g:message
                                code="batch.jobExecution.exitStatus.label"/></div>

                        <div class="panel-body">
                            <g:if test="${jobExecution.exitStatus.exitCode.toString().equals(ConstantesBatch.BATCH_JOB_INSTANCE_STARTED_STATUS)}">
                                <span class="batch-result status-background-white">${jobExecution.exitStatus.exitCode}</span>
                            </g:if>
                            <g:elseif
                                    test="${jobExecution.exitStatus.exitCode.toString().equals(ConstantesBatch.BATCH_JOB_INSTANCE_COMPLETED_STATUS)}">
                                <span class="batch-result status-background-green">${jobExecution.exitStatus.exitCode}</span>
                            </g:elseif>
                            <g:elseif
                                    test="${jobExecution.exitStatus.exitCode.toString().equals(ConstantesBatch.BATCH_JOB_INSTANCE_FAILED_STATUS)}">
                                <span class="batch-result status-background-red">${jobExecution.exitStatus.exitCode}</span>
                            </g:elseif>
                            <g:else>
                                <span class="batch-result status-background-orange">${jobExecution.exitStatus.exitCode}</span>
                            </g:else>
                        </div>
                    </div>
                </div>

                <div class="portlet-body">
                    <div class="table-container">

                        <g:if test="${flash.message}">
                            <div class="message" role="status">${flash.message}</div>
                        </g:if>
                        <g:if test="${flash.error}">
                            <div class="errors" role="status">${flash.error}</div>
                        </g:if>

                        <div class="step-container">

                            <div class="step-box">
                                <g:message code="batch.jobExecution.steps.label"/>
                            </div>

                            <table class="table table-striped table-bordered table-hover dt-responsive"
                                   id="sample_2">
                                <thead>
                                <tr>
                                    <!-- g:sortableColumn property="id"
                                                                                  title="${message(code: 'batch.stepExecution.id.label', default: 'ID')}"/ -->
                                    <g:sortableColumn property="name"
                                                      title="${message(code: 'batch.stepExecution.name.label', default: 'Name')}"/>
                                    <g:sortableColumn property="startDateTime"
                                                      title="${message(code: 'batch.stepExecution.startDateTime.label', default: 'Start Date Time')}"/>
                                    <g:sortableColumn property="duration"
                                                      title="${message(code: 'batch.stepExecution.duration.label', default: 'Duration')}"/>
                                    <g:sortableColumn property="status"
                                                      title="${message(code: 'batch.stepExecution.status.label', default: 'Status')}"/>
                                    <g:sortableColumn property="reads"
                                                      title="${message(code: 'batch.stepExecution.reads.label', default: 'Reads')}"/>
                                    <g:sortableColumn property="writes"
                                                      title="${message(code: 'batch.stepExecution.writes.label', default: 'Writes')}"/>
                                    <g:sortableColumn property="skips"
                                                      title="${message(code: 'batch.stepExecution.skips.label', default: 'Skips')}"/>
                                    <g:sortableColumn property="exitCode"
                                                      title="${message(code: 'batch.stepExecution.exitStatus.exitCode.label', default: 'Exit Code')}"/>
                                    %{--<g:sortableColumn property="detail"
                                                      title="${message(code: 'batch.stepExecution.detail.label', default: 'Détail')}"/>--}%
                                </tr>
                                </thead>
                                <tbody>
                                <g:each in="${modelInstances}" status="i"
                                        var="stepExecutionModelInstance">
                                    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                                        <!-- td><g:link controller="batchStepExecution"
                                                        action="show"
                                                        id="${stepExecutionModelInstance.id}"
                                                        params="${[jobExecutionId: jobExecution.id]}">${fieldValue(bean: stepExecutionModelInstance, field: "id")}</g:link></td -->
                                        <td>
                                            <g:message code="batch.step.${stepExecutionModelInstance.name}"
                                                       default="${stepExecutionModelInstance.name}"/>
                                        </td>
                                        <td><g:formatDate type="datetime" style="SHORT"
                                                          timeStyle="MEDIUM"
                                                          date="${stepExecutionModelInstance.startDateTime}"/></td>
                                        <td><batch:durationPrint
                                                duration="${stepExecutionModelInstance?.duration}"/></td>
                                        <td>
                                            <g:if test="${fieldValue(bean: stepExecutionModelInstance, field: "status").toString().equals(ConstantesBatch.BATCH_JOB_INSTANCE_STARTED_STATUS)}">
                                                <span class="batch-result status-background-white">${fieldValue(bean: stepExecutionModelInstance, field: "status")}</span>
                                            </g:if>
                                            <g:elseif
                                                    test="${fieldValue(bean: stepExecutionModelInstance, field: "status").toString().equals(ConstantesBatch.BATCH_JOB_INSTANCE_COMPLETED_STATUS)}">
                                                <span class="batch-result status-background-green">${fieldValue(bean: stepExecutionModelInstance, field: "status")}</span>
                                            </g:elseif>
                                            <g:elseif
                                                    test="${fieldValue(bean: stepExecutionModelInstance, field: "status").toString().equals(ConstantesBatch.BATCH_JOB_INSTANCE_FAILED_STATUS)}">
                                                <span class="batch-result status-background-red">${fieldValue(bean: stepExecutionModelInstance, field: "status")}</span>
                                            </g:elseif>
                                            <g:else>
                                                <span class="batch-result status-background-orange">${fieldValue(bean: stepExecutionModelInstance, field: "status")}</span>
                                            </g:else>
                                        </td>
                                        <td>${fieldValue(bean: stepExecutionModelInstance, field: "reads")}</td>
                                        <td>${fieldValue(bean: stepExecutionModelInstance, field: "writes")}</td>
                                        <td>${fieldValue(bean: stepExecutionModelInstance, field: "skips")}</td>
                                        <td>
                                            <g:if test="${fieldValue(bean: stepExecutionModelInstance, field: "exitStatus.exitCode").toString().equals(ConstantesBatch.BATCH_JOB_INSTANCE_STARTED_STATUS)}">
                                                <span class="batch-result status-background-white">${fieldValue(bean: stepExecutionModelInstance, field: "exitStatus.exitCode")}</span>
                                            </g:if>
                                            <g:elseif
                                                    test="${fieldValue(bean: stepExecutionModelInstance, field: "exitStatus.exitCode").toString().equals(ConstantesBatch.BATCH_JOB_INSTANCE_COMPLETED_STATUS)}">
                                                <span class="batch-result status-background-green">${fieldValue(bean: stepExecutionModelInstance, field: "exitStatus.exitCode")}</span>
                                            </g:elseif>
                                            <g:elseif
                                                    test="${fieldValue(bean: stepExecutionModelInstance, field: "exitStatus.exitCode").toString().equals(ConstantesBatch.BATCH_JOB_INSTANCE_FAILED_STATUS)}">
                                                <span class="batch-result status-background-red">${fieldValue(bean: stepExecutionModelInstance, field: "exitStatus.exitCode")}</span>
                                            </g:elseif>
                                            <g:else>
                                                <span class="batch-result status-background-orange">${fieldValue(bean: stepExecutionModelInstance, field: "exitStatus.exitCode")}</span>
                                            </g:else>
                                        </td>
                                        %{--<td align="center">
                                            <g:if test="${fieldValue(bean: stepExecutionModelInstance, field: "exitStatus.exitCode").toString().equals(ConstantesBatch.BATCH_JOB_INSTANCE_COMPLETED_STATUS)}">
                                                <g:link controller="batchStepExecution"
                                                        action="show"
                                                        id="${stepExecutionModelInstance.id}"
                                                        params="${[jobExecutionId: jobExecution.id]}"><i
                                                        class="fa fa-eye fa-lg"
                                                        title="${message(code: 'batch.stepExecution.detail.label', default: 'Détail')}"></i></g:link>
                                            </g:if>
                                            <g:elseif
                                                    test="${fieldValue(bean: stepExecutionModelInstance, field: "exitStatus.exitCode").toString().equals('FAILED')}">
                                                <g:link controller="batchStepExecution"
                                                        action="show"
                                                        id="${stepExecutionModelInstance.id}"
                                                        params="${[jobExecutionId: jobExecution.id]}"><i
                                                        class="fa fa-eye fa-lg"
                                                        title="${message(code: 'batch.stepExecution.detail.label', default: 'Détail')}"></i></g:link>
                                            </g:elseif>
                                        </td>--}%
                                    </tr>
                                </g:each>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>