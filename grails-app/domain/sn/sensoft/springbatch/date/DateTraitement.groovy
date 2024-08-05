package sn.sensoft.springbatch.date

import sn.sensoft.springbatch.utils.Constantes

import javax.persistence.Version

class DateTraitement {

    String id = UUID.randomUUID().toString()
    @Version
    Long version

    String codeDate
    Date dateTraitement
    Date ancienneDateTraitement

    static constraints = {
        codeDate blank: false, nullable: false, inList: [
                Constantes.DATE_TRAITEMENT_JOUR,
                Constantes.DATE_TRAITEMENT_VEILLE,
                Constantes.DATE_TRAITEMENT_LENDEMAIN,
                Constantes.DATE_TRAITEMENT_MOIS
        ]
        dateTraitement nullable: false
        ancienneDateTraitement nullable: false
    }

    static mapping = {
        id generator: 'assigned'
    }

    String toString() {
        return "${dateTraitement}"
    }

    String userCreate
    String userUpdate
    Date dateCreated
    Date lastUpdated


}

