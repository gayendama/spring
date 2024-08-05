<div id="controllers" role="navigation">
    <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
           aria-expanded="false">Installed Plugins <span class="caret"></span></a>
        <ul class="dropdown-menu">
            <g:each var="plugin" in="${applicationContext.getBean('pluginManager').allPlugins}">
                <li><a href="#">${plugin.name} - ${plugin.version}</a></li>
            </g:each>
        </ul>
    </li>

    <h2>Available Controllers:</h2>
    <ul>
        <g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName }}">
            <li class="controller">
                <g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link>
            </li>
        </g:each>
    </ul>
</div>

<!--[if IE 8]> <html lang="fr" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="fr" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="fr">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8"/>
    <g:set var="entityName" value="${message(code: 'batchJob.label', default: 'Batchs')}"/>
    <title><g:message code="batchJob.label"/></title>
    <asset:stylesheet src="srpingBatchRefont.css"/>
    <meta name="layout" content="main"/>
    <script>
        var sz = ${programmeBatchList.size()};
    </script>
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

                                <ul class="page-breadcrumb breadcrumb">
                                    <li>
                                        <g:link controller="dashboard" action="index"><i
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

                                            <div class="alert alert-danger alert-dismissable" id="errors"
                                                 style="display: none;">
                                                <button type="button" class="close"
                                                        data-dismiss="alert"
                                                        aria-hidden="true"></button>
                                                <strong>Erreur !</strong>
                                            </div>

                                            <g:render template="/layouts/flashMessage"
                                                      model="[objetInstance: banqueInstance]"/>

                                            <div class="portlet-title">
                                                <div class="caption font-green" id="kkk">
                                                    <span class="fa fa-cog fa-lg "></span>
                                                    <span class="caption-subject bold uppercase">
                                                        Batch Settings
                                                    </span>
                                                </div>

                                                <div class="actions">
                                                    <div class="btn-group btn-group-devided">
                                                        <input type="button" id="1-edit" class="btn green-meadow"
                                                               value="Enregistrer">
                                                        <g:link controller="batch" action="list"
                                                                class="btn blue-hoki"><i
                                                                class="fa fa-list"></i> Job List</g:link>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="portlet-body">
                                                <div class="table-container">

                                                    <g:if test="${flash.message}">
                                                        <div class="message" role="status">${flash.message}</div>
                                                    </g:if>
                                                    <g:if test="${flash.error}">
                                                        <div class="errors" role="status">${flash.error}</div>
                                                    </g:if>

                                                    <div id="programmeTable">
                                                        <asset:javascript src="metronic_v4_7_1_admin_3_rounded.js"/>
                                                        <asset:stylesheet src="lc_switch.css.css"/>
                                                        <asset:javascript src="lc_switch.js"/>
                                                        <script>
                                                            $(document).ready(function () {
                                                                $('input').lc_switch();
                                                            });
                                                        </script>

                                                        <table class="table table-striped table-bordered table-hover dt-responsive"
                                                               id="sample_step">
                                                            <thead>
                                                            <tr>
                                                                <g:sortableColumn property="code" title="CODE"/>
                                                                <g:sortableColumn property="name" title="NAME"/>
                                                                <g:sortableColumn property="sequence" title="SEQUENCE"
                                                                                  style="text-align: center;"/>
                                                                <g:sortableColumn property="status" title="STATUS"
                                                                                  style="text-align: center;"/>
                                                            </tr>
                                                            </thead>
                                                            <tbody>
                                                            <g:each in="${programmeBatchList}" status="i" var="step">
                                                                <tr>
                                                                    <td>${fieldValue(bean: step, field: "codeProgramme")}</td>
                                                                    <td>${fieldValue(bean: step, field: "nomProgramme")}</td>
                                                                    <td align="center">
                                                                        <input type="hidden" id="${i}-input-step-id"
                                                                               value="${step.codeProgramme}">
                                                                        <i class="fa fa-spinner fa-spin fa-lg load-step"
                                                                           style="display: none;"></i>

                                                                        <div id="${i}-edit-seq-div"
                                                                             style="display: none"
                                                                             class="edit-step-div">
                                                                            <input id="${i}-input-seq" type="number"
                                                                                   min="1"
                                                                                   class="seq-input form-control input-inline"
                                                                                   value="${fieldValue(bean: step, field: "sequenceNumber")}"
                                                                                   onkeyup="onKeyPress(${i})"/>
                                                                            <i id="${i}-seq-done"
                                                                               class="fa fa-check fa-lg btn-done "
                                                                               onclick="saveSequeces(${i});"></i>
                                                                        </div>

                                                                        <div id="${i}-show-seq-div"
                                                                             class="show-step-div">
                                                                            ${step.sequenceNumber}
                                                                            <i id="${i}-seq-pencil"
                                                                               class="fa fa-pencil fa-lg edit-step "
                                                                               onclick="editSequence(${i});"></i>
                                                                        </div>
                                                                    </td>
                                                                    <td align="center">
                                                                        <div id="${i}-content-switch">
                                                                            <g:if test="${step.sequenceNumber != null}">
                                                                                <g:if test="${step.batchStepStatus != null}">
                                                                                    <g:if test="${step.batchStepStatus.id == sn.sensoft.springbatch.utils.BatchStepStatus.ACTIVER.id}">
                                                                                        <input id="${i}-switch"
                                                                                               type="checkbox"
                                                                                               class="lcs_check lcs_tt1"
                                                                                               checked="checked"
                                                                                               autocomplete="off"
                                                                                               onclick="disableStep(${i})"/>
                                                                                    </g:if>
                                                                                    <g:else>
                                                                                        <input id="${i}-switch"
                                                                                               type="checkbox"
                                                                                               class="lcs_check"
                                                                                               autocomplete="off"/>
                                                                                    </g:else>
                                                                                </g:if>
                                                                                <g:else>
                                                                                    <input id="${i}-switch"
                                                                                           type="checkbox"
                                                                                           class="lcs_check"
                                                                                           disabled="disabled"
                                                                                           autocomplete="off"/>
                                                                                </g:else>
                                                                            </g:if>
                                                                            <g:else>
                                                                                <input id="${i}-switch" type="checkbox"
                                                                                       class="lcs_check"
                                                                                       disabled="disabled"
                                                                                       autocomplete="off"/>
                                                                            </g:else>
                                                                        </div>
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
<script>
    function flashMessage(type, message) {

        if (type == "message") {
            $("#messages").html("<strong>Succès !</strong> " + message);
            $("#messages").css("display", "block");
        } else {
            $("#errors").html("<strong>Erreur !</strong> " + message);
            $("#errors").css("display", "block");
        }
        setTimeout(function () {
            $("#errors").css("display", "none");
        }, 10000);
    }

    $(document).ready(function () {
        $('#sample_step').DataTable({
            "ordering": false,
            paging: false,
        });
    });

    function onKeyPress(num) {
        var val = $("#" + num + "-input-seq").val();
        if (parseInt(val, 10) > sz || parseInt(val, 10) == 0) {
            $("#" + num + "-input-seq").val("");
        }
        if (val.length == 0 || val == "e" || val == "E") {
            $("#" + num + "-input-seq").css("border", "1px red solid");
            $("#" + num + "-switch").lcs_off();
        } else {
            $("#" + num + "-input-seq").css("border", "1px solid #c2cad8");
            $("#" + num + "-switch").lcs_on();
        }
    }

    function editSequence(num) {
        $("#" + num + "-show-seq-div").css("display", "none");
        $("#" + num + "-edit-seq-div").css("display", "block");
        $("#" + num + "-input-seq").focus();
    }

    function saveSequeces(num) {
        var seq = $("#" + num + "-input-seq").val();
        $(".edit-step-div").css("display", "none");
        $(".show-step-div").css("display", "none");
        $(".load-step").css("display", "block");
        validerForm();
    }

    function validerForm() {
        var isValidSequences = true;
        for (i = 0; i < sz; i++) {
            var iVal = $("#" + i + "-input-seq").val();
            for (j = 0; j < sz; j++) {
                var jval = $("#" + j + "-input-seq").val();
                if (iVal.length > 0 && j != i && iVal == jval) {
                    isValidSequences = false;
                    flashMessage("error", "Vous avez saisi plus d'une fois la valeur " + jval);
                    $(".edit-step-div").css("display", "block");
                    $(".show-step-div").css("display", "none");
                    $(".load-step").css("display", "none");
                    break;
                }

            }
        }

        if (isValidSequences) {
            //alert("isValidSequences");
            send();
        }
    }

    function send() {

        var programme = [];

        for (i = 0; i < sz; i++) {
            json = {code: $("#" + i + "-input-step-id").val(), sequenceNumber: $("#" + i + "-input-seq").val()};
            programme[i] = json;
        }

        $.ajax({
            url: '${request.contextPath}/batch/updateStepSettings',
            type: 'POST',
            dataType: 'html',
            contentType: 'application/json',
            success: function (data) {
                setTimeout(function () {
                    $("#programmeTable").html(data);
                }, 1000);
            },
            data: JSON.stringify(programme)
        });
    }
</script>

</body>

</html>