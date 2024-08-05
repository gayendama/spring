<g:set var="toolsService" bean="habilitationService"/> <!DOCTYPE html>
<!--[if IE 8]> <html lang="fr" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="fr" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="fr">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8"/>
    <g:set var="entityName" value="${message(code: 'extourne.enMasse.label', default: 'Extourne en masse')}"/>
    <g:set var="entityNames" value="${message(code: 'extourne.enMasse.label', default: 'Extourne en masse')}"/>

    <title><g:message code="default.create.label" args="[entityName]"/></title>
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
                                        <g:link controller="dashboard" action="index"><i
                                                class="icon-home"></i> <g:message
                                                code="default.home.label" default="Accueil"/></g:link>
                                        <i class="fa fa-circle"></i>
                                    </li>
                                    <li>
                                        <span class="glyphicon glyphicon-plus "></span>
                                        <g:message code="extourne.enMasse.label" default="Extourne en masse"/>
                                    </li>
                                </ul>
                            </div>
                            <!-- END PAGE BREADCRUMBS -->
                            <!-- BEGIN PAGE CONTENT INNER -->
                            <div class="page-content-inner">
                                <div class="row">
                                <!-- BEGIN FORM-->
                                    <g:form enctype="multipart/form-data" role="form" method="POST"
                                            onsubmit="onSubmit('submit','${message(code: 'default.load.enregistrer.label', 'default': 'Traitement en cours...')}')"
                                            class="form-horizontal">
                                        <input type="hidden" name="appVersion" value="${appVersion}">
                                        <input type="hidden" name="dateTraitement" value="${dateTraitement}">
                                        <div class="col-md-12">
                                            <div class="portlet box green">
                                                <div class="portlet-title">
                                                    <div class="caption">
                                                        <span class="glyphicon glyphicon-plus "></span>
                                                        <span class="caption-subject bold uppercase">
                                                            <g:message code="extourne.enMasse.label" default="Extourne en masse"/>
                                                        </span>
                                                    </div>

                                                    <div class="actions">
                                                        <g:actionSubmit id="submit1"
                                                            action="launchExtourneEnMasse"
                                                            class="btn green-meadow min-100"
                                                            value="${message(code: 'default.lancer.label', 'default': 'Lancer')}"/>
                                                        <g:link controller="dashboard" action="index"
                                                                class="btn blue-steel min-100-marge"><g:message
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

                                                        <br><br>

                                                        <div class="marge-5-5">
                                                            <div class="row">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">

                                                                        <div class="col-md-8">
                                                                            <g:link controller="traitementBatch"
                                                                                    action="downloadModeleExtourneCsv">
                                                                                <i class="fa fa-download"></i>   <g:message
                                                                                    code="etudiant.fileModelDownload.label"
                                                                                    default="Télécharger le fichier modèle"/>
                                                                            </g:link>
                                                                        </div>

                                                                    </div>
                                                                </div>
                                                            </div>

                                                            <div class="row">

                                                                <div class="col-md-6">
                                                                    <div class="form-group form-md-line-input form-md-floating-label has-info">
                                                                        <label
                                                                                for="data"><g:message
                                                                                code="fichier.label"
                                                                                default="Fichier "/>
                                                                            <span class="required">*</span>
                                                                        </label>

                                                                        <div class="fileinput fileinput-new"
                                                                             data-provides="fileinput">
                                                                            <span class="btn green btn-file">
                                                                                <span class="fileinput-new">Select file</span>
                                                                                <span class="fileinput-exists">Change</span>
                                                                                <input type="file" name="data" id="data"
                                                                                       class="form-control"
                                                                                       required="required"/>
                                                                            </span>
                                                                            <span class="fileinput-filename"></span> &nbsp;
                                                                            <a href="javascript:;"
                                                                               class="close fileinput-exists"
                                                                               data-dismiss="fileinput"></a>

                                                                        </div>

                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <br>

                                                    </div>


                                                    <div class="form-actions">
                                                        <div class="row">
                                                            <div class="col-3-button">
                                                                <g:actionSubmit action="launchExtourneEnMasse" id="submit2"
                                                                                class="btn green-meadow min-100"
                                                                                value="${message(code: 'default.lancer.label', 'default': 'Lancer ')}"/>
                                                                <g:link controller="dashboard" action="index"
                                                                        class="btn blue-steel min-100-marge"><g:message
                                                                        code="default.cancel.label"
                                                                        default="Annuler"/>
                                                                </g:link>

                                                            </div>
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