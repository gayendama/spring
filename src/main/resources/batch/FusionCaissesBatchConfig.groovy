/*
import sn.sensoft.springbatch.utils.BatchList
import sn.sensoft.springbatch.utils.ConstantesBatch
import springbatch.batch.asynchroneResponse.ItemProcessorAsync
import springbatch.batch.asynchroneResponse.ItemReaderAsync
import springbatch.batch.asynchroneResponse.ItemWriterAsync
import springbatch.batch.synchroneResponse.ItemProcessorSync
import springbatch.batch.synchroneResponse.ItemReaderSync
import springbatch.batch.synchroneResponse.ItemWriterSync

beans {
    xmlns batch:"http://www.springframework.org/schema/batch"
    batch.job(id: BatchList.BatchFusionCaisse.id) {

        batch.step(id: ConstantesBatch.Programme.COMPTA_JOURNALIER_FC_A.name, next: ConstantesBatch.Programme.MAJ_SOLDE_COMPTES_GENEAUX_FC_A.name) {
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

        batch.step(id: ConstantesBatch.Programme.MAJ_SOLDE_COMPTES_GENEAUX_FC_A.name, next: ConstantesBatch.Programme.FUSION_CAISSES.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'cmajCgItemReader',
                        processor: 'cmajCgItemProcessor',
                        writer: 'cmajCgItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.FUSION_CAISSES.name, next: ConstantesBatch.Programme.LIQUIDATION_BUREAUX.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'fusionCaisseItemReader',
                        processor: 'fusionCaisseItemProcessor',
                        writer: 'fusionCaisseItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.LIQUIDATION_BUREAUX.name, next: ConstantesBatch.Programme.DUPLICATION_COMPTES_GENERAUX.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'liquidationBureauItemReader',
                        processor: 'liquidationBureauItemProcessor',
                        writer: 'liquidationBureauItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.DUPLICATION_COMPTES_GENERAUX.name, next: ConstantesBatch.Programme.DUPLICATION_COMPTES_AUXILIAIRES.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'duplicationCGItemReader',
                        processor: 'duplicationCGItemProcessor',
                        writer: 'duplicationCGItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.DUPLICATION_COMPTES_AUXILIAIRES.name, next: ConstantesBatch.Programme.TRANSFERT_CLIENT.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'duplicationCAItemReader',
                        processor: 'duplicationCAItemProcessor',
                        writer: 'duplicationCAItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }
        batch.step(id: ConstantesBatch.Programme.TRANSFERT_CLIENT.name, next: ConstantesBatch.Programme.COMPTA_JOURNALIER_FC.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'transferClientItemReader',
                        processor: 'transferClientItemProcessor',
                        writer: 'transferClientItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }


        batch.step(id: ConstantesBatch.Programme.COMPTA_JOURNALIER_FC.name, next: ConstantesBatch.Programme.MAJ_SOLDE_COMPTES_GENEAUX_FC.name) {
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

        batch.step(id: ConstantesBatch.Programme.MAJ_SOLDE_COMPTES_GENEAUX_FC.name, next: ConstantesBatch.Programme.CALCUL_STATISTIQUE_FC.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'cmajCgItemReader',
                        processor: 'cmajCgItemProcessor',
                        writer: 'cmajCgItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.CALCUL_STATISTIQUE_FC.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'calculStatItemReader',
                        processor: 'calculStatItemProcessor',
                        writer: 'calculStatItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }        }
    }

    fusionCaisseItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.FUSION_CAISSES.code
        batchId = BatchList.BatchFusionCaisse.id
    }
    fusionCaisseItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    fusionCaisseItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.FUSION_CAISSES.code
        batchId = BatchList.BatchFusionCaisse.id
            objet = "Caisse"
    }

    duplicationCGItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.DUPLICATION_COMPTES_GENERAUX.code
        batchId = BatchList.BatchFusionCaisse.id
    }
    duplicationCGItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    duplicationCGItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.DUPLICATION_COMPTES_GENERAUX.code
        batchId = BatchList.BatchFusionCaisse.id
        objet = "CompteGeneral"
    }

    duplicationCAItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.DUPLICATION_COMPTES_AUXILIAIRES.code
        batchId = BatchList.BatchFusionCaisse.id
    }
    duplicationCAItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    duplicationCAItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.DUPLICATION_COMPTES_AUXILIAIRES.code
        batchId = BatchList.BatchFusionCaisse.id
        objet = "Compte"
    }

    liquidationBureauItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.LIQUIDATION_BUREAUX.code
        batchId = BatchList.BatchFusionCaisse.id
    }
    liquidationBureauItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    liquidationBureauItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.LIQUIDATION_BUREAUX.code
        batchId = BatchList.BatchFusionCaisse.id
        objet = "LiquidationBureauTmp"
    }

    transferClientItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.TRANSFERT_CLIENT.code
        batchId = BatchList.BatchFusionCaisse.id
    }
    transferClientItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    transferClientItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.TRANSFERT_CLIENT.code
        batchId = BatchList.BatchFusionCaisse.id
        objet = "client"
    }

    */
/**
     * Step Compta Journalier (asynchrone)
     *//*

    comptaJournalierItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.COMPTA_JOURNALIER_FC_A.code
        batchId = BatchList.BatchFusionCaisse.id
    }
    comptaJournalierItemProcessor(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    comptaJournalierItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

    */
/**
     * Step Mise a jour des soldes comptes généraux (asynchrone)
     *//*

    cmajCgItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.MAJ_SOLDE_COMPTES_GENEAUX_FC_A.code
        batchId = BatchList.BatchFusionCaisse.id
    }
    cmajCgItemProcessor(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    cmajCgItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

    */
/**
     * Step Calcul statistiques (asynchrone)
     *//*

    calculStatItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.CALCUL_STATISTIQUE_FC.code
        batchId = BatchList.BatchFusionCaisse.id
    }
    calculStatItemProcessor(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    calculStatItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

}
*/
