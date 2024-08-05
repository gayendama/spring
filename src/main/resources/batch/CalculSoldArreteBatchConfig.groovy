import sn.sensoft.springbatch.utils.BatchList
import sn.sensoft.springbatch.utils.ConstantesBatch
import springbatch.batch.asynchroneResponse.ItemProcessorAsync
import springbatch.batch.asynchroneResponse.ItemReaderAsync
import springbatch.batch.asynchroneResponse.ItemWriterAsync
beans {
    xmlns batch:"http://www.springframework.org/schema/batch"
    batch.job(id: BatchList.BatchCalculSoldeArrete.id) {

        batch.step(id: ConstantesBatch.Programme.CALSD.name) {
            batch.tasklet{
                batch.chunk(
                    reader: 'soldeArreteItemReader',
                    processor: 'soldeArreteItemProcess',
                    writer: 'soldeArreteItemWriter',
                    'commit-interval': 1
                )
            }
        }

    }

    /**
     * Step de soldeArrete (asynchrone)
     */
    soldeArreteItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.CALSD.code
        batchId = BatchList.BatchCalculSoldeArrete.id
    }
    soldeArreteItemProcess(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    soldeArreteItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

}


