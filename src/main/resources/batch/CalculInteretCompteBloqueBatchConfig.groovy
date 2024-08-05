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
    batch.job(id: BatchList.BatchCalculInteretCompteBloque.id) {

        batch.step(id: ConstantesBatch.Programme.CALICB.name) {
            batch.tasklet{
                batch.chunk(
                    reader: 'interetCompteBloqueItemReader',
                    processor: 'interetCompteBloqueItemProcess',
                    writer: 'interetCompteBloqueItemWriter',
                    'commit-interval': 1
                )
            }
        }

    }

    /**
     * Step de interetCompteBloque (asynchrone)
     */
    interetCompteBloqueItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.CALICB.code
        batchId = BatchList.BatchCalculInteretCompteBloque.id
    }
    interetCompteBloqueItemProcess(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    interetCompteBloqueItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.CALICB.code
        batchId = BatchList.BatchCalculInteretCompteBloque.id
        objet = "DateArrete"
    }

}
