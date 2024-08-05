<g:set var="toolsService" bean="habilitationService"/> <!DOCTYPE html>
<!--[if IE 8]> <html lang="fr" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="fr" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="fr">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8"/>
    <g:set var="entityName" value="${message(code: 'traitementBatch.label', default: 'Traitement batch')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>

    <meta name="layout" content="main"/>

    <asset:javascript src="timeline.js"/>

    <script>
        function callAjax() {
            $.ajax({
                url: "${request.contextPath}/traitementBatch/runBatch",
                type: "post",
                dataType: 'json',
                success: function (data) {
                    console.log(data); //<-----this logs the data in browser's console
                },
                error: function (data) {
                    alert(data.responseText); //<----when no data alert the err msg
                }
            });
        }
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
                                    <li>
                                        <span class="glyphicon glyphicon-list-alt "></span>
                                        <g:message code="default.list.label" args="[entityName]"/>
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
                                                            args="[entityName]"/></span>
                                                </div>

                                                <div class="actions">
                                                    <div class="btn-group btn-group-devided">
                                                        <g:link class="btn green" action="create"><i
                                                                class="fa fa-plus"></i> <g:message
                                                                code="default.add.label"
                                                                args="[entityName]"/></g:link>
                                                    </div>

                                                    <div class="btn-group btn-group-devided">
                                                        <g:link class="btn green" action="traitementBatch"><i
                                                                class="fa fa-plus"></i>Traiter</g:link>
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
                                                <button id="runBatch" onclick="callAjax()">Run batch</button>

                                                <div id="batchMessageDiv"></div>
                                            </div>


                                            <div class="container">
                                                <header class="clearfix">
                                                    <span>Progammes batch</span>

                                                    <h1>Timeline</h1>
                                                </header>

                                                <div class="main">
                                                    <ul id="timeline" class="cbp_tmtimeline">
                                                    </ul>
                                                </div>
                                            </div>

                                            %{--// Template pour générer les résultats du batch basé sur les vues timeline--}%
                                            <script type="html/tpl" id="liTemplate">
                                                <li>
                                                    <time class="cbp_tmtime" datetime="{datetime}"><span>{date}</span> <span>{time}</span></time>
                                                    <div class="cbp_tmicon cbp_tmicon-{category}"></div>
                                                    <div class="cbp_tmlabel">
                                                        <h2>{title}</h2>
                                                        <p>{body}</p>
                                                    </div>
                                                </li>
                                            </script>

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