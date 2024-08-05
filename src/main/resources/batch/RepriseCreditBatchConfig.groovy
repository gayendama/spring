import sn.sensoft.springbatch.utils.BatchList
import sn.sensoft.springbatch.utils.ConstantesBatch
import springbatch.batch.asynchroneResponse.ItemProcessorAsync
import springbatch.batch.asynchroneResponse.ItemReaderAsync
import springbatch.batch.asynchroneResponse.ItemWriterAsync
import springbatch.batch.synchroneResponse.ItemProcessorSync
import springbatch.batch.synchroneResponse.ItemReaderRepriseSync
import springbatch.batch.synchroneResponse.ItemReaderSync
import springbatch.batch.synchroneResponse.ItemWriterRepriseSync
import springbatch.batch.synchroneResponse.ItemWriterSync
beans {
    xmlns batch:"http://www.springframework.org/schema/batch"
    batch.job(id: BatchList.BatchRepriseCredit.id) {

        batch.step(id: ConstantesBatch.Programme.RPCRD.name, next: ConstantesBatch.Programme.COMPTARC.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'repriseCreditReder',
                        processor: 'repriseCreditProcessor',
                        writer: 'repriseCreditWriter',
                        'commit-interval': 1
                )
            }
        }
        batch.step(id: ConstantesBatch.Programme.COMPTARC.name, next: ConstantesBatch.Programme.COMPTACGRC.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'comptaJournalierItemReader',
                        processor: 'comptaJournalierItemProcessor',
                        writer: 'comptaJournalierItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }
        batch.step(id: ConstantesBatch.Programme.COMPTACGRC.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'cmajCgItemReader',
                        processor: 'cmajCgItemProcessor',
                        writer: 'cmajCgItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }                }
    }


    comptaJournalierItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.COMPTARC.code
        batchId = BatchList.BatchRepriseCredit.id
    }
    comptaJournalierItemProcessor(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    comptaJournalierItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

    /**
     * Step Mise a jour des soldes comptes généraux (asynchrone)
     */
    cmajCgItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.COMPTACGRC.code
        batchId = BatchList.BatchRepriseCredit.id
    }
    cmajCgItemProcessor(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    cmajCgItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

    repriseCreditReder(ItemReaderRepriseSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.RPCRD.code
        batchId = BatchList.BatchRepriseCredit.id    }
    repriseCreditProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    repriseCreditWriter(ItemWriterRepriseSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.RPCRD.code
        batchId = BatchList.BatchRepriseCredit.id
        objet = "ItemToProcess"

    }

}
