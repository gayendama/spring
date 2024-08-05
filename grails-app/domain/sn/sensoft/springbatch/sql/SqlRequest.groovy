package sn.sensoft.springbatch.sql

import javax.persistence.Version

class SqlRequest {

    String id = UUID.randomUUID().toString()
    @Version
    Long version

    // Informations de gestion pour les transferts d'objet.
    String uid = UUID.randomUUID().toString()

    String code
    String requestType
    String requestText
    String databaseType
    String comment
    String requestCategory

    static mapping = {
        requestText type: "text"
        comment type: "text"
    }

    static final List databaseTypes = [DatabaseType.MSSQL.toString(), DatabaseType.MYSQL.toString(), DatabaseType.ORACLE.toString(), DatabaseType.POSTGRESQL.toString()]
    static final List requestTypes = [RequestType.SELECT.toString(), RequestType.UPDATE.toString(), RequestType.INSERT.toString(), RequestType.DELETE.toString(), RequestType.CREATE.toString(), RequestType.DROP.toString(), RequestType.SCRIPT.toString(), RequestType.EXECUTESP.toString(), RequestType.EXECUTESPROW.toString(), RequestType.ALTER.toString()]

    static constraints = {
        code nullable: false, unique: true
        requestType nullable: false, inList: requestTypes
        databaseType nullable: false, inList: databaseTypes
        id generator: 'assigned'
    }

    static auditable = true
    Boolean deleted = false
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
    }

    def beforeUpdate = {
        def userPrincipal = securityUtils?.getCurrentUser()
        if ((userPrincipal != null) && (userPrincipal != 'anonymousUser')) {
            userUpdate = userPrincipal.username
        }
        else {
            userUpdate = ""
        }
    }


}
