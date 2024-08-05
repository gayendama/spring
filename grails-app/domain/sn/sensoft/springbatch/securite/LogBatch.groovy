package sn.sensoft.springbatch.securite

import javax.persistence.Version

class LogBatch {

    String id = UUID.randomUUID().toString()
    @Version
    Long version

    String codeProgramme
    String message
    Date dateComptable
    Date dateMessage

    static mapping = {
        message type: "text"
        id generator: 'assigned'
    }

    static constraints = {

    }

    String toString() {
        return "${dateComptable}-${codeProgramme}"
    }


    def logInfos() {
        return [
                id           : id,
                codeProgramme: codeProgramme,
                message      : message,
                dateComptable: dateComptable?.format("dd/MM/yyyy"),
                dateMessage  : dateMessage?.format("dd/MM/yyyy HH:mm:ss")
        ]
    }


}
