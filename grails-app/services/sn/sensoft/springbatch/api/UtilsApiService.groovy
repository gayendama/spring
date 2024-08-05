package sn.sensoft.springbatch.api

import grails.gorm.transactions.Transactional
import groovy.json.JsonOutput
import org.springframework.http.HttpStatus

import java.text.DecimalFormat

@Transactional
class UtilsApiService {

    def getJsonError(def response, String errorCode, String debugMessage, String userMessage, String moreInfo) {
        response.status = HttpStatus.BAD_REQUEST.value()
        response.setContentType('text/json')
        def data = [errorCode   : errorCode,
                    userMessage : userMessage,
                    debugMessage: debugMessage,
                    moreInfo    : moreInfo
        ]
        return JsonOutput.toJson(data)
    }

    def getJsonServerError(def response, String message) {
        response.status = HttpStatus.INTERNAL_SERVER_ERROR.value()
        response.setContentType('text/json')
        def data = [errorCode   : "9999",
                    userMessage : Constantes.MESSAGE_INTERNAL_SERVER_ERROR,
                    debugMessage: message
        ]
        return JsonOutput.toJson(data)
    }

    def getJsonSimpleResponse(def response, String message) {
        response.status = HttpStatus.OK.value()
        response.setContentType('text/json')
        def data = [status  : "ok",
                    userMessage : message,
                    debugMessage: message
        ]
        return JsonOutput.toJson(data)
    }
}
