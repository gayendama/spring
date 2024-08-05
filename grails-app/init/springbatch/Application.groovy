package springbatch

import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration
import groovy.transform.CompileStatic
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableScheduling
import sn.sensoft.springbatch.configuration.Custom

@CompileStatic
//@EnableBatchProcessing
@EnableScheduling
@EnableConfigurationProperties
@ComponentScan(basePackages = ["springbatch","sn.sensoft.springbatch.utils"], basePackageClasses = [Custom.class])
class Application extends GrailsAutoConfiguration {
    static void main(String[] args) {
        GrailsApp.run(Application, args)
    }
}