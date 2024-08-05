package sn.sensoft.springbatch.configuration

import grails.util.Holders
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import sn.sensoft.springbatch.RuntimeConfigService

@Configuration
class Custom {


    RuntimeConfigService runtimeConfigService = (RuntimeConfigService) Holders.grailsApplication.mainContext.getBean("runtimeConfigService")

    //======================= Start Beans for alerts ===============================
    @Bean('getaichaurl')
    String getaichaurl() {
        return "http://192.168.1.49:8094/aicha/api/v1"
    }
}
