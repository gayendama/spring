package sn.sensoft.springbatch.jobs

import org.springframework.scheduling.annotation.Scheduled
import sn.sensoft.springbatch.RuntimeConfigService
import sn.sensoft.springbatch.comm.NotificationService
import sn.sensoft.springbatch.communication.ComMessage

class StartJobService {
    static lazyInit = false
    NotificationService notificationService
    RuntimeConfigService runtimeConfigService
    ////@Scheduled(fixedDelay = 10000L)
    @Scheduled(cron = "*/10 * * * * ?")
    def sendApiComMessage(){
        if (runtimeConfigService.apiComIsActive.equalsIgnoreCase("O")) {
            ComMessage.withNewSession {
                notificationService.processMessage()
            }
        }
    }
}
