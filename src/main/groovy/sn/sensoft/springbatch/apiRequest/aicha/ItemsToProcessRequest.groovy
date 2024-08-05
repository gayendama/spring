package sn.sensoft.springbatch.apiRequest.aicha

import groovy.transform.CompileStatic
import org.grails.web.json.JSONObject

@CompileStatic
class ItemsToProcessRequest {
    String batch
    String program
    Integer max
    Integer offset
    JSONObject data
}
