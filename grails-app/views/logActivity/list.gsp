<%@ page import="grails.converters.JSON" %>
<g:set var="toolsService" bean="habilitationService"/> <!DOCTYPE html>
<!--[if IE 8]> <html lang="fr" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="fr" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="fr">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8"/>
    <g:set var="entityName" value="${message(code: 'logActivity.label', default: 'Logs')}"/>
    <g:set var="entityNames" value="${message(code: 'logActivity.label', default: 'Logs')}"/>

    <title><g:message code="default.list.label" args="[entityNames]"/></title>

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
                                <ul class="page-breadcrumb breadcrumb">
                                    <li>
                                        <g:link controller="batch" action="list"><i
                                                class="icon-home"></i> <g:message
                                                code="default.home.label" default="Accueil"/></g:link>
                                        <i class="fa fa-circle"></i>
                                    </li>
                                    <li>
                                        <span class="glyphicon glyphicon-list-alt "></span>
                                        <g:message code="default.list.label" args="[entityNames]"/>
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
                                                    <span class="caption-subject bold uppercase"><g:message
                                                            code="default.list.label"
                                                            args="[entityNames]"/></span>
                                                </div>

                                                <div class="actions">
                                                    <div class="btn-group btn-group-devided">
                                                        <sec:ifAnyGranted
                                                                roles="${toolsService.getRoleSubMenu("/logActivity/recherche")}">
                                                            <g:link class="btn green" action="recherche"><i
                                                                    class="fa fa-plus"></i> <g:message
                                                                    code="logActivity.recherche"
                                                                    args="[entityName]"/></g:link>
                                                        </sec:ifAnyGranted>

                                                    </div>

                                                    <div class="btn-group">
                                                        <button type="button" class="btn blue-hoki"><i
                                                                class="fa fa-paperclip"></i> Exporter</button>
                                                        <button type="button"
                                                                class="btn blue-hoki dropdown-toggle"
                                                                data-toggle="dropdown" data-hover="dropdown"
                                                                data-delay="1000" data-close-others="true"
                                                                aria-expanded="false">
                                                            <i class="fa fa-angle-down"></i>
                                                        </button>
                                                        <ul class="dropdown-menu"
                                                            id="sample_2_tools">
                                                            <li>
                                                                <a href="javascript:" data-action="0"
                                                                   class="tool-action">
                                                                    <i class="fa fa-file-pdf-o"></i> Imprimer
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a href="javascript:" data-action="1"
                                                                   class="tool-action">
                                                                    <i class="fa fa-clone"></i> Copier</a>
                                                            </li>
                                                            <li>
                                                                <a href="javascript:" data-action="2"
                                                                   class="tool-action">
                                                                    <i class="fa fa-file-pdf-o"></i> PDF</a>
                                                            </li>
                                                            <li>
                                                                <a href="javascript:" data-action="3"
                                                                   class="tool-action">
                                                                    <i class="fa fa-file-excel-o"></i> Excel</a>
                                                            </li>
                                                            <li>
                                                                <a href="javascript:" data-action="4"
                                                                   class="tool-action">
                                                                    <i class="fa fa-file-excel-o"></i> CSV</a>
                                                            </li></ul>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="portlet-body">
                                                <div class="table-container">

                                                    <table id="bootstrap-table" data-show-export="true"
                                                           class="table table-striped table-bordered"
                                                           data-sort-order="desc">
                                                        <thead>
                                                        <th class="text-center" data-field="dateCreated" data-sortable="true">
                                                            <g:message code="logActivity.dateCreated.label"
                                                                       default="Date"/>
                                                        </th>
                                                        <th class="text-left" data-field="typeLog" data-sortable="true">
                                                            <g:message code="logActivity.typeLog.label"
                                                                       default="Type Log"/>
                                                        </th>
                                                        <th class="text-leftt" data-field="objectName" data-sortable="true">
                                                            <g:message code="logActivity.objetame.label"
                                                                       default="Objet"/>
                                                        </th>

                                                        <th data-field="message" data-sortable="true" class="text-left">
                                                            <g:message code="logActivity.message.label"
                                                                       default="Message"/>
                                                        </th>
                                                        <th data-field="username" data-sortable="true" class="text-left">
                                                            <g:message code="logActivity.user.label"
                                                                       default="Username"/>
                                                        </th>

                                                        </thead>
                                                    </table>

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

    var waitingValidation = "";


    function operateFormatter(value, row, index) {
        return [
            %{--'<sec:ifAnyGranted roles="${toolsService.getRoleSubMenu("/wallet/show")}">',--}%
            %{--'<a class="m-r-10" href="show/' + row.id +  '">',--}%
            %{--'<span class="glyphicon glyphicon-eye-open "></span>',--}%
            %{--'</a>',--}%
            %{--' </sec:ifAnyGranted>',--}%
            %{--'<sec:ifNotGranted roles="${toolsService.getRoleSubMenu("/wallet/show")}">',--}%
            %{--'<a class="m-r-10 grey " readonly="readonly">',--}%
            %{--'<span class="glyphicon glyphicon-eye-open "></span>',--}%
            %{--'</a>',--}%
            %{--' </sec:ifNotGranted>',--}%
            %{--'&nbsp;&nbsp;&nbsp;',--}%
            %{--'<sec:ifAnyGranted roles="${toolsService.getRoleSubMenu("/wallet/edit")}">',--}%
            %{--'<a class="m-r-10" href="edit/' + row.id +  '">',--}%
            %{--'<span class="glyphicon glyphicon-pencil tooltips"></span>',--}%
            %{--'</a>',--}%
            %{--' </sec:ifAnyGranted>',--}%
            %{--'<sec:ifNotGranted roles="${toolsService.getRoleSubMenu("/wallet/edit")}">',--}%
            %{--'<a class="m-r-10 grey " readonly="readonly">',--}%
            %{--'<span class="glyphicon glyphicon-pencil tooltips"></span>',--}%
            %{--'</a>',--}%
            %{--' </sec:ifNotGranted>'--}%

        ].join('');
    }


    $(document).ready(function () {
        waitingValidation = "";

        console.log("waitingValidation == " + waitingValidation);
        $table.bootstrapTable({
            url: "${createLink(controller: 'logActivity',action: 'loadData',params:[searchParams:searchParams])}",
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