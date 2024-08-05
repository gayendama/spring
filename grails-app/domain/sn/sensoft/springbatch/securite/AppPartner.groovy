package sn.sensoft.springbatch.securite

import javax.persistence.Version

class AppPartner {

    String id = UUID.randomUUID().toString()
    @Version
    Long version

    // Informations de gestion pour les transferts d'objet.
    String uid = UUID.randomUUID().toString();

    String code
    String intitule
    String apiKey
    String serverUrl //Api url


    String realm
    String clientId
    String clientSecret
    String authUrl
    String username
    String password

    //Informations Système
    Boolean deleted = false
    Date dateCreated
    Date lastUpdated
    String userCreate
    String userUpdate

    static mapping = {
        id generator: 'assigned'
        uid generator: 'assigned'

    }

    static constraints = {
        code blank: false, nullable: false, unique: true
        uid blank: false, nullable: true, unique: true
        intitule blank: false, nullable: true, size: 2..250
    }


    String toString() {
        return "${code} - ${intitule}"
    }

    JwtInfo getTokenInfo() {
        // Get the token
        return JwtInfo.findByCode(this.code)
    }


}
