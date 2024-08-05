package sn.sensoft.springbatch.api

import grails.gorm.transactions.Transactional
import grails.plugins.rest.client.RestBuilder
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.time.TimeCategory
import io.micronaut.http.client.exceptions.HttpClientException
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken
import org.apache.http.HttpResponse
import org.apache.http.HttpStatus
import org.apache.http.NameValuePair
import org.apache.http.client.HttpClient
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicNameValuePair
import org.apache.http.protocol.HTTP
import org.grails.web.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import sn.sensoft.springbatch.BatchJobExecutionController
import sn.sensoft.springbatch.apiResponse.aicha.SimpleResponse
import sn.sensoft.springbatch.apiResponse.aicha.SituationAgenceResponse
import sn.sensoft.springbatch.dto.AppPartnerDTO
import sn.sensoft.springbatch.exception.SpringbatchException
import sn.sensoft.springbatch.parametrage.Batch
import sn.sensoft.springbatch.parametrage.ProgrammeBatch
import sn.sensoft.springbatch.securite.JwtInfo
import sn.sensoft.springbatch.utils.Constantes
import sn.sensoft.springbatch.utils.ConstantesBatch
import io.micronaut.http.HttpResponse

import javax.batch.runtime.JobExecution
import java.nio.charset.Charset

@Transactional
class ApiManagerService {

    def runtimeConfigService

    @Autowired
    final KeycloakClient keycloakClient

    @Autowired
    final AichaClient aichaClient

    @Autowired
    final ApicomClient apicomClient

    AppPartnerDTO createPartnerDTO(String appPartner) {
        AppPartnerDTO appPartnerDTO

        switch (appPartner) {
            case Constantes.APP_PARTNER_APICOM:
                appPartnerDTO = new AppPartnerDTO()
                appPartnerDTO.code = appPartner
                appPartnerDTO.serverType = appPartner
                appPartnerDTO.serverUrl = runtimeConfigService.getApiComURL()
                appPartnerDTO.username = runtimeConfigService.getApiComUSER()
                appPartnerDTO.password = runtimeConfigService.getApiComPWD()
                appPartnerDTO.loginUri = Constantes.APP_PARTNER_DEFAULT_LOGIN_URI
                appPartnerDTO.refreshTokenUri = Constantes.APP_PARTNER_DEFAULT_REFRESH_TOKEN_URI
                log.debug(JsonOutput.toJson([method: "createPartnerDTO", appPartnerDTO: appPartnerDTO.logInfos()]))

                break;
            case Constantes.APP_PARTNER_AICHA:
                appPartnerDTO = new AppPartnerDTO()
                appPartnerDTO.code = appPartner
                appPartnerDTO.serverType = appPartner
                appPartnerDTO.serverUrl = runtimeConfigService.getAichaUrl()
                appPartnerDTO.username = runtimeConfigService.getAichaUser()
                appPartnerDTO.password = runtimeConfigService.getAichaPassword()
                appPartnerDTO.loginUri = runtimeConfigService.getAichakeycloakUrl()
                appPartnerDTO.refreshTokenUri = runtimeConfigService.getAichakeycloakUrl()
                appPartnerDTO.realm = runtimeConfigService.getAichaKeycloakRealm()
                appPartnerDTO.clientId = runtimeConfigService.getAichaClient()
                appPartnerDTO.clientSecret = runtimeConfigService.getAichaSecret()

                log.debug(JsonOutput.toJson([method: "createPartnerDTO", appPartnerDTO: appPartnerDTO.logInfos()]))

                break;
            default:
                log.error(JsonOutput.toJson([method: "createPartnerDTO", message: "Partner code invalide ${appPartner}"]))
                throw new SpringbatchException("Code partenaire API invalide ${appPartner}")
                break;
        }

        return appPartnerDTO

    }

    JwtInfo accessToken(AppPartnerDTO appPartner) {

        JwtInfo jwtInfo = JwtInfo.findByCode(appPartner.code)
        if (jwtInfo == null) {
            jwtInfo = new JwtInfo()
            jwtInfo.code = appPartner.code
        }

        List<NameValuePair> nvps = new ArrayList<NameValuePair>()
        nvps.add(new BasicNameValuePair("grant_type", "password"))
        nvps.add(new BasicNameValuePair("client_id", appPartner.clientId))
        nvps.add(new BasicNameValuePair("client_secret", appPartner.clientSecret))
        nvps.add(new BasicNameValuePair("username", appPartner.username))
        nvps.add(new BasicNameValuePair("password", appPartner.password))

        String url = appPartner.loginUri + "/realms/${appPartner.realm}/protocol/openid-connect/token"
        JSONObject reponse = callKeyCloack(url, nvps)

        return saveToken(jwtInfo, reponse)
    }

    JSONObject callKeyCloack(String url, List<NameValuePair> nameValuePairs) {
        HttpPost httpPost = new HttpPost(url)
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, Charset.defaultCharset()))
        httpPost.setHeader("Accept", "application/json")

        HttpClient httpClient = new HttpClients().createDefault()
        HttpResponse httpResponse = httpClient.execute(httpPost);
        int codeResonse = httpResponse.getStatusLine().getStatusCode()

        if (codeResonse == 500) {
            throw new SpringbatchException("Aicha Erreur serveur. Veuillez contacter l'administrateur")
        }

        InputStream inputStream = httpResponse.getEntity().getContent()
        String result = convertInputStreamToString(inputStream)
        JSONObject reponse = new JSONObject(result)

        if (inputStream == null) {
            throw new SpringbatchException("accessToken: response is null")
        }

        if (codeResonse == 400) {
            throw new SpringbatchException("${reponse.invalid_request}: ${reponse.error_description}")
        }

        return reponse
    }

    JwtInfo refreshToken(AppPartnerDTO appPartner, JwtInfo jwtInfo) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>()
        nvps.add(new BasicNameValuePair("grant_type", "refresh_token"))
        nvps.add(new BasicNameValuePair("client_id", appPartner.clientId))
        nvps.add(new BasicNameValuePair("client_secret", appPartner.clientSecret))
        nvps.add(new BasicNameValuePair("refresh_token", jwtInfo.refreshToken))

        String url = appPartner.loginUri + "/realms/${appPartner.realm}/protocol/openid-connect/token"
        JSONObject reponse = callKeyCloack(url, nvps)

        return saveToken(jwtInfo, reponse)
    }

    JwtInfo saveToken(JwtInfo jwtInfo, JSONObject reponse) {
        def tokenExpiresDate = new Date()
        int expires_in = reponse.expires_in
        use(TimeCategory) {
            tokenExpiresDate = tokenExpiresDate + expires_in.seconds
        }

        def refreshTokenExpiresDate = new Date()
        int refreshExpires_in = reponse.refresh_expires_in
        use(TimeCategory) {
            refreshTokenExpiresDate = refreshTokenExpiresDate + refreshExpires_in.seconds
        }

        jwtInfo.accessToken = reponse.access_token
        jwtInfo.refreshToken = reponse.refresh_token
        jwtInfo.tokenType = reponse.token_type
        jwtInfo.expiresIn = reponse.expires_in
        jwtInfo.refreshExpiresIn = reponse.refresh_expires_in
        jwtInfo.tokenExpiresDate = tokenExpiresDate
        jwtInfo.refreshTokenExpiresDate = refreshTokenExpiresDate
        jwtInfo.roles = reponse.roles
        jwtInfo.lastUpdated = new Date()

        if (!jwtInfo.save(flush: true) || jwtInfo.hasErrors()) {
            log.error("methode: saveToken, message: Erreur lors de la dauvegarde de jwtInfo,  ${jwtInfo.errors}")
            throw new SpringbatchException("methode: saveToken, message: Erreur lors de la dauvegarde de jwtInfo,  ${jwtInfo.errors}")
        }

        return jwtInfo
    }

    JwtInfo getToken(String partnerCode) {
        AppPartnerDTO appPartner = createPartnerDTO(partnerCode)
        JwtInfo jwtInfo = JwtInfo.findByCode(appPartner.code)

        if (jwtInfo == null) {
            jwtInfo = accessToken(appPartner)
        }
        else {
            def currentDate = new Date()
            int expire_in = 0
            use(TimeCategory) {
                def duration = jwtInfo.tokenExpiresDate - currentDate
                expire_in = duration.minutes
            }

            if (expire_in <= 0) {
                jwtInfo = accessToken(appPartner)
            }
            else if (expire_in <= 1) {
                jwtInfo = refreshToken(appPartner, jwtInfo)
            }
        }

        return jwtInfo
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    def callApp(def method) {
        HttpClient httpclient = new HttpClients().createDefault()
        HttpResponse httpResponse = httpclient.execute(method)
        int responseCode = httpResponse.getStatusLine().getStatusCode()

        if (responseCode == 500) {
            throw new SpringbatchException("AmlLnk Erreur serveur. Veuillez contacter l'administrateur")
        }

        InputStream inputStream = httpResponse.getEntity().getContent()
        String result = convertInputStreamToString(inputStream)
        JSONObject reponse = new JSONObject(result)

        if (responseCode == 400) {
            log.error(reponse.toString())
            throw new SpringbatchException(reponse.userMessage)
        }

        return reponse
    }

    def callAppByGet(String appPartnerCode, String apiResource, String parameters) {
        AppPartnerDTO appPartnerDTO = createPartnerDTO(appPartnerCode)
        String token = getToken(appPartnerCode)?.accessToken

        String url = "${appPartnerDTO.serverUrl}/${apiResource}"
        if (parameters != null && parameters != "") {
            url = "${url}?${parameters}"
        }

        HttpGet httpGet = new HttpGet(url)
        httpGet.addHeader("Authorization", "Bearer ${token}")

        return callApp(httpGet)
    }

    String aichaBearerToken() {
        KeycloakConfiguration keycloakConfiguration = new KeycloakConfiguration(
                runtimeConfigService.getAichaUser(),
                runtimeConfigService.getAichaPassword(),
                runtimeConfigService.getAichaClient(),
                runtimeConfigService.getAichaSecret()
        )

        JwtInfo jwtInfo = JwtInfo.findByCode(Constantes.APP_PARTNER_AICHA)

        if (jwtInfo == null) {//Si le token n'existe pas on fait un access token

            log.debug(JsonOutput.toJson([method: "aichaBearerToken", "keycloakConfiguration": keycloakConfiguration.logInfos()]))

            jwtInfo = new JwtInfo()
            jwtInfo.code = Constantes.APP_PARTNER_AICHA
            BearerAccessRefreshToken bearerAccessRefreshToken = keycloakClient.login(keycloakConfiguration.loginMap()).body()
            jwtInfo = saveToken(jwtInfo, bearerAccessRefreshToken)
        }
        else {// si le token existe on verifie si ce n'est pas expiré
            def currentDate = new Date()
            int expire_in = 0
            use(TimeCategory) {
                def duration = jwtInfo.expirationDate - currentDate
                expire_in = duration.seconds
            }

            // si le token a expiré on fait un refresh token => { "error": "invalid_grant", "error_description": "Token is not active"}
            if (expire_in <= 0) {
                BearerAccessRefreshToken bearerAccessRefreshToken = keycloakClient.login(keycloakConfiguration.loginMap()).body()
                jwtInfo = saveToken(jwtInfo, bearerAccessRefreshToken)
                log.debug(JsonOutput.toJson([method: "aichaBearerToken", message: "TOKEN expired, refreshede!! ==> ${jwtInfo.tokenType} ${jwtInfo.accessToken} "]))
            }
            else if (expire_in <= 5) { //si le token est sur le point d'expirer
                BearerAccessRefreshToken bearerAccessRefreshToken = keycloakClient.login(keycloakConfiguration.refreshTokenMap(jwtInfo.refreshToken)).body()
                jwtInfo = saveToken(jwtInfo, bearerAccessRefreshToken)

                log.debug(JsonOutput.toJson([method: "aichaBearerToken", message: "TOKEN expired, refreshede!! ==> ${jwtInfo.tokenType} ${jwtInfo.accessToken} "]))
            }
            else {
                log.debug(JsonOutput.toJson([method: "aichaBearerToken", message: "TOKEN is Active!! ==> ${jwtInfo.tokenType} ${jwtInfo.accessToken} "]))
            }
        }

        return "${jwtInfo.tokenType} ${jwtInfo.accessToken}"
    }

    String apiComBearerToken() {
        ApicomConfiguration apicomConfiguration = new ApicomConfiguration(runtimeConfigService.getApiComUSER(), runtimeConfigService.getApiComPWD())
        JwtInfo jwtInfo = JwtInfo.findByCode(Constantes.APP_PARTNER_APICOM)

        if (jwtInfo == null) {//Si le token n'existe pas on fait un access token
            jwtInfo = new JwtInfo()
            jwtInfo.code = Constantes.APP_PARTNER_APICOM

            //JSONObject result = apicomClient.login(apicomConfiguration.loginJsonObject()).body()

            def bearerAccessRefreshToken = apicomClient.login(apicomConfiguration.loginJsonObject()).body()
            jwtInfo = saveToken(jwtInfo, bearerAccessRefreshToken)
        }
        else {// si le token existe on verifie si ce n'est pas expiré
            def currentDate = new Date()
            int expire_in = 0
            use(TimeCategory) {
                def duration = jwtInfo.expirationDate - currentDate
                expire_in = duration.seconds
            }

            // si le token a expiré on fait un refresh token => { "error": "invalid_grant", "error_description": "Token is not active"}
            if (expire_in <= 1) {
                BearerAccessRefreshToken bearerAccessRefreshToken = apicomClient.login(apicomConfiguration.loginJsonObject()).body()
                jwtInfo = saveToken(jwtInfo, bearerAccessRefreshToken)
            }
//            else if (expire_in <= 5) { //si le token est sur le point d'expirer
//                BearerAccessRefreshToken bearerAccessRefreshToken = apicomClient.login(apicomConfiguration.loginJsonObject()).body()
//                jwtInfo = saveToken(jwtInfo, bearerAccessRefreshToken)
//            }
        }


        return "${jwtInfo.tokenType} ${jwtInfo.accessToken}"
    }

    JwtInfo saveToken(JwtInfo jwtInfo, BearerAccessRefreshToken bearerAccessRefreshToken) {
        jwtInfo.accessToken = bearerAccessRefreshToken.accessToken
        jwtInfo.refreshToken = bearerAccessRefreshToken.refreshToken
        jwtInfo.tokenType = bearerAccessRefreshToken.tokenType
        jwtInfo.expiresIn = bearerAccessRefreshToken.expiresIn
        def expirationDate = new Date()
        use(TimeCategory) {
            jwtInfo.expirationDate = expirationDate + bearerAccessRefreshToken.expiresIn.seconds
        }
        jwtInfo.save(flush: true)
        return jwtInfo
    }

    SituationAgenceResponse getAichaSituationAgence() {
        try {
            String aichaBearerToken = aichaBearerToken()
            log.debug(JsonOutput.toJson([method: "getAichaSituationAgence", aichaBearerToken: aichaBearerToken]))

            HttpResponse<SituationAgenceResponse> httpResponse = aichaClient.getAgencySituation(aichaBearerToken)

            log.debug("httpResponse.status=${httpResponse?.status()} ,httpResponse=${httpResponse?.toString()}   ")

            SituationAgenceResponse situationAgence = httpResponse.body()
            if(situationAgence == null){
                log.error(JsonOutput.toJson([method: "getAichaSituationAgence", error: "Reponse serveur null, veuillez bérifier si selatis est disponible", response: httpResponse?.body()?.toString()]))
                return new SituationAgenceResponse(apiAccess: false, openAgency: false, openedCheckout: 0, appVersion: 'un kown')
            }

            situationAgence.apiAccess = true
            return situationAgence
        } catch (HttpClientException exception) {
            log.error(JsonOutput.toJson([method: "getAichaSituationAgence", message: exception.getMessage(), localizedMessage: exception.localizedMessage]))
            //closeBatch()
            return new SituationAgenceResponse(apiAccess: false, openAgency: false, openedCheckout: 0, appVersion: 'un kown')
        }
    }

    def closeBatch() {
        Batch.findAllByIndActif(true).each {
            try {
                def programStatus = [Constantes.STATUT_STARTED]

                def programmeBatchList = ProgrammeBatch.findAllByCodeProgrammeAndProgramStatusInList(it.name, programStatus)
                if (programmeBatch == null) {
                    return
                }
                programmeBatchList.each {
                    log.warn(JsonOutput.toJson([method: "---------------------------------------programmeStatus------------------------------------------------------", message: it.toString()]))
                }
            }
            catch (SpringbatchException ex) {
                log.error(JsonOutput.toJson([method: "programmeStatus", message: ex.message]))
            }
        }
    }
}
