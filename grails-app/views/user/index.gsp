<!DOCTYPE html>
<!--[if IE 8]> <html lang="fr" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="fr" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="fr">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8"/>
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>

    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <meta content="" name="author"/>

    <asset:stylesheet href="font.css"/>
    <asset:stylesheet href="metronic_v4_7_1_admin_3_rounded.css"/>
    <asset:stylesheet href="intlTelInput.css"/>

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
                                    <!-- END PAGE BREADCRUMBS -->
                                    <!-- BEGIN PAGE CONTENT INNER -->
                                    <div class="page-content-inner">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="portlet light portlet-fit portlet-datatable ">

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

                                                    <div class="portlet-title">
                                                        <div class="caption font-green">
                                                            <span class="glyphicon glyphicon-list-alt "></span>
                                                            <span class="caption-subject bold uppercase"><g:message
                                                                    code="default.list.label"
                                                                    args="[entityName]"/></span>
                                                        </div>

                                                        <div class="actions">
                                                            <div class="btn-group btn-group-devided">
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
                                                                        <a href="javascript:;" data-action="0"
                                                                           class="tool-action">
                                                                            <i class="fa fa-file-pdf-o"></i> Imprimer
                                                                        </a>
                                                                    </li>
                                                                    <li>
                                                                        <a href="javascript:;" data-action="1"
                                                                           class="tool-action">
                                                                            <i class="fa fa-clone"></i> Copier</a>
                                                                    </li>
                                                                    <li>
                                                                        <a href="javascript:;" data-action="2"
                                                                           class="tool-action">
                                                                            <i class="fa fa-file-pdf-o"></i> PDF</a>
                                                                    </li>
                                                                    <li>
                                                                        <a href="javascript:;" data-action="3"
                                                                           class="tool-action">
                                                                            <i class="fa fa-file-excel-o"></i> Excel</a>
                                                                    </li>
                                                                    <li>
                                                                        <a href="javascript:;" data-action="4"
                                                                           class="tool-action">
                                                                            <i class="fa fa-file-excel-o"></i> CSV</a>
                                                                    </li>
                                                                </li>
                                                                </ul>
                                                            </div>
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
                                                                            code="user.username.label"
                                                                            default="Identifiant"/></th>
                                                                    <th><g:message code="user.nom.label"
                                                                                   default="Nom"/></th>
                                                                    <th><g:message
                                                                            code="user.prenom.label"
                                                                            default="Prénom"/></th>
                                                                    <th><g:message code="user.fonction.label"
                                                                                   default="Fonction"/></th>
                                                                    <th><g:message
                                                                            code="user.telephone.label"
                                                                            default="Téléphone"/></th>
                                                                    <th><g:message
                                                                            code="user.email.label"
                                                                            default="Email"/></th>
                                                                    <th class="text-center"><g:message
                                                                            code="user.enabled.label"
                                                                            default="Actif ?"/></th>
                                                                    <th class="all text-center col-md-1 noExport noSort"><g:message
                                                                            code="default.action.label"
                                                                            default="Actions"/>
                                                                </tr>
                                                                </thead>
                                                                <tbody>
                                                                <g:each in="${userList}" var="userInstance">
                                                                    <tr>
                                                                        <th></th>
                                                                        <td>
                                                                            <g:link action="show" class="tooltips"
                                                                                    data-container="body"
                                                                                    data-placement="left"
                                                                                    data-original-title="Consulter"
                                                                                    id="${userInstance.id}">
                                                                                ${fieldValue(bean: userInstance, field: 'username')}
                                                                            </g:link>
                                                                        </td>
                                                                        <td>${fieldValue(bean: userInstance, field: 'nom')}</td>
                                                                        <td>${fieldValue(bean: userInstance, field: 'prenom')}</td>
                                                                        <td>${fieldValue(bean: userInstance, field: 'fonction')}</td>
                                                                        <td>${fieldValue(bean: userInstance, field: 'telephone')}</td>
                                                                        <td>${fieldValue(bean: userInstance, field: 'email')}</td>
                                                                        <td class="text-center"><g:formatBoolean
                                                                                boolean="${userInstance.enabled}"/></td>
                                                                        <td class="text-center">
                                                                            <g:link action="show"
                                                                                    id="${userInstance.id}"><span
                                                                                    class="glyphicon glyphicon-eye-open tooltips"
                                                                                    data-container="body"
                                                                                    data-placement="left"
                                                                                    data-original-title="Consulter"></span></g:link>
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

<div class="quick-nav-overlay"></div>
<!-- END QUICK NAV -->

<asset:javascript src="metronic_v4_7_1_admin_3_rounded.js"/>
<asset:javascript src="intlTelInput.js"/>
<asset:javascript src="jquery.maskedinput.js"/>

<asset:javascript src="application.utils.js"/>

<script type="text/javascript">
    function checkPhoneNumber(phoneNumber, countryCode) {
        jQuery.ajax({
            type: "GET",
            processData: true,
            url: '${request.contextPath}/utils/checkPhoneNumber?phoneNumber=' + phoneNumber + '&countryCode=' + countryCode,
            dataType: "json",
            success: function (data) {
                if (data.isValidPhoneNumber) {
                    errorMsg.removeClass("hide");
                    errorMsg.removeClass("fa-warning");
                    errorMsg.addClass("fa-check-circle-o");
                    telephoneDiv.addClass("has-success");
                } else {
                    errorMsg.removeClass("hide");
                    errorMsg.removeClass("fa-check-circle-o");
                    errorMsg.addClass("fa-warning");
                    telephoneDiv.addClass("has-error");
                }
            },
            error: function (data) {
                errorMsg.removeClass("hide");
                errorMsg.removeClass("fa-check-circle-o");
                errorMsg.addClass("fa-warning");
                telephoneDiv.addClass("has-error");
            }
        });
    }
</script>

</body>

</html>