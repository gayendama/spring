<%@ page import="org.springframework.batch.core.BatchStatus; sn.sensoft.springbatch.utils.Constantes; org.springframework.batch.core.ExitStatus; sn.sensoft.springbatch.utils.ConstantesBatch" %>
<div id="controllers" role="navigation">
    <li class="dropdown">Status
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
    <g:set var="entityName" value="${message(code: 'step.execution.label', default: 'Step execution')}"/>
    <title><g:message code="batchJob.label"/></title>
    <asset:stylesheet src="srpingBatchRefont.css"/>

    <meta name="layout" content="main"/>

    <meta http-equiv="refresh" content="15">

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
                                                        <g:message code="batch.jobExecution.show.label"
                                                                   args="[jobExecution.jobName, jobExecution.instanceId, jobExecution.id]"/>
                                                    </span>
                                                </div>

                                                <div class="actions">
                                                    <div class="btn-group btn-group-devided">
                                                        <g:link controller="batch" action="list"
                                                                class="btn blue-hoki"><i
                                                                class="fa fa-list"></i> Job List</g:link>
                                                        <g:link controller="batchJobInstance" action="show"
                                                                id="${jobExecution.instanceId}"
                                                                params="[jobName: jobExecution.jobName]"
                                                                class="btn blue-sharp">
                                                            <i class="fa fa-angle-double-left"></i> <g:message
                                                                code="batch.jobExecution.backToJobInstance.label2" default="Job Instance"/></g:link>&nbsp;&nbsp;

                                                        <g:if test="${jobExecution.status == BatchStatus.FAILED}">
                                                            <g:link action="restart" id="${jobExecution.id}"
                                                                    class="btn green">
                                                                <i class="fa fa-toggle-on"></i> <g:message
                                                                    code="batch.jobExecution.restart.label"/></g:link>&nbsp;&nbsp;
                                                        </g:if>
                                                        <g:elseif test="${jobExecution.status != BatchStatus.COMPLETED && batchListWithOptions.contains(jobExecution.jobName)}">
                                                            <g:link action="#"
                                                                    id="${jobExecution.id}"
                                                                    class="btn green dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" >
                                                                <i class="fa fa-angle-down"></i>
                                                                <g:message code="batch.jobExecution.restart.label" default="Restart"/>
                                                            </g:link>&nbsp;&nbsp;
                                                            <div class="dropdown-menu" style="width: 250px; padding: 5px;">
                                                                <a data-toggle="modal" href="#force_to_completed">
                                                                    Forcer le status du batch à complét
                                                                </a><br>
                                                                <div style="border-bottom: 1px solid #cccccc; margin-top: 5px; margin-bottom: 5px;"></div>
                                                                <a data-toggle="modal" href="#force_to_launch">
                                                                    Forcer le lancement d'une nouvelle instance dans la même journée
                                                                </a><br>
                                                                <div style="border-bottom: 1px solid #cccccc; margin-top: 5px; margin-bottom: 5px;"></div>
                                                                <a data-toggle="modal" href="#force_to_restart">
                                                                    Forcer le lancement d'une nouvelle instance en commençant par la dernière séquence en cours
                                                                </a><br>
                                                            </div>
                                                        </g:elseif>

                                                        <g:if test="${jobExecution.status == BatchStatus.STARTED}">
                                                            <g:link action="stop" id="${jobExecution.id}" params="[instanceId: jobExecution.instanceId]"
                                                                    class="btn blue-hoki">
                                                                <i class="fa fa-toggle-off"></i> <g:message
                                                                    code="batch.jobExecution.stop.label"/></g:link>&nbsp;&nbsp;
                                                        </g:if>

                                                    </div>
                                                </div>
                                            </div>

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
                                                        <g:elseif test="${jobExecution.status.toString().equals(ConstantesBatch.BATCH_JOB_INSTANCE_COMPLETED_STATUS)}">
                                                            <span class="batch-result status-background-green">${jobExecution.status}</span>
                                                        </g:elseif>
                                                        <g:elseif test="${jobExecution.status.toString().equals(ConstantesBatch.BATCH_JOB_INSTANCE_FAILED_STATUS)}">
                                                            <span class="batch-result status-background-red">${jobExecution.status}</span>
                                                        </g:elseif>
                                                        <g:elseif test="${jobExecution.status.toString().equals(ConstantesBatch.BATCH_JOB_INSTANCE_STOPPING_STATUS)}">
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
                                                               id="progressing_step">
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
                                                                <g:sortableColumn property="reads" title="${message(code: 'batch.stepExecution.reads.label', default: 'Reads')}" />
                                                                <g:sortableColumn property="writes" title="${message(code: 'batch.stepExecution.writes.label', default: 'Writes')}" />
                                                                <g:sortableColumn property="skips"
                                                                                  title="${message(code: 'batch.stepExecution.skips.label', default: 'Skips')}"/>
                                                                <g:sortableColumn property="exitCode"
                                                                                  title="${message(code: 'batch.stepExecution.exitStatus.exitCode.label', default: 'Exit Code')}"/>
                                                                <g:sortableColumn property="detail"
                                                                                  title="${message(code: 'batch.stepExecution.detail.label', default: 'Détail')}"/>
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
                                                                        <g:message code="batch.step.${stepExecutionModelInstance.name}" default="${stepExecutionModelInstance.name}"/>
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
                                                                    <td align="center">
                                                                        <g:if test="${fieldValue(bean: stepExecutionModelInstance, field: "exitStatus.exitCode").toString().equals(ConstantesBatch.BATCH_JOB_INSTANCE_COMPLETED_STATUS)}">
                                                                            <g:link controller="batchStepExecution"
                                                                                    action="show"
                                                                                    id="${stepExecutionModelInstance.id}"
                                                                                    params="${[jobExecutionId: jobExecution.id]}"><i class="fa fa-eye fa-lg" title="${message(code: 'batch.stepExecution.detail.label', default: 'Détail')}"></i></g:link>
                                                                        </g:if>
                                                                        <g:elseif test="${fieldValue(bean: stepExecutionModelInstance, field: "exitStatus.exitCode").toString().equals('FAILED')}">
                                                                            <g:link controller="batchStepExecution"
                                                                                    action="show"
                                                                                    id="${stepExecutionModelInstance.id}"
                                                                                    params="${[jobExecutionId: jobExecution.id]}"><i class="fa fa-eye fa-lg" title="${message(code: 'batch.stepExecution.detail.label', default: 'Détail')}"></i></g:link>
                                                                        </g:elseif>
                                                                    </td>
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


    <div id="force_to_completed" class="modal fade" tabindex="-1" data-width="600">
        <div class="modal-content">
            <div class="page-content-inner">
                <div class="row">
                    <div class="col-md-12">
                        <div class="portlet box green">
                            <div class="portlet-title">
                                <div class="caption">
                                    <span class="glyphicon glyphicon-signal "></span>
                                    <span class="caption-subject bold uppercase">
                                        Forcer le status du batch à complét
                                    </span>
                                </div>
                            </div>
                        </div>

                        <div class="portlet-body form">
                            <div class="form-body">
                                <div class="row">
                                    <span>
                                        Voulez-vous forcer le statut du batch à complet ?
                                    </span>
                                </div>
                            </div>
                        </div>
                        <br>
                        <div class="form-actions">
                            <div class="row">
                                <div class="col-3-button" id="btnActionCompleted">
                                    <button class="success btn green-soft min-100-marge" data-dismiss="modal" tabindex="2" onclick="relaunch('${Constantes.LANCEMENT_BATCH_OPTION_COMPLETED_STATUS}');">Oui</button>
                                    <button class="cancel btn red-soft min-100-marge" data-dismiss="modal" tabindex="2">Non</button>
                                </div>
                            </div>
                        </div>
                        <br>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div id="force_to_launch" class="modal fade" tabindex="-1" data-width="600">
        <div class="modal-content">
            <div class="page-content-inner">
                <div class="row">
                    <div class="col-md-12">
                        <div class="portlet box green">
                            <div class="portlet-title">
                                <div class="caption">
                                    <span class="glyphicon glyphicon-signal "></span>
                                    <span class="caption-subject bold uppercase">
                                        Forcer le lancement d'une nouvelle instance dans la même journée
                                    </span>
                                </div>
                            </div>
                        </div>

                        <div class="portlet-body form">
                            <div class="form-body">
                                <div class="row">
                                    <span>
                                        Attention, Si vous lancer une nouvelle instance dans la même journée, Assurez vous que la dernière instance c'est interrompue et que les séquences complétes ne seront pas traitées à nouveau en les désactivant si nécessaire.
                                    </span>
                                </div>
                            </div>
                        </div>
                        <br>
                        <div class="form-actions">
                            <div class="row">
                                <div class="col-3-button" id="btnActionLaunch">
                                    <button class="success btn green-soft min-100-marge" data-dismiss="modal" tabindex="2" onclick="relaunch('${Constantes.LANCEMENT_BATCH_OPTION_NOUVELLE_INSTANCE}');">Lancer</button>
                                    <button class="cancel btn red-soft min-100-marge" data-dismiss="modal" tabindex="2">Annuler</button>
                                </div>
                            </div>
                        </div>
                        <br>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <div id="force_to_restart" class="modal fade" tabindex="-1" data-width="600">
        <div class="modal-content">
            <div class="page-content-inner">
                <div class="row">
                    <div class="col-md-12">
                        <div class="portlet box green">
                            <div class="portlet-title">
                                <div class="caption">
                                    <span class="glyphicon glyphicon-signal "></span>
                                    <span class="caption-subject bold uppercase">
                                        Forcer le lancement d'une nouvelle instance en commençant par la dernière séquence en cours
                                    </span>
                                </div>
                            </div>
                        </div>

                        <div class="portlet-body form">
                            <div class="form-body">
                                <div class="row">
                                    <span>
                                        Attention, cette option doit être utilisé que si l'application a été redémarré alors que le batch était en cours. Assurez vous que la dernière instance c'est interrompue.
                                        <br>
                                        <br>
                                        Les séquences complétes ne seront pas traiter à noveau.
                                    </span>
                                </div>
                            </div>
                        </div>
                        <br>
                        <div class="form-actions">
                            <div class="row">
                                <div class="col-3-button" id="btnActionRestart">
                                    <button class="success btn green-soft min-100-marge" data-dismiss="modal" tabindex="2" onclick="relaunch('${Constantes.LANCEMENT_BATCH_OPTION_RESTART_INSTANCE}');">Lancer</button>
                                    <button class="cancel btn red-soft min-100-marge" data-dismiss="modal" tabindex="2">Annuler</button>
                                </div>
                            </div>
                        </div>
                        <br>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <!-- BEGIN INNER FOOTER -->
    <g:render template="/layouts/footer"/>
    <!-- END INNER FOOTER -->
</div>


<div class="quick-nav-overlay"></div>
<!-- END QUICK NAV -->
<asset:javascript src="metronic_v4_7_1_admin_3_rounded.js"/>
<script type="text/javascript">
    $('#progressing_step').dataTable({
        paging: false,
        "ordering": false
    });

    function relaunch(type){
        if (type === '${Constantes.LANCEMENT_BATCH_OPTION_COMPLETED_STATUS}'){
            $("#btnActionCompleted").html('<div id="spinner"> <i class="fa fa-spinner fa-spin fa-lg"></i> en cours ...</div>');
            window.location = "${request.contextPath}/batch/initBatch?jobExecutionId=${jobExecution.id}&status=${ConstantesBatch.BATCH_JOB_INSTANCE_COMPLETED_STATUS}";
        }
        else if (type === '${Constantes.LANCEMENT_BATCH_OPTION_NOUVELLE_INSTANCE}'){
            $("#btnActionLaunch").html('<div id="spinner"> <i class="fa fa-spinner fa-spin fa-lg"></i> en cours ...</div>');
            window.location = "${request.contextPath}/batch/launch?id=${jobExecution.jobName}&jobExecutionId=${jobExecution.id}&option=${Constantes.LANCEMENT_BATCH_OPTION_NOUVELLE_INSTANCE}";
        }
        else if (type === '${Constantes.LANCEMENT_BATCH_OPTION_RESTART_INSTANCE}'){
            $("#btnActionRestart").html('<div id="spinner"> <i class="fa fa-spinner fa-spin fa-lg"></i> en cours ...</div>');
            window.location = "${request.contextPath}/batch/launch?id=${jobExecution.jobName}&jobExecutionId=${jobExecution.id}&option=${Constantes.LANCEMENT_BATCH_OPTION_RESTART_INSTANCE}";
        }
    }
</script>

</body>

</html>