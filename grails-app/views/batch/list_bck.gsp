<%@ page import="sn.sensoft.springbatch.utils.Constantes; sn.sensoft.springbatch.utils.DateUtils" %>
<%@ page import="sn.sensoft.springbatch.utils.ConstantesBatch" %>
<!--[if IE 8]> <html lang="fr" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="fr" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="fr">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8"/>
    <g:set var="entityName" value="${message(code: 'batchJobs.label', default: 'Batchs')}"/>
    <title><g:message code="batchJobs.label"/></title>
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
                                <g:set var="securityUtils" bean="securityUtils"/>

                                <ul class="page-breadcrumb breadcrumb">
                                    <li>
                                        <g:link controller="dashboard" action="index"><i
                                                class="icon-home"></i> <g:message
                                                code="default.home.label" default="Accueil"/></g:link>
                                        <i class="fa fa-circle"></i>
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
                                                    <span class="caption-subject bold uppercase">JOB LIST</span>
                                                </div>

                                                <div class="actions">
                                                    <div class="btn-group btn-group-devided">
                                                        <g:if test="${ready}">
                                                            <g:link controller="batch" action="disableLaunching"
                                                                    class="btn red">
                                                                <i class="fa fa-toggle-off"></i>&nbsp;<b><g:message
                                                                    code="batch.disableLaunching"/></b></g:link></g:if>
                                                        <g:else>
                                                            <g:link controller="batch" action="enableLaunching"
                                                                    class="btn green">
                                                                <i class="fa fa-toggle-on"></i>&nbsp;<b><g:message
                                                                    code="batch.enableLaunching"/></b></g:link></g:else>

                                                    </div>

                                                    <div class="btn-group">

                                                    &nbsp;&nbsp;&nbsp;<g:link action="stopAllExecutions"
                                                                              class="btn blue-hoki">
                                                        <i class="fa fa-power-off"></i>&nbsp;<b><g:message
                                                                code="batch.job.stopall.label"/></b>
                                                    </g:link>

                                                    </div>
                                                </div>
                                            </div>

                                            <div class="report" style="margin-left: 10px;">
                                                <div class="panel panel-default report-content">
                                                    <div class="panel-heading">Date Comptable</div>
                                                    <div class="panel-body">
                                                        <g:formatDate format="dd/MM/yyyy" date="${DateUtils.dateTraitement(Constantes.DATE_TRAITEMENT_JOUR)}"/>
                                                    </div>
                                                </div>

                                                <div class="panel panel-default report-content">
                                                    <div class="panel-heading">Situation Agence</div>
                                                    <div class="panel-body">
                                                            <span class="batch-result status-background-green">Fermée</span>
                                                    </div>
                                                </div>

                                                <div class="panel panel-default report-content">
                                                    <div class="panel-heading">Nombre de Caisses Ouverttes</div>
                                                    <div class="panel-body">
                                                        ${nombreDeCaissesOuvertes}
                                                    </div>
                                                </div>

                                                <div class="panel panel-default report-content">
                                                    <div class="panel-heading">Batch en Cours</div>
                                                    <div class="panel-body">
                                                        <g:if test="${indcateurBatchEncour}">
                                                            <span class="batch-result status-background-orange">Oui</span>
                                                        </g:if>
                                                        <g:else>
                                                            <span class="batch-result status-background-green">Non</span>
                                                        </g:else>
                                                    </div>
                                                </div>

                                            </div>

                                            <div class="portlet-body">
                                                <div class="table-container">
                                                    <table class="table table-striped table-bordered table-hover dt-responsive"
                                                           id="sample_2">
                                                        <thead>
                                                        <tr>
                                                            <g:sortableColumn property="name"
                                                                              title="${message(code: 'jobModel.name.label', default: 'Name')}"/>
                                                            <g:sortableColumn property="currentlyRunning"
                                                                              title="${message(code: 'batch.job.currentlyRunning.label', default: 'Running')}"/>
                                                            <g:sortableColumn property="executionCount"
                                                                              title="${message(code: 'jobModel.executionCount.label', default: 'Execution Count')}"/>
                                                            <td>Most Recent Execution</td>
                                                            <td align="center">Action</td>
                                                            <!-- g:sortableColumn align="center" style="text-align: center" property="launchable" title="${message(code: 'jobModel.launchable.label', default: 'Is Launchable')}" / -->
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        <g:each in="${modelInstances}" status="i" var="modelInstance">
                                                            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                                                                <td>
                                                                    <g:link controller="batch" action="show"
                                                                            id="${modelInstance.name}">${fieldValue(bean: modelInstance, field: "name")}</g:link>
                                                                </td>
                                                                <td>
                                                                    <g:if test="${fieldValue(bean: modelInstance, field: "currentlyRunning") == 'false'}">
                                                                        <span class="batch-result status-background-orange">
                                                                            ${fieldValue(bean: modelInstance, field: "currentlyRunning")}
                                                                        </span>
                                                                    </g:if>
                                                                    <g:else>
                                                                        <span class="batch-result status-background-green">
                                                                            ${fieldValue(bean: modelInstance, field: "currentlyRunning")}
                                                                        </span>
                                                                    </g:else>

                                                                </td>
                                                                <td>${fieldValue(bean: modelInstance, field: "executionCount")}</td>
                                                                <td>
                                                                    <g:if test="${modelInstance.mostRecentJobExecution}">
                                                                        <g:link controller="batchJobExecution"
                                                                                action="show"
                                                                                id="${modelInstance.mostRecentJobExecution.id}">
                                                                            <span class="batch-result status-background-grey">
                                                                                <g:formatDate type="datetime"
                                                                                              style="LONG"
                                                                                              timeStyle="MEDIUM"
                                                                                              date="${modelInstance.mostRecentJobExecution.startDateTime}"/>
                                                                            </span>

                                                                            <g:if test="${modelInstance.mostRecentJobExecution.exitStatus.exitCode.toString().equals(ConstantesBatch.BATCH_JOB_INSTANCE_STARTED_STATUS)}">
                                                                                <span class="batch-result status-background-white">Exit Status: ${modelInstance.mostRecentJobExecution.exitStatus.exitCode}</span>
                                                                            </g:if>
                                                                            <g:elseif
                                                                                    test="${modelInstance.mostRecentJobExecution.exitStatus.exitCode.toString().equals(ConstantesBatch.BATCH_JOB_INSTANCE_COMPLETED_STATUS)}">
                                                                                <span class="batch-result status-background-green">Exit Status: ${modelInstance.mostRecentJobExecution.exitStatus.exitCode}</span>
                                                                            </g:elseif>
                                                                            <g:elseif
                                                                                    test="${modelInstance.mostRecentJobExecution.exitStatus.exitCode.toString().equals(ConstantesBatch.BATCH_JOB_INSTANCE_FAILED_STATUS)}">
                                                                                <span class="batch-result status-background-red">Exit Status: ${modelInstance.mostRecentJobExecution.exitStatus.exitCode}</span>
                                                                            </g:elseif>
                                                                            <g:elseif
                                                                                    test="${modelInstance.mostRecentJobExecution.status.toString().equals(ConstantesBatch.BATCH_JOB_INSTANCE_STOPPING_STATUS)}">
                                                                                <span class="batch-result status-background-grey">
                                                                                    <i class="fa fa-spinner fa-spin fa-lg load-step"></i> ${modelInstance.mostRecentJobExecution.status.toString()}
                                                                                </span>
                                                                            </g:elseif>
                                                                            <g:elseif
                                                                                    test="${modelInstance.mostRecentJobExecution.status.toString().equals(ConstantesBatch.BATCH_JOB_INSTANCE_STARTED_STATUS)}">
                                                                                <span class="batch-result status-background-white">
                                                                                    <i class="fa fa-spinner fa-spin fa-lg load-step"></i> ${modelInstance.mostRecentJobExecution.status.toString()}
                                                                                </span>
                                                                            </g:elseif>
                                                                            <g:else>
                                                                                <span class="batch-result status-background-orange">Exit Status: ${modelInstance.mostRecentJobExecution.exitStatus.exitCode}</span>
                                                                            </g:else>
                                                                        </g:link>
                                                                    </g:if>
                                                                </td>
                                                                <td align="center">
                                                                    <g:if test="${modelInstance.mostRecentJobExecution}">
                                                                        <g:link controller="batchJobExecution"
                                                                                action="show"
                                                                                id="${modelInstance.mostRecentJobExecution.id}" class="action">
                                                                            <i class="fa fa-eye fa-lg"
                                                                               title="Most recent execution"></i>
                                                                        </g:link>
                                                                    </g:if>

                                                                    <g:link controller="batch" action="show"
                                                                            id="${modelInstance.name}" class="action">
                                                                        <i class="fa fa-bars fa-lg"
                                                                           title="Job report"></i>
                                                                    </g:link>

                                                                    <g:link controller="batch" action="showStepSettings"
                                                                            class="action">
                                                                        <i class="fa fa-cog fa-lg" title="Settings"></i>
                                                                    </g:link>

                                                                    <g:if test="${modelInstance.mostRecentJobExecution.exitStatus.exitCode == ConstantesBatch.BATCH_JOB_INSTANCE_STOPED_STATUS ||
                                                                            (modelInstance.mostRecentJobExecution && modelInstance.mostRecentJobExecution.exitStatus.exitCode.toString().equals(ConstantesBatch.BATCH_JOB_INSTANCE_FAILED_STATUS))}">
                                                                        <g:link controller="batchJobExecution"
                                                                                action="restart"
                                                                                id="${modelInstance.mostRecentJobExecution.id}" class="action">
                                                                            <i class="fa fa-refresh fa-lg"
                                                                               title="Restart"></i>
                                                                        </g:link>
                                                                    </g:if>
                                                                    <g:elseif test="${modelInstance.launchable && ready && !indcateurBatchEncour}">
                                                                        <g:link action="launch"
                                                                                id="${modelInstance.name}"
                                                                                class="launchJobButton"
                                                                                params="[a: 'l']">
                                                                            <!-- g:message code="batch.job.launch.label"/ -->
                                                                            <i class="fa fa-play fa-lg"
                                                                               aria-hidden="true"
                                                                               title="Launch Job"></i>
                                                                        </g:link>
                                                                    </g:elseif>
                                                                </td>
                                                            </tr>
                                                        </g:each>
                                                        </tbody>
                                                    </table>

                                                    <div class="pagination">
                                                        <g:paginate total="${modelInstances.resultsTotalCount}"/>
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