<%@ page import="sn.sensoft.springbatch.securite.Role" %>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="fr" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="fr" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="fr">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8"/>
    <g:set var="entityName" value="${message(code: 'user.label', default: 'Utilisateur')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
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
                                            <g:link controller="user" action="index">
                                                <span class="glyphicon glyphicon-list-alt "></span>
                                                <g:message code="default.list.label" args="[entityName]"/>
                                            </g:link>
                                            <i class="fa fa-circle"></i>
                                        </li>
                                        <li>
                                            <span class="glyphicon glyphicon-eye-open "></span>
                                            <g:message code="default.show.label" args="[entityName]"/>
                                        </li>
                                    </ul>

                                    <div class="page-content-inner">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <g:render template="/layouts/flashMessage"
                                                          model="[objetInstance: objetInstance]"/>


                                                <!-- BEGIN PROFILE SIDEBAR -->
                                                <div class="profile-sidebar">
                                                    <!-- PORTLET MAIN -->
                                                    <div class="portlet light profile-sidebar-portlet ">
                                                        <!-- SIDEBAR USERPIC -->
                                                        <div class="profile-userpic">
                                                            <g:if test="${userInstance?.enabled == true }">
                                                                <asset:image class="img-responsive"
                                                                             src="user-enabled.png"
                                                                             alt="logo"/>
                                                            </g:if>
                                                            <g:else>
                                                                <asset:image class="img-responsive"
                                                                             src="user-disabled.png"
                                                                             alt="logo"/>
                                                            </g:else>
                                                        </div>
                                                        <!-- END SIDEBAR USERPIC -->
                                                        <!-- SIDEBAR USER TITLE -->
                                                        <div class="profile-usertitle">
                                                            <div class="profile-usertitle-name">${fieldValue(bean: userInstance, field: 'username')}</div>

                                                            <div class="profile-usertitle-job">
                                                                <g:if test="${!userInstance?.nom && !userInstance?.prenom}">
                                                                    <small style="display: block; font-size: 80%; line-height: 1.42857;  color: #777; font-weight: normal;">
                                                                        <cite title="Source Title">Nom - Prénom</cite>
                                                                    </small>
                                                                </g:if>
                                                                <g:else>
                                                                    ${fieldValue(bean: userInstance, field: 'nom')} ${fieldValue(bean: userInstance, field: 'prenom')}
                                                                </g:else>
                                                            </div>
                                                        </div>
                                                        <!-- END SIDEBAR USER TITLE -->

                                                        <div class="portlet light " style="margin-bottom: 0px">
                                                            <div class="profile-desc-link bg-grey-steel bg-font-grey-cararra">
                                                                <i class="fa fa-bookmark"></i>${fieldValue(bean: userInstance, field: 'fonction')}
                                                            </div>

                                                            <div class="margin-top-10 profile-desc-link bg-grey-steel bg-font-grey-cararra">
                                                                <i class="fa fa-phone"></i>${fieldValue(bean: userInstance, field: 'telephone')}
                                                            </div>

                                                            <div class="margin-top-10 profile-desc-link bg-grey-steel bg-font-grey-cararra">
                                                                <i class="fa fa-at"></i>${fieldValue(bean: userInstance, field: 'email')}
                                                            </div>




                                                        </div>

                                                        <!-- SIDEBAR BUTTONS -->
                                                        <div class="profile-userbuttons bg-grey-steel bg-font-grey-cararra"
                                                             style="padding: 10px">

                                                        </div>
                                                        %{--<!-- END SIDEBAR BUTTONS -->--}%
                                                    </div>
                                                    <!-- END PORTLET MAIN -->
                                                </div>
                                                <!-- END BEGIN PROFILE SIDEBAR -->
                                                <!-- BEGIN PROFILE CONTENT -->
                                                <div class="profile-content">
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <!-- BEGIN PORTLET -->
                                                            <div class="portlet light ">

                                                                <div class="portlet-title tabbable-line">
                                                                    <div class="caption caption-md">
                                                                        <i class="icon-globe theme-font hide"></i>
                                                                        <span class="caption-subject font-blue-madison bold uppercase">Parametres du compte</span>
                                                                    </div>
                                                                    <ul class="nav nav-tabs">
                                                                        <li class="active">
                                                                            <a href="#tab_1"
                                                                               data-toggle="tab">Profils affectés</a>
                                                                        </li>

                                                                    </ul>
                                                                </div>

                                                                <div class="portlet-body">
                                                                    <!--BEGIN TABS-->
                                                                    <div class="tab-content scroller"
                                                                         style="height: 385px;">
                                                                        <div class="tab-pane active" id="tab_1">

                                                                            <div data-always-visible="1"
                                                                                 data-rail-visible1="0"
                                                                                 data-handle-color="#D7DCE2"
                                                                                 style="padding-top: 50px;">
                                                                                <ul class="feeds">
                                                                                    <g:each in="${userInstance.userRoles}"
                                                                                            var="userRoleInstance">
                                                                                        <li>
                                                                                            <div class="col1">
                                                                                                <div class="cont">
                                                                                                    <div class="cont-col1">
                                                                                                        <div class="label label-sm label-success">
                                                                                                            <i class="fa fa-filter"></i>
                                                                                                        </div>
                                                                                                    </div>

                                                                                                    <div class="cont-col2">
                                                                                                        <div class="desc">
                                                                                                            <strong>${userRoleInstance.role.authority}</strong>
                                                                                                            <g:if test="${userRoleInstance.role.description}">
                                                                                                                <cite>(${userRoleInstance.role.description})</cite>
                                                                                                            </g:if>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>


                                                                                        </li>
                                                                                    </g:each>

                                                                                </ul>
                                                                            </div>
                                                                        </div>


                                                                    </div>
                                                                    <!--END TABS-->
                                                                </div>
                                                            </div>
                                                            <!-- END PORTLET -->
                                                        </div>
                                                    </div>

                                                </div>
                                                <!-- END PROFILE CONTENT -->
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- END PAGE CONTENT BODY -->
                            </div>
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

<div id="modalDisableUser" class="sweet-alert showSweetAlert visible fade" tabindex="-1"
     style="display: block; margin-top: -142px;">
    <div class="sa-icon sa-error animateErrorIcon"
         style="display: block;">
        <span class="sa-x-mark animateXMark">
            <span class="sa-line sa-left"></span>
            <span class="sa-line sa-right"></span>
        </span>
    </div>

    <h2>Voulez-vous désactiver le compte de cet utilisateur ?</h2>

    <div class="sa-button-container">
        <g:form resource="${userInstance}" action="update"
                role="form" method="POST" class="form-horizontal">

            <g:hiddenField name="enabled" value="false"/>
            <button class="cancel btn btn-lg blue-hoki" data-dismiss="modal" tabindex="2"
                    style="display: inline-block;">Retour</button>

            <div class="sa-confirm-button-container">
                <button class="confirm btn btn-lg btn-danger" tabindex="1"
                        style="display: inline-block;">Confirmer</button>
            </div>
        </g:form>
    </div>
</div>

<div id="modalEnableUser" class="sweet-alert showSweetAlert visible fade" tabindex="-1"
     style="display: block; margin-top: -131px;">

    <div class="sa-icon sa-success animate" style="display: block;">
        <span class="sa-line sa-tip animateSuccessTip"></span>
        <span class="sa-line sa-long animateSuccessLong"></span>

        <div class="sa-placeholder"></div>

        <div class="sa-fix"></div>
    </div>

    <h2>Voulez-vous activer le compte de cet utilisateur ?</h2>

    <div class="sa-button-container">
        <g:form resource="${userInstance}" action="update"
                role="form" method="POST" class="form-horizontal">

            <g:hiddenField name="enabled" value="true"/>
            <button class="cancel btn btn-lg btn blue-hoki" data-dismiss="modal" tabindex="2"
                    style="display: inline-block;">Retour</button>

            <div class="sa-confirm-button-container">
                <button class="confirm btn btn-lg btn-success" tabindex="1"
                        style="display: inline-block;">Confirmer</button>
            </div>
        </g:form>
    </div>
</div>

<div id="modalReinitPassword" class="sweet-alert showSweetAlert visible fade" tabindex="-1"
     style="display: block; margin-top: -131px;">

    <div class="sa-icon sa-warning pulseWarning" style="display: block;">
        <span class="sa-body pulseWarningIns"></span>
        <span class="sa-dot pulseWarningIns"></span>
    </div>

    <h2>Voulez-vous réinitialiser le mot de passe de cet utilisateur ?</h2>

    <div class="sa-button-container">
        <g:form resource="${userInstance}" action="reinitPassword"
                role="form" method="POST" class="form-horizontal">

            <button class="cancel btn btn-lg btn blue-hoki" data-dismiss="modal" tabindex="2"
                    style="display: inline-block;">Retour</button>

            <div class="sa-confirm-button-container">
                <button class="confirm btn btn-lg btn-warning" tabindex="1"
                        style="display: inline-block;">Confirmer</button>
            </div>
        </g:form>
    </div>
</div>

<div id="modalEditUser" class="modal fade" tabindex="-1" data-width="560">
    <div class="modal-content">
        <div class="page-content-inner">
            <div class="row">
                <div class="col-md-12">
                    <div class="portlet box green">
                        <div class="portlet-title">
                            <div class="caption">
                                <span class="glyphicon glyphicon-pencil "></span>
                                <span class="caption-subject bold uppercase"><g:message
                                        code="default.edit.label"
                                        args="[entityName]"/></span>
                            </div>
                        </div>
                    </div>

                    <div class="portlet-body form">
                    <!-- BEGIN FORM-->
                        <g:form resource="${userInstance}" action="update"
                                role="form" method="POST" class="form-horizontal">

                            <div class="form-body">
                                <g:render template="form"/>
                            </div>

                            <div class="form-actions">
                                <div class="row">
                                    <div class="col-md-9">
                                        <div class="row">
                                            <div class="col-md-offset-3 col-md-9">
                                                <g:submitButton name="edit"
                                                                class="btn green-meadow"
                                                                value="${message(code: 'enregistrer', 'default': 'Enregistrer')}"/>
                                                <button class="cancel btn blue-hoki" data-dismiss="modal" tabindex="2"
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



<div id="modalAddUserRole" class="modal fade" tabindex="-1" data-width="600">
    <div class="modal-content">
        <div class="page-content-inner">
            <div class="row">
                <div class="col-md-12">
                    <div class="portlet box green">
                        <div class="portlet-title">
                            <div class="caption">
                                <span class="glyphicon glyphicon-plus "></span>
                                <span class="caption-subject bold uppercase">
                                    <g:message code="default.add.label" args="['Profil']"/>
                                </span>
                            </div>
                        </div>
                    </div>

                    <div class="portlet-body form">
                    <!-- BEGIN FORM-->
                        <g:form resource="${userInstance}" action="addUserRole"
                                role="form" method="POST" class="form-horizontal">

                            <div class="form-body">
                                <h3 class="form-section"><g:message code="default.info.label" args="['Profil']"/></h3>

                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <div class="col-md-12">
                                                <g:select id="roles" name="roles" from="${Role.list().sort { it.authority }}"
                                                          class="form-control select2-multiple select2-hidden-accessible"
                                                          value="${userInstance?.authorities}" multiple=""/>
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
                                                                value="${message(code: 'enregistrer', 'default': 'Enregistrer')}"/>
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

<div class="quick-nav-overlay"></div>
<!-- END QUICK NAV -->

<asset:javascript src="metronic_v4_7_1_admin_3_rounded.js"/>
<asset:javascript src="intlTelInput.js"/>
<asset:javascript src="jquery.maskedinput.js"/>

<asset:javascript src="application.utils.js"/>

<script type="text/javascript">

    $(document).ready(function(){
        $("#lienServeurDidentite").click(function() {

            window.location="http://127.0.0.1:8080/auth/realms/aicha/account/password";
        });
    });

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