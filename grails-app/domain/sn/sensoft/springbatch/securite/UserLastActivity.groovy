package sn.sensoft.springbatch.securite

import javax.persistence.Version

// Classe créée pour éviter de faire des maj sur la table utilisateur au risque de créer des locks
// @gorm.AuditStamp
class UserLastActivity {

    static auditable = false

    String id = UUID.randomUUID().toString()
    @Version
    Long version

    User user
    String event
    Date eventDate
    String additionalInfo
    String remoteAddress
    String sessionId
    String telephone

    // Sauvegarder l'avant dernière action, utile pour sauvegarder les informations de la précédente connexion après un login successful
    Date previousEventDate
    String previousAdditionalInfo
    String previousRemoteAddress
    String previousSessionId
    String previousTelephone

    //audit-trail fields
    String userCreate
    Date dateCreated
    String userUpdate
    Date lastUpdated

    static constraints = {
        user nullable: false, unique: ['event']
    }

    static mapping = {
        id generator: 'assigned'
    }

    def logInfos() {
        def map = [user                  : user?.username,
                   event                 : event,
                   eventDate             : eventDate,
                   additionalInfo        : additionalInfo,
                   remoteAddress         : remoteAddress,
                   sessionId             : sessionId,
                   telephone             : telephone,
                   previousEventDate     : previousEventDate,
                   previousAdditionalInfo: previousAdditionalInfo,
                   previousRemoteAddress : previousRemoteAddress,
                   previousSessionId     : previousSessionId,
                   previousTelephone     : previousTelephone]
        return map
    }


}
