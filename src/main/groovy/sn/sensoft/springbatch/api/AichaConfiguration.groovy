package sn.sensoft.springbatch.api

import groovy.json.JsonOutput
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.grails.web.json.JSONObject

@CompileStatic
@Slf4j
class AichaConfiguration {
    String username
    String password
    JSONObject token

    AichaConfiguration(String username, String password) {
        this.username = username
        this.password = password
    }

    Map<String, String> loginMap() {
        return [username: username, password: password]
    }

    String loginJsonObject() {
        return JsonOutput.toJson(loginMap())
    }

    String authString() {
        return "Bearer ${token?.access_token}"
    }

    String authToken(JSONObject token) {
        this.token = token
    }
}
