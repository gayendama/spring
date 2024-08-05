package batch

import sn.sensoft.springbatch.utils.BatchList
import sn.sensoft.springbatch.utils.ConstantesBatch
import springbatch.batch.synchroneResponse.ItemProcessorSync
import springbatch.batch.synchroneResponse.ItemReaderSync
import springbatch.batch.synchroneResponse.ItemWriterSync

beans {
    xmlns batch:"http://www.springframework.org/schema/batch"

    batch.job(id: BatchList.BatchExtourneOperationsFromFile.id) {

        batch.step(id: ConstantesBatch.Programme.EXTOURNE_OPERATION_FROM_FILE.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'extourneEnMasseItemReader',
                        processor: 'extourneEnMasseItemProcessor',
                        writer: 'extourneEnMasseItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

    }

    /**
     * Step Comptabilisation_transacttions_fichiers (synchrone)
     */
    extourneEnMasseItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.EXTOURNE_OPERATION_FROM_FILE.code
        batchId = BatchList.BatchExtourneOperationsFromFile.id
        notBeforStep = true
    }
    extourneEnMasseItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    extourneEnMasseItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.EXTOURNE_OPERATION_FROM_FILE.code
        batchId = BatchList.BatchExtourneOperationsFromFile.id
        objet = "Operation"
    }
}
