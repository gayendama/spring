package sn.sensoft.springbatch.api


import groovy.json.JsonOutput
import sn.sensoft.springbatch.dto.ProgrammeBatchStatusDTO
import sn.sensoft.springbatch.exception.SpringbatchException

class ProgrammeBatchApiController {

    def utilsApiService
    def programmeBatchApiService

    def programmeStatus() {
        def params = request.JSON
        log.info(JsonOutput.toJson([method: "programmeStatus", params: params]))
        try {
            ProgrammeBatchStatusDTO programmeBatchStatusDTO = new ProgrammeBatchStatusDTO()

            programmeBatchStatusDTO.program = params.program
            programmeBatchStatusDTO.programStatus = params.programStatus
            programmeBatchStatusDTO.userMessage = params.userMessage
            programmeBatchStatusDTO.debugMessage = params.debugMessage
            programmeBatchStatusDTO.errorMessage = params.errorMessage

            programmeBatchApiService.updateProgrammeStatus(programmeBatchStatusDTO)

            render utilsApiService.getJsonSimpleResponse(response, "Programme mis à jour avec succès")
        }
        catch (SpringbatchException ex) {
            log.error(JsonOutput.toJson([method: "programmeStatus", message: ex.message]))
            render utilsApiService.getJsonError(response, ex.codeMsg, ex.message, ex.message, null)
        }
    }
}
