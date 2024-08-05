<g:set var="toolsService" bean="habilitationService"/> <!DOCTYPE html>

<!--[if IE 8]> <html lang="fr" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="fr" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="fr"><!--<![endif]-->
<!-- BEGIN HEAD -->

<head>
    <g:set var="runtimeConfigService" bean="runtimeConfigService"/>
    <g:set var="modeFinance" value="${runtimeConfigService.getModeFinance()}"/>
    <meta charset="utf-8"/>
    <title>Connexion</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta content="SENSOFT" name="author"/>
    <asset:link rel="shortcut icon" type="image/png" href="logo-${modeFinance}-32.png"></asset:link>
    <asset:stylesheet href="font.css"/>
    <asset:stylesheet href="metronic_v4_7_1_admin_3_rounded.css"/>
    <asset:link rel="shortcut icon" type="image/x-icon" href="logo-${modeFinance}.png"></asset:link>

    <asset:stylesheet href="login.css"/>
</head>
<!-- END HEAD -->

<body class=" login">
<!-- BEGIN LOGO -->
<div class="logo">
    <asset:image class="logo-default" src="logo-${modeFinance}.png" alt="logo" style="height: 200px; width: 200px;"/>
</div>
<!-- END LOGO -->
<!-- BEGIN LOGIN -->
<div class="content">
    <!-- BEGIN LOGIN FORM -->
    <form class="login-form" action="${postUrl ?: '/login/authenticate'}" method="POST"
          id="loginForm" autocomplete="off">
        <h3 class="form-title">Identification</h3>

        <g:render template="/layouts/flashMessage" model="[objetInstance: objetInstance]"/>


        <div class="form-group">
            <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
            <label class="control-label visible-ie8 visible-ie9">Username</label>

            <div class="input-icon">
                <i class="fa fa-user"></i>
                <input class="form-control placeholder-no-fix" type="text" placeholder="Identifiant"
                       name="${usernameParameter ?: 'username'}" id="username" required="true" autofocus/>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label visible-ie8 visible-ie9">Password</label>

            <div class="input-icon">
                <i class="fa fa-lock"></i>
                <input class="form-control placeholder-no-fix" type="password" placeholder="Mot de passe"
                       name="${passwordParameter ?: 'password'}" id="password" required="true"/>
            </div>
        </div>

        <div class="form-actions">
            <button type="submit" id="submitLoginForm" class="btn green pull-right">Se connecter</button>
        </div>

        <br/>

    </form>
    <!-- END LOGIN FORM -->

</div>
<!-- END LOGIN -->

<!-- BEGIN COPYRIGHT -->
<div class="copyright">
    <b>2019 &copy; <a href="http://www.sensoft.sn">SENSOFT</a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <span class="text-uppercase"><g:meta name="info.app.name"></g:meta></span> <g:meta
            name="info.app.version"></g:meta></b>
</div>
<!-- END COPYRIGHT -->
<asset:javascript src="metronic_v4_7_1_admin_3_rounded.js"/>

<asset:javascript src="login.js"/>

<script>
    //    $(document).ready(function () {
    //        $("#submitLoginForm").attr("disabled","disabled")
    //    });


    function checkCaptcha() {
        var captcha = $("#captcha").val();
        if (captcha.length >= 6) {
            jQuery.ajax({
                type: "POST",
                processData: true,
                url: "${request.contextPath}/login/checkCaptcha",
                dataType: "json",
                data: {captcha: captcha},
                success: function (data) {
                    if (data.status == "OK") {
                        $("#submitLoginForm").removeAttr("disabled");
                        $("#groupCaptcha").addClass("has-success");
                        $("#groupCaptcha").removeClass("has-error");
                        $("#helpBlock").text("");
                        $("#icone").removeClass("fa-times");
                        $("#icone").removeClass("font-red");
                        $("#icone").addClass("fa-check");
                        $("#icone").addClass("font-green")
                    } else {
                        $("#submitLoginForm").attr("disabled", "disabled");
                        $("#groupCaptcha").addClass("has-error");
                        $("#icone").addClass("fa-times");
                        $("#icone").addClass("font-red");
                        $("#groupCaptcha").addClass("has-error");
                        $("#icone").removeClass("font-green");
                        $("#helpBlock").text(data.message);
                    }
                },
                error: function (data) {
                    $("#submitLoginForm").attr("disabled", "disabled");
                }
            });
        } else {
            $("#submitLoginForm").attr("disabled", "disabled");
        }

    }
</script>

</body>

</html>