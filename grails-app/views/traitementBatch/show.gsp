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
                                            <div class="portlet-title">
                                                <div class="caption">
                                                    <span class="glyphicon glyphicon-eye-open "></span>
                                                    <span class="caption-subject bold uppercase"><g:message
                                                            code="default.show.label"
                                                            args="[entityName]"/></span>
                                                </div>
                                            </div>

                                            <div class="portlet-body form">
                                                <g:render template="/layouts/flashMessage"
                                                          model="[objetInstance: objetInstance]"/>


                                                <!-- BEGIN FORM-->
                                                <div class="form-horizontal">

                                                    <div class="form-actions top right show-info"></div>

                                                    <div class="form-body">
                                                        <h3 class="form-section font-blue-madison bold"><g:message
                                                                code="default.info.label"
                                                                args="[entityName]"/></h3>
                                                        <g:form role="form" method="POST"
                                                                class="form-horizontal">

                                                            <div class="pull-right"
                                                                 style="margin-top: -70px">
                                                                <g:actionSubmit
                                                                        action="edit"
                                                                        class="btn blue"
                                                                        value="${message(code: 'default.button.edit.label', 'default': 'Modifier')}"/>
                                                                <g:link action="index"
                                                                        class="btn blue-hoki">
                                                                    <g:message
                                                                            code="default.cancel.label"
                                                                            default="Retour"/>
                                                                </g:link>
                                                            </div>

                                                            <div class="row">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label class="control-label col-md-4"
                                                                               for="numeroSequence"><g:message
                                                                                code="traitementBatch.numeroSequence.label"
                                                                                default="Numéro sequence"/> :
                                                                        </label>

                                                                        <div class="col-md-8 bg-grey-steel bg-font-grey-cararra">
                                                                            <p class="form-control-static">${fieldValue(bean: traitementBatchInstance, field: 'numeroSequence')}</p>
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label class="control-label col-md-4"
                                                                               for="codeProgramme"><g:message
                                                                                code="traitementBatch.codeProgramme.label"
                                                                                default="Code Programme"/> :
                                                                        </label>

                                                                        <div class="col-md-8 bg-grey-steel bg-font-grey-cararra">
                                                                            <p class="form-control-static">${fieldValue(bean: traitementBatchInstance, field: 'codeProgramme')}</p>
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                            </div>

                                                            <div class="row">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label class="control-label col-md-4"
                                                                               for="programmeALancer"><g:message
                                                                                code="traitementBatch.programmeALancer.label"
                                                                                default="Programme a lancer"/> :
                                                                        </label>

                                                                        <div class="col-md-8 bg-grey-steel bg-font-grey-cararra">
                                                                            <p class="form-control-static">${fieldValue(bean: traitementBatchInstance, field: 'programmeALancer')}</p>
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label class="control-label col-md-4"
                                                                               for="majRealisee"><g:message
                                                                                code="traitementBatch.majRealisee.label"
                                                                                default="MAJ réalisée"/> :
                                                                        </label>

                                                                        <div class="col-md-8 bg-grey-steel bg-font-grey-cararra">
                                                                            <p class="form-control-static">${fieldValue(bean: traitementBatchInstance, field: 'majRealisee')}</p>
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                            </div>

                                                            <div class="row">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label class="control-label col-md-4"
                                                                               for="numeroEtape"><g:message
                                                                                code="traitementBatch.numeroEtape.label"
                                                                                default="Numéro etape"/> :
                                                                        </label>

                                                                        <div class="col-md-8 bg-grey-steel bg-font-grey-cararra">
                                                                            <p class="form-control-static">${fieldValue(bean: traitementBatchInstance, field: 'numeroEtape')}</p>
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label class="control-label col-md-4"
                                                                               for="dateComptable"><g:message
                                                                                code="traitementBatch.dateComptable.label"
                                                                                default="Date comptable"/> :
                                                                        </label>

                                                                        <div class="col-md-8 bg-grey-steel bg-font-grey-cararra">
                                                                            <p class="form-control-static">${fieldValue(bean: traitementBatchInstance, field: 'dateComptableDisplay')}</p>
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                            </div>

                                                            <div class="row">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label class="control-label col-md-4"
                                                                               for="dateDebutLancement"><g:message
                                                                                code="traitementBatch.dateDebutLancement.label"
                                                                                default="Date début lancement"/> :
                                                                        </label>

                                                                        <div class="col-md-8 bg-grey-steel bg-font-grey-cararra">
                                                                            <p class="form-control-static">${fieldValue(bean: traitementBatchInstance, field: 'dateDebutLancementDisplay')}</p>
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label class="control-label col-md-4"
                                                                               for="dateFinLancement"><g:message
                                                                                code="traitementBatch.dateFinLancement.label"
                                                                                default="Date fin lancement"/> :
                                                                        </label>

                                                                        <div class="col-md-8 bg-grey-steel bg-font-grey-cararra">
                                                                            <p class="form-control-static">${fieldValue(bean: traitementBatchInstance, field: 'dateFinLancementDisplay')}</p>
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                            </div>

                                                            <div class="row">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label class="control-label col-md-4"
                                                                               for="dateModif"><g:message
                                                                                code="traitementBatch.dateModif.label"
                                                                                default="Date modification"/> :
                                                                        </label>


                                                                        <div class="col-md-8 bg-grey-steel bg-font-grey-cararra">
                                                                            <p class="form-control-static">${fieldValue(bean: traitementBatchInstance, field: 'dateModifDisplay')}</p>
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label class="control-label col-md-4"
                                                                               for="codeUtilisateur"><g:message
                                                                                code="traitementBatch.codeUtilisateur.label"
                                                                                default="Code utilisateur"/> :
                                                                        </label>

                                                                        <div class="col-md-8 bg-grey-steel bg-font-grey-cararra">
                                                                            <p class="form-control-static">${fieldValue(bean: traitementBatchInstance, field: 'codeUtilisateur')}</p>
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                            </div>

                                                            <div class="row">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label class="control-label col-md-4"
                                                                               for="programmeBatch"><g:message
                                                                                code="traitementBatch.programmeBatch.label"
                                                                                default="Programme batch"/> :
                                                                        </label>

                                                                        <div class="col-md-8 bg-grey-steel bg-font-grey-cararra">
                                                                            <p class="form-control-static">${fieldValue(bean: traitementBatchInstance, field: 'programmeBatch')}</p>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>

                                                            <div class="row">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label class="control-label col-md-4 for="
                                                                               messageBatch"><g:message
                                                                            code="traitementBatch.messageBatch.label"
                                                                            default="Message "/>
                                                                    </label>
                                                                    </div>
                                                                </div>
                                                            </div>

                                                            <div class="row">
                                                                <div class="col-md-12">
                                                                    <div class="form-group">
                                                                        <div class="col-md-12">
                                                                            <textarea id="mytextarea"
                                                                                      name="messageBatch"
                                                                                      rows="5"
                                                                                      cols="15" readonly="readonly">
                                                                                ${fieldValue(bean: traitementBatchInstance, field: 'messageBatch')}
                                                                            </textarea>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>

                                                        </g:form>

                                                    </div>
                                                </div>
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

</body>

</html>