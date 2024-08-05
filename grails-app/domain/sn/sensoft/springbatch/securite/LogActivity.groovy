package sn.sensoft.springbatch.securite


import javax.persistence.Version

class LogActivity {

    String id = UUID.randomUUID().toString()
    @Version
    Long version

    User user
    Date dateComptable
    String typeLog
    String ipAddressUser
    String codeUtilisateur
    String message

    // Ajouts autres attributs inspirés de Concept Linker
    Date eventDate
    String detailedMessage
    String objectId // L'id de l'objet à loguer
    String objectName // Le nom de l'objet à loguer

    //Informations de notifications
    String roleToNotify  // Les user ayant ce role seront automatiquement notifiés voir Constants.ROLE_NOTIFY_XXXXX
    Date noticationDate
    Boolean isNotified = false // Si les personnes ont été notifiés


    static constraints = {

        typeLog nullable: false
        codeUtilisateur blank: false, nullable: false
        // codeBureau blank: false, nullable: false   // Modif ABB pour loguer les évenements à l'authentification
        message nullable: false, size: 0..500
        detailedMessage size: 0..3000
    }

    String toString() {
        return "${typeLog}-${message}"
    }

    static mapping = {
        id generator: 'assigned'
    }

    static auditable = true
    String userCreate
    String userUpdate
    Date dateCreated
    Date lastUpdated



}
