package sn.sensoft.springbatch.communication

import javax.persistence.Version

class CommEvenement {

    String id = UUID.randomUUID().toString()
    @Version
    Long version

    String code
    String codeTemplate
    Boolean deleted = false
    Boolean indContrePartie
    String description

    static mapping = {
        description type: "text"
        id generator: 'assigned'
    }

    static constraints = {
        code nullable: false, unique: true
    }

    String toString() {
        return "${code}-${codeTemplate}"
    }
}
