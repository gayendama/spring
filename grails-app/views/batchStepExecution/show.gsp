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
    <g:set var="entityName" value="${message(code: 'step.execution.detail.label', default: 'Step détails')}"/>
    <title><g:message code="batchJob.label"/></title>

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
                                        <g:message code="default.show.label" args="[entityName]"/>
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
                                                        STEP: <g:message code="batch.step.${stepExecution.name}" default="${stepExecution.name}"/>
                                                    </span>
                                                </div>

                                                <div class="actions">
                                                    <div class="btn-group btn-group-devided">
                                                        <g:link controller="batch" action="list" class="btn blue-hoki"><i class="fa fa-list"></i> Job List</g:link>

                                                        <g:link controller="batchJobExecution" action="show" id="${stepExecution.jobExecutionId}" class="btn blue-sharp">
                                                            <i class="fa fa-angle-double-left"></i> Back to steps
                                                        </g:link>&nbsp;&nbsp;

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

                                                    <div style="max-height: 500px; overflow: scroll; border: 1px solid #CCC; margin: 10px; padding: 10px;">
                                                        <ol class="property-list example">
                                                            <li class="fieldcontain">
                                                                <span id="startDateTime-label"
                                                                      class="property-label"><g:message
                                                                        code="batch.stepExecution.startDateTime.label"/></span>
                                                                <span class="property-value"
                                                                      aria-labelledby="startDateTime-label">${stepExecution.startDateTime}</span>
                                                            </li>
                                                            <li class="fieldcontain">
                                                                <span id="duration-label" class="property-label"><g:message
                                                                        code="batch.stepExecution.duration.label"/></span>
                                                                <span class="property-value"
                                                                      aria-labelledby="duration-label"><batch:durationPrint
                                                                        duration="${stepExecution.duration}"/></span>
                                                            </li>
                                                            <li class="fieldcontain">
                                                                <span id="status-label" class="property-label"><g:message
                                                                        code="batch.stepExecution.status.label"/></span>
                                                                <span class="property-value"
                                                                      aria-labelledby="status-label">${stepExecution.status}</span>
                                                            </li>
                                                            <li class="fieldcontain">
                                                                <span id="exitStatus-label"
                                                                      class="property-label"><g:message
                                                                        code="batch.stepExecution.exitStatus.exitCode.label"/></span>
                                                                <span class="property-value"
                                                                      aria-labelledby="exitStatus-label">${stepExecution.exitStatus.exitCode}</span>
                                                            </li>

                                                            <li class="fieldcontain">
                                                                <span id="reads-label" class="property-label"><g:message
                                                                        code="batch.stepExecution.reads.label"/></span>
                                                                <span class="property-value"
                                                                      aria-labelledby="reads-label">${stepExecution.reads}</span>
                                                            </li>
                                                            <li class="fieldcontain">
                                                                <span id="readSkipCount-label"
                                                                      class="property-label"><g:message
                                                                        code="batch.stepExecution.readSkipCount.label"/></span>
                                                                <span class="property-value"
                                                                      aria-labelledby="readSkipCount-label">${stepExecution.readSkipCount}</span>
                                                            </li>
                                                            <li class="fieldcontain">
                                                                <span id="writes-label" class="property-label"><g:message
                                                                        code="batch.stepExecution.writes.label"/></span>
                                                                <span class="property-value"
                                                                      aria-labelledby="writes-label">${stepExecution.writes}</span>
                                                            </li>
                                                            <li class="fieldcontain">
                                                                <span id="writeSkipCount-label"
                                                                      class="property-label"><g:message
                                                                        code="batch.stepExecution.writeSkipCount.label"/></span>
                                                                <span class="property-value"
                                                                      aria-labelledby="writeSkipCount-label">${stepExecution.writeSkipCount}</span>
                                                            </li>
                                                            <li class="fieldcontain">
                                                                <span id="skips-label" class="property-label"><g:message
                                                                        code="batch.stepExecution.skips.label"/></span>
                                                                <span class="property-value"
                                                                      aria-labelledby="skips-label">${stepExecution.skips}</span>
                                                            </li>
                                                            <li class="fieldcontain">
                                                                <span id="commitCount-label"
                                                                      class="property-label"><g:message
                                                                        code="batch.stepExecution.commitCount.label"/></span>
                                                                <span class="property-value"
                                                                      aria-labelledby="commitCount-label">${stepExecution.commitCount}</span>
                                                            </li>
                                                            <li class="fieldcontain">
                                                                <span id="filterCount-label"
                                                                      class="property-label"><g:message
                                                                        code="batch.stepExecution.filterCount.label"/></span>
                                                                <span class="property-value"
                                                                      aria-labelledby="filterCount-label">${stepExecution.filterCount}</span>
                                                            </li>
                                                            <li class="fieldcontain">
                                                                <span id="processSkipCount-label"
                                                                      class="property-label"><g:message
                                                                        code="batch.stepExecution.processSkipCount.label"/></span>
                                                                <span class="property-value"
                                                                      aria-labelledby="processSkipCount-label">${stepExecution.processSkipCount}</span>
                                                            </li>
                                                            <li class="fieldcontain">
                                                                <span id="lastUpdated-label"
                                                                      class="property-label"><g:message
                                                                        code="batch.stepExecution.lastUpdated.label"/></span>
                                                                <span class="property-value"
                                                                      aria-labelledby="lastUpdated-label">${stepExecution.lastUpdated}</span>
                                                            </li>
                                                            <g:if test="${stepExecution.failureExceptions}">
                                                                <li class="fieldcontain">
                                                                    <span id="exceptions-label" class="property-label"><g:message code="batch.stepExecution.exceptions.label"/></span>
                                                                    <g:each in="${stepExecution.failureExceptions}">
                                                                        <p><span class="property-value" aria-labelledby="exceptions-label" style="color: red">${it.message}</span></p>
                                                                    </g:each>
                                                                </li>
                                                            </g:if>

                                                            <g:if test="${stepExecution.exitStatus.exitDescription && numberItemsWithException == 0}">
                                                                <li class="fieldcontain">
                                                                    <span id="exceptions2-label" class="property-label" ><g:message code="batch.stepExecution.exceptions.label"/></span>
                                                                    <span class="property-value" aria-labelledby="exceptions2-label" style="color: red">${stepExecution.exitStatus.exitDescription}</span>
                                                                </li>
                                                            </g:if>
                                                        </ol>

                                                        <div style="color: red; margin-top: 50px; margin-bottom: -50px;">
                                                            Liste de éléments traités avec exception
                                                        </div>
                                                        <table id="bootstrap-table" data-show-export="true"
                                                               class="table table-striped table-bordered"
                                                               data-sort-order="desc">
                                                            <thead>
                                                            <th class="text-center" data-field="objet" data-sortable="true">
                                                                <g:message code="element.label"
                                                                           default="ELEMENT"/>
                                                            </th>
                                                            <th class="text-center" data-field="objetId" data-sortable="true">
                                                                <g:message code="elementId.label"
                                                                           default="NUMBER OR ID"/>
                                                            </th>
                                                            <th class="text-center" data-field="responseCode" data-sortable="true" data-formatter="formatterCode">
                                                                <g:message code="responseCode.label"
                                                                           default="RESPONSE CODE"/>
                                                            </th>

                                                            <th data-field="responseMessage" data-sortable="true" data-formatter="formatterMessage"
                                                                class="text-left">
                                                                <g:message code="responseMessage.label"
                                                                           default="RESPONSE MESSAGE"/>
                                                            </th>
                                                            </thead>
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
    <!-- BEGIN INNER FOOTER -->
    <g:render template="/layouts/footer"/>
    <!-- END INNER FOOTER -->
