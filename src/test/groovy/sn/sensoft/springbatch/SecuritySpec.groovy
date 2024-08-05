package sn.sensoft.springbatch

import grails.testing.gorm.DomainUnitTest
import spock.lang.Ignore
import spock.lang.Specification

import java.security.Security

@Ignore
class SecuritySpec extends Specification implements DomainUnitTest<Security> {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}
