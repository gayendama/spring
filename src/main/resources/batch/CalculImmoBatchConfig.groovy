import sn.sensoft.springbatch.utils.BatchList
import sn.sensoft.springbatch.utils.ConstantesBatch
import springbatch.batch.asynchroneResponse.ItemProcessorAsync
import springbatch.batch.asynchroneResponse.ItemReaderAsync
import springbatch.batch.asynchroneResponse.ItemWriterAsync
beans {
    xmlns batch:"http://www.springframework.org/schema/batch"
    batch.job(id: BatchList.BatchCalculImmobilisations.id) {

        batch.step(id: ConstantesBatch.Programme.CALCUL_IMMO.name) {
            batch.tasklet{
                batch.chunk(
                    reader: 'calculImmoItemReader',
                    processor: 'calculImmoItemProcess',
                    writer: 'calculImmoItemWriter',
                    'commit-interval': 1
                )
            }
        }

    }

    /**
     * Step de calculImmo  (asynchrone)
     */
    calculImmoItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.CALCUL_IMMO.code
        batchId = BatchList.BatchCalculImmobilisations.id
    }
    calculImmoItemProcess(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    calculImmoItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

}
