import sn.sensoft.springbatch.utils.BatchList
import sn.sensoft.springbatch.utils.ConstantesBatch
import springbatch.batch.asynchroneResponse.ItemProcessorAsync
import springbatch.batch.asynchroneResponse.ItemReaderAsync
import springbatch.batch.asynchroneResponse.ItemWriterAsync
beans {
    xmlns batch:"http://www.springframework.org/schema/batch"
    batch.job(id: BatchList.BatchCalculPosteBudgetaire.id) {

        batch.step(id: ConstantesBatch.Programme.CALPB.name) {
            batch.tasklet{
                batch.chunk(
                    reader: 'posteBudgetaireItemReader',
                    processor: 'posteBudgetaireItemProcess',
                    writer: 'posteBudgetaireItemWriter',
                    'commit-interval': 1
                )
            }
        }

    }

    /**
     * Step de posteBudgetaire (asynchrone)
     */
    posteBudgetaireItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.CALPB.code
        batchId = BatchList.BatchCalculPosteBudgetaire.id
    }
    posteBudgetaireItemProcess(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    posteBudgetaireItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

}
