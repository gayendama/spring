package sn.sensoft.springbatch.dto

import groovy.json.JsonOutput
import groovy.transform.CompileStatic

@CompileStatic
class AppPartnerDTO {

    String code
    String intitule
    String apiKey
    String serverUrl
    String loginUri
    String refreshTokenUri
    String serverType

    String realm
    String clientId
    String clientSecret
    String username
    String password

    AppPartnerDTO() {
    }


    String logInfos() {
        return JsonOutput.toJson([
            code           : this.code,
            intitule       : this.intitule,
            apiKey         : this.apiKey,
            serverUrl      : this.serverUrl,
            loginUri       : this.loginUri,
            refreshTokenUri: this.refreshTokenUri,
            serverType     : this.serverType,
            username       : this.username,
            password       : this.password
        ])
    }

}
