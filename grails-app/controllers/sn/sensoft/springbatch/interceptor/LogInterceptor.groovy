package sn.sensoft.springbatch.interceptor

import groovy.json.JsonOutput
import sn.sensoft.springbatch.exception.SpringbatchException
import sn.sensoft.springbatch.securite.User
import sn.sensoft.springbatch.utils.ServiceResponse


class UserInterceptor {

    int order = LOWEST_PRECEDENCE

    def keycloakHabilitationService

    public UserInterceptor() {
        // match all requests except requests
        // to the logout controller
        matchAll().excludes(controller: 'logout')
    }

    boolean before() {
        try {
            if (session.currentUser == null) {
                ServiceResponse serviceResponse = keycloakHabilitationService.userInfo(request)
                if (serviceResponse.serviceOk == true) {
                    User currentUser = serviceResponse.obj2
                    session.currentUser = currentUser
                }
                else {
                    throw new SpringbatchException("Impossible d'obetenir les informations de l'utilisateur connecté via Keycloak")
                }


            }
            return true
        }
        catch (SpringbatchException ex) {
            log.error(JsonOutput.toJson([method: "before", message: ex.toString()]), ex)
            return false
        }
    }


    boolean after() { true }

    void afterView() {
        // no-op
    }
}
