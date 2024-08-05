package sn.sensoft.springbatch

import javax.persistence.Version


class ItemToProcess {

    String id = UUID.randomUUID().toString()

    @Version
    Long version

    String objetId
    String jsonString //utiliser s'il y'a d'autres paramètres à prendre en compte
    Integer seq = 0 // Pour avoir un critère de tri pour le traitement

    static constraints = {
        objetId nullable: false, unique: true
        jsonString nullable: true
    }

    static mapping = {
        id generator: 'assigned'
        jsonString type: "text"
    }
}
