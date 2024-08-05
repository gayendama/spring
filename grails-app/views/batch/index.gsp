<%@ page import="sn.sensoft.springbatch.utils.SecurityUtils; springbatch.model.JobModel; springbatch.ui.PagedResult; sn.sensoft.springbatch.utils.BatchType; sn.sensoft.springbatch.utils.BatchList; sn.sensoft.springbatch.utils.Constantes; sn.sensoft.springbatch.utils.DateUtils; sn.sensoft.springbatch.ToolsBatchService" %>
<!--[if IE 8]> <html lang="fr" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="fr" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="fr">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8"/>
    <g:set var="entityName" value="${message(code: 'batchJobs.label', default: 'Batchs')}"/>
    <title><g:message code="batchJobs.label"/></title>

    <title>Aicha BATCH JOB LIST</title>
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
                                %{-- <g:set var="securityUtils" bean="SecurityUtils"/> --}%
                                <ul class="page-breadcrumb breadcrumb">
                                    <li>
                                        <g:link controller="batch" action="list"><i
                                                class="icon-home"></i> <g:message
                                                code="default.home.label" default="Accueil"/></g:link>
                                        <i class="fa fa-circle"></i>
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
                                                    <span class="caption-subject bold uppercase">JOB LIST</span>
                                                </div>

                                                <div class="actions">
                                                    <div class="btn-group btn-group-devided">
                                                        <g:link controller="batch" action="list"
                                                                class="btn blue-hoki"><i
                                                                class="fa fa-undo"></i> Retour</g:link>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="portlet-body">
                                                <div class="mt-element-ribbon bg-grey-light">
                                                    <div class="ribbon ribbon-shadow ribbon-color-success uppercase">
                                                        Liste Batch
                                                    </div>

                                                    <div class="ribbon-content">
                                                        <div class="table-container"
                                                             style="margin-top: -10px; padding-bottom: 15px;">
                                                            <table class="table table-striped table-bordered table-hover dt-responsive">
                                                                <thead>
                                                                <tr>
                                                                    <td align="center">N°</td>
                                                                    <td>TYPE</td>
                                                                    <td>NOM</td>
                                                                    <td>DESCRIPTION</td>
                                                                    <td align="center">STATUT</td>
                                                                </tr>
                                                                </thead>
                                                                <tbody>
                                                                <g:each in="${batchList}" var="batch" status="i">
                                                                    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                                                                        <td align="center">${i + 1}</td>
                                                                        <td>${batch.batchType}</td>
                                                                        <td>${batch.name}</td>
                                                                        <td>${batch.description}</td>
                                                                        <td align="center" valign="center"
                                                                            style="width: 125px">
                                                                            <input type="hidden" id="${i}-input-step-id"
                                                                                   value="${batch.id}">
                                                                            <g:if test="${batch.indActif == true}">
                                                                                <input id="${batch.id}" type="checkbox"
                                                                                       class="lcs_check"
                                                                                       checked="checked"
                                                                                       autocomplete="off"/>
                                                                            </g:if>
                                                                            <g:else>
                                                                                <input id="${batch.id}" type="checkbox"
                                                                                       class="lcs_check"
                                                                                       autocomplete="off"/>
                                                                            </g:else>
                                                                            <i class="fa fa-spinner fa-spin fa-lg load-step"
                                                                               id="${batch.id}-spinner"
                                                                               style="display: none; float: right; margin-top: 5px"></i>
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
<asset:javascript src="lc_switch.js"/>
<script>
    $(document).ready(function () {
        $('input').lc_switch();
    });

    function updateBatch(id, status) {
        $("#" + id + "-spinner").css("display", "block");
        json = {id: id, status: status};
        $.ajax({
            url: '${request.contextPath}/batch/ajaxUpDatBatcheIndActif',
            type: 'POST',
            dataType: 'html',
            contentType: 'application/json',
            success: function (data) {
                var parsedJson = $.parseJSON(data);
                //console.log(parsedJson)
                setTimeout(function () {
                    $("#" + id + "-spinner").css("display", "none");
                    if (parsedJson.status == "failed") {
                        alert(parsedJson.message)
                    }
                }, 1000);
            },
            data: JSON.stringify(json)
        });
    }

    <g:each in="${batchList}" var="batch">
    $('body').delegate('#${batch.id}', 'lcs-statuschange', function () {
        if (($('#${batch.id}').is(':checked'))) {
            updateBatch('${batch.id}', true);
        } else {
            updateBatch('${batch.id}', false);
        }
    });
    </g:each>
</script>

</body>

</html>