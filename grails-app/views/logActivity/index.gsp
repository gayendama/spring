<%@ page import="sn.sensoft.springbatch.TypeEntite" %>
<g:set var="toolsService" bean="habilitationService"/> <!DOCTYPE html>
<!--[if IE 8]> <html lang="fr" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="fr" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="fr">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8"/>
    <g:set var="entityName" value="${entityName}"/>
    <title><g:message code="default.historyActivity.label" args="[entityName]"/></title>
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
                                    style="font-weight: 400; margin-right: .5em; display: inline-block; color: #3b464f;">${session.bureauChoisi}</h4>
                                <g:if
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
                                    <li>
                                        <g:link controller="${entityController}" action="show" id="${entityId}">
                                            <span class="glyphicon glyphicon-list-alt "></span>
                                            <g:message code="default.show.label" args="[entityName]"/>
                                        </g:link>
                                        <i class="fa fa-circle"></i>
                                    </li>
                                    <li>
                                        <span class="glyphicon glyphicon-list-alt "></span>
                                        <g:message code="default.historyActivity.label" args="[entityName]"/>
                                    </li>
                                </ul>
                            </div>
                            <!-- END PAGE BREADCRUMBS -->
                            <!-- BEGIN PAGE CONTENT INNER -->
                            <div class="page-content-inner">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="portlet light portlet-fit portlet-datatable ">

                                            <div class="portlet-title">
                                                <div class="caption font-green">
                                                    <span class="glyphicon glyphicon-list-alt "></span>
                                                    <span class="caption-subject bold uppercase"><g:message
                                                            code="default.historyActivity.label"
                                                            args="[entityName]"/></span>
                                                </div>

                                                <div class="actions">

                                                    <g:link controller="${entityController}" action="show"
                                                            id="${entityId}" class="btn green-meadow">
                                                        <g:message code="default.return.label" default="show"/>
                                                    </g:link>
                                                </div>
                                            </div>

                                            <div class="portlet-body">
                                                <div class="table-container">
                                                    <table class="table table-striped table-bordered table-hover dt-responsive"
                                                           id="sample_2">
                                                        <thead>
                                                        <tr>
                                                            <th class="all noSort width5"></th>
                                                            <th class="all"><g:message
                                                                    code="logActivity.codeBureau.label"
                                                                    default="Code bureau"/></th>
                                                            <th><g:message code="logActivity.dateCreated.label"
                                                                           default="Date"/></th>
                                                            <th><g:message code="logActivity.message.label"
                                                                           default="Message"/></th>
                                                            <th><g:message code="logActivity.user.label"
                                                                           default="Utilisateur"/></th>
                                                            <th><g:message code="logActivity.ipAddressUser.label"
                                                                           default="IP adress"/></th>
                                                            <th><g:message code="logActivity.typeLog.label"
                                                                           default="Type log"/></th>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        <g:each in="${historyEntityList}"
                                                                var="historyEntity">
                                                            <tr>
                                                                <th></th>
                                                                <td>${fieldValue(bean: historyEntity, field: 'codeBureau')}</td>
                                                                <td>${historyEntity.dateCreated?.format("dd/MM/yyyy")}</td>
                                                                <td>${fieldValue(bean: historyEntity, field: 'message')}</td>
                                                                <td>${fieldValue(bean: historyEntity, field: 'userCreate')}</td>
                                                                <td>${fieldValue(bean: historyEntity, field: 'ipAddressUser')}</td>
                                                                <td>${fieldValue(bean: historyEntity, field: 'typeLog')}</td>

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