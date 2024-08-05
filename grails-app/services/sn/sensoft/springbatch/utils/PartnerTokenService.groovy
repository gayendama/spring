package sn.sensoft.springbatch.utils

import grails.gorm.transactions.Transactional
import grails.plugins.rest.client.RestBuilder
import groovy.time.TimeCategory
import org.grails.web.json.JSONObject
import sn.sensoft.springbatch.exception.SpringbatchException
import sn.sensoft.springbatch.securite.AppPartner
import sn.sensoft.springbatch.securite.JwtInfo

@Transactional
class PartnerTokenService{

    def runtimeConfigService

    AppPartner aicha(){
        AppPartner appPartner = new AppPartner()
        appPartner.intitule = "AICHA"
        appPartner.code = "AICHA"
        appPartner.serverUrl = runtimeConfigService.getAichaUrl()
        appPartner.username = runtimeConfigService.getAichaUser()
        appPartner.password = runtimeConfigService.getAichaPassword()
        appPartner.password = runtimeConfigService.getAichaPassword()
        appPartner.realm = runtimeConfigService.getAichaKeycloakRealm()
        appPartner.clientId = runtimeConfigService.getAichaClient()
        appPartner.clientSecret = runtimeConfigService.getAichaSecret()
        appPartner.authUrl = runtimeConfigService.getAichakeycloakUrl()

//        JSONObject apiKey = new JSONObject()
//        apiKey.put("codeEtablissement", runtimeConfigService.getAsterAgentEtab())
//        apiKey.put("sqlRequest", runtimeConfigService.getAsterAgentSqlRequest())
//        apiKey.put("connectionId", runtimeConfigService.getAsterAgentConnectionId())
//        appPartner.apiKey = apiKey.toString()

        //verifAppPartner(appPartner)

        return appPartner
    }

    JwtInfo accessToken(AppPartner appPartner){
        RestBuilder rest = new RestBuilder()
        String url = appPartner.serverUrl+"/api/login"

        JSONObject jsonObjectDivceInfo = new JSONObject()
        jsonObjectDivceInfo.put("username", appPartner.username)
        jsonObjectDivceInfo.put("password", appPartner.password)

        def resp = rest.post(url) {
            accept("application/json")
            contentType("application/json")
            //auth(user, passwd)
            body(jsonObjectDivceInfo.toString())
        }

        if (resp.statusCodeValue != 200){
            throw new SpringbatchException("accessToken: ${resp.body.toString()}")
        }

        if(!resp.json.access_token){
            throw new SpringbatchException("accessToken: reponse inattendue => ${resp.json.toString()}")
        }

        def expirationDate =  new Date()
        int expires_in = resp.json.expires_in
        use( TimeCategory ) {
            expirationDate = expirationDate + expires_in.seconds
        }

        JwtInfo jwtInfo = appPartner.getTokenInfo()
        if(jwtInfo == null){
            jwtInfo = new JwtInfo()
            jwtInfo.code = appPartner.code
        }

        jwtInfo.accessToken = resp.json.access_token
        jwtInfo.refreshToken = resp.json.refresh_token
        jwtInfo.tokenType = resp.json.token_type
        jwtInfo.expiresIn = resp.json.expires_in
        jwtInfo.expirationDate = expirationDate
        jwtInfo.save(flush: true)


        return jwtInfo
    }

    JwtInfo refreshToken(AppPartner appPartner, JwtInfo jwtInfo){
        RestBuilder rest = new RestBuilder()
        String url = appPartner.serverUrl+"/oauth/access_token"

        def resp = rest.post(url) {
            accept("application/json")
            contentType("application/x-www-form-urlencoded")
            setProperty("grant_type", "refresh_token")
            setProperty("refresh_token", jwtInfo.refreshToken)
        }

        if (resp.statusCodeValue != 200){
            throw new SpringbatchException("refreshToken: ${resp.body.toString()}")
        }

        if(!resp.json.access_token){
            throw new SpringbatchException("refreshToken: reponse inattendue => ${resp.json.toString()}")
        }

        def expirationDate =  new Date()
        int expires_in = resp.json.expires_in
        use( TimeCategory ) {
            expirationDate = expirationDate + expires_in.seconds
        }

        jwtInfo.accessToken = resp.json.access_token
        jwtInfo.refreshToken = resp.json.refresh_token
        jwtInfo.tokenType = resp.json.token_type
        jwtInfo.expiresIn = resp.json.expires_in
        jwtInfo.expirationDate = expirationDate
        jwtInfo.save(flush: true)

        return jwtInfo
    }

    JwtInfo getValideToken(AppPartner appPartner){

        JwtInfo jwtInfo = appPartner.getTokenInfo()
        if(jwtInfo == null){
            jwtInfo = accessToken(appPartner)
        }
        else {
            def currentDate = new Date()
            int expire_in = 0
            use(groovy.time.TimeCategory) {
                def duration = jwtInfo.expirationDate - currentDate
                expire_in = duration.minutes
            }

            if(expire_in <= 0){
                jwtInfo = accessToken(appPartner)
            }
            else if(expire_in <= 5){
                jwtInfo = refreshToken(appPartner, jwtInfo)
            }
        }

        return jwtInfo
    }


}
