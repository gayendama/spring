<g:set var="toolsService" bean="habilitationService"/> <!DOCTYPE html>
<!--[if IE 8]> <html lang="fr" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="fr" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="fr">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8"/>
    <g:set var="entityName" value="${message(code: 'role.label', default: 'Profil')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
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
                                    <sec:ifAnyGranted
                                            roles="${toolsService.getRoleSubMenu("/role/index")}">
                                        <li>
                                            <g:link action="index">
                                                <span class="glyphicon glyphicon-list-alt "></span>
                                                <g:message code="default.list.label" args="[entityName]"/>
                                            </g:link>
                                            <i class="fa fa-circle"></i>
                                        </li>
                                    </sec:ifAnyGranted>
                                    <li>
                                        <span class="glyphicon glyphicon-eye-open "></span>
                                        <g:message code="default.show.label" args="[entityName]"/>
                                    </li>
                                </ul>
                            </div>
                            <!-- END PAGE BREADCRUMBS -->
                            <!-- BEGIN PAGE CONTENT INNER -->
                            <div class="page-content-inner">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="portlet box green">

                                            <g:render template="/layouts/flashMessage"
                                                      model="[objetInstance: objetInstance]"/>

                                            <div class="portlet-title">
                                                <div class="caption">
                                                    <i class="icon-users"></i>
                                                    <span class="caption-subject bold uppercase">${fieldValue(bean: roleInstance, field: 'authority')}</span>
                                                </div>

                                                <div class="actions">
                                                    <a class="btn btn-circle btn-icon-only default tooltips"
                                                       data-container="body" data-placement="top"
                                                       data-original-title="Modifier"
                                                       data-toggle="modal" href="#editRole">
                                                        <span class="glyphicon glyphicon-pencil"></span>
                                                    </a>
                                                </div>

                                                <div id="editRole" class="modal fade" tabindex="-1"
                                                     data-width="600">
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
                                                                        <g:form action="update"
                                                                                role="form" method="POST"
                                                                                class="form-horizontal">
                                                                            <g:hiddenField name="id"
                                                                                           value="${roleInstance.id}"/>

                                                                            <div class="form-body">
                                                                                <h3 class="form-section font-blue-madison bold"><g:message
                                                                                        code="default.info.label"
                                                                                        args="[entityName]"/></h3>

                                                                                <div class="row">
                                                                                    <div class="col-md-12">
                                                                                        <div class="form-group">
                                                                                            <label class="control-label col-md-2"
                                                                                                   for="authority"><g:message
                                                                                                    code="role.authority.label"
                                                                                                    default="Libellé"/>
                                                                                                <span class="required">*</span>
                                                                                            </label>

                                                                                            <div class="col-md-10">
                                                                                                <input type="text"
                                                                                                       name="authority"
                                                                                                       required="required"
                                                                                                       pattern="ROLE_[A-Za-z0-9_]+"
                                                                                                       class="form-control"
                                                                                                       placeholder="le rôle doit commercer par : ROLE_"
                                                                                                       title="Le rôle doit commercer par : ROLE_"
                                                                                                       value="${fieldValue(bean: roleInstance, field: 'authority')}"/>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>

                                                                                <div class="row">
                                                                                    <div class="col-md-12">
                                                                                        <div class="form-group">
                                                                                            <label class="control-label col-md-2"
                                                                                                   for="description"><g:message
                                                                                                    code="role.description.label"
                                                                                                    default="Description"/></label>

                                                                                            <div class="col-md-10">
                                                                                                <textarea
                                                                                                        name="description"
                                                                                                        class="form-control">${fieldValue(bean: roleInstance, field: 'description')}</textarea>
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
                                                                                                <g:submitButton
                                                                                                        name="edit"
                                                                                                        class="btn green-meadow"
                                                                                                        value="${message(code: 'default.enregistrer.label', 'default': 'Enregistrer')}"/>
                                                                                                <button class="cancel btn blue-hoki"
                                                                                                        data-dismiss="modal"
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

                                            </div>

                                            <div class="portlet-body form">

                                                <div class="form-horizontal">
                                                    <div class="form-actions">

                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="form-group">
                                                                    <label class="control-label col-md-12 text-left">
                                                                        ${fieldValue(bean: roleInstance, field: 'description')}
                                                                    </label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <div class="portlet light ">
                                                            <div class="portlet-title">
                                                                <div class="caption font-green">
                                                                    <g:message code="role.modules.label"
                                                                               default="Les modules accessibles"></g:message>
                                                                </div>

                                                                <div class="actions">
                                                                    <sec:ifAnyGranted
                                                                            roles="${toolsService.getRoleSubMenu("/role/edit")}">
                                                                        <g:link class="btn default green"
                                                                                action="edit"
                                                                                id="${roleInstance?.id}">
                                                                            <i class="icon-pencil"></i>
                                                                            <g:message code="editAccess.btn.label"
                                                                                       default="Éditer les droits d'accès"/>
                                                                        </g:link>
                                                                    </sec:ifAnyGranted>

                                                                </div>
                                                            </div>

                                                            <div class="portlet-body">
                                                                <fieldset>
                                                                    <div class="panel-group" id="accordion"
                                                                         role="tablist"
                                                                         aria-multiselectable="true">
                                                                        <g:each in="${moduleInstanceList}" var="module">
                                                                            <div class="panel panel-default">
                                                                                <div class="panel-heading" role="tab"
                                                                                     id="heading${module.id}">
                                                                                    <h4 class="panel-title">
                                                                                        <a role="button"
                                                                                           data-toggle="collapse"
                                                                                           data-parent="#accordion"
                                                                                           href="#collapse_${module.id}"
                                                                                           aria-expanded="true"
                                                                                           aria-controls="collapse_${module.id}"
                                                                                           onclick="respondToSelect('${module.id}', '${roleInstance?.id}')">
                                                                                            ${module}
                                                                                        </a>
                                                                                        <span class="indicator glyphicon glyphicon-chevron-down  pull-right"></span>
                                                                                    </h4>
                                                                                </div>

                                                                                <div id="collapse_${module.id}"
                                                                                     class="panel-collapse collapse">
                                                                                    <div class="panel-body">
                                                                                        <svg width='30px'
                                                                                             height='30px'
                                                                                             xmlns="http://www.w3.org/2000/svg"
                                                                                             viewBox="0 0 100 100"
                                                                                             preserveAspectRatio="xMidYMid"
                                                                                             class="uil-facebook">
                                                                                            <rect x="0" y="0"
                                                                                                  width="100"
                                                                                                  height="100"
                                                                                                  fill="#ffffff"
                                                                                                  class="bk"></rect>
                                                                                            <g transform="translate(20 50)">
                                                                                                <rect x="-10"
                                                                                                      y="-30"
                                                                                                      width="20"
                                                                                                      height="60"
                                                                                                      fill="#3769c8"
                                                                                                      opacity="0.6">
                                                                                                    <animateTransform
                                                                                                            attributeName="transform"
                                                                                                            type="scale"
                                                                                                            from="2"
                                                                                                            to="1"
                                                                                                            begin="0s"
                                                                                                            repeatCount="indefinite"
                                                                                                            dur="1s"
                                                                                                            calcMode="spline"
                                                                                                            keySplines="0.1 0.9 0.4 1"
                                                                                                            keyTimes="0;1"
                                                                                                            values="2;1"></animateTransform>
                                                                                                </rect>
                                                                                            </g>
                                                                                            <g transform="translate(50 50)">
                                                                                                <rect x="-10"
                                                                                                      y="-30"
                                                                                                      width="20"
                                                                                                      height="60"
                                                                                                      fill="#3769c8"
                                                                                                      opacity="0.8">
                                                                                                    <animateTransform
                                                                                                            attributeName="transform"
                                                                                                            type="scale"
                                                                                                            from="2"
                                                                                                            to="1"
                                                                                                            begin="0.1s"
                                                                                                            repeatCount="indefinite"
                                                                                                            dur="1s"
                                                                                                            calcMode="spline"
                                                                                                            keySplines="0.1 0.9 0.4 1"
                                                                                                            keyTimes="0;1"
                                                                                                            values="2;1"></animateTransform>
                                                                                                </rect>
                                                                                            </g>
                                                                                            <g transform="translate(80 50)">
                                                                                                <rect x="-10"
                                                                                                      y="-30"
                                                                                                      width="20"
                                                                                                      height="60"
                                                                                                      fill="#3769c8"
                                                                                                      opacity="0.9">
                                                                                                    <animateTransform
                                                                                                            attributeName="transform"
                                                                                                            type="scale"
                                                                                                            from="2"
                                                                                                            to="1"
                                                                                                            begin="0.2s"
                                                                                                            repeatCount="indefinite"
                                                                                                            dur="1s"
                                                                                                            calcMode="spline"
                                                                                                            keySplines="0.1 0.9 0.4 1"
                                                                                                            keyTimes="0;1"
                                                                                                            values="2;1"></animateTransform>
                                                                                                </rect>
                                                                                            </g>
                                                                                        </svg>

                                                                                        <div id="menu${module.id}"></div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </g:each>

                                                                    </div>

                                                                </fieldset>

                                                                %{--<div class="panel-group accordion"--}%
                                                                %{--id="accordion">--}%
                                                                %{--<g:if test="${moduleInstanceList.empty}">--}%
                                                                %{--${message(code: 'role.noModule.info', default: '<span class=\'font-red-mint\'> <cite>Aucun module n\'est lié à ce profil</cite> </span>')}--}%
                                                                %{--</g:if>--}%
                                                                %{--<g:else>--}%
                                                                %{--<g:each in="${moduleInstanceList}"--}%
                                                                %{--var="module">--}%
                                                                %{--<div class="panel panel-default">--}%
                                                                %{--<div class="panel-heading">--}%
                                                                %{--<h4 class="panel-title">--}%
                                                                %{--<a class="accordion-toggle accordion-toggle-styled"--}%
                                                                %{--data-toggle="collapse"--}%
                                                                %{--data-parent="#accordion"--}%
                                                                %{--href="#collapse_${module.id}"--}%
                                                                %{--onclick="respondToSelect('${module.id}', '${roleInstance?.id}')">${module}</a>--}%
                                                                %{--</h4>--}%
                                                                %{--</div>--}%

                                                                %{--<div id="collapse_${module.id}"--}%
                                                                %{--class="panel-collapse collapse">--}%
                                                                %{--<div class="panel-body">--}%
                                                                %{--<div id="menu${module.id}"></div>--}%
                                                                %{--</div>--}%
                                                                %{--</div>--}%
                                                                %{--</div>--}%
                                                                %{--</g:each>--}%
                                                                %{--</g:else>--}%
                                                                %{--</div>--}%
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- END PAGE CONTENT INNER -->
                        </div>
                        <!-- END CONTENT BODY -->
                    </div>
                    <!-- END CONTENT -->
                </div>
                <!-- END CONTAINER -->
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
    $('a[data-toggle="collapse"]').click(function () {
        if ($(this).closest('div.panel-heading').next('div.collapse').hasClass('in')) {
            $(this).next('span').removeClass('glyphicon-chevron-up').addClass('glyphicon-chevron-down');
        } else {
            $('a[data-toggle="collapse"]').next('span').removeClass('glyphicon-chevron-up').addClass('glyphicon-chevron-down');

            $(this).next('span').addClass('glyphicon-chevron-up').removeClass('glyphicon-chevron-down');
        }

        $(".uil-facebook").show();

    });

    function respondToSelect(moduleid, idRole) {
        if ($("#collapse_" + moduleid).hasClass("collapse")) {
            $(".uil-facebook").show();
            $.ajax({
                type: "POST",
                processData: true,
                url: "${request.contextPath}/role/orgGetParentNode",
                data: {idModule: moduleid, idRole: idRole},
                dataType: "html",
                success: function (data) {
                    $(".uil-facebook").hide();
                    $(".panel-body:gt(0)").css("text-align", "initial");
                    $("#menu" + moduleid).html(data);
                    document.getElementById('menu' + moduleid).style.display = "inline";
                } //end success
            });
        }
    }

    function hideModalDel() {
        console.log("Hide  onLoading");
        $('#deleteRole').modal('hide');
    }
</script>

</body>

</html>