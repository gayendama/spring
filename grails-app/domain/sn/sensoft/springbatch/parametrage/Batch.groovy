package sn.sensoft.springbatch.parametrage

import sn.sensoft.springbatch.utils.Constantes

import javax.persistence.Version

class Batch {

    String id = UUID.randomUUID().toString()
    @Version
    Long version

    String batchType
    String name
    String description
    Boolean indActif = true
    Boolean deleted = false

    static constraints = {
        name blank: false, nullable: false, unique: true
    }
    static mapping = {
        id generator: 'assigned'
    }

    String toString() {
        return "${name}"
    }

    static auditable = true
    String userCreate
    String userUpdate
    Date dateCreated
    Date lastUpdated
}
