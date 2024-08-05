package sn.sensoft.springbatch.consul

import com.ecwid.consul.v1.ConsulClient
import com.ecwid.consul.v1.QueryParams
import com.ecwid.consul.v1.Response
import com.ecwid.consul.v1.agent.model.NewService
import com.ecwid.consul.v1.health.HealthServicesRequest
import com.ecwid.consul.v1.health.model.HealthService
import com.ecwid.consul.v1.kv.model.GetValue
import grails.core.GrailsApplication
import grails.gorm.transactions.Transactional
import groovy.json.JsonOutput
import sn.sensoft.springbatch.SpringBatchService
import sn.sensoft.springbatch.exception.SpringbatchException

/**
 * Service permettant d'appeler les APIs de consul grâce à la librairie java  consul-api
 *   https://github.com/Ecwid/consul-api
 */
@Transactional
class ConsulService {

    GrailsApplication grailsApplication
    NewService newService
    ConsulClient consulClient

    /**
     * Pour enregistrer l'application Grails dans consul
     */
    void register() {

        log.info(JsonOutput.toJson([method: "register", message: "Register service to consul start...."]))
        Boolean isConsulEnabled = grailsApplication.config.getProperty('consulclient.enabled', Boolean, false)
        if (!isConsulEnabled) {
            log.warn(JsonOutput.toJson([method: "selfRegister", message: "consul service is not registered, consulclient.enabled not set or false "]))
            return
        }

        String consulHost = grailsApplication.config.getProperty("consulclient.consul.host")
        Integer consulPort = grailsApplication.config.getProperty("consulclient.consul.port", Integer)
        String appName = grailsApplication.config.getProperty("consulclient.application.name")
        List<String> appTags = grailsApplication.config.getProperty("consulclient.application.tags", List<String>)

        if (consulClient == null) {
            consulClient = new ConsulClient(consulHost, consulPort);
        }

        // register new service
        String appAddress = "localhost" // TODO à récupérer en dynamique
        Integer appPort = grailsApplication.config.getProperty("server.port", Integer)
        if (newService == null) {
            newService = new NewService();
        }
        newService.setId(appName);
        newService.setName(appName);
        if (appTags != null) {
            //newService.setTags(Arrays.asList(appTags));
            newService.setTags(appTags);
        }
        newService.setPort(appPort);
        newService.setAddress(appAddress)
        consulClient.agentServiceRegister(newService);

        log.info(JsonOutput.toJson([method: "register", message: "Register service to consul end...."]))

    }

    /**
     * Pour vérifier
     */
    void serviceCheck() {

        // register new service
        if (consulClient == null) {
            log.warn(JsonOutput.toJson([method: "selfRegister", message: "No active consul client "]))
            return
        }
        String appName = grailsApplication.config.getProperty("consulclient.application.name")
        // query for healthy services based on name (returns myapp_01 and myapp_02 if healthy)
        HealthServicesRequest request = HealthServicesRequest.newBuilder()
                .setPassing(true)
                .setQueryParams(QueryParams.DEFAULT)
                .build();
        Response<List<HealthService>> healthyServices = consulClient.getHealthServices(appName, request);
        healthyServices.each {
            it.value.each {
                log.info(JsonOutput.toJson([method           : "serviceCheck",
                                            "node.address"   : it.node.address,
                                            "node.node"      : it.node.node,
                                            "node.id"        : it.node.id,
                                            "service.address": it.service.address,
                                            "service.service": it.service.service,
                                            "service.port"   : it.service.port,
                                            "service.tags"   : it.service.tags
                ]))
            }
        }

    }


    /**
     * Pour enregistrer un paramètre dans consul
     */
    void setParamValue(String key, String value) {

        // register new service
        if (consulClient == null) {
            log.warn(JsonOutput.toJson([method: "setParamValue", message: "No active consul client "]))
            return
        }
        consulClient.setKVValue(key, value);
    }

    /**
     * Pour lire la valeur d'un paramètre dans consul
     */
    String getParamValue(String key) {

        // register new service
        if (consulClient == null) {
            log.warn(JsonOutput.toJson([method: "setParamValue", message: "No active consul client "]))
            return null
        }
        // get single KV for key
        Response<GetValue> keyValueResponse = consulClient.getKVValue(key);
        if (keyValueResponse == null) {
            log.error(JsonOutput.toJson([method: "getParamValue", message: "keyValueResponse is null"]))
            throw new SpringbatchException("${key} inexistant ")
        }
        if (keyValueResponse.getValue() == null) {
            log.error(JsonOutput.toJson([method: "getParamValue", message: "keyValueResponse.getValue() is null"]))
            throw new SpringbatchException("${key} inexistant ")
        }
        log.info(JsonOutput.toJson([method: "getParamValue", key: keyValueResponse.getValue().getKey(), value: keyValueResponse.getValue().getDecodedValue()]))
        return keyValueResponse.getValue().getDecodedValue();

    }

    /**
     * Pour supprimer un paramètre dans consul
     */
    void deleteParamValue(String key) {

        // register new service
        if (consulClient == null) {
            log.warn(JsonOutput.toJson([method: "setParamValue", message: "No active consul client "]))
            return
        }
        consulClient.deleteKVValue(key);
    }

    void unRegister() {

        log.info(JsonOutput.toJson([method: "register", message: "Unregister service to consul start...."]))
        // register new service
        if (consulClient == null) {
            log.warn(JsonOutput.toJson([method: "selfRegister", message: "No active consul client "]))
            return
        }
        if (newService == null) {
            log.warn(JsonOutput.toJson([method: "selfRegister", message: "No active consul service "]))
            return
        }
        consulClient.agentServiceDeregister(newService.name);
        log.info(JsonOutput.toJson([method: "register", message: "Unregister service to consul end...."]))
    }

}
