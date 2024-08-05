package springbatch


import grails.util.Environment
import io.micronaut.http.HttpRequest
import io.micronaut.http.MediaType
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken
import org.springframework.beans.factory.annotation.Autowired
import sn.sensoft.springbatch.api.ApicomClient

class BootStrap {

    def dataSource
    def runtimeConfigService
    def grailsApplication

    def sqlCatalogService
    def dbModificationService
    def consulService


    def init = { servletContext ->

        // Initialisation du service sqlCatalogService
        sqlCatalogService.initServiceFromDataSource(dataSource)
        sqlCatalogService.loadSqlCache()

        loadRuntimeConfigParam()

        // Register to consul
        consulService.register()
        // Pour test
        // consulService.setParamValue("sn.sensoft.etab","SENSOFT")

        //testCallHttpClient()

        switch (Environment.current) {

            case Environment.DEVELOPMENT:

                log.info "======== Start BootStrap Environment.DEVELOPMENT ========"

                // Charger les modifications de structures de la base
                dbModificationService.loadDbModification()
                dbModificationService.applyDbModification()
                dbModificationService.syncParamGen()
                dbModificationService.syncMsgTemplate()
                dbModificationService.syncCommEvenement()
                dbModificationService.syncBatchList()
                dbModificationService.syncConstantesBatch()
                dbModificationService.syncMenus()

                log.info "======== End BootStrap Environment.DEVELOPMENT ========"

                break

                // ==============================================================================

            case Environment.PRODUCTION:

                log.info "======== Start BootStrap Environment.PRODUCTION ========"

                // Charger les modifications de structures de la base
                dbModificationService.loadDbModification()
                dbModificationService.applyDbModification()
                dbModificationService.syncParamGen()
                dbModificationService.syncMsgTemplate()
                dbModificationService.syncCommEvenement()
                dbModificationService.syncBatchList()
                dbModificationService.syncConstantesBatch()
                dbModificationService.syncMenus()


                log.info "======== End BootStrap Environment.PRODUCTION ======== "

                break
                // ========================================

            case Environment.TEST:
                log.info "======== Start BootStrap Environment.TEST ========"

                // Charger les données de test
                runtimeConfigService.prop.setProperty("reprise.mode", "test")
                //bootstrapService.loadData()

                // Génération du fichier reqsuestMap pour Keycloak
                //keycloakHabilitationService.saveRequestmapToFile()

                log.info "======== End BootStrap Environment.TEST ========"
        }

        logApplicationInfo()

        // Pour test
        // consulService.serviceCheck()
        // consulService.getParamValue("sn.sensoft.etab")

    }

    def destroy = {
        // unregister to consul
        consulService.unRegister()
        // Pour test
        // consulService.deleteParamValue("sn.sensoft.etab")
    }


    def loadRuntimeConfigParam() {
        log.info "======== Start load properties file ========"
        runtimeConfigService.loadPropertiesFile()
        log.info "======== End load properties file ========"

    }

    def logApplicationInfo() {

        def appVersion = grailsApplication.metadata.getApplicationVersion()
        def grailsVersion = grailsApplication.metadata.getGrailsVersion()
        def jasperReportDir = System.getenv("JASPER_REPORT_DIR")

        log.info("============================================================")
        log.info("      DEMARRAGE SPRINGBATCH ")
        log.info("      --------------------- ")
        log.info("    ")
        log.info(" Version de l'application : ${appVersion}")
        log.info(" Version de grails        : ${grailsVersion}")
        log.info(" System TimeZone          : ${TimeZone.getDefault().getDisplayName()} , ${TimeZone.getDefault().getID()}  ")
        log.info(" OpenApiCom url           : ${runtimeConfigService.getApiComURL()} ")
        log.info(" OpenApiCom user          : ${runtimeConfigService.getApiComUSER()} ")
        log.info(" OpenApiCom is active     : ${runtimeConfigService.getApiComIsActive()} ")
        log.info(" Jasperserver user        : ${runtimeConfigService.getJasperServerUSER()}  ")
        log.info(" Jasperserver url         : ${runtimeConfigService.getJasperServerURL()}  ")
        log.info(" Jasperserver folder      : ${runtimeConfigService.getJasperServerFolderUri()}  ")
        log.info(" Jasperserver etab        : ${runtimeConfigService.getJasperServerEtab()}  ")
        log.info(" Report dir               : ${runtimeConfigService.getJasperReportDir()}  ")
        log.info(" Keycloak url             : ${runtimeConfigService.getKeycloakAuthServer()}  ")
        log.info(" Keycloak realm           : ${runtimeConfigService.getKeycloakRealm()}  ")
        log.info(" Keycloak resource        : ${runtimeConfigService.getKeycloakResource()}  ")
        log.info(" Environnement            : ${Environment.current.name}")
        log.info("    ")
        if (jasperReportDir == null) {
            log.warn(" Environment variable JASPER_REPORT_DIR not set !!! ")
        }
        log.info("============================================================")

    }


    @Client()
    HttpClient client

    @Client()
    HttpClient clientKeyCloack

    HttpRequest request

    BearerAccessRefreshToken bearerAccessRefreshToken

    def testCallHttpClient() {

        // =============================================================================
        // Exemple login to keycloak
        // =============================================================================s
        String clientSecret = "9993c2af-ab16-49b0-a57b-28ed1fc3db4a"
        String username = "user_admin"
        String password = "Password12345"
        String clientId = "biclink"
        String realm = "sensoft"
        String authServerUrl = "http://localhost:8180"
        String serverUrl = "http://localhost:8096"

        clientKeyCloack = HttpClient.create(authServerUrl.toURL())
        request = HttpRequest.POST("/auth/realms/${realm}/protocol/openid-connect/token",
                "grant_type=password&username=" + username + "&password=" + password + "&client_id=" + clientId + "&client_secret=" + clientSecret)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED
                ).contentType(MediaType.APPLICATION_FORM_URLENCODED)
        def httpResponse = clientKeyCloack.toBlocking().exchange(request, BearerAccessRefreshToken.class)
        bearerAccessRefreshToken = httpResponse.body()
        clientKeyCloack.close()
        log.info("Auth to keycloak OK... accessToken=${bearerAccessRefreshToken.accessToken}, httpStatus=${httpResponse.status}")

        // =============================================================================
        // Exemple call with keycloak token
        // =============================================================================
        /*

        client = HttpClient.create(serverUrl.toURL())

        Map<String, Object> institution = [
                "bicId"        : "1599822X",
                "itemValue"    : "AW9958X",
                "descriptionEN": "ARUBA 98 20222X ---",
                "descriptionFR": "ARUBA 98 202222X ----"]

        request = HttpRequest.POST("/country/save", institution).bearerAuth(bearerAccessRefreshToken.accessToken)
        httpResponse = client.toBlocking().exchange(request, JSONObject.class)
        log.info("testCallHttpClient response=${httpResponse.body()}")
        client.close()
        */

    }


    @Autowired
    final ApicomClient apicomClient

    /**
     *
     * @return
     */
//    def sendMsgToApicom() {
//        // Exemple login to apicom (avec interface client)
//        ApicomConfiguration apicomConfiguration = new ApicomConfiguration(
//                grailsApplication.config.getProperty('apicom.username', String),
//                grailsApplication.config.getProperty('apicom.password', String))
//
//        def httpResponse = apicomClient.login(apicomConfiguration.loginJsonObject())
//        if (httpResponse.status == HttpStatus.SC_OK) {
//            log.info("Auth to apicom OK... accessToken=${apicomConfiguration.token.access_token}, httpStatus=${httpResponse.status}")
//            apicomConfiguration.setToken(httpResponse.body())
//        }
//        else {
//            log.error("Auth to apicom NOK... httpStatus=${httpResponse.status}")
//            return
//        }
//
//        // Exemple envoi to apicom (avec interface client)
//        def toList = ["alioune.badiane@sensoft.sn", "alioune@badiane.org"]
//        def comMessageMap = [etabCode    : "SENSOFT",
//                             templateCode: "",
//                             paramMessage: [
//                                     xxx: "xxx",
//                                     zzz: "zzz"
//                             ],
//                             comEmail    : [
//                                     fromEmailAddress: "alioune.badiane@sensoft.sn",
//                                     fromName        : "sensoft",
//                                     toList          : toList,
//                                     ccList          : [],
//                                     bccList         : [],
//                                     subject         : "Test envoi mail émail 202 Sendgrid 123410 via springbatch ${(new Date()).format('dd/MM/yyyy HH:hh:ss')}",
//                                     message         : "Test envoi mail via springbatch ${(new Date()).format('dd/MM/yyyy HH:hh:ss')} ",
//                                     attachmentList  : []
//                             ],
//                             comSms      : [senderID: "SENSOFT", telDst: "221773332579", message: "Test envoi sms"],
//                             typeMessage : "MAIL",
//                             source      : "AICHA"
//        ]
//
//        httpResponse = apicomClient.sendSmsMail(apicomConfiguration.authString(), JsonOutput.toJson(comMessageMap))
//        def apiComResponse = httpResponse.body()
//        if (httpResponse.status == HttpStatus.SC_OK) {
//            log.info("After call " +
//                    "apiComResponse.code=${apiComResponse.code}, " +
//                    "apiComResponse.message=${apiComResponse.message}")
//        }
//        else {
//            log.error("Error call : httpResponseStatus=${httpResponse.status}")
//            return
//        }
//
//
//    }


    // =============================================================================
    // Exemple login to apicom (manuel sans interface client)
    // =============================================================================
    /*
    String apicomUsername = "api_test"
    String apicomPassword = "api_test"
    String apicomServerUrl = "https://apicom.sensoft.sn"
    String apicomSendMailUri = "/api_communication/api/v2/sendsmsmail"

    def loginMap = [username: apicomUsername, password: apicomPassword]

    client = HttpClient.create(apicomServerUrl.toURL())
    request = HttpRequest.POST("/api_communication/api/login", JsonOutput.toJson(loginMap))
            .contentType("application/vnd.org.jfrog.artifactory.security.Group+json")
            .accept("application/json")
    log.info("create request OK")
    response = client.toBlocking().exchange(request, JSONObject.class)
    def jsonToken = response.body()
    // client.close()   // Serra fermé après le call
    log.info("Auth to apicom OK... jsonToken=${jsonToken.access_token}, httpStatus=${response.status}")


    // =============================================================================
    // Exemple send message to apicom (manuel sans interface client)
    // =============================================================================
    def toList = ["alioune.badiane@sensoft.sn", "alioune@badiane.org"]
    def comMessageMap = [etabCode    : "SENSOFT",
                         templateCode: "",
                         paramMessage: [
                                 xxx: "xxx",
                                 yyy: "yyy",
                                 zzz: "zzz"
                         ],
                         comEmail    : [
                                 fromEmailAddress: "alioune.badiane@sensoft.sn",
                                 fromName        : "sensoft",
                                 toList          : toList,
                                 ccList          : [],
                                 bccList         : [],
                                 subject         : "Test envoi mail émail 202 Sendgrid 123410 via springbatch ${(new Date()).format('dd/MM/yyyy HH:hh:ss')}",
                                 message         : "Test envoi mail via springbatch ${(new Date()).format('dd/MM/yyyy HH:hh:ss')} ",
                                 attachmentList  : []
                         ],
                         comSms      : [senderID: "SENSOFT", telDst: "221773332579", message: "Test envoi sms"],
                         typeMessage : "MAIL",
                         source      : "AICHA"
    ]

    request = HttpRequest.POST(apicomSendMailUri, JsonOutput.toJson(comMessageMap))
            .bearerAuth(jsonToken.access_token)
            .contentType("application/json")
            .accept("application/json")
    response = client.toBlocking().exchange(request, JSONObject.class)
    def apiComResponse = response.body()
    log.info("After call " +
            "apiComResponse.code=${apiComResponse.code}, " +
            "apiComResponse.message=${apiComResponse.message}, " +
            "response=${response.status}")
    client.close()

     */

}

