package sn.sensoft.springbatch.apiRequest.aicha

import groovy.transform.CompileStatic
import org.grails.web.json.JSONObject

@CompileStatic
class ProcessRequest {
    String batch
    String program
    String itemId
    JSONObject data
}
