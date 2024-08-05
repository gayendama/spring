import sn.sensoft.springbatch.utils.BatchList
import sn.sensoft.springbatch.utils.ConstantesBatch
import springbatch.batch.synchroneResponse.ItemProcessorSync
import springbatch.batch.synchroneResponse.ItemReaderSync
import springbatch.batch.synchroneResponse.ItemWriterSync

beans {
    xmlns batch:"http://www.springframework.org/schema/batch"
    batch.job(id: BatchList.BatchComptabilisationImmo.id) {

        batch.step(id: ConstantesBatch.Programme.COMPTA_IMMO.name) {
            batch.tasklet{
                batch.chunk(
                    reader: 'comptaImmoItemReader',
                    processor: 'comptaImmoItemProcess',
                    writer: 'comptaImmoItemWriter',
                    'allow-start-if-complete': true,
                    'commit-interval': 10
                )
            }
        }

    }

    /**
     * Step de comptabilisation immo (synchrone)
     */
    comptaImmoItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.COMPTA_IMMO.code
        batchId = BatchList.BatchComptabilisationImmo.id
    }
    comptaImmoItemProcess(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    comptaImmoItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.COMPTA_IMMO.code
        batchId = BatchList.BatchComptabilisationImmo.id
        objet = "AmortissementImmo"
    }

}
