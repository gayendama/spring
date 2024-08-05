<g:set var="toolsService" bean="habilitationService"/> <!DOCTYPE html>
<!--[if IE 8]> <html lang="fr" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="fr" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="fr">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8"/>
    <g:set var="entityName" value="${message(code: 'traitementBatch.extractionBic', default: 'Extraction  BIC')}"/>



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
                                    <sec:ifAnyGranted roles="${toolsService.getRoleSubMenu("/extractionBic/index")}">
                                        <li>
                                            <g:link action="index">
                                                <span class="glyphicon glyphicon-list-alt "></span>
                                                <g:message code="default.list.label" args="[entityNames]"/>
                                            </g:link>
                                            <i class="fa fa-circle"></i>
                                        </li>
                                    </sec:ifAnyGranted>
                                    <li>
                                        <span class="glyphicon glyphicon-plus "></span>
                                        <g:message code="extractionBic.label" args="[entityName]"/>
                                    </li>
                                </ul>
                            </div>
                            <!-- END PAGE BREADCRUMBS -->
                            <!-- BEGIN PAGE CONTENT INNER -->
                            <div class="page-content-inner">
                                <div class="row">
                                <!-- BEGIN FORM-->
                                    <g:form role="form" method="POST"  class="form-horizontal">
                                        <g:hiddenField name="id" value="" />
                                        <div class="col-md-12">
                                            <div class="portlet box green">
                                                <div class="portlet-title">
                                                    <div class="caption">
                                                        <span class="glyphicon glyphicon-plus "></span>
                                                        <span class="caption-subject bold uppercase"><g:message
                                                                code="traitementBatch.extractionBic"
                                                                args="[entityName]"/></span>
                                                    </div>

                                                    <div class="actions">
                                                        <g:actionSubmit name="create" action="launchExtractionBic"
                                                                        class="btn green-meadow min-100"
                                                                        value="${message(code: 'default.lancer.label', 'default': 'Lancer')}"/>
                                                        <g:link controller="batch" action="list" class="btn blue-steel min-100-marge">
                                                            <g:message code="default.cancel.label"
                                                                       default="Annuler"/>
                                                        </g:link>
                                                    </div>
                                                </div>

                                                <div class="portlet-body form">

                                                    <g:render template="/layouts/flashMessage"
                                                              model="[objetInstance: objetInstance]"/>


                                                    <div class="form-actions show-info"></div>

                                                    <div class="form-body">
                                                        <div class="row">
                                                            <div class="col-md-offset-1 col-md-4">
                                                                <div class="form-group form-md-line-input form-md-floating-label has-info">
                                                                    <label
                                                                            for="dateArrete"><g:message
                                                                            code="dateArrete.label"
                                                                            default="Date arrêté"/>
                                                                        <span class="required">*</span>
                                                                    </label>

                                                                    <g:select class="form-control select2"
                                                                              name="dateArreteId" id="dateArreteId"
                                                                              optionKey="id"
                                                                              optionValue="${{ it.dateArreteDisplay + ' - ' + it.mutuelleDisplay }}"
                                                                              from="${dateArreteList}"
                                                                              required="required"
                                                                              noSelection="['': '--- Choisir ---']"/>
                                                                </div>
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

    <!-- BEGIN INNER FOOTER -->
    <g:render template="/layouts/footer"/>
    <!-- END INNER FOOTER -->
</div>

<div class="quick-nav-overlay"></div>
<!-- END QUICK NAV -->

<asset:javascript src="metronic_v4_7_1_admin_3_rounded.js"/>
<asset:javascript src="lc_switch.js" />

<script>
    function traitementModal() {
        //alert("test "+jQuery('#dateArrete').val())

        if (jQuery('#dateArrete').val()) {
            jQuery.modal({
                content: '<p class="colx2-center "><h1> <blink> ${message(code: 'traitement.extractionBic.message', default: 'Traitement des extractions bic en cours !')} </blink></h1></p>' +
                    '<br><br>' +
                    '<div class="columns"><p class="colx0-left"><img id="img-spinner" src="${resource(dir:'images',file:'batch.gif')}" alt="Loading"/></p>' +
                    '<p class="colx2-left full-width"> <H2>${message(code: 'traitement.extractionBic.messageAttente', default: 'Veuillez patienter pendant le des extractions bic...')} </H2>  </p></div>',
                title: 'Traitement...',
                maxWidth: 500,
                buttons: {

                    //'Fermer': function(win) { win.closeModal(); }
                }
            });
        }
    }
</script>

</body>

</html>