<g:set var="toolsService" bean="habilitationService"/> <!DOCTYPE html>
<!--[if IE 8]> <html lang="fr" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="fr" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="fr">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8"/>
    <g:set var="entityName" value="${message(code: 'role.label', default: 'Role')}"/>
    <g:set var="entityNames" value="${message(code: 'roles.label', default: 'Profil')}"/>

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
                                                           </li>                                                         </ul>
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
                                                                    code="role.authority.label"
                                                                    default="Intitulé"/></th>
                                                            <th><g:message code="role.description.label"
                                                                           default="Description"/></th>
                                                            <th class="all text-center col-md-1 noExport noSort"><g:message
                                                                    code="default.action.label"
                                                                    default="Actions"/>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        <g:each in="${roleList}" var="roleInstance">
                                                            <tr>
                                                                <th></th>
                                                                <td>
                                                                    <sec:ifAnyGranted roles="${toolsService.getRoleSubMenu("/role/show")}">
                                                                        <g:link action="show" class="tooltips"
                                                                                data-container="body"
                                                                                data-placement="left"
                                                                                data-original-title="Consulter"
                                                                                id="${roleInstance.id}">
                                                                            ${fieldValue(bean: roleInstance, field: 'authority')}
                                                                        </g:link>
                                                                    </sec:ifAnyGranted>
                                                                    <sec:ifNotGranted roles="${toolsService.getRoleSubMenu("/role/show")}">
                                                                        <a disabled="disabled" class="tooltips grey"
                                                                                data-container="body"
                                                                                data-placement="left"
                                                                                data-original-title="Consulter"
                                                                                id="${roleInstance.id}">
                                                                            ${fieldValue(bean: roleInstance, field: 'authority')}
                                                                        </a>
                                                                    </sec:ifNotGranted>

                                                                </td>
                                                                <td>${fieldValue(bean: roleInstance, field: 'description')}</td>
                                                                <td class="text-center">

                                                                    <sec:ifAnyGranted roles="${toolsService.getRoleSubMenu("/role/show")}">
                                                                        <g:link action="show"
                                                                                id="${roleInstance.id}"><span
                                                                                class="glyphicon glyphicon-eye-open tooltips"
                                                                                data-container="body"
                                                                                data-placement="left"
                                                                                data-original-title="Consulter"></span></g:link>
                                                                        &nbsp;&nbsp;&nbsp;
                                                                    </sec:ifAnyGranted>
                                                                    <sec:ifNotGranted roles="${toolsService.getRoleSubMenu("/role/show")}">
                                                                        <a class="grey"  disabled="disabled"
                                                                                id="${roleInstance.id}"><span
                                                                                class="glyphicon glyphicon-eye-open tooltips"
                                                                                data-container="body"
                                                                                data-placement="left"
                                                                                data-original-title="Consulter"></span></a>
                                                                        &nbsp;&nbsp;&nbsp;
                                                                    </sec:ifNotGranted>
                                                                    <sec:ifAnyGranted roles="${toolsService.getRoleSubMenu("/role/editRole")}">
                                                                        <a onclick="edition('${roleInstance.authority}', '${roleInstance.description}', '${roleInstance.id}')">
                                                                            <span
                                                                                    class="glyphicon glyphicon-pencil tooltips"
                                                                                    data-container="body"
                                                                                    data-placement="right"
                                                                                    data-original-title="Modifier"></span>
                                                                        </a>
                                                                    </sec:ifAnyGranted>
                                                                    <sec:ifNotGranted roles="${toolsService.getRoleSubMenu("/role/editRole")}">
                                                                        <a class="grey"  disabled="disabled">
                                                                            <span
                                                                                    class="glyphicon glyphicon-pencil tooltips"
                                                                                    data-container="body"
                                                                                    data-placement="right"
                                                                                    data-original-title="Modifier"></span>
                                                                        </a>
                                                                    </sec:ifNotGranted>

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
                    <!-- END CONTENT BODY -->
                </div>
                <!-- END CONTENT -->
            </div>
            <!-- END CONTAINER -->
        </div>
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
                                    role="form" method="PUT"
                                    class="form-horizontal">

                                <g:hiddenField name="id" id="idRole" value=""/>
                                <div class="form-body">
                                    <h3 class="form-section font-blue-madison bold"><g:message code="default.info.label"
                                                                                               args="[entityName]"/></h3>

                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label class="control-label col-md-2"
                                                       for="authority"><g:message
                                                        code="role.authority.label"
                                                        default="Intitulé"/>
                                                    <span class="required">*</span>
                                                </label>

                                                <div class="col-md-10">
                                                    <input type="text"
                                                           name="authority"
                                                           id="authority"
                                                           required="required"
                                                           pattern="ROLE_[A-Za-z0-9_]+"
                                                           class="form-control"
                                                           placeholder="le rôle doit commercer par : ROLE_"
                                                           title="Le rôle doit commercer par : ROLE_"
                                                           value=""/>
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
                                                            name="description" id="description"
                                                            class="form-control"></textarea>
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

    <!-- BEGIN INNER FOOTER -->
    <g:render template="/layouts/footer"/>
    <!-- END INNER FOOTER -->
</div>

<div class="quick-nav-overlay"></div>
<!-- END QUICK NAV -->

<asset:javascript src="metronic_v4_7_1_admin_3_rounded.js"/>

<script type="text/javascript">
    function edition(authority, description, rowid) {
        $("#authority").val(authority);
        $("#description").val(description);
        $("#idRole").val(rowid);
        $("#editRole").modal("show");
    }
</script>

</body>

</html>