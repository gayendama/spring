package sn.sensoft.springbatch.securite

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.springframework.http.HttpMethod

import javax.persistence.Version

@EqualsAndHashCode(includes = ['configAttribute', 'httpMethod', 'url'])
@ToString(includes = ['configAttribute', 'httpMethod', 'url'], cache = true, includeNames = true, includePackage = false)
class Requestmap implements Serializable {

    private static final long serialVersionUID = 1

    String id = UUID.randomUUID().toString()
    @Version
    Long version

    String configAttribute
    HttpMethod httpMethod
    String url

    static constraints = {
        configAttribute blank: false
        httpMethod nullable: true
        url blank: false, unique: ['httpMethod']
    }

    static mapping = {
        cache true
        id generator: 'assigned'
    }

    static auditable = true
    String userCreate
    String userUpdate
    Date dateCreated
    Date lastUpdated


}
