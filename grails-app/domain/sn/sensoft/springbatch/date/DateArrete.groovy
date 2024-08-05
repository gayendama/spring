package sn.sensoft.springbatch.date


import javax.persistence.Version

class DateArrete {

    String id = UUID.randomUUID().toString()
    @Version
    Long version

    String mutuelleId
    String codeMutuelle
    String libelleMutuelle

    Date dateDebut
    Date dateArrete
    Boolean arreteEnCours = false
    Date dateClotureArrete
    String typeArrete

    static constraints = {
        dateArrete nullable: false, unique: true
        typeArrete nullable: false
        mutuelle nullable: true
    }

    static mapping = {
        id generator: 'assigned'
    }

    String toString() {
        return "${dateArrete}"
    }

    def getDateArreteDisplay() {
        dateArrete?.format("dd-MM-yyyy")
    }

    def getDateArreteDisplayFr() {
        dateArrete?.format("dd/MM/yyyy")
    }

    //pour controller l'affichage au format voulu
    String getDateDebutDisplay() {
        dateDebut?.format("dd/MM/yyyy")
    }

    String getDateClotureArreteDisplay() {
        dateClotureArrete?.format("dd/MM/yyyy")
    }

    static auditable = true
    String userCreate
    String userUpdate
    Date dateCreated
    Date lastUpdated
    // GORM Events

}
