import sn.sensoft.springbatch.utils.BatchList
import sn.sensoft.springbatch.utils.ConstantesBatch
import springbatch.batch.asynchroneResponse.ItemProcessorAsync
import springbatch.batch.asynchroneResponse.ItemReaderAsync
import springbatch.batch.asynchroneResponse.ItemWriterAsync
beans {
    xmlns batch:"http://www.springframework.org/schema/batch"
    batch.job(id: BatchList.BatchCalculCreancesRattachees.id) {

        batch.step(id: ConstantesBatch.Programme.CALCR.name) {
            batch.tasklet{
                batch.chunk(
                    reader: 'creancesRattacheesItemReader',
                    processor: 'creancesRattacheesItemProcess',
                    writer: 'creancesRattacheesItemWriter',
                    'commit-interval': 1
                )
            }
        }

    }

    /**
     * Step de creancesRattachees (asynchrone)
     */
    creancesRattacheesItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.CALCR.code
        batchId = BatchList.BatchCalculCreancesRattachees.id
    }
    creancesRattacheesItemProcess(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    creancesRattacheesItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

}


