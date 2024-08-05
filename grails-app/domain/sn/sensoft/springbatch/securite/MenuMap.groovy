package sn.sensoft.springbatch.securite

import javax.persistence.Version

class MenuMap {

    String id = UUID.randomUUID().toString()
    @Version
    Long version

    Module module
    MenuMap parent

    String url
    String label
    String description

    static mapping = {
        description type: "text"
        id generator: 'assigned'
    }

    static constraints = {
        url blank: false, nullable: false, unique: true
    }

    String toString() {
        return "${label ?: url}"
    }

    def getMenuMapFils() {
        return MenuMap.findAllWhere(parent: this)
    }

    static auditable = true
    String userCreate
    String userUpdate
    Date dateCreated
    Date lastUpdated

}
