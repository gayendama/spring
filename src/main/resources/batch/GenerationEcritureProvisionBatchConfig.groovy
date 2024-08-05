import sn.sensoft.springbatch.utils.BatchList
import sn.sensoft.springbatch.utils.ConstantesBatch
import springbatch.batch.synchroneResponse.ItemProcessorSync
import springbatch.batch.synchroneResponse.ItemReaderSync
import springbatch.batch.synchroneResponse.ItemWriterSync

beans {
    xmlns batch:"http://www.springframework.org/schema/batch"
    batch.job(id: BatchList.BatchGenerationEcritureProvision.id) {

        batch.step(id: ConstantesBatch.Programme.GEEP.name) {
            batch.tasklet{
                batch.chunk(
                    reader: 'ecritureProvItemReader',
                    processor: 'ecritureProvItemProcess',
                    writer: 'ecritureProvItemWriter',
                    'commit-interval': 1
                )
            }
        }

    }

    /**
     * Step de ecritureProv (synchrone)
     */
    ecritureProvItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.GEEP.code
        batchId = BatchList.BatchGenerationEcritureProvision.id
    }
    ecritureProvItemProcess(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    ecritureProvItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.GEEP.code
        batchId = BatchList.BatchGenerationEcritureProvision.id
        objet= "ItemToProcess"
    }

}
