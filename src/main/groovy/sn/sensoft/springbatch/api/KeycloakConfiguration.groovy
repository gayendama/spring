package sn.sensoft.springbatch.api


import groovy.json.JsonOutput
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.grails.web.json.JSONObject

@CompileStatic
@Slf4j
class KeycloakConfiguration {

    String username
    String password
    String clientId
    String clientSecret
    String grantType
    String refreshToken
    JSONObject token


    KeycloakConfiguration(String username, String password, String clientId, String clientSecret) {
        this.username = username
        this.password = password
        this.clientId = clientId
        this.clientSecret = clientSecret
    }

    KeycloakConfiguration(String clientId, String clientSecret, String refreshToken) {
        this.refreshToken = refreshToken
        this.clientId = clientId
        this.clientSecret = clientSecret
    }

    Map<String, String> loginMap() {
        return [grant_type: "password", client_id: clientId, client_secret: clientSecret, username: username, password: password]
    }

    Map<String, String> refreshTokenMap(String refreshToken) {
        return [grant_type: "refresh_token", client_id: clientId, client_secret: clientSecret, refresh_token: refreshToken]
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

    def logInfos() {
        return [
                username    : username,
                password    : "********",
                clientId    : clientId,
                clientSecret: clientSecret,
                grantType   : grantType,
                refreshToken: refreshToken,
                token       : token
        ]
    }
}
