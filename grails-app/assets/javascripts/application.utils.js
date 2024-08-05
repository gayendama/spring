var telInput = $(".inputTelephone"),
    errorMsg = $("#error-msg"),
    telephoneDiv = $("#telephoneDiv");

$(document).ready(function () {
    initInputTel();
});

// function showSpinner(msg) {
//     alert(msg)
//     $('#msg').text(msg);
//     $('#spinner').modal({backdrop: 'static', keyboard: false, show: true});
// }
//
// function hideSpinner() {
//     console.log("Hide  onLoading");
//     $('#msg').text('');
//     $('#spinner').modal('hide');
// }

function initInputTel() {
    // initialise plugin
    try {
        $(".inputTelephone").intlTelInput({
            defaultCountry: "sn",
            autoFormat: true,
            autoPlaceholder: true
        });
    }
    catch(err) {
        console.warn("Impossible d'initialiser 'intlTelInput': "+err.message)
    }

    var reset = function () {
        errorMsg.addClass("hide");
        telephoneDiv.removeClass("has-error");
        telephoneDiv.removeClass("has-success");
    };

    telInput.change(function () {
        reset();
        testPhoneNumber();
    });

    telInput.on("countrychange", function () {
        reset();
        testPhoneNumber();
    });

    function testPhoneNumber() {
        if ($.trim(telInput.val())) {
            var countryData = telInput.intlTelInput("getSelectedCountryData").iso2;
            checkPhoneNumber($.trim(telInput.val()), countryData);
        }
    }

    // on keyup / change flag: reset
    telInput.on("keyup change countrychange", reset);
}