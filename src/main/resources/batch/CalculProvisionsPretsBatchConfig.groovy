import sn.sensoft.springbatch.utils.BatchList
import sn.sensoft.springbatch.utils.ConstantesBatch
import springbatch.batch.asynchroneResponse.ItemProcessorAsync
import springbatch.batch.asynchroneResponse.ItemReaderAsync
import springbatch.batch.asynchroneResponse.ItemWriterAsync
beans {
    xmlns batch:"http://www.springframework.org/schema/batch"
    batch.job(id: BatchList.BatchCalculProvisionsPrets.id) {

        batch.step(id: ConstantesBatch.Programme.CALPP.name) {
            batch.tasklet{
                batch.chunk(
                    reader: 'provisionPretItemReader',
                    processor: 'provisionPretItemProcess',
                    writer: 'provisionPretItemWriter',
                    'commit-interval': 1
                )
            }
        }

    }

    /**
     * Step de provisionPret (asynchrone)
     */
    provisionPretItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.CALPP.code
        batchId = BatchList.BatchCalculProvisionsPrets.id
    }
    provisionPretItemProcess(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    provisionPretItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

}
