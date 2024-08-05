<g:set var="toolsService" bean="habilitationService"/> <!DOCTYPE html>
<!--[if IE 8]> <html lang="fr" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="fr" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="fr">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8"/>
    <g:set var="runtimeConfigService" bean="runtimeConfigService"/>
    <g:set var="entityName" value="${message(code: "activite.label", default: "Rcherche de logs")}"/>
    <title><g:message code="default.search.label" args="[entityName]"/></title>
    <meta name="layout" content="main"/>

    <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet"
          type="text/css"/>

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
                                        <span class="glyphicon glyphicon-search "></span>
                                        ${message(code: "logActivity.search.label", default: "Recherche de logs")}
                                    </li>
                                </ul>
                            </div>
                            <!-- END PAGE BREADCRUMBS -->
                            <!-- BEGIN PAGE CONTENT INNER -->
                            <div class="page-content-inner">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="portlet box green">
                                            <div class="portlet-title">
                                                <div class="caption">
                                                    <span class="glyphicon glyphicon-search "></span>
                                                    <span class="caption-subject bold uppercase">
                                                        ${message(code: "logActivity.search.label", default: "Recherche de logs")}
                                                    </span>
                                                </div>
                                            </div>

                                            <div class="portlet-body form">
                                                <g:render template="/layouts/flashMessage"
                                                          model="[objetInstance: objetInstance]"/>

                                            <!-- BEGIN FORM-->
                                                <g:form role="form" action="list" method="POST"
                                                        class="form-horizontal">

                                                    <g:hiddenField name="fromSearchView" value="1"/>
                                                    <div class="form-body">
                                                        <div class="row">
                                                            <g:render template="formSearch" model="[objetNameList: objetNameList, typeLogList: typeLogList]"/>
                                                        </div>

                                                    </div>

                                                    <div class="form-actions">
                                                        <div class="row">
                                                            <div class="col-3-button">
                                                                <g:submitButton name="search" action="list"
                                                                                class="btn blue min-100"
                                                                                value="${message(code: 'btn.search', 'default': 'Rechercher')}"/>

                                                            </div>
                                                        </div>
                                                    </div>
                                                </g:form>
                                            <!-- END FORM-->
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
    $(document).ready(function () {
        var action = ${isFormSearchCreditForValidation};
        if (action == true || action == "true") {
            $('#statut').prepend('<div class="disabled-select"></div>');
        }
    })
</script>

</body>

</html>