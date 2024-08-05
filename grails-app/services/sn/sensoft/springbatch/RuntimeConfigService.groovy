package sn.sensoft.springbatch

import grails.util.Environment
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import sn.sensoft.springbatch.exception.SpringbatchException

import javax.naming.Context
import javax.naming.InitialContext

class RuntimeConfigService {


    def grailsApplication


    String getKeycloakRealm() {
        return getRuntimeProperty("keycloak.realm")
    }

    String getKeycloakSslRequired() {
        return getRuntimeProperty("keycloak.sslRequired")
    }

    String getKeycloakAuthServer() {
        return getRuntimeProperty("keycloak.url")
    }

    String getKeycloakResource() {
        return getRuntimeProperty("keycloak.resource")
    }

    String getKeycloakSecret() {
        return getRuntimeProperty("keycloak.secret")
    }

    String getKeycloakUri() {
        "${getKeycloakAuthServer()}/realms/${getKeycloakRealm()}/account/password"
    }

    String getUriAccount() {
        "${getKeycloakAuthServer()}/realms/${getKeycloakRealm()}/account"
    }

    String getOpenKMURL() {
        return getRuntimeProperty("openkm.url")
    }

    String getOpenKMUSER() {
        return getRuntimeProperty("openkm.user")
    }

    String getOpenKMPWD() {
        return getRuntimeProperty("openkm.password")
    }

    String getBackupScript() {
        return getRuntimeProperty("backup.script")
    }

    String getBackupExtension() {
        return getRuntimeProperty("backup.extension")
    }

    String getBackupContext() {
        return getRuntimeProperty("backup.context")
    }


    String getJasperServerURL() {
        return getRuntimeProperty("jasper.url")
    }

    String getJasperServerUSER() {
        return getRuntimeProperty("jasper.user")
    }


    String getJasperServerPWD() {
        return getRuntimeProperty("jasper.password")
    }


    String getJasperServerEtab() {
        return getRuntimeProperty("jasper.etab")
    }

    String getJasperServerFolderUri() {
        return getRuntimeProperty("jasper.folder")
    }

    String getJasperReportDir() {
        return getRuntimeProperty("jasper.reportDir")
    }


    String getAdminEmail() {
        return getRuntimeProperty("admin.email")
    }

    String getApiComURL() {
        return getRuntimeProperty("apicom.url")
    }

    String getApiComEstablishmentCode() {
        return getRuntimeProperty("apicom.etablissementCode")
    }

    String getApiComSenderMail(){
        return getRuntimeProperty("apicom.senderMail","noreply@sensoft.sn")
    }

    String getApiComUSER() {
        return getRuntimeProperty("apicom.user")
    }

    String getApiComPWD() {
        return getRuntimeProperty("apicom.password")
    }

    String getApiComIsActive() {
        return getRuntimeProperty("apicom.isActive", "N")
    }

    String getAichaUrl() {
        return getRuntimeProperty("aicha.url")
    }

    String getAichaUser() {
        return getRuntimeProperty("aicha.user")
    }

    String getAichaPassword() {
        return getRuntimeProperty("aicha.password")
    }

    String getAichaClient() {
        return getRuntimeProperty("aicha.client")
    }

    String getAichakeycloakUrl() {
        return getRuntimeProperty("aicha.keycloak.url")
    }

    String getAichaKeycloakRealm() {
        return getRuntimeProperty("aicha.keycloak.realm")
    }

    String getAichaSecret() {
        return getRuntimeProperty("aicha.secret")
    }

    synchronized String getCryptoKey() {
        return getRuntimeProperty("crypto.key.password")
    }

    synchronized String getKeycloackConfigurationFile() {
        return getRuntimeProperty("keycloak.configFilePath")
    }

    String getSendSecurityAlert() {
        return getRuntimeProperty("notif.sendSecurityAlert", "true")
    }

    Properties prop

    // see aicha.properties sample file in src/main/resources/aicha.properties
    def loadPropertiesFile() {
        try {

            String propFileLocation
            if ((Environment.isDevelopmentMode()) || (Environment.getCurrent() == Environment.TEST)) {
                propFileLocation = grailsApplication.config.getProperty('propFile.location')
            }
            else {
                propFileLocation = ((Context) (new InitialContext().lookup("java:comp/env"))).lookup("propFileLocation")
            }
            if (propFileLocation == null) {
                throw new SpringbatchException("propFileLocation is not set")
            }

            InputStream input = new FileInputStream(propFileLocation)
            Properties prop = new Properties();

            // load a properties file
            prop.load(input);
            this.prop = prop
            // update keycloack values loaded from file
            updateKeycloakProperties()

            // Log properties values
            this.prop.each { code, value ->
                if ((code.contains("password")) || (code.contains("secret"))) {
                    log.info(JsonOutput.toJson([method: "loadPropertiesFile", "${code}": "*************"]))
                }
                else {
                    log.info(JsonOutput.toJson([method: "loadPropertiesFile", "${code}": value]))
                }
            }

        }
        catch (IOException ex) {
            log.error(JsonOutput.toJson([method: "loadPropertiesFile", message: ex.toString()]))
        }
        catch (SpringbatchException ex) {
            log.error(JsonOutput.toJson([method: "loadPropertiesFile", message: ex.toString()]))
        }
    }

    // see aicha.properties sample file in src/main/resources/aicha.properties
    String getRuntimeProperty(String propertyCode) {
        return getRuntimeProperty(propertyCode, null)
    }

    // see aicha.properties sample file in src/main/resources/aicha.properties
    String getRuntimeProperty(String propertyCode, String defaultValue) {

        String propertyValue
        if (this.prop == null) {
            throw new SpringbatchException("Property file not loaded!!!")
        }
        propertyValue = this.prop.getProperty(propertyCode)?.trim()
        if (propertyValue == null) {
            if (defaultValue == null) {
                throw new SpringbatchException("Aucune valeur de propiété n'a été définie pour ${propertyCode}")
            }
            else {
                propertyValue = defaultValue
            }
        }
        return propertyValue
    }

    /**
     * Parse and save keycloak values read from keycloak config file
     */
    void updateKeycloakProperties() {

        def jsonSlurper = new JsonSlurper()

        if (this.prop == null) {
            log.warn(JsonOutput.toJson([method: "updateKeycloakProrties", message: "properties object is null !!"]))
            return
        }

        String keycloakConfigFilePath = this.prop.getProperty("keycloak.configFilePath")?.trim()
        if (keycloakConfigFilePath == null) {
            log.warn(JsonOutput.toJson([method: "updateKeycloakProrties", message: "No keycloak.configFilePath found in property file!!!"]))
            return
        }

        def keycloakConfigFile = new File(keycloakConfigFilePath)
        if (!keycloakConfigFile.exists()) {
            log.warn(JsonOutput.toJson([method: "updateKeycloakProrties", message: " keycloak config file does not exists ${keycloakConfigFilePath}"]))
            return
        }

        def keycloakMap = []
        keycloakMap = jsonSlurper.parse(keycloakConfigFile)
        log.debug(JsonOutput.toJson([method: "updateKeycloakProrties", keycloakMap: keycloakMap]))

        this.prop.setProperty("keycloak.realm", keycloakMap.realm)
        this.prop.setProperty("keycloak.url", keycloakMap."auth-server-url")
        this.prop.setProperty("keycloak.sslRequired", keycloakMap."ssl-required")
        this.prop.setProperty("keycloak.resource", keycloakMap.resource)
        this.prop.setProperty("keycloak.secret", keycloakMap.credentials.secret)

        return

    }

    Map searchParams(String params) {
        Map searchParams = [:]
        if (params != null) {
            params.replace("[", "")?.replace("]", "")?.replace(", ", ",")?.split(",")?.each { sParams ->
                def nameAndValue = sParams.split(":")
                if (nameAndValue.size() > 1) {
                    searchParams.put(nameAndValue[0].trim(), nameAndValue[1])
                }
                else {
                    searchParams.put(nameAndValue[0], null)
                }
            }
        }

        return searchParams
    }


}

