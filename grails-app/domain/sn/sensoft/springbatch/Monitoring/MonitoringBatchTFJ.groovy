package sn.sensoft.springbatch.Monitoring

import javax.persistence.Version

class MonitoringBatchTFJ {

    String id = UUID.randomUUID().toString()
    @Version
    Long version

    String idBatch

    Date lastExcecutionDate

    Boolean batchlaunched

    static constraints = {
        lastExcecutionDate(nullable: true)
        batchlaunched(nullable: false)
        idBatch(nullable: false, unique : true)
    }

    String toString() {
        return "${MonitoringBatchTFJ.toString()}"
    }

    static mapping = {
        id generator: 'assigned'
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
