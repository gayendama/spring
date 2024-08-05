<g:set var="toolsService" bean="habilitationService"/> <!DOCTYPE html>
<!--[if IE 8]> <html lang="fr" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="fr" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="fr">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8"/>
    <g:set var="entityName" value="${message(code: 'traitementBatch.label', default: 'traitement batch')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
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
                                <g:set var="securityUtils" bean="securityUtils"/>
                                Bureau: <h4
                                    style="font-weight: 400; margin-right: .5em; display: inline-block; color: #3b464f;">${session.bureauChoisi}</h4> <g:if
                                    test="${session.currentUser?.bureaux?.size() > 1}"><a
                                        class="tooltips"
                                        data-container="body"
                                        data-placement="bottom"
                                        data-original-title="${message(code: 'bureau.changeBureau.label', default: 'Changer de bureau')}"
                                        onclick='changeBureau()'>
                                    <i class="fa fa-exchange">&nbsp;&nbsp;&nbsp;</i></a>
                            </g:if>
                                <ul class="page-breadcrumb breadcrumb">
                                    <li>
                                        <g:link controller="dashboard" action="index"><i
                                                class="icon-home"></i> <g:message
                                                code="default.home.label" default="Accueil"/></g:link>
                                        <i class="fa fa-circle"></i>
                                    </li>
                                    <sec:ifAnyGranted
                                            roles="${toolsService.getRoleSubMenu("/traitementBatch/index")}">
                                        <li>
                                            <g:link action="index">
                                                <span class="glyphicon glyphicon-list-alt "></span>
                                                <g:message code="default.list.label" args="[entityName]"/>
                                            </g:link>
                                            <i class="fa fa-circle"></i>
                                        </li>
                                    </sec:ifAnyGranted>
                                    <li>
                                        <span class="glyphicon glyphicon-pencil "></span>
                                        <g:message code="default.edit.label" args="[entityName]"/>
                                    </li>
                                </ul>
                            </div>
                            <!-- END PAGE BREADCRUMBS -->
                            <!-- BEGIN PAGE CONTENT INNER -->
                            <div class="page-content-inner">
                                <div class="row">
                                <!-- BEGIN FORM-->
                                    <g:form role="form" method="PUT" class="form-horizontal">
                                        <g:hiddenField name="id" value="${traitementBatchInstance.id}"/>
                                        <div class="col-md-12">
                                            <div class="portlet box green">
                                                <div class="portlet-title">
                                                    <div class="caption">
                                                        <span class="glyphicon glyphicon-pencil "></span>
                                                        <span class="caption-subject bold uppercase"><g:message
                                                                code="default.edit.label"
                                                                args="[entityName]"/></span>
                                                    </div>

                                                    <div class="actions">
                                                        <g:actionSubmit action="update"
                                                                        class="btn green-meadow"
                                                                        value="${message(code: 'default.enregistrer.label', 'default': 'Enregistrer')}"/>
                                                        <g:link action="index" class="btn blue-hoki">
                                                            <g:message
                                                                    code="default.cancel.label"
                                                                    default="Annuler"/>
                                                        </g:link>
                                                    </div>
                                                </div>

                                                <div class="portlet-body form">

                                                    <g:render template="/layouts/flashMessage"
                                                              model="[objetInstance: objetInstance]"/>

                                                    <div class="form-actions show-info"></div>

                                                    <div class="form-body">
                                                        <g:render template="form"
                                                                  model="[traitementBatchInstance: traitementBatchInstance]"/>
                                                    </div>

                                                    <div class="form-actions">
                                                        <div class="row">
                                                            <div class="col-md-6">
                                                                <div class="row">
                                                                    <div class="col-md-offset-3 col-md-9">
                                                                        <g:actionSubmit
                                                                                action="update"
                                                                                class="btn green-meadow"
                                                                                value="${message(code: 'default.enregistrer.label', 'default': 'Enregistrer')}"/>
                                                                        <g:link action="index"
                                                                                class="btn blue-hoki"><g:message
                                                                                code="default.cancel.label"
                                                                                default="Annuler"/>
                                                                        </g:link>
                                                                    </div>
                                                                </div>
                                                            </div>

                                                            <div class="col-md-6"></div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </g:form>
                                <!-- END FORM-->
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