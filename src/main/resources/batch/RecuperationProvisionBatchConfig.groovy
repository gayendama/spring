import sn.sensoft.springbatch.utils.BatchList
import sn.sensoft.springbatch.utils.ConstantesBatch
import springbatch.batch.synchroneResponse.ItemProcessorSync
import springbatch.batch.synchroneResponse.ItemReaderSync
import springbatch.batch.synchroneResponse.ItemWriterSync

beans {
    xmlns batch:"http://www.springframework.org/schema/batch"
    batch.job(id: BatchList.BatchRecuperationProvison.id) {

        batch.step(id: ConstantesBatch.Programme.APECRTX.name, next: ConstantesBatch.Programme.APECHIMP.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'appelRecouvrementCtxItemReader',
                        processor: 'appelRecouvrementCtxItemProcessor',
                        writer: 'appelRecouvrementCtxItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.APECHIMP.name, next: ConstantesBatch.Programme.APECH.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'appelImpayesItemReader',
                        processor: 'appelImpayesItemProcessor',
                        writer: 'appelImpayesItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.APECH.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'appelEchanceItemReader',
                        processor: 'appelEchanceItemProcessor',
                        writer: 'appelEchanceItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }
    }

    /**
     * Step Récupération provision pour les crédits en contetieux (synchrone)
     */
    appelRecouvrementCtxItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.APECRTX.code
        batchId = BatchList.BatchRecuperationProvison.id
    }
    appelRecouvrementCtxItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    appelRecouvrementCtxItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.APECRTX.code
        batchId = BatchList.BatchRecuperationProvison.id
        objet = "Credit"
    }

    /**
     * Step Récupération provision pour les échéances impayées (synchrone)
     */
    appelImpayesItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.APECHIMP.code
        batchId = BatchList.BatchRecuperationProvison.id
    }
    appelImpayesItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    appelImpayesItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.APECHIMP.code
        batchId = BatchList.BatchRecuperationProvison.id
        objet = "CreditCompte"
    }

    /**
     * Step Récupération provision pour les tombées d'échéances (synchrone)
     */
    appelEchanceItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.APECH.code
        batchId = BatchList.BatchRecuperationProvison.id
    }
    appelEchanceItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    appelEchanceItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.APECH.code
        batchId = BatchList.BatchRecuperationProvison.id
        objet = "CreditCompte"
    }
}
