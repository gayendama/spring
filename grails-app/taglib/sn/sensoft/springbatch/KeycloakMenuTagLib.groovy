package sn.sensoft.springbatch

import sn.sensoft.springbatch.securite.KeycloakHabilitationService
import sn.sensoft.springbatch.securite.User


class KeycloakMenuTagLib {

    static defaultEncodeAs = [taglib:'none']

    static namespace = "sec"

    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    KeycloakHabilitationService keycloakHabilitationService


    def ifLoggedIn = { attrs, body ->
        User user = keycloakHabilitationService.currentUser()
        if(user!=null){
            out << body
        }
    }


    def ifAnyGranted = { attrs, body ->
        def currentUser = User.findWhere(username: keycloakHabilitationService.currentUser()?.username)
        if (currentUser != null) {

            def roles = currentUser.authorities

            List<String> anyRoleToHave = attrs.roles.replace(' ', '').split(",")

            roles.any { role ->
                if (anyRoleToHave.contains(role.authority)) {
                    out << body()
                }
            }
        }
    }

    def ifNotGranted = { attrs, body ->
        def currentUser = User.findWhere(username: keycloakHabilitationService.currentUser()?.username)
        if (currentUser != null) {

            def roles = currentUser.authorities

            List<String> anyRoleToHave = attrs.roles.replace(' ', '').split(",")
            Integer roleCount = 0
            roles.any { role ->
              if (anyRoleToHave.contains(role.authority)) {
                  roleCount = roleCount + 1
                }
            }
            if(roleCount == 0){
                out << body()
            }
        }
    }




//    def ifAnyGranted = { attrs, body ->
//
//         def roles = attrs.roles
//
//         User user = keycloakHabilitationService.currentUser()
//        log.debug("user dans le tag >>> $user")
//         //intersection des roles de user et des roles definis dans l'attribut roles
//         def hasAccess = ((Collection<String>) user.getAuthorities().authority ).intersect((ArrayList)roles.split(",")).empty
//         if(hasAccess){
//             out << body
//         }
//
//    }
}