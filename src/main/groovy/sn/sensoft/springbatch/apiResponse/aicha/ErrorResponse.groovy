package sn.sensoft.springbatch.apiResponse.aicha

import groovy.transform.CompileStatic

@CompileStatic
class ErrorResponse {
    String errorCode
    String userMessage
    String debugMessage
    String moreInfo
}
