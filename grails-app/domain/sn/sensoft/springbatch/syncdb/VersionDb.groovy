package sn.sensoft.springbatch.syncdb

import javax.persistence.Version

class VersionDb {

    String id = UUID.randomUUID().toString()
    @Version
    Long version

    Integer versionDbNumber
    Date versionDbDate
    String appGrailsVersion
    String appName
    String appVersion
    String comments

    static constraints = {

    }
    static mapping = {
        id generator: 'assigned'
    }

    String toString() {
        return "${versionDbNumber}"
    }
}
