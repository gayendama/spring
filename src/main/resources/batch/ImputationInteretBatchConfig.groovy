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
    batch.job(id: BatchList.BatchImputationInteret.id) {

        batch.step(id: ConstantesBatch.Programme.IMPINT.name) {
            batch.tasklet{
                batch.chunk(
                    reader: 'imputaInteretItemReader',
                    processor: 'imputaInteretItemProcess',
                    writer: 'imputaInteretItemWriter',
                    'commit-interval': 1
                )
            }
        }

    }

    /**
     * Step de imputaInteret (synchrone)
     */
    imputaInteretItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.IMPINT.code
        batchId = BatchList.BatchImputationInteret.id
    }
    imputaInteretItemProcess(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    imputaInteretItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.IMPINT.code
        batchId = BatchList.BatchImputationInteret.id
        objet = "Batch Imputation_Interet !"

    }

}
