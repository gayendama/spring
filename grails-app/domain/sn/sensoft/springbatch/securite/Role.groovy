package sn.sensoft.springbatch.securite

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Version

@EqualsAndHashCode(includes = 'authority')
@ToString(includes = 'authority', includeNames = true, includePackage = false)
class Role implements Serializable {

    private static final long serialVersionUID = 1

    String id = UUID.randomUUID().toString()
    @Version
    Long version

    String authority
    String description

    static constraints = {
        authority nullable: false, blank: false, unique: true, matches: /ROLE_[A-Za-z0-9_]+/, validator: { value, roleInstance, errors ->
            if (!value || !(value?.startsWith("ROLE_"))) {
                errors.rejectValue("authority", "role.authority.wrong.syntax", "Le role doit commencer par 'ROLE_' ")
                return false
            }
            return true
        }
        description nullable: true
    }

    static mapping = {
        cache true
        description type: "text"
        id generator: 'assigned'
    }

    String toString() {
        return "${authority}"
    }

    static auditable = true
    String userCreate
    String userUpdate
    Date dateCreated
    Date lastUpdated

}
