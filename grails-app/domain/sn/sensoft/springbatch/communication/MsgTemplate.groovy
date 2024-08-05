package sn.sensoft.springbatch.communication

import javax.persistence.Version

/**
 * Template de mesages pour les libellés internes.
 */
class MsgTemplate {

    String id = UUID.randomUUID().toString()
    @Version
    Long version

    String code
    String lang = Locale.FRENCH.toString() // Langue Française par défaut
    String message
    String categorie
    String description

    static mapping = {
        message type: "text"
        id generator: 'assigned'
    }

    static constraints = {
        code blank: false, nullable: false, unique: ['lang']
        lang blank: false, nullable: false, inList: [Locale.FRENCH.toString(), Locale.ENGLISH.toString(), Locale.GERMAN.toString(), (new Locale("es", "ES")).toString()]
        message blank: false, nullable: false
    }

    String toString() {
        return "${code}-${lang}"
    }

    String userCreate
    String userUpdate
    Date dateCreated
    Date lastUpdated


}
