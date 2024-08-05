<!DOCTYPE html>
<!--[if IE 8]> <html lang="fr" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="fr" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="fr">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8"/>
    <g:set var="entityName" value="${message(code: 'role.label', default: 'Profil')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <meta content="" name="author"/>

    <asset:stylesheet href="font.css"/>
    <asset:stylesheet href="metronic_v4_7_1_admin_3_rounded.css"/>
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
                    <!-- BEGIN PAGE HEAD-->
                    <div class="page-content">
                        <div class="container">
                            <!-- BEGIN PAGE CONTENT BODY -->
                            <div class="page-content">
                                <div class="container">

                                    <!-- BEGIN PAGE BREADCRUMBS -->
                                    <ul class="page-breadcrumb breadcrumb">
                                        <li>
                                            <g:link controller="dashboard" action="index"><i
                                                    class="icon-home"></i> <g:message
                                                    code="default.home.label" default="Accueil"/></g:link>
                                            <i class="fa fa-circle"></i>
                                        </li>
                                        <li>
                                            <g:link controller="role" action="index">
                                                <span class="glyphicon glyphicon-list-alt "></span>
                                                <g:message code="default.list.label" args="[entityName]"/>
                                            </g:link>
                                            <i class="fa fa-circle"></i>
                                        </li>
                                        <li>
                                            <span class="glyphicon glyphicon-plus "></span>
                                            <g:message code="default.create.label" args="[entityName]"/>
                                        </li>
                                    </ul>
                                    <!-- END PAGE BREADCRUMBS -->
                                    <!-- BEGIN PAGE CONTENT INNER -->
                                    <div class="page-content-inner">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="portlet box green">
                                                    <div class="portlet-title">
                                                        <div class="caption">
                                                            <span class="glyphicon glyphicon-plus "></span>
                                                            <span class="caption-subject bold uppercase"><g:message
                                                                    code="default.edit.label"
                                                                    args="[entityName]"/></span>
                                                        </div>
                                                    </div>

                                                    <div class="portlet-body form">
                                                        <g:if test="${flash.message}">
                                                            <div class="alert alert-success alert-dismissable">
                                                                <button type="button" class="close"
                                                                        data-dismiss="alert"
                                                                        aria-hidden="true"></button>
                                                                <strong>Succès !</strong> ${flash.message}.
                                                            </div>
                                                        </g:if>
                                                        <g:if test="${flash.error}">
                                                            <div class="alert alert-danger alert-dismissable">
                                                                <button type="button" class="close"
                                                                        data-dismiss="alert"
                                                                        aria-hidden="true"></button>
                                                                <strong>Erreur !</strong> ${flash.error}.
                                                            </div>
                                                        </g:if>
                                                        <g:if test="${flash.warning}">
                                                            <div class="alert alert-warning alert-dismissable">
                                                                <button type="button" class="close"
                                                                        data-dismiss="alert"
                                                                        aria-hidden="true"></button>
                                                                <strong>Attention !</strong> ${flash.warning}.
                                                            </div>
                                                        </g:if>
                                                        <g:if test="${flash.info}">
                                                            <div class="alert alert-info alert-dismissable">
                                                                <button type="button" class="close"
                                                                        data-dismiss="alert"
                                                                        aria-hidden="true"></button>
                                                                <strong>Information !</strong> ${flash.info}.
                                                            </div>
                                                        </g:if>

                                                    <!-- BEGIN FORM-->
                                                        <g:form resource="${roleInstance}"
                                                                role="form" method="POST" class="form-horizontal">

                                                            <div class="form-actions top right">
                                                                <g:actionSubmit  action="update"
                                                                                class="btn green-meadow"
                                                                                value="${message(code: 'enregistrer', 'default': 'Enregistrer')}"/>
                                                                <g:link action="index" class="btn blue-hoki">
                                                                    <g:message code="default.cancel.label"
                                                                               default="Annuler"/>
                                                                </g:link>
                                                            </div>

                                                            <div class="form-body">
                                                                <g:render template="form"/>
                                                            </div>

                                                            <div class="form-actions">
                                                                <div class="row">
                                                                    <div class="col-md-6">
                                                                        <div class="row">
                                                                            <div class="col-md-offset-3 col-md-9">
                                                                                <g:submitButton name="edit"
                                                                                                action="update"
                                                                                                class="btn green-meadow"
                                                                                                value="${message(code: 'enregistrer', 'default': 'Enregistrer')}"/>
                                                                                <g:link action="index"
                                                                                        class="btn blue-hoki">
                                                                                    <g:message
                                                                                            code="default.cancel.label"
                                                                                            default="Annuler"/>
                                                                                </g:link>
                                                                            </div>
                                                                        </div>
                                                                    </div>

                                                                    <div class="col-md-6"></div>
                                                                </div>
                                                            </div>
                                                        </g:form>
                                                    <!-- END FORM-->
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- END PAGE CONTENT INNER -->
                                    </div>
                                </div>
                                <!-- END PAGE CONTENT BODY -->
                            </div>
                        </div>
                        <!-- END PAGE HEAD-->
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
</div>

<div class="quick-nav-overlay"></div>
<!-- END QUICK NAV -->

<asset:javascript src="metronic_v4_7_1_admin_3_rounded.js"/>

</body>

</html>