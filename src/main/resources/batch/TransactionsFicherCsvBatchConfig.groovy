import sn.sensoft.springbatch.utils.BatchList
import sn.sensoft.springbatch.utils.ConstantesBatch
import springbatch.batch.asynchroneResponse.ItemProcessorAsync
import springbatch.batch.asynchroneResponse.ItemReaderAsync
import springbatch.batch.asynchroneResponse.ItemWriterAsync
import springbatch.batch.synchroneResponse.ItemProcessorSync
import springbatch.batch.synchroneResponse.ItemReaderSync
import springbatch.batch.synchroneResponse.ItemWriterSync

beans {
    xmlns batch:"http://www.springframework.org/schema/batch"

    batch.job(id: BatchList.BatchTraitementTransationsFichiers.id) {

        batch.step(id: ConstantesBatch.Programme.VERIFICATION_PREPARATION_TRAITEMENT.name, next: ConstantesBatch.Programme.COMPTA_TRANSCTION_PAR_FICHIER.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'traitementItemReader',
                        processor: 'traitementItemProcess',
                        writer: 'traitementItemWriter',
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.COMPTA_TRANSCTION_PAR_FICHIER.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'comptaItemReader',
                        processor: 'comptaItemProcessor',
                        writer: 'comptaItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 10
                )
            }
        }
    }

    /**
     * Step traitementFichiersTransactions (asynchrone)
     */
    traitementItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.VERIFICATION_PREPARATION_TRAITEMENT.code
        batchId = BatchList.BatchTraitementTransationsFichiers.id
    }
    traitementItemProcess(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    traitementItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

    /**
     * Step Comptabilisation_transacttions_fichiers (synchrone)
     */
    comptaItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.COMPTA_TRANSCTION_PAR_FICHIER.code
        batchId = BatchList.BatchTraitementTransationsFichiers.id
    }
    comptaItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    comptaItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.COMPTA_TRANSCTION_PAR_FICHIER.code
        batchId = BatchList.BatchTraitementTransationsFichiers.id
        objet = "TransactionFromFile"
    }
}
