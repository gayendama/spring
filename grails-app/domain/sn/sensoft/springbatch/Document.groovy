package sn.sensoft.springbatch

import javax.persistence.Version

class Document {

    String id = UUID.randomUUID().toString()
    @Version
    Long version

    String name
    byte[] data
    int size
    String extension
    String docType
    String keyHash

    static mapping = {
        id generator: 'assigned'
    }

    static constraints = {
        name blank: false, nullable: false
        keyHash unique: true
    }

    String toString() {
        return "${name}"
    }

    static auditable = true
    String userCreate
    String userUpdate
    Date dateCreated
    Date lastUpdated
    def securityUtils
    // GORM Events
    def beforeInsert = {
        def userPrincipal = securityUtils?.getCurrentUser()
        if ((userPrincipal != null) && (userPrincipal != 'anonymousUser')) {
            userCreate = userPrincipal.username
        }
        else {
            userCreate = ""
        }
        dateCreated = new Date()

    }

    def beforeUpdate = {
        def userPrincipal = securityUtils?.getCurrentUser()
        if ((userPrincipal != null) && (userPrincipal != 'anonymousUser')) {
            userUpdate = userPrincipal.username
        }
        else {
            userUpdate = ""
        }
        lastUpdated = new Date()

    }

}

