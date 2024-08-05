package sn.sensoft.springbatch.api

import grails.gorm.transactions.Transactional
import sn.sensoft.springbatch.dto.ProgrammeBatchStatusDTO
import sn.sensoft.springbatch.exception.SpringbatchException
import sn.sensoft.springbatch.parametrage.ProgrammeBatch
import sn.sensoft.springbatch.utils.Constantes

@Transactional
class ProgrammeBatchApiService {

    def updateProgrammeStatus(ProgrammeBatchStatusDTO programmeBatchStatusDTO ){
        if(programmeBatchStatusDTO.program == null || programmeBatchStatusDTO.program == ""){
            throw new SpringbatchException("Veuillez renseigner le programme concerné")
        }

        def programStatus = [Constantes.STATUT_SUCCESS, Constantes.STATUT_FAILED]
        if(programmeBatchStatusDTO.programStatus == null || programmeBatchStatusDTO.programStatus == "" || !programStatus.contains(programmeBatchStatusDTO.programStatus)){
            throw new SpringbatchException("Veuillez renseigner le programStatus, valeurs possible: ${programStatus.toString()}")
        }

        ProgrammeBatch programmeBatch = ProgrammeBatch.findByCodeProgramme(programmeBatchStatusDTO.program)
        if(programmeBatch == null){
            throw new SpringbatchException("Le programme batch ${programmeBatchStatusDTO.program} n'existe pas")
        }

        programmeBatch.programStatus = programmeBatchStatusDTO.programStatus
        if(programmeBatch.programStatus == Constantes.STATUT_FAILED){
            programmeBatch.errorMessage = programmeBatchStatusDTO.errorMessage
        }
        else {
            programmeBatch.errorMessage = null
        }

        if(!programmeBatch.save(flush: true)){
            throw new SpringbatchException("Erreur de mise à jour du programmeBatch ${programmeBatch.errors.allErrors.join('\\n')}")
        }
    }

}
