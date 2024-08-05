package sn.sensoft.springbatch.syncdb

import javax.persistence.Version

class VersionDbScript {

    String id = UUID.randomUUID().toString()
    @Version
    Long version

    VersionDb versionDb
    Integer seq
    String scriptCode
    // Champ introduit pour utiliser SqlCatalogService. En effet, en fonction de la base de données utilisée le script peut être différent
    String scriptText
    String scriptErrorMessage
    String scriptStatus
    Date dateExecuted

    static constraints = {

    }

    static mapping = {
        id generator: 'assigned'
    }

    String toString() {
        return "${seq}"
    }
}
