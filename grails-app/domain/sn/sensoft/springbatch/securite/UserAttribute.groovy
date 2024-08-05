package sn.sensoft.springbatch.securite

import javax.persistence.Version

class UserAttribute {

    String id = UUID.randomUUID().toString()
    @Version
    Long version

    User user
    String otherClaimsKey
    String otherClaimsValue

    static constraints = {
        user nullable: false
        otherClaimsKey blank: true
        otherClaimsValue blank: true
    }
}
