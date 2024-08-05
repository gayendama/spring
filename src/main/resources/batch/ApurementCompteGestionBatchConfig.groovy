import sn.sensoft.springbatch.utils.BatchList
import sn.sensoft.springbatch.utils.ConstantesBatch
import springbatch.batch.asynchroneResponse.ItemProcessorAsync
import springbatch.batch.asynchroneResponse.ItemReaderAsync
import springbatch.batch.asynchroneResponse.ItemWriterAsync

beans {
    xmlns batch:"http://www.springframework.org/schema/batch"
    batch.job(id: BatchList.BatchApurementCompteGestion.id) {

        batch.step(id: ConstantesBatch.Programme.APCG.name) {
            batch.tasklet{
                batch.chunk(
                    reader: 'apurementItemReader',
                    processor: 'apurementItemProcess',
                    writer: 'apurementItemWriter',
                    'commit-interval': 1
                )
            }
        }

    }

    /**
     * Step de apurement compte gestion (asynchrone)
     */
    apurementItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.APCG.code
        batchId = BatchList.BatchApurementCompteGestion.id
    }
    apurementItemProcess(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    apurementItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

}
