package sn.sensoft.springbatch.parametrage

import grails.gorm.annotation.Entity
import org.grails.datastore.gorm.GormEntity
import sn.sensoft.springbatch.utils.BatchStepStatus
import sn.sensoft.springbatch.utils.Constantes

import javax.persistence.Version

@Entity
class ProgrammeBatch implements GormEntity<ProgrammeBatch> {

    String id = UUID.randomUUID().toString()
    @Version
    Long version

    String codeProgramme
    String nomProgramme
    String libelleProgramme
    String typeProgramme
    String periodiciteProgramme = Constantes.PERIODICITE_JOURNALIERE

    Integer sequenceNumber
    BatchStepStatus batchStepStatus

    String batchId

    //Au lancement du programme
    String programStatus
    String errorMessage

    //En cas de désactivation automatique, le batch à besoin de cet indicateur pour réactiver le step à la fin
    Boolean indReactiverApresBatch = false

    static constraints = {

        codeProgramme blank: false, nullable: false, size: 1..50
        nomProgramme blank: false, nullable: false, size: 0..200
        libelleProgramme blank: false, nullable: false, size: 0..500
        typeProgramme blank: false, nullable: false, size: 0..1
        periodiciteProgramme blank: false, nullable: false, size: 0..1
        programStatus inList: [Constantes.STATUT_SUCCESS, Constantes.STATUT_FAILED, Constantes.STATUT_STARTED]
    }

    static mapping = {
        id generator: 'assigned'
        codeProgramme unique: true
        errorMessage type: 'text'
    }

    String toString() {
        return "${libelleProgramme}"
    }

    static auditable = true
    String userCreate
    String userUpdate
    Date dateCreated
    Date lastUpdated


}
