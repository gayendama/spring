
import sn.sensoft.springbatch.utils.BatchList
import sn.sensoft.springbatch.utils.ConstantesBatch
import springbatch.batch.asynchroneResponse.ItemProcessorAsync
import springbatch.batch.asynchroneResponse.ItemReaderAsync
import springbatch.batch.asynchroneResponse.ItemWriterAsync

beans {
    xmlns batch:"http://www.springframework.org/schema/batch"

    batch.job(id: BatchList.BatchExtractionBic.id) {
        batch.step(id: ConstantesBatch.Programme.EXTRACTION_SOCIETE.name,  next:ConstantesBatch.Programme.EXTRACTION_INDIVIDU.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'extractionSocieteItemReader',
                        processor: 'extractionSocieteItemProcess',
                        writer: 'extractionSocieteItemWriter',
                        'commit-interval': 1
                )
            }        }

        batch.step(id: ConstantesBatch.Programme.EXTRACTION_INDIVIDU.name,  next: ConstantesBatch.Programme.EXTRACTION_GARANTIE.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'extractionIndividuItemReader',
                        processor: 'extractionIndividuItemProcess',
                        writer: 'extractionIndividuItemWriter',
                        'commit-interval': 1
                )
            }        }

        batch.step(id: ConstantesBatch.Programme.EXTRACTION_GARANTIE.name,  next: ConstantesBatch.Programme.EXTRACTION_CREDIT.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'extractionGarantieItemReader',
                        processor: 'extractionGarantieItemProcess',
                        writer: 'extractionGarantieItemWriter',
                        'commit-interval': 1
                )
            }        }

        batch.step(id: ConstantesBatch.Programme.EXTRACTION_CREDIT.name,  next: ConstantesBatch.Programme.EXTRACTION_BIC_NOTIFICATION.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'extractionCreditItemReader',
                        processor: 'extractionCreditItemProcess',
                        writer: 'extractionCreditItemWriter',
                        'commit-interval': 1
                )
            }        }

        batch.step(id: ConstantesBatch.Programme.EXTRACTION_BIC_NOTIFICATION.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'extractionBicNotificationItemReader',
                        processor: 'extractionBicNotificationItemProcess',
                        writer: 'extractionBicNotificationItemWriter',
                        'commit-interval': 1
                )
            }        }

    }

    /**
     * Step de extractionSociete (asynchrone)
     */
    extractionSocieteItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.EXTRACTION_SOCIETE.code
        batchId = BatchList.BatchExtractionBic.id
    }
    extractionSocieteItemProcess(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    extractionSocieteItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

    /**
     * Step de extractionIndividu (asynchrone)
     */
    extractionIndividuItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.EXTRACTION_INDIVIDU.code
        batchId = BatchList.BatchExtractionBic.id
    }
    extractionIndividuItemProcess(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    extractionIndividuItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

    /**
     * Step de extractionGarantie (asynchrone)
     */
    extractionGarantieItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.EXTRACTION_GARANTIE.code
        batchId = BatchList.BatchExtractionBic.id
    }
    extractionGarantieItemProcess(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    extractionGarantieItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

    /**
     * Step de extractionCredit (asynchrone)
     */
    extractionCreditItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.EXTRACTION_CREDIT.code
        batchId = BatchList.BatchExtractionBic.id
    }
    extractionCreditItemProcess(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    extractionCreditItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

    /**
     * Step de extractionBicNotification (asynchrone)
     */
    extractionBicNotificationItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.EXTRACTION_BIC_NOTIFICATION.code
        batchId = BatchList.BatchExtractionBic.id
    }
    extractionBicNotificationItemProcess(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    extractionBicNotificationItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }
    
}
