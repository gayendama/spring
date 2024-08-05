//= require jquery-2.2.0.min
//= require modernizr.custom
//= require spring-websocket
//= require_self

$(function() {

     var socket = new SockJS("stomp");
    var client = Stomp.over(socket);


    var template = $('#liTemplate').html();
    function addLogBatch(logBatch, animate) {
        // See https://www.codementor.io/tips/7463752841/simple-javascript-jquery-templating
        var tpl = template.replace(/{title}/g, logBatch.programme)
            .replace(/{body}/g, logBatch.message)
            .replace(/{datetime}/g, logBatch.dateFinExecution)
            .replace(/{date}/g, logBatch.dateF)
            .replace(/{time}/g, logBatch.heureF)
            .replace(/{category}/g, logBatch.codeProgramme);

        if (animate) {
            $(tpl).hide()
                .css('opacity', 0.0)
                .prependTo('#timeline')
                .slideDown('slow')
                .animate({opacity: 1.0});
        }
        else {
            $(tpl).prependTo('#timeline')
        }
    }

    client.connect({}, function() {
        client.subscribe("/topic/batch", function(message) {
            var logBatch = $.parseJSON(message.body);
            addLogBatch(logBatch, true);
        });

        client.subscribe("/app/batch.messages", function(message) {
            var logBatchs = $.parseJSON(message.body);
            $.each(logBatchs, function(index, value) {
                addLogBatch(value, false);
            });
        });        
    });
});