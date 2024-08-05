package sn.sensoft.springbatch.securite

import javax.persistence.Version

class Module {

    String id = UUID.randomUUID().toString()
    @Version
    Long version

    String code
    String libelle

    static constraints = {

        code blank: false, nullable: false, unique: true
        libelle blank: false, nullable: false
    }
    static mapping = {
        id generator: 'assigned'
    }

    String toString() {
        return "${libelle}"
    }

    def getMenuMaps() {
        return MenuMap.findAllWhere(module: this)
    }

    static auditable = true
    String userCreate
    String userUpdate
    Date dateCreated
    Date lastUpdated

}
