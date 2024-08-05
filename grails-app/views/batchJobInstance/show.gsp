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
    <g:set var="entityName" value="${message(code: 'instance.label', default: 'Job execution')}"/>
    <title><g:message code="batchJob.label"/></title>
    <asset:stylesheet src="srpingBatchRefont.css"/>
    <meta name="layout" content="main"/>

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
                                                        <g:message code="batch.jobInstance.show.label"
                                                                   args="[jobInstance.jobName, jobInstance.id]"/>
                                                    </span>
                                                </div>

                                                <div class="actions">
                                                    <div class="btn-group btn-group-devided">
                                                        <g:link controller="batch" action="list"
                                                                class="btn blue-hoki"><i
                                                                class="fa fa-list"></i> Job List</g:link>&nbsp;
                                                        <g:link controller="batch" action="show"
                                                                id="${jobInstance.jobName}" class="btn blue-sharp"><i
                                                                class="fa fa-angle-double-left"></i> <g:message
                                                                code="batch.jobInstance.backToJob.label"/></g:link>&nbsp;&nbsp;

                                                    </div>
                                                </div>
                                            </div>

                                            <div class="report" style="margin-left: 10px;">
                                                <div class="panel panel-default report-content">
                                                    <div class="panel-heading"><g:message
                                                            code="batch.jobInstance.jobExecutionCount.label"/></div>

                                                    <div class="panel-body">${jobInstance.jobExecutionCount}</div>
                                                </div>

                                                <div class="panel panel-default report-content">
                                                    <div class="panel-heading"><g:message
                                                            code="batch.jobInstance.lastJobExecutionStatus.label"/></div>

                                                    <div class="panel-body">
                                                        <g:if test="${jobInstance.lastJobExecutionStatus.toString().equals('STARTED')}">
                                                            <span class="batch-result status-background-white">${jobInstance.lastJobExecutionStatus}</span>
                                                        </g:if>
                                                        <g:elseif
                                                                test="${jobInstance.lastJobExecutionStatus.toString().equals('COMPLETED')}">
                                                            <span class="batch-result status-background-green">${jobInstance.lastJobExecutionStatus}</span>
                                                        </g:elseif>
                                                        <g:elseif
                                                                test="${jobInstance.lastJobExecutionStatus.toString().equals('FAILED')}">
                                                            <span class="batch-result status-background-red">${jobInstance.lastJobExecutionStatus}</span>
                                                        </g:elseif>
                                                        <g:else>
                                                            <span class="batch-result status-background-orange">${jobInstance.lastJobExecutionStatus}</span>
                                                        </g:else>
                                                    </div>
                                                </div>

                                                <div class="panel panel-default report-content">
                                                    <div class="panel-heading"><g:message
                                                            code="batch.jobInstance.jobParameters.label"/></div>

                                                    <div class="panel-body">${jobInstance.jobParameters}</div>
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

                                                    <h2><g:message code="batch.jobInstance.executions.label"/></h2>

                                                    <table class="table table-striped table-bordered table-hover dt-responsive"
                                                           id="sample_2">
                                                        <thead>
                                                        <tr>
                                                            <g:sortableColumn property="id"
                                                                              title="${message(code: 'batch.jobExecution.id.label', default: 'Id')}"/>
                                                            <g:sortableColumn property="startDateTime"
                                                                              title="${message(code: 'batch.jobExecution.startDateTime.label', default: 'Start Date Time')}"/>
                                                            <g:sortableColumn property="duration"
                                                                              title="${message(code: 'batch.jobExecution.duration.label', default: 'Duration')}"/>
                                                            <g:sortableColumn property="status"
                                                                              title="${message(code: 'batch.jobExecution.status.label', default: 'Status')}"/>
                                                            <g:sortableColumn property="exitCode"
                                                                              title="${message(code: 'batch.jobExecution.exitStatus.exitCode.label', default: 'Exit Code')}"/>
                                                            <td>Action</td>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        <g:each in="${modelInstances}" status="i"
                                                                var="jobExecutionModelInstance">
                                                            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                                                                <td><g:link controller="batchJobExecution" action="show"
                                                                            id="${jobExecutionModelInstance.id}">${fieldValue(bean: jobExecutionModelInstance, field: "id")}</g:link></td>
                                                                <td><g:formatDate type="datetime" style="LONG"
                                                                                  timeStyle="MEDIUM"
                                                                                  date="${jobExecutionModelInstance.startDateTime}"/></td>
                                                                <td><batch:durationPrint
                                                                        duration="${jobExecutionModelInstance.duration}"/></td>
                                                                <td>
                                                                    <g:if test="${fieldValue(bean: jobExecutionModelInstance, field: "status").toString().equals('STARTED')}">
                                                                        <span class="batch-result status-background-white">${fieldValue(bean: jobExecutionModelInstance, field: "status")}</span>
                                                                    </g:if>
                                                                    <g:elseif
                                                                            test="${fieldValue(bean: jobExecutionModelInstance, field: "status").toString().equals('COMPLETED')}">
                                                                        <span class="batch-result status-background-green">${fieldValue(bean: jobExecutionModelInstance, field: "status")}</span>
                                                                    </g:elseif>
                                                                    <g:elseif
                                                                            test="${fieldValue(bean: jobExecutionModelInstance, field: "status").toString().equals('FAILED')}">
                                                                        <span class="batch-result status-background-red">${fieldValue(bean: jobExecutionModelInstance, field: "status")}</span>
                                                                    </g:elseif>
                                                                    <g:else test="${fieldValue(bean: jobExecutionModelInstance, field: "status").toString().equals('UNKNOWN')}">
                                                                        <span class="batch-result status-background-orange">${fieldValue(bean: jobExecutionModelInstance, field: "status")}</span>
                                                                    </g:else>
                                                                </td>
                                                                <td>
                                                                    <g:if test="${fieldValue(bean: jobExecutionModelInstance, field: "exitStatus.exitCode").toString().equals('STARTED')}">
                                                                        <span class="batch-result status-background-white">${fieldValue(bean: jobExecutionModelInstance, field: "exitStatus.exitCode")}</span>
                                                                    </g:if>
                                                                    <g:elseif
                                                                            test="${fieldValue(bean: jobExecutionModelInstance, field: "exitStatus.exitCode").toString().equals('COMPLETED')}">
                                                                        <span class="batch-result status-background-green">${fieldValue(bean: jobExecutionModelInstance, field: "exitStatus.exitCode")}</span>
                                                                    </g:elseif>
                                                                    <g:elseif
                                                                            test="${fieldValue(bean: jobExecutionModelInstance, field: "exitStatus.exitCode").toString().equals('FAILED')}">
                                                                        <span class="batch-result status-background-red">${fieldValue(bean: jobExecutionModelInstance, field: "exitStatus.exitCode")}</span>
                                                                    </g:elseif>
                                                                    <g:else>
                                                                        <span class="batch-result status-background-orange">${fieldValue(bean: jobExecutionModelInstance, field: "exitStatus.exitCode")}</span>
                                                                    </g:else>
                                                                </td>
                                                                <td>
                                                                    <g:link controller="batchJobExecution" action="show"
                                                                            id="${jobExecutionModelInstance.id}">
                                                                        <i class="fa fa-eye fa-lg"></i>
                                                                    </g:link>
                                                                </td>
                                                            </tr>
                                                        </g:each>
                                                        </tbody>
                                                    </table>

                                                    <div class="pagination">
                                                        <g:paginate total="${modelInstances.resultsTotalCount}"
                                                                    id="${jobInstance.id}" params="[jobName: jobName]"/>
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