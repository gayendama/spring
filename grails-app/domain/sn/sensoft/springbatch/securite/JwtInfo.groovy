package sn.sensoft.springbatch.securite

import groovy.json.JsonOutput

import javax.persistence.Version

class JwtInfo {

    String id = UUID.randomUUID().toString()
    @Version
    Long version

    // Informations de gestion pour les transferts d'objet.
    String uid = UUID.randomUUID().toString();

    // AppPartner appPartner

    String code // Implémenation spécifique à AICHA, le champ AppPartner n'est plus utilisé.
    String accessToken
    String refreshToken
    String tokenType
    String roles
    String apiKey
    Integer expiresIn
    Date dateCreated
    Date lastUpdated
    String userCreate
    String userUpdate

    Date expirationDate

    Integer refreshExpiresIn

    Date tokenExpiresDate
    Date refreshTokenExpiresDate

    static mapping = {
        accessToken type: 'text'
        refreshToken type: 'text'
        id generator: 'assigned'
    }


    static constraints = {
        uid nullable: true, unique: true
        code blank: false, nullable: false, unique: true
    }


    String logInfos() {
        return JsonOutput.toJson([
                code        : this.code,
                accessToken : this.accessToken,
                refreshToken: this.refreshToken,
                tokenType   : this.tokenType,
                roles       : this.roles,
                expiresIn   : this.expiresIn,
                dateCreated : this.dateCreated
        ])
    }

}
