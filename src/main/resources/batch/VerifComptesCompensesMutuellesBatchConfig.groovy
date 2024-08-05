import sn.sensoft.springbatch.utils.BatchList
import sn.sensoft.springbatch.utils.ConstantesBatch
import springbatch.batch.asynchroneResponse.ItemProcessorAsync
import springbatch.batch.asynchroneResponse.ItemReaderAsync
import springbatch.batch.asynchroneResponse.ItemWriterAsync

beans {
    xmlns batch:"http://www.springframework.org/schema/batch"
    batch.job(id: BatchList.BatchVerifComptesCompensesCaisses.id) {
        batch.step(id: ConstantesBatch.Programme.VERIF_COMPTES_COMPENSES_MUTUELLE.code) {
            batch.tasklet{
                batch.chunk(
                    reader: 'verifComptesCompensesItemReader',
                    processor: 'verifComptesCompensesItemProcessor',
                    writer: 'verifComptesCompensesItemWriter',
                    'allow-start-if-complete': true,
                    'commit-interval': 1
                )
            }
        }
    }

    verifComptesCompensesItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.VERIF_COMPTES_COMPENSES_MUTUELLE.code
        batchId = BatchList.BatchVerifComptesCompensesCaisses.id
    }
    verifComptesCompensesItemProcessor(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    verifComptesCompensesItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

}