</div>


<div class="quick-nav-overlay"></div>
<!-- END QUICK NAV -->

<asset:javascript src="metronic_v4_7_1_admin_3_rounded.js"/>

<script>
    var $table = $('#bootstrap-table');

    function formatterCode(value, row, index) {
        return [
            '<span class="badge badge-danger badge-pill bold">' + row.responseCode + '</span>'
        ].join('');
    }

    function formatterMessage(value, row, index) {
        return [
            '<span style="color: red">' + row.responseMessage + '</span>'
        ].join('');
    }

    $(document).ready(function () {
        waitingValidation = "";

        console.log("waitingValidation == " + waitingValidation);
        $table.bootstrapTable({
            url: "${createLink(controller: 'batchStepExecution',action: 'loadError')}",
            toolbar: ".toolbar",
            showRefresh: false,
            search: true,
            showToggle: false,
            showColumns: false,
            pagination: true,
            searchAlign: 'right',
            pageSize: 10,
            clickToSelect: false,
            sidePagination: 'server',
            pageList: [10, 25, 50, 100],

            formatShowingRows: function (pageFrom, pageTo, totalRows) {
                //do nothing here, we don't want to show the text "showing x of y from..."
            },

            language: {
                "sProcessing": "Traitement en cours ...",
                "sLengthMenu": "Afficher _MENU_ lignes",
                "sZeroRecords": "Aucun résultat trouvé",
                "sEmptyTable": "Aucune donnée disponible",
                "sInfo": "Lignes _START_ à _END_ sur _TOTAL_",
                "sInfoEmpty": "Aucune ligne affichée",
                "sInfoFiltered": "(Filtrer un maximum de _MAX_)",
                "sInfoPostFix": "",
                "sSearch": "Chercher:",
                "sUrl": "",
                "sInfoThousands": ",",
                "sLoadingRecords": "Chargement...",
                "oPaginate": {
                    "sFirst": "Premier", "sLast": "Dernier", "sNext": "Suivant", "sPrevious": "Précédent"
                },
                "oAria": {
                    "sSortAscending": ": Trier par ordre croissant", "sSortDescending": ": Trier par ordre décroissant"
                }
            },

            icons: {
                refresh: 'fa fa-refresh',
                toggle: 'fa fa-th-list',
                columns: 'fa fa-columns',
                detailOpen: 'fa fa-plus-circle',
                detailClose: 'ti-close'
            }
        });
    });
</script>
</body>

</html>