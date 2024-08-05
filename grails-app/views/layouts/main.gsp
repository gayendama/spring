 <!DOCTYPE html>
<!--[if IE 8]> <html lang="fr" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="fr" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="fr">
<!--<![endif]-->
<!-- BEGIN HEAD -->

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="Aicha"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta content="SENSOFT" name="author"/>
    <asset:link rel="shortcut icon" type="image/png" href="logo-CONV-32.png"></asset:link>
    <asset:stylesheet href="font.css"/>
    <asset:stylesheet href="lc_switch.css"/>
    <asset:stylesheet href="metronic_v4_7_1_admin_3_rounded.css"/>

    <g:layoutHead/>
</head>

<body>
<g:layoutBody/>


<div id="cover-spin"></div>

<script>

    // jQuery(document).ready(function () {
    //
    // });

    $('.form').submit(function () {
        $(this).find("button[type='submit']").prop('disabled', true);
    });

    $('#form').submit(function () {
        $(this).find("button[type='submit']").prop('disabled', true);
    });

</script>
</body>
</html>
