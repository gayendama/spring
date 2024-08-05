package sn.sensoft.springbatch.dto

import groovy.transform.CompileStatic

@CompileStatic
class ProgrammeBatchStatusDTO {

    String program
    String programStatus
    String userMessage
    String debugMessage
    String errorMessage

    ProgrammeBatchStatusDTO() {
    }

    def logInfos() {
        return [
                program      : this.program,
                programStatus: this.programStatus,
                userMessage  : this.userMessage,
                debugMessage : this.debugMessage,
                errorMessage : this.errorMessage
        ]
    }

}
