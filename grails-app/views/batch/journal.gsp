<%@ page import=" sn.sensoft.springbatch.ToolsBatchService" %>
<!--[if IE 8]> <html lang="fr" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="fr" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="fr">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8"/>
    <g:set var="entityName" value="${message(code: 'batchJobs.label', default: 'Batchs')}"/>
    <g:set var="entityNames" value="${message(code: 'logActivity.label', default: 'Journal batch')}"/>
    <title><g:message code="batchJobs.label"/></title>

    <title>Journal des traitement des fin de journée</title>
    <asset:stylesheet src="srpingBatchRefont.css"/>
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
                                        <g:message code="default.show.label" args="[entityNames]"/>
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
                                                    <span class="caption-subject bold uppercase">Journal des traitement des fin de journée</span>
                                                </div>


                                                <div class="actions">

                                                </div>
                                            </div>
                                            <div class="marge-5-5">
                                                <div class="row ">
                                                    <div class="col-md-6">
                                                        <div class="form-group form-md-line-input form-md-floating-label has-info">
                                                            <label
                                                                    for="dateComptable"><g:message
                                                                    code="dateComptable.label"
                                                                    default="Date comptable"/>
                                                                <span class="required">*</span>
                                                            </label>

                                                            <input type="text" name="dateComptable" id="dateComptable" onchange="load()"
                                                                   class="form-control form-control-inline input-medium date-picker" autocomplete="off"
                                                            >

                                                        </div>
                                                    </div>


                                                </div>

                                            </div>
                                            <div class="col-md-12">
                                                <div class="portlet-body">
                                            <div class="row margin-top-10" id="tabs" >
                                                <div class="tabbable" id="tabbable">
                                                    <ul class="nav nav-tabs nav-justified"
                                                        id="myTab">
                                                        <li class="active">
                                                            <a data-toggle="tab"
                                                               href="#historique">
                                                                <g:message
                                                                        code="journal.batch.historique"
                                                                        default="historique"/>
                                                            </a>
                                                        </li>

                                                        <li class="tab-red">
                                                            <a data-toggle="tab"
                                                               href="#detail"
                                                               id="labelInfos">
                                                                <g:message
                                                                        code="journal.batch.detail"
                                                                        default="Informations détaillées"/>

                                                            </a>
                                                        </li>
                                                    </ul>

                                                    <div class="tab-content">
                                                        <div id="historique"
                                                             class="tab-pane in active">

                                                                    <div class="mt-element-ribbon bg-grey-light">

                                                                        <div class="ribbon-content">
                                                                            <div id="tabJournal">

                                                                            </div>
                                                                        </div>

                                                            </div>
                                                        </div>
                                                        <div id="detail"
                                                             class="tab-pane in active">

                                                                    <div class="mt-element-ribbon bg-grey-light">

                                                                        <div class="ribbon-content">
                                                                            <div id="tabDetail">

                                                                            </div>
                                                                        </div>

                                                            </div>
                                                        </div>




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
<asset:javascript src="intlTelInput.js"/>
<asset:javascript src="jquery.maskedinput.js"/>
<asset:javascript src="application.utils.js"/>
<script>
    function load() {
        getJournal();
        getJournalDetail();
    }
    function getJournal() {
        $.ajax({
            type: 'POST',
            processData: true,
            url: "${createLink(action:'ajaxTabJournal')}",
            data: {
                dateComptable: $("#dateComptable").val()
            },
            dataType: 'html',
            success: function (data) {
                $('#tabJournal').html(data);
            }
        });

    }

    function getJournalDetail() {
        $.ajax({
            type: 'POST',
            processData: true,
            url: "${createLink(action:'ajaxTabJournalDetail')}",
            data: {
                dateComptable: $("#dateComptable").val()
            },
            dataType: 'html',
            success: function (data) {
                $('#tabDetail').html(data);
            }
        });

    }
</script>

</body>

</html>