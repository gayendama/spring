<%@ page import="sn.sensoft.springbatch.utils.BatchList;" %>
<div id="controllers" role="navigation">
    <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
           aria-expanded="false">Installed Plugins <span class="caret"></span></a>
        <ul class="dropdown-menu">
            <g:each var="plugin" in="${applicationContext.getBean('pluginManager').allPlugins}">
                <li><a href="#">${plugin.name} - ${plugin.version}</a></li>
            </g:each>
        </ul>
    </li>

    <h2>Available Controllers:</h2>
    <ul>
        <g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName }}">
            <li class="controller">
                <g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link>
            </li>
        </g:each>
    </ul>
</div>

<!--[if IE 8]> <html lang="fr" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="fr" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="fr">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8"/>
    <g:set var="entityName" value="${message(code: 'batchJob.label', default: 'Liste des instances')}"/>
    <title><g:message code="batchJob.label"/></title>
    <asset:stylesheet src="srpingBatchRefont.css"/>

    <meta name="layout" content="main"/>
    <meta http-equiv="refresh" content="20">

</head>
<!-- END HEAD -->

<body class="page-container-bg-solid page-header-menu-fixed">
<div class="page-wrapper">
    <div class="page-wrapper-row">
        <div class="page-wrapper-top">
            <!-- BEGIN HEADER -->
            <div class="page-header">
                <!-- BEGIN HEADER TOP -->
                <g:render template="/layouts/header_top"/>
                <!-- END HEADER TOP -->
                <!-- BEGIN HEADER MENU -->
                <g:render template="/layouts/header_menu"/>
                <!-- END HEADER MENU -->
            </div>
            <!-- END HEADER -->
        </div>
    </div>

    <div class="page-wrapper-row full-height">
        <div class="page-wrapper-middle">
            <!-- BEGIN CONTAINER -->
            <div class="page-container">
                <!-- BEGIN CONTENT -->
                <div class="page-content-wrapper">
                    <!-- BEGIN CONTENT BODY -->
                    <div class="page-content">
                        <div class="container">
                            <!-- BEGIN PAGE BREADCRUMBS -->
                            <div style="display: block; padding-bottom: 5px;">
%{--                                <g:set var="securityUtils" bean="securityUtils"/>--}%

                                <ul class="page-breadcrumb breadcrumb">
                                    <li>
                                        <g:link controller="batch" action="list"><i
                                                class="icon-home"></i> <g:message
                                                code="default.home.label" default="Accueil"/></g:link>
                                        <i class="fa fa-circle"></i>
                                    </li>
                                    <li>
                                        <span class="glyphicon glyphicon-list-alt "></span>
                                        <g:message code="default.list.label" args="[entityName]"/>
                                    </li>
                                </ul>
                            </div>
                            <!-- END PAGE BREADCRUMBS -->
                            <!-- BEGIN PAGE CONTENT INNER -->
                            <div class="page-content-inner">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="portlet light portlet-fit portlet-datatable ">

                                            <g:render template="/layouts/flashMessage"
                                                      model="[objetInstance: objetInstance]"/>

                                            <div class="portlet-title">
                                                <div class="caption font-green">
                                                    <span class="glyphicon glyphicon-list-alt "></span>
                                                    <span class="caption-subject bold uppercase">
                                                        <g:message code="batch.job.show.label" args="[job.name]"/>
                                                    </span>
                                                </div>

                                                <div class="actions">
                                                    <div class="btn-group btn-group-devided">
                                                        <g:link action="list" class="btn blue-hoki"><i
                                                                class="fa fa-list"></i> Job List</g:link>
                                                    &nbsp;&nbsp;
                                                        <g:if test="${job.launchable}">
                                                            <g:if test="${job.name.equals(BatchList.BatchCalculSoldeArrete.id)}">
                                                                <g:link controller="traitementBatch"
                                                                        action="calculerSoldeArrete"
                                                                        class="btn green">
                                                                    <i class="fa fa-toggle-on"></i>
                                                                    <g:message code="batch.job.launch.label"/>
                                                                </g:link>
                                                            </g:if>
                                                            <g:elseif test="${job.name.equals(BatchList.BatchCalculCreancesRattachees.id)}">
                                                                <g:link controller="traitementBatch"
                                                                        action="calculCreancesRattachees"
                                                                        class="btn green">
                                                                    <i class="fa fa-toggle-on"></i>
                                                                    <g:message code="batch.job.launch.label"/>
                                                                </g:link>
                                                            </g:elseif>
                                                            <g:elseif test="${job.name.equals(BatchList.BatchApurementCompteGestion.id)}">
                                                                <g:link controller="traitementBatch"
                                                                        action="apurementCompteGestion"
                                                                        class="btn green">
                                                                    <i class="fa fa-toggle-on"></i>
                                                                    <g:message code="batch.job.launch.label"/>
                                                                </g:link>
                                                            </g:elseif>
                                                            <g:elseif test="${job.name.equals(BatchList.BatchCalculPosteBudgetaire.id)}">
                                                                <g:link controller="traitementBatch"
                                                                        action="calculPosteBudgetaire"
                                                                        class="btn green">
                                                                    <i class="fa fa-toggle-on"></i>
                                                                    <g:message code="batch.job.launch.label"/>
                                                                </g:link>
                                                            </g:elseif>
                                                            <g:elseif test="${job.name.equals(BatchList.BatchCalculProvisionsPrets.id)}">
                                                                <g:link controller="traitementBatch"
                                                                        action="calculProvisionsPrets"
                                                                        class="btn green">
                                                                    <i class="fa fa-toggle-on"></i>
                                                                    <g:message code="batch.job.launch.label"/>
                                                                </g:link>
                                                            </g:elseif>
                                                            <g:elseif test="${job.name.equals(BatchList.BatchGenerationEcritureProvision.id)}">
                                                                <g:link controller="traitementBatch"
                                                                        action="generationEcritureProvision"
                                                                        class="btn green">
                                                                    <i class="fa fa-toggle-on"></i>
                                                                    <g:message code="batch.job.launch.label"/>
                                                                </g:link>
                                                            </g:elseif>
                                                            <g:elseif test="${job.name.equals(BatchList.BatchCalculInteretCompteBloque.id)}">
                                                                <g:link controller="interet"
                                                                        action="calculInteretCompteBloque"
                                                                        class="btn green">
                                                                    <i class="fa fa-toggle-on"></i>
                                                                    <g:message code="batch.job.launch.label"/>
                                                                </g:link>
                                                            </g:elseif>
                                                            <g:elseif test="${job.name.equals(BatchList.BatchImputationInteret.id)}">
                                                                <g:link controller="interet"
                                                                        action="imputationInteret"
                                                                        class="btn green">
                                                                    <i class="fa fa-toggle-on"></i>
                                                                    <g:message code="batch.job.launch.label"/>
                                                                </g:link>
                                                            </g:elseif>
                                                            <g:elseif test="${job.name.equals(BatchList.BatchComptabilisationImmo.id)}">
                                                                <g:link controller="traitementBatch"
                                                                        action="comptaImmo"
                                                                        class="btn green">
                                                                    <i class="fa fa-toggle-on"></i>
                                                                    <g:message code="batch.job.launch.label"/>
                                                                </g:link>
                                                            </g:elseif>
                                                            <g:elseif test="${job.name.equals(BatchList.BatchCalculImmobilisations.id)}">
                                                                <g:link controller="traitementBatch"
                                                                        action="calculImmo"
                                                                        class="btn green">
                                                                    <i class="fa fa-toggle-on"></i>
                                                                    <g:message code="batch.job.launch.label"/>
                                                                </g:link>
                                                            </g:elseif>
                                                            <g:else>
                                                                <g:link action="launch" id="${job.name}"
                                                                        class="btn green">
                                                                    <i class="fa fa-toggle-on"></i>
                                                                    <g:message code="batch.job.launch.label"/></g:link>
                                                            </g:else>
                                                        </g:if>

                                                        <g:link action="stopAllExecutions" id="${job.name}"
                                                                class="btn red"><i
                                                                class="fa fa-toggle-off"></i> <g:message
                                                                code="batch.job.stopall.label"/></g:link>

                                                    </div>
                                                </div>
                                            </div>

                                            <div class="report" style="margin-left: 10px;">
                                                <div class="panel panel-default report-content">
                                                    <div class="panel-heading"><g:message
                                                            code="batch.job.jobInstanceCount.label"/></div>

                                                    <div class="panel-body">${job.jobInstanceCount}</div>
                                                </div>

                                                <div class="panel panel-default report-content">
                                                    <div class="panel-heading"><g:message
                                                            code="batch.job.executionCount.label"/></div>

                                                    <div class="panel-body">${job.executionCount}</div>
                                                </div>

                                                <div class="panel panel-default report-content">
                                                    <div class="panel-heading"><g:message
                                                            code="batch.job.incrementable.label"/></div>

                                                    <div class="panel-body">${job.incrementable}</div>
                                                </div>

                                                <div class="panel panel-default report-content">
                                                    <div class="panel-heading"><g:message
                                                            code="batch.job.currentlyRunning.label"/></div>

                                                    <div class="panel-body">${job.currentlyRunning}</div>
                                                </div>
                                            <!-- div class="panel panel-default report-content">
													<div class="panel-heading"><g:message code="batch.job.steps.label"/></div>
													<div class="panel-body">${job.stepNames.join(', ')}</div>
												</div -->
                                                <g:if test="${job.mostRecentJobExecution}">
                                                    <div class="panel panel-default report-content">
                                                        <div class="panel-heading">
                                                            <span id="mostRecentExecution-label"
                                                                  class="property-label"><g:message
                                                                    code="batch.job.mostRecentJobExecution.label"/></span>
                                                        </div>

                                                        <div class="panel-body">
                                                            <g:link controller="batchJobExecution" action="show"
                                                                    id="${job.mostRecentJobExecution.id}">
                                                                <span class="batch-result status-background-grey">
                                                                    <g:message
                                                                            code="batch.jobExecution.startDateTime.label"/>: <g:formatDate
                                                                            type="datetime" style="LONG"
                                                                            timeStyle="MEDIUM"
                                                                            date="${job.mostRecentJobExecution.startDateTime}"/>
                                                                </span>

                                                                <span class="batch-result status-background-grey">
                                                                    <g:message
                                                                            code="batch.jobExecution.duration.label"/>: <batch:durationPrint
                                                                            duration="${job.mostRecentJobExecution.duration}"/>
                                                                </span>

                                                                <g:if test="${job.mostRecentJobExecution.status.toString().equals('STARTED')}">
                                                                    <span class="batch-result status-background-white"><g:message
                                                                            code="batch.jobExecution.status.label"/>: ${job.mostRecentJobExecution.status}</span>
                                                                </g:if>
                                                                <g:elseif
                                                                        test="${job.mostRecentJobExecution.status.toString().equals('COMPLETED')}">
                                                                    <span class="batch-result status-background-green"><g:message
                                                                            code="batch.jobExecution.status.label"/>: ${job.mostRecentJobExecution.status}</span>
                                                                </g:elseif>
                                                                <g:elseif
                                                                        test="${job.mostRecentJobExecution.status.toString().equals('FAILED')}">
                                                                    <span class="batch-result status-background-red"><g:message
                                                                            code="batch.jobExecution.status.label"/>: ${job.mostRecentJobExecution.status}</span>
                                                                </g:elseif>
                                                                <g:else>
                                                                    <span class="batch-result status-background-orange"><g:message
                                                                            code="batch.jobExecution.status.label"/>: ${job.mostRecentJobExecution.status}</span>
                                                                </g:else>

                                                                <g:if test="${job.mostRecentJobExecution.exitStatus.exitCode.toString().equals('STARTED')}">
                                                                    <span class="batch-result status-background-white"><g:message
                                                                            code="batch.jobExecution.exitStatus.label"/>: ${job.mostRecentJobExecution.exitStatus.exitCode}</span>
                                                                </g:if>
                                                                <g:elseif
                                                                        test="${job.mostRecentJobExecution.exitStatus.exitCode.toString().equals('COMPLETED')}">
                                                                    <span class="batch-result status-background-green"><g:message
                                                                            code="batch.jobExecution.exitStatus.label"/>: ${job.mostRecentJobExecution.exitStatus.exitCode}</span>
                                                                </g:elseif>
                                                                <g:elseif
                                                                        test="${job.mostRecentJobExecution.exitStatus.exitCode.toString().equals('FAILED')}">
                                                                    <span class="batch-result status-background-red"><g:message
                                                                            code="batch.jobExecution.exitStatus.label"/>: ${job.mostRecentJobExecution.exitStatus.exitCode}</span>
                                                                </g:elseif>
                                                                <g:else>
                                                                    <span class="batch-result status-background-orange"><g:message
                                                                            code="batch.jobExecution.exitStatus.label"/>: ${job.mostRecentJobExecution.exitStatus.exitCode}</span>
                                                                </g:else>
                                                            </g:link>
                                                        </div>
                                                    </div>
                                                </g:if>

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
                                                            <g:message code="batch.job.instances.label"/>
                                                        </div>

                                                        <table class="table table-striped table-bordered table-hover dt-responsive"
                                                               id="sample_2">
                                                            <thead>
                                                            <tr>
                                                                <g:sortableColumn property="id"
                                                                                  title="${message(code: 'batch.jobInstance.id.label')}"/>
                                                                <g:sortableColumn property="jobExecutionCount"
                                                                                  title="${message(code: 'batch.jobInstance.jobExecutionCount.label')}"/>
                                                                <g:sortableColumn property="lastJobExecutionStatus"
                                                                                  title="${message(code: 'batch.jobInstance.lastJobExecutionStatus.label')}"/>
                                                                <td>DATE</td>
                                                                <td>Action</td>
                                                            </tr>
                                                            </thead>
                                                            <tbody>
                                                            <g:each in="${jobModelInstances}" status="i"
                                                                    var="jobInstanceModelInstance">
                                                                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                                                                    <td><g:link controller="batchJobInstance"
                                                                                action="show"
                                                                                id="${jobInstanceModelInstance.id}"
                                                                                params="[jobName: job.name]">${fieldValue(bean: jobInstanceModelInstance, field: "id")}</g:link></td>
                                                                    <td>${fieldValue(bean: jobInstanceModelInstance, field: "jobExecutionCount")}</td>
                                                                    <td>
                                                                        <g:if test="${fieldValue(bean: jobInstanceModelInstance, field: "lastJobExecutionStatus").toString().equals('STARTED')}">
                                                                            <span class="batch-result status-background-white">${fieldValue(bean: jobInstanceModelInstance, field: "lastJobExecutionStatus")}</span>
                                                                        </g:if>
                                                                        <g:elseif
                                                                                test="${fieldValue(bean: jobInstanceModelInstance, field: "lastJobExecutionStatus").toString().equals('COMPLETED')}">
                                                                            <span class="batch-result status-background-green">${fieldValue(bean: jobInstanceModelInstance, field: "lastJobExecutionStatus")}</span>
                                                                        </g:elseif>
                                                                        <g:elseif
                                                                                test="${fieldValue(bean: jobInstanceModelInstance, field: "lastJobExecutionStatus").toString().equals('FAILED')}">
                                                                            <span class="batch-result status-background-red">${fieldValue(bean: jobInstanceModelInstance, field: "lastJobExecutionStatus")}</span>
                                                                        </g:elseif>
                                                                        <g:else>
                                                                            <span class="batch-result status-background-orange">${fieldValue(bean: jobInstanceModelInstance, field: "lastJobExecutionStatus")}</span>
                                                                        </g:else>
                                                                    </td>
                                                                    <td>
                                                                        ${jobInstanceModelInstance.jobParameters.date}
                                                                    </td>
                                                                    <td>
                                                                        <g:link controller="batchJobInstance"
                                                                                action="show"
                                                                                id="${jobInstanceModelInstance.id}"
                                                                                params="[jobName: job.name]">
                                                                            <i class="fa fa-eye fa-lg"></i>
                                                                        </g:link>
                                                                    </td>
                                                                </tr>
                                                            </g:each>
                                                            </tbody>
                                                        </table>

                                                        <div class="pagination">
                                                            <g:paginate total="${jobModelInstances.resultsTotalCount}"
                                                                        id="${job.name}"/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- END PAGE CONTENT INNER -->
                        </div>
                    </div>



                    <!-- END CONTENT BODY -->
                </div>
                <!-- END CONTENT -->
            </div>
            <!-- END CONTAINER -->
        </div>
    </div>





    <!-- BEGIN INNER FOOTER -->
    <g:render template="/layouts/footer"/>
    <!-- END INNER FOOTER -->
</div>


<div class="quick-nav-overlay"></div>
<!-- END QUICK NAV -->

<asset:javascript src="metronic_v4_7_1_admin_3_rounded.js"/>

</body>

</html>