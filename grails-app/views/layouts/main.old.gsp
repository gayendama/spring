<g:set var="toolsService" bean="habilitationService"/> <!DOCTYPE html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="Grails"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <g:layoutHead/>
    <style>
    .bars, .chart, .pie {
        overflow: hidden;
        min-height: 1px;
        width: 100%;
        font-size: 14px;
    }

    .font-40 {
        font-size: 40px;
    }

    .hr2 {
        padding: 10px;
        border-bottom: 1px solid #e7ecf1;
    }

    .m-r-10 {
        margin-right: 10px;
    }

    .search {
        margin-bottom: 5px;
    }
    </style>
</head>

<body>

<g:layoutBody/>



<div class="modal modal-message modal-warning fade in" id="spinner" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <span class="font-40">
                    <i class="fa fa-spinner fa-spin margin-right-10"></i>
                </span>
            </div>
            <span class="modal-body" id="message">
                ${message(code: "loading.message", default: "Chargement en cours...")}
            </span>

        </div> <!-- / .modal-content -->
    </div> <!-- / .modal-dialog -->
</div>

<script>

    $(document).ready(function () {

    });

    $('.form').submit(function () {
        $(this).find("button[type='submit']").prop('disabled', true);
    });

    $('#form').submit(function () {
        $(this).find("button[type='submit']").prop('disabled', true);
    });

    function showSpinner(message) {
        if (message != "") {
            $("#message").text(message);
        }

        $("#spinner").modal({
            backdrop: 'static'
        });
        $("#spinner").modal('show');
    }

    function formatCurrency(num, devise) {
        num = num.toString().replace(/\$|\,/g, '');
        if (isNaN(num)) num = "0";
        sign = (num == (num = Math.abs(num)));
        num = Math.floor(num * 100 + 0.50000000001);
        cents = num % 100;
        num = Math.floor(num / 100).toString();
        if (cents < 10) cents = "0" + cents;

        for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
            num = num.substring(0, num.length - (4 * i + 3)) + ' ' + num.substring(num.length - (4 * i + 3));

        return (((sign) ? '' : '-') + num + ' ' + devise);
    }
</script>
</body>
</html>
