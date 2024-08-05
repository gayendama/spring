<%@ page import="sn.sensoft.springbatch.parametrage.Batch; springbatch.model.JobModel; springbatch.ui.PagedResult; sn.sensoft.springbatch.utils.BatchType; sn.sensoft.springbatch.utils.BatchList; sn.sensoft.springbatch.utils.Constantes; sn.sensoft.springbatch.utils.DateUtils; sn.sensoft.springbatch.ToolsBatchService" %>
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

    <title>Aicha BATCH JOB LIST</title>
    <asset:stylesheet src="srpingBatchRefont.css"/>
    <meta name="layout" content="main"/>
    <meta http-equiv="refresh" content="60">

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

                                <ul class="page-breadcrumb breadcrumb">
                                    <li>
                                        <g:link controller="batch" action="list"><i
                                                class="icon-home"></i> <g:message
                                                code="default.home.label" default="Accueil"/></g:link>
                                        <i class="fa fa-circle"></i>
                                    <li>
                                        <span class="glyphicon glyphicon-list-alt "></span>
                                        <g:message code="default.list.label" args="[entityName]"/>
                                    </li>
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

                                                                <g:link action="disableLaunching"
                                                                        class="btn red">
                                                                    <i class="fa fa-toggle-off"></i>&nbsp;<b><g:message
                                                                        code="batch.disableLaunching"/></b></g:link>
                                                        </g:if>
                                                        <g:else>
                                                                <g:link action="enableLaunching"
                                                                        class="btn green">
                                                                    <i class="fa fa-toggle-on"></i>&nbsp;<b><g:message
                                                                        code="batch.enableLaunching"/></b></g:link>

                                                        </g:else>

                                                    </div>

                                                    <div class="btn-group">

                                                    &nbsp;&nbsp;&nbsp;
                                                            <g:link action="stopAllExecutions"
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
                                                        ${ situationAgence.apiAccess?  Date.parse("yyyy-MM-dd", situationAgence.treatmentDate).format("dd/MM/yyyy"):'NOT FOUND'}
                                                    </div>
                                                </div>

                                                <div class="panel panel-default report-content">
                                                    <div class="panel-heading">Situation Agence</div>
                                                    <div class="panel-body">

                                                        <g:if test="${!situationAgence.apiAccess}">
                                                            <span class="batch-result status-background-red">
                                                                SELATIS non disponible
                                                            </span>
                                                        </g:if>
                                                        <g:else>
                                                            <g:if test="${situationAgence.openAgency}">
                                                                <span class="batch-result status-background-orange">
                                                                    Ouverte
                                                                </span>
                                                            </g:if>
                                                            <g:else>
                                                                <span class="batch-result status-background-gree">
                                                                    Fermée
                                                                </span>
                                                            </g:else>
                                                        </g:else>
                                                    </div>
                                                </div>

                                                <div class="panel panel-default report-content">
                                                    <div class="panel-heading">Nombre de Caisses Ouvertes</div>
                                                    <div class="panel-body">
                                                        ${situationAgence.openedCheckout}
                                                    </div>
                                                </div>

                                                <div class="panel panel-default report-content">
                                                    <div class="panel-heading">Batch en Cours</div>

                                                    <div class="panel-body">
                                                        <g:if test="${currentlyRunning}">
                                                            <span class="batch-result status-background-orange">Oui</span>
                                                        </g:if>
                                                        <g:else>
                                                            <span class="batch-result status-background-green">Non</span>
                                                        </g:else>
                                                    </div>
                                                </div>

                                                <div class="panel panel-default report-content">
                                                    <div class="panel-heading">Batch désactivés</div>

                                                    <div class="panel-body">
                                                        <g:link action="index">
                                                            <span class="badge badge-warning bold">${Batch.countByIndActifAndDeleted(false, false)}</span>
                                                        </g:link>
                                                    </div>
                                                </div>

                                            </div>

                                            <div class="portlet-body">

                                                <g:each in="${modelInstancesGroup}" var="group">
                                                    <%
                                                        BatchType batchType = BatchType.findByOrder(group.key)
                                                        PagedResult<JobModel> modelInstances = group.value
                                                    %>

                                                    <div class="mt-element-ribbon bg-grey-light">
                                                        <div class="ribbon ribbon-shadow ribbon-color-success uppercase">
                                                            ${batchType.libelle} %{-- ==key=${group.key}--}%
                                                        </div>

                                                        <div class="ribbon-content">
                                                            <div class="table-container"
                                                                 style="margin-top: -10px; padding-bottom: 15px;">
                                                                <table class="table table-striped table-bordered table-hover dt-responsive">
                                                                    <thead>
                                                                    <tr>
                                                                        <g:sortableColumn property="name"
                                                                                          title="${message(code: 'jobModel.name.label', default: 'Name')}"/>
                                                                        <g:sortableColumn property="currentlyRunning"
                                                                                          title="${message(code: 'batch.job.currentlyRunning.label', default: 'Running')}"/>
                                                                        <td>Recent Execution</td>
                                                                        <td>Next Execution</td>
                                                                        <td align="center">Action</td>
                                                                    </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                    <g:each in="${modelInstances}" status="i"
                                                                            var="modelInstance">
                                                                        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                                                                            <td>
                                                                                    <g:link action="show"
                                                                                            id="${modelInstance.name}">
                                                                                        <g:message
                                                                                                code="batch.${modelInstance.name}"
                                                                                                default="${modelInstance.name}"/>
                                                                                    </g:link>

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
                                                                            <td>
                                                                                <g:if test="${modelInstance.mostRecentJobExecution}">
                                                                                        <g:link controller="batchJobExecution"
                                                                                                action="show"
                                                                                                id="${modelInstance.mostRecentJobExecution.id}">
                                                                                            <span class="batch-result status-background-grey">
                                                                                                <g:formatDate
                                                                                                        type="datetime"
                                                                                                        style="MEDIUM"
                                                                                                        timeStyle="MEDIUM"
                                                                                                        date="${modelInstance.mostRecentJobExecution.startDateTime}"/>
                                                                                            </span>

                                                                                            <g:if test="${modelInstance.mostRecentJobExecution.status.toString().equals('STARTED')}">
                                                                                                <span class="batch-result status-background-white">${modelInstance.mostRecentJobExecution.status}</span>
                                                                                            </g:if>
                                                                                            <g:elseif
                                                                                                    test="${modelInstance.mostRecentJobExecution.status.toString().equals('COMPLETED')}">
                                                                                                <span class="batch-result status-background-green">${modelInstance.mostRecentJobExecution.status}</span>
                                                                                            </g:elseif>
                                                                                            <g:elseif
                                                                                                    test="${modelInstance.mostRecentJobExecution.status.toString().equals('FAILED')}">
                                                                                                <span class="batch-result status-background-red">${modelInstance.mostRecentJobExecution.status}</span>
                                                                                            </g:elseif>
                                                                                            <g:else>
                                                                                                <span class="batch-result status-background-orange">${modelInstance.mostRecentJobExecution.status}</span>
                                                                                            </g:else>

                                                                                        </g:link>

                                                                                </g:if>
                                                                            </td>
                                                                            <td>
                                                                                <div id="${modelInstance.name.replaceAll(" ", "")}"></div>
                                                                            </td>
                                                                            <td align="left">
                                                                                    <g:if test="${modelInstance.mostRecentJobExecution}">
                                                                                        <g:link controller="batchJobExecution"
                                                                                                action="show"
                                                                                                id="${modelInstance.mostRecentJobExecution.id}"
                                                                                                class="action">
                                                                                            <i class="fa fa-eye fa-lg"
                                                                                               title="Most recent execution"></i>
                                                                                        </g:link>
                                                                                    </g:if>
                                                                                    <g:else>
                                                                                        <i class="fa fa-eye fa-lg disabled-action-color action"
                                                                                           title="Most recent execution"
                                                                                           style="cursor: not-allowed"></i>
                                                                                    </g:else>

                                                                                    <g:link action="show"
                                                                                            id="${modelInstance.name}"
                                                                                            class="action">
                                                                                        <i class="fa fa-bars fa-lg"
                                                                                           title="Job report"></i>
                                                                                    </g:link>

                                                                                    <g:link controller="batch"
                                                                                            action="showStepSettings"
                                                                                            id="${modelInstance.name}"
                                                                                            class="action">
                                                                                        <i class="fa fa-cog fa-lg"
                                                                                           title="Settings"></i>
                                                                                    </g:link>

                                                                                    <a data-toggle="modal" href="#modalEditCronTrigger"> </a>
                                                                                    <a data-toggle="modal"
                                                                                       href="#modalEditCronTrigger-${batchType.code}-${i}"
                                                                                       onclick="showCronExpression();">
                                                                                        <i id="clock-${batchType.code}-${i}"
                                                                                           class="fa fa-clock-o fa-lg action"
                                                                                           title="cron expression"></i>
                                                                                    </a>

                                                                                <g:if test="${modelInstance.mostRecentJobExecution && modelInstance.mostRecentJobExecution.exitStatus.exitCode.toString().equals('FAILED')}">
                                                                                        <g:link controller="batchJobExecution"
                                                                                                action="restart"
                                                                                                id="${modelInstance.mostRecentJobExecution.id}"
                                                                                                class="action">
                                                                                            <i class="fa fa-refresh fa-lg"
                                                                                               title="Restart"></i>
                                                                                        </g:link>

                                                                                </g:if>
                                                                                <g:elseif
                                                                                        test="${modelInstance.launchable && ready && !currentlyRunning}">
                                                                                        <g:if test="${modelInstance.name.equals(BatchList.BatchCalculSoldeArrete.id)}">
                                                                                            <g:link controller="traitementBatch"
                                                                                                    action="calculerSoldeArrete"
                                                                                                    params="[appVersion: situationAgence.appVersion, dateTraitement: situationAgence.treatmentDate]">
                                                                                                <i class="fa fa-play fa-lg"
                                                                                                   aria-hidden="true"
                                                                                                   title="Launch Job"></i>
                                                                                            </g:link>
                                                                                        </g:if>
                                                                                        <g:elseif
                                                                                                test="${modelInstance.name.equals(BatchList.BatchCalculCreancesRattachees.id)}">
                                                                                            <g:link controller="traitementBatch"
                                                                                                    action="calculCreancesRattachees"
                                                                                                    params="[appVersion: situationAgence.appVersion, dateTraitement: situationAgence.treatmentDate]">
                                                                                                <i class="fa fa-play fa-lg"
                                                                                                   aria-hidden="true"
                                                                                                   title="Launch Job"></i>
                                                                                            </g:link>
                                                                                        </g:elseif>
                                                                                        <g:elseif
                                                                                                test="${modelInstance.name.equals(BatchList.BatchApurementCompteGestion.id)}">
                                                                                            <g:link controller="traitementBatch"
                                                                                                    action="apurementCompteGestion"
                                                                                                    params="[appVersion: situationAgence.appVersion, dateTraitement: situationAgence.treatmentDate]">
                                                                                                <i class="fa fa-play fa-lg"
                                                                                                   aria-hidden="true"
                                                                                                   title="Launch Job"></i>
                                                                                            </g:link>
                                                                                        </g:elseif>
                                                                                        <g:elseif
                                                                                                test="${modelInstance.name.equals(BatchList.BatchCalculPosteBudgetaire.id)}">
                                                                                            <g:link controller="traitementBatch"
                                                                                                    action="calculPosteBudgetaire"
                                                                                                    params="[appVersion: situationAgence.appVersion, dateTraitement: situationAgence.treatmentDate]">
                                                                                                <i class="fa fa-play fa-lg"
                                                                                                   aria-hidden="true"
                                                                                                   title="Launch Job"></i>
                                                                                            </g:link>
                                                                                        </g:elseif>
                                                                                        <g:elseif
                                                                                                test="${modelInstance.name.equals(BatchList.BatchCalculProvisionsPrets.id)}">
                                                                                            <g:link controller="traitementBatch"
                                                                                                    action="calculProvisionsPrets"
                                                                                                    params="[appVersion: situationAgence.appVersion, dateTraitement: situationAgence.treatmentDate]">
                                                                                                <i class="fa fa-play fa-lg"
                                                                                                   aria-hidden="true"
                                                                                                   title="Launch Job"></i>
                                                                                            </g:link>
                                                                                        </g:elseif>
                                                                                        <g:elseif
                                                                                                test="${modelInstance.name.equals(BatchList.BatchGenerationEcritureProvision.id)}">
                                                                                            <g:link controller="traitementBatch"
                                                                                                    action="generationEcritureProvision"
                                                                                                    params="[appVersion: situationAgence.appVersion, dateTraitement: situationAgence.treatmentDate]">
                                                                                                <i class="fa fa-play fa-lg"
                                                                                                   aria-hidden="true"
                                                                                                   title="Launch Job"></i>
                                                                                            </g:link>
                                                                                        </g:elseif>
                                                                                        <g:elseif
                                                                                                test="${modelInstance.name.equals(BatchList.BatchCalculInteretCompteBloque.id)}">
                                                                                            <g:link controller="traitementBatch"
                                                                                                    action="calculInteretCompteBloque"
                                                                                                    params="[appVersion: situationAgence.appVersion, dateTraitement: situationAgence.treatmentDate]">
                                                                                                <i class="fa fa-play fa-lg"
                                                                                                   aria-hidden="true"
                                                                                                   title="Launch Job"></i>
                                                                                            </g:link>
                                                                                        </g:elseif>
                                                                                        <g:elseif
                                                                                                test="${modelInstance.name.equals(BatchList.BatchImputationInteret.id)}">
                                                                                            <g:link controller="traitementBatch"
                                                                                                    action="imputationInteret"
                                                                                                    params="[appVersion: situationAgence.appVersion, dateTraitement: situationAgence.treatmentDate]">
                                                                                                <i class="fa fa-play fa-lg"
                                                                                                   aria-hidden="true"
                                                                                                   title="Launch Job"></i>
                                                                                            </g:link>
                                                                                        </g:elseif>
                                                                                        <g:elseif
                                                                                                test="${modelInstance.name.equals(BatchList.BatchComptabilisationImmo.id)}">
                                                                                            <g:link controller="traitementBatch"
                                                                                                    action="comptaImmo"
                                                                                                    params="[appVersion: situationAgence.appVersion, dateTraitement: situationAgence.treatmentDate]">
                                                                                                <i class="fa fa-play fa-lg"
                                                                                                   aria-hidden="true"
                                                                                                   title="Launch Job"></i>
                                                                                            </g:link>
                                                                                        </g:elseif>
                                                                                        <g:elseif
                                                                                                test="${modelInstance.name.equals(BatchList.BatchCalculImmobilisations.id)}">
                                                                                            <g:link controller="traitementBatch"
                                                                                                    action="calculImmo"
                                                                                                    params="[appVersion: situationAgence.appVersion, dateTraitement: situationAgence.treatmentDate]">
                                                                                                <i class="fa fa-play fa-lg"
                                                                                                   aria-hidden="true"
                                                                                                   title="Launch Job"></i>
                                                                                            </g:link>
                                                                                        </g:elseif>
                                                                                        <g:elseif
                                                                                                test="${modelInstance.name.equals(BatchList.BatchInitialisationCredit.id)}">
                                                                                            <g:link controller="creditInitialisation"
                                                                                                    action="create"
                                                                                                    params="[appVersion: situationAgence.appVersion, dateTraitement: situationAgence.treatmentDate]">
                                                                                                <i class="fa fa-play fa-lg"
                                                                                                   aria-hidden="true"
                                                                                                   title="Launch Job"></i>
                                                                                            </g:link>
                                                                                        </g:elseif>
                                                                                        <g:elseif
                                                                                                test="${modelInstance.name.equals(BatchList.BatchExtourneOperationsFromFile.id)}">
                                                                                            <g:link controller="traitementBatch"
                                                                                                    action="extourneOperationsFile"
                                                                                                    params="[appVersion: situationAgence.appVersion, dateTraitement: situationAgence.treatmentDate]">
                                                                                                <i class="fa fa-play fa-lg"
                                                                                                   aria-hidden="true"
                                                                                                   title="Launch Job"></i>
                                                                                            </g:link>
                                                                                        </g:elseif>
                                                                                        <g:elseif
                                                                                                test="${modelInstance.name.equals(BatchList.CorrectionCredits.id)}">
                                                                                            <g:link controller="traitementBatch"
                                                                                                    action="launchCorrectionCredit"
                                                                                                    id="${BatchList.CorrectionCredits.id}"
                                                                                                    params="[appVersion: situationAgence.appVersion, dateTraitement: situationAgence.treatmentDate]">
                                                                                                <i class="fa fa-play fa-lg"
                                                                                                   aria-hidden="true"
                                                                                                   title="Launch Job"></i>
                                                                                            </g:link>
                                                                                        </g:elseif>
                                                                                        <g:elseif
                                                                                                test="${modelInstance.name.equals(BatchList.CorrectionFinancementCredit.id)}">
                                                                                            <g:link controller="traitementBatch"
                                                                                                    action="launchCorrectionCredit"
                                                                                                    id="${BatchList.CorrectionFinancementCredit.id}"
                                                                                                    params="[appVersion: situationAgence.appVersion, dateTraitement: situationAgence.treatmentDate]">
                                                                                                <i class="fa fa-play fa-lg"
                                                                                                   aria-hidden="true"
                                                                                                   title="Launch Job"></i>
                                                                                            </g:link>
                                                                                        </g:elseif>
                                                                                        <g:elseif
                                                                                                test="${modelInstance.name.equals(BatchList.RecreerDossierCredit.id)}">
                                                                                            <g:link controller="traitementBatch"
                                                                                                    action="launchCorrectionCredit"
                                                                                                    id="${BatchList.RecreerDossierCredit.id}"
                                                                                                    params="[appVersion: situationAgence.appVersion, dateTraitement: situationAgence.treatmentDate]">
                                                                                                <i class="fa fa-play fa-lg"
                                                                                                   aria-hidden="true"
                                                                                                   title="Launch Job"></i>
                                                                                            </g:link>
                                                                                        </g:elseif>
                                                                                        <g:elseif
                                                                                                test="${modelInstance.name.equals(BatchList.SelcectFraisTcpNonRecycles.id)}">
                                                                                            <g:link controller="traitementBatch"
                                                                                                    action="correctionFraisTcp"
                                                                                                    id="${BatchList.SelcectFraisTcpNonRecycles.id}"
                                                                                                    params="[appVersion: situationAgence.appVersion, dateTraitement: situationAgence.treatmentDate]">
                                                                                                <i class="fa fa-play fa-lg"
                                                                                                   aria-hidden="true"
                                                                                                   title="Launch Job"></i>
                                                                                            </g:link>
                                                                                        </g:elseif>
                                                                                    <g:elseif
                                                                                            test="${modelInstance.name.equals(BatchList.BatchExtractionBic.id)}">
                                                                                        <g:link controller="traitementBatch"
                                                                                                action="extractionBic"
                                                                                                id="${BatchList.BatchExtractionBic.id}"
                                                                                                params="[appVersion: situationAgence.appVersion, dateTraitement: situationAgence.treatmentDate]">
                                                                                            <i class="fa fa-play fa-lg"
                                                                                               aria-hidden="true"
                                                                                               title="Launch Job"></i>
                                                                                        </g:link>
                                                                                    </g:elseif>
                                                                                    <g:elseif
                                                                                            test="${modelInstance.name.equals(BatchList.BatchVerifComptesCompensesCaisses.id)}">
                                                                                        <g:link controller="traitementBatch"
                                                                                                action="launchVerifCompensesCaisses"
                                                                                                id="${BatchList.BatchVerifComptesCompensesCaisses.id}"
                                                                                                params="[appVersion: situationAgence.appVersion, dateTraitement: situationAgence.treatmentDate]">
                                                                                            <i class="fa fa-play fa-lg"
                                                                                               aria-hidden="true"
                                                                                               title="Launch Job"></i>
                                                                                        </g:link>
                                                                                    </g:elseif>
                                                                                        <g:else>
                                                                                            <g:link action="launch"
                                                                                                    id="${modelInstance.name}"
                                                                                                    class="launchJobButton"
                                                                                                    params="[appVersion: situationAgence.appVersion, dateTraitement: situationAgence.treatmentDate,agenceOuverte : situationAgence.openAgency]">
                                                                                                <!-- g:message code="batch.job.launch.label"/ -->
                                                                                                <i class="fa fa-play fa-lg"
                                                                                                   aria-hidden="true"
                                                                                                   title="Launch Job"></i>
                                                                                            </g:link>
                                                                                        </g:else>

                                                                                </g:elseif>
                                                                            </td>
                                                                        </tr>
                                                                    </g:each>
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </div>
                                                    </div>

                                                </g:each>
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

<g:each in="${modelInstancesGroup}" var="group">
    <%
        BatchType typeBatch = BatchType.findByOrder(group.key)
        PagedResult<JobModel> modelList = group.value
    %>
    <g:each in="${modelList}" status="i"  var="modelInstance">
        <div id="modalEditCronTrigger-${typeBatch.code}-${i}" class="modal fade" tabindex="-1" data-width="600">
            <div class="modal-content">
                <div class="page-content-inner">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="portlet box green">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <span class="glyphicon glyphicon-plus "></span>
                                        <span class="caption-subject bold uppercase">
                                            <span id="job-title">${modelInstance.name}</span>
                                        </span>
                                    </div>
                                </div>
                            </div>

                            <div class="portlet-body form">
                            <!-- BEGIN FORM-->
                                <g:form action="saveCronTrigger"
                                        role="form" method="POST" class="form-horizontal">
                                    <g:hiddenField name="id" value="oo"/>
                                    <input type="hidden" id="triggerName-${typeBatch.code}-${i}" name="triggerName">
                                    <input type="hidden" id="triggerGroup-${typeBatch.code}-${i}" name="triggerGroup">

                                    <div class="form-body">
                                        <h3 class="form-section font-blue-madison bold">
                                            Définition de l'expression cron
                                        </h3>

                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="form-group">
                                                    <div class="col-md-12">
                                                        <div class="form-group form-md-line-input form-md-floating-label has-info">
                                                            <label for="cronexpression-${typeBatch.code}-${i}"><g:message
                                                                    code="quartz.next.executionDate" default="Expression"/>:
                                                            </label>

                                                            <div class="row">
                                                                <div class="col-md-8">
                                                                    <input type="text" name="cronexpression"
                                                                           id="cronexpression-${typeBatch.code}-${i}"
                                                                           class="form-control"
                                                                           value=""/>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-9">
                                                <div class="row">
                                                    <div class="col-md-offset-3 col-md-9">
                                                        <g:submitButton name="edit"
                                                                        class="btn green-meadow"
                                                                        value="${message(code: 'default.enregistrer.label', 'default': 'Enregistrer')}"/>
                                                        <button class="cancel btn blue-hoki" data-dismiss="modal"
                                                                tabindex="2"
                                                                style="display: inline-block;">Annuler</button>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-md-3"></div>
                                        </div>
                                    </div>
                                </g:form>
                            <!-- END FORM-->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </g:each>
</g:each>

<div class="quick-nav-overlay"></div>
<!-- END QUICK NAV -->

<asset:javascript src="metronic_v4_7_1_admin_3_rounded.js"/>
<asset:javascript src="intlTelInput.js"/>
<asset:javascript src="jquery.maskedinput.js"/>
<asset:javascript src="application.utils.js"/>
<asset:javascript src="lc_switch.js"/>
<script>
    function showNextExecutionDate(batchName, idNum) {
        json = {triggerName: batchName};
        $.ajax({
            url: '${request.contextPath}/batch/getDetailJobTriger',
            type: 'POST',
            dataType: 'html',
            contentType: 'application/json',
            success: function (data) {
                var parsedJson = $.parseJSON(data);
                setTimeout(function () {
                    if (parsedJson.status == "success") {
                        $("#" + batchName.replaceAll(' ', '')).html(parsedJson.nextExecutionDate);
                        $("#clock-" + idNum).css("cursor", "pointer");
                        $("#clock-" + idNum).addClass("enabled-action-color");
                        $("#clock-" + idNum).attr("data-toggle", "modal");
                        $("#cronexpression-" + idNum).val(parsedJson.cronExpression);
                        $("#triggerName-" + idNum).val(parsedJson.triggerName);
                        $("#triggerGroup-" + idNum).val(parsedJson.triggerGroup);
                    }
                    else {
                        $("#clock-" + idNum).addClass("disabled-action-color");
                        $("#clock-" + idNum).click(false);
                        $("#clock-" + idNum).css('cursor', 'not-allowed');
                    }
                }, 1000);
            },
            data: JSON.stringify(json)
        });
    }


    <g:each in="${modelInstancesGroup}" var="group">
        <%
            BatchType type = BatchType.findByOrder(group.key)
            PagedResult<JobModel> models = group.value
        %>
        <g:each in="${models}" status="i"  var="model">
            showNextExecutionDate('${model.name}', '${type.code}-${i}');
        </g:each>
    </g:each>
</script>

</body>

</html>