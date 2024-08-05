<style>

.grey {
    color: grey !important;
}

.fade-scale {
    transform: scale(0);
    opacity: 0;
    -webkit-transition: all .25s linear;
    -o-transition: all .25s linear;
    transition: all .25s linear;
}

.fade-scale.in {
    opacity: 1;
    transform: scale(1);
}



</style>
<asset:stylesheet href="intlTelInput.css"/>

<div class="page-header-top" style="background-color: #EFF3F8">
    <div class="container-fluid">
        <!-- BEGIN LOGO -->
        <div class="page-logo">
            <g:link controller="dashboard" action="index">
                <asset:image class="" src="spring_batch.png" alt="logo"/>
            </g:link>
        </div>
        <!-- END LOGO -->
        <!-- BEGIN RESPONSIVE MENU TOGGLER -->
        <a href="javascript:;" class="menu-toggler"></a>
        <!-- END RESPONSIVE MENU TOGGLER -->

        <!-- BEGIN TOP NAVIGATION MENU -->
        <div class="top-menu">

            <ul class="nav navbar-nav pull-right">
                <!-- BEGIN USER LOGIN DROPDOWN -->
                <li class="dropdown dropdown-user dropdown-dark-light">
                    <a href="javascript:" class="dropdown-toggle" data-toggle="dropdown"
                       data-hover="dropdown" data-close-others="true">
                        <asset:image class="img-circle" src="user.png" alt="logo"/>
                        <span class="username username-hide-mobile">${session.currentUser}</span>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-default">
                        <li>
                            <g:link action="showProfile" controller="user"
                                    id="${session.currentUser?.id}">
                                <i class="fa fa-user"></i> Mon profil</g:link>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <g:link action="index" controller="logout">
                                <i class="fa fa-power-off"></i> Déconnexion</g:link>
                        </li>
                    </ul>
                </li>
                <!-- END USER LOGIN DROPDOWN -->
            </ul>
        </div>
        <!-- END TOP NAVIGATION MENU -->

        <!-- BEGIN LOGO -->
        <div class="page-logo-right">
            <asset:image class="" src="spring-batch.png" />
        </div>
        <!-- END LOGO -->
    </div>
</div>

<div id="changeBureau" class="modal fade" tabindex="-1"
     data-width="350">
    <div class="modal-content">
        <div class="page-content-inner">
            <div class="row">
                <div class="col-md-12">
                    <div class="portlet box green">
                        <div class="portlet-title">
                            <div class="caption">

                            </div>
                        </div>
                    </div>

                    <div class="portlet-body form">
                    <!-- BEGIN FORM-->
%{--
                        <g:form class="login-form" action="changeBureau" method="POST"
                                id="loginForm" autocomplete="off" controller="dashboard">

                            <div class="form-body">
                                <div class="form-group">
                                    <div class="input-group input-medium" style="margin-left: 25px !important;">
                                        <g:select class="form-control select2 "
                                                  name="bureauId" id="bureauId" optionKey="id"
                                                  from="${session.currentUser?.bureaux?.sort { it.codeBureau }}"
                                                  required="required"
                                                  value="${session?.bureauChoisi?.id}"/>
                                        <span class="input-group-btn">
                                            <button type="submit" class="btn green icn-only"
                                                    style="margin-left: 5px !important;">
                                                <i class="m-icon-swapright m-icon-white"></i>
                                            </button>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </g:form>
--}%
                    <!-- END FORM-->
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="modalErrorMessage" class="modal fade" tabindex="-1"
     style="margin-top: -142px">
    <div class="sa-icon sa-error animateErrorIcon"
         style="display: block">
        <span class="sa-x-mark animateXMark">
            <span class="sa-line sa-left"></span>
            <span class="sa-line sa-right"></span>
        </span>
    </div>

    <div id="messageErrorContent"></div>

    <div class="sa-button-container">
        <button class="cancel btn btn-lg blue-hoki" data-dismiss="modal" tabindex="2"
                style="display: inline-block;">Retour</button>
    </div>
</div>

<div id="modalSuccessMessage" class="modal fade" tabindex="-1"
     style="margin-top: -131px;">

    <div class="sa-icon sa-success animate" style="display: block;">
        <span class="sa-line sa-tip animateSuccessTip"></span>
        <span class="sa-line sa-long animateSuccessLong"></span>

        <div class="sa-placeholder"></div>

        <div class="sa-fix"></div>
    </div>

    <div id="messageSuccessContent"></div>

    <div class="sa-button-container">
        <button class="cancel btn btn-lg btn blue-hoki" data-dismiss="modal" tabindex="2"
                style="display: inline-block;">Retour</button>
    </div>
</div>

<div id="modalWarningMessage" class="modal fade" tabindex="-1"
     style="margin-top: -131px;">

    <div class="sa-icon sa-warning pulseWarning" style="display: block;">
        <span class="sa-body pulseWarningIns"></span>
        <span class="sa-dot pulseWarningIns"></span>
    </div>

    <div id="messageWarningContent"></div>

    <div class="sa-button-container">
        <button class="cancel btn btn-lg btn blue-hoki" data-dismiss="modal" tabindex="2"
                style="display: inline-block">Retour</button>
    </div>
</div>

<div id="indexLog" class="modal fade" tabindex="-1"
     data-width="1100">
    <div class="modal-content">
        <div class="page-content-inner">
            <div class="row" style="margin-right: 15px; margin-left: 15px">

                <div class="col-md-12">
                    <div class="portlet box green">
                        <div class="portlet-title">
                            <div class="caption">
                                <span class="fa fa-history"></span>
                                <g:set var="entityName" value="${entityName}"/>

                                <span class="caption-subject bold uppercase"><g:message
                                        code="default.history.label"
                                        args="[entityName]"/></span>
                            </div>
                        </div>
                    </div>

                    <div class="portlet-body">
                        <div class="table-container" id="historyList">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="signatureModal" class="modal fade" tabindex="-1"
     data-width="200">
    <div class="modal-content">
        <div class="page-content-inner">
            <div class="row" style="margin-right: 15px; margin-left: 15px">

                <div class="col-md-12">
                    <div class="portlet box green">
                        <div class="portlet-title">
                            <div class="caption">
                                Signature
                            </div>
                        </div>
                    </div>

                    <div class="portlet-body">
                        <div class="table-container" id="signature">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="modalsInstruction" class="modal fade" tabindex="-1"
     data-width="600">
</div>

<script type="text/javascript">


    function fenetreSuccessMessage(message) {
        swal(message, 'Succés', 'success');

    }

    function fenetreErrorMessage(message) {
        swal(message, 'Erreur', 'error');
    }

    function fenetreWarningMessage(message) {
        swal(message, 'Warning', 'warning');

    }

    function unNombre(nbSaisi) {
        if (isNaN(nbSaisi.value)) {
            return false;
        } else {
            return true;
        }
    }

    // Load Spinner befor form submit

    function showSpinner() {
        $('#cover-spin').show(0)
        // $('#msg').text(msg);
        // $('#spinner').modal({backdrop: 'static', keyboard: false, show: true});
    }

    function hideSpinner() {
        $('#cover-spin').hide(0)
        // $('#msg').text('');
        // $('#spinner').modal('hide');
    }

    function onSubmit(id, value) {
        var id1=id+"1"
        var id2=id+"2"
        document.getElementById(id1).value=value;
        document.getElementById(id2).value=value;
        $("#"+id1).attr('readonly', 'readonly');
        $("#"+id2).attr('readonly', 'readonly');
        return true;
    }

    function formatRepoCompte(repo) {

        if (repo.loading) return repo.text;

        var markup = "<div class='select2-result-repository clearfix'>" +
            "<div class='select2-result-repository__title'>" + repo.numeroCompte + " - " + repo.intituleCompte + "</div></div>";

        return markup;
    }

    function formatRepoSelectionCompte(repo) {
        console.log(repo)
        var compte = repo.numeroCompte + " - " + repo.intituleCompte;
        return compte;
    }


</script>
