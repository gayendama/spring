package batch


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
    batch.job(id: BatchList.BatchTFJ.id) {

        batch.step(id: ConstantesBatch.Programme.SAUVBA.name, next: ConstantesBatch.Programme.COMPTA_REMISE_CHEQUE.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'sauvDbItemReader',
                        processor: 'sauvDbItemProcess',
                        writer: 'sauvDbItemWriter',
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.COMPTA_REMISE_CHEQUE.name, next: ConstantesBatch.Programme.REFCHARG.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'comptaChequeItemReader',
                        processor: 'comptaChequeItemProcess',
                        writer: 'comptaChequeItemWriter',
                        'commit-interval': 1
                )
            }
        }
        batch.step(id: ConstantesBatch.Programme.REFCHARG.name, next: ConstantesBatch.Programme.RPCTX.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'chargeRefactureItemReader',
                        processor: 'chargeRefactureItemProcessor',
                        writer: 'chargeRefactureItemWriter',
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.RPCTX.name, next: ConstantesBatch.Programme.CNBJR.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'rembCreditCtxItemReader',
                        processor: 'rembCreditCtxItemProcess',
                        writer: 'rembCreditCtxItemWriter',
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.CNBJR.name, next: ConstantesBatch.Programme.PENAL.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'calculNbJourRetardItemReader',
                        processor: 'calculNbJourRetardItemProcessor',
                        writer: 'calculNbJourRetardItemWriter',
                        'commit-interval': 1

                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.PENAL.name, next: ConstantesBatch.Programme.GPENA.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'calculPenaliteItemReader',
                        processor: 'calculPenaliteItemProcessor',
                        writer: 'calculPenaliteItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.GPENA.name, next: ConstantesBatch.Programme.BSOUF.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'calculPenaliteV2ItemReader',
                        processor: 'calculPenaliteV2ItemProcessor',
                        writer: 'calculPenaliteV2ItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.BSOUF.name, next: ConstantesBatch.Programme.VSOUF.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'bsoufItemReader',
                        processor: 'bsoufItemProcessor',
                        writer: 'bsoufRembItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.VSOUF.name, next: ConstantesBatch.Programme.RSOUF.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'vsoufItemReader',
                        processor: 'vsoufItemProcessor',
                        writer: 'vsoufRembItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.RSOUF.name, next: ConstantesBatch.Programme.RPENA.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'rsoufItemReader',
                        processor: 'rsoufItemProcessor',
                        writer: 'rsoufRembItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.RPENA.name, next: ConstantesBatch.Programme.CAPIN.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'rembPenaliteV2ItemReader',
                        processor: 'rembPenaliteV2ItemProcessor',
                        writer: 'rembPenaliteV2RembItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.CAPIN.name, next: ConstantesBatch.Programme.RPRET.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'capIntDiffItemReader',
                        processor: 'capIntDiffItemProcess',
                        writer: 'capIntDiffItemWriter',
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.RPRET.name, next: ConstantesBatch.Programme.TPRET.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'rembImpayeItemReader',
                        processor: 'rembImpayeItemProcess',
                        writer: 'rembImpayeItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.TPRET.name, next: ConstantesBatch.Programme.TEGSOUF.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'tombeeEcheanceItemReader',
                        processor: 'tombeeEcheanceItemProcess',
                        writer: 'tombeeEcheanceItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.TEGSOUF.name, next: ConstantesBatch.Programme.PPRET.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'tombeeEchGsoufItemReader',
                        processor: 'tombeeEchGsoufItemProcessor',
                        writer: 'tombeeEchGsoufItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.PPRET.name, next: ConstantesBatch.Programme.IPRET.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'pretPayeItemReader',
                        processor: 'pretPayeItemProcessor',
                        writer: 'pretPayeItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.IPRET.name, next: ConstantesBatch.Programme.CALINP.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'impayesItemReader',
                        processor: 'impayesItemProcessor',
                        writer: 'impayesItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }



        batch.step(id: ConstantesBatch.Programme.CALINP.name, next: ConstantesBatch.Programme.TDAT.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'calculIntDATItemReader',
                        processor: 'calculIntDATProcessor',
                        writer: 'calculIntDATItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.TDAT.name, next: ConstantesBatch.Programme.APPRO_PLAN_EPARGNE.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'tombeeDatItemReader',
                        processor: 'tombeeDatItemProcessor',
                        writer: 'tombeeDatItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.APPRO_PLAN_EPARGNE.name, next: ConstantesBatch.Programme.TDAG.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'approPlanEpargneItemReader',
                        processor: 'approPlanEpargneItemProcessor',
                        writer: 'approPlanEpargneItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }


        batch.step(id: ConstantesBatch.Programme.TDAG.name, next: ConstantesBatch.Programme.MAIN_LEVEE_GARANTIE.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'tombeeDagItemReader',
                        processor: 'tombeeDagItemProcessor',
                        writer: 'tombeeDagItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.MAIN_LEVEE_GARANTIE.name, next: ConstantesBatch.Programme.DECLASSEMENT_CREDIT_V2.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'mainLeveeGarantieItemReader',
                        processor: 'mainLeveeGarantieItemProcessor',
                        writer: 'mainLeveeGarantieItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }


        batch.step(id: ConstantesBatch.Programme.DECLASSEMENT_CREDIT_V2.name, next: ConstantesBatch.Programme.PASSAGE_CONTENTIEUX.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'declassementV2ItemReader',
                        processor: 'declassementV2ItemProcessor',
                        writer: 'declassementV2ItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.PASSAGE_CONTENTIEUX.name, next: ConstantesBatch.Programme.NOTIFICATION_PASSAGE_CONTENTIEUX.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'dCotentieuxItemReader',
                        processor: 'dCotentieuxItemProcessor',
                        writer: 'dCotentieuxItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.NOTIFICATION_PASSAGE_CONTENTIEUX.name, next: ConstantesBatch.Programme.MIPCM.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'notifCotentieuxItemReader',
                        processor: 'notifCotentieuxItemProcessor',
                        writer: 'notifCotentieuxItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.MIPCM.name, next: ConstantesBatch.Programme.FRCPT.name) {
           // batch.tasklet(ref: 'majIndicateurPaiementCarteMembre')
            batch.tasklet{
                batch.chunk(
                        reader: 'majIndPaiementCarteMembreItemReader',
                        processor: 'majIndPaiementCarteMembreItemProcessor',
                        writer: 'majIndPaiementCarteMembreItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }
        batch.step(id: ConstantesBatch.Programme.FRCPT.name, next: ConstantesBatch.Programme.GEN_COMPTA_FRCPT.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'fraisDeTenuDeComptesItemReader',
                        processor: 'fraisDeTenuDeComptesItemProcessor',
                        writer: 'fraisDeTenuDeComptesItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }



        batch.step(id: ConstantesBatch.Programme.GEN_COMPTA_FRCPT.name, next: ConstantesBatch.Programme.FFGAR.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'genComptaFraisTenuCompteItemReader',
                        processor: 'genComptaFraisTenuCompteItemProcessor',
                        writer: 'genComptaFraisTenuCompteItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.FFGAR.name, next: ConstantesBatch.Programme.ASMS.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'fraisFondGarantieItemReader',
                        processor: 'fraisFondGarantieItemProcessor',
                        writer: 'fraisFondGarantieItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.ASMS.name, next: ConstantesBatch.Programme.CFSMS.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'alertTombeeEchItemReader',
                        processor: 'alertTombeeEchItemProcessor',
                        writer: 'alertTombeeEchItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.CFSMS.name, next: ConstantesBatch.Programme.CCTT.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'comptabilisationFraisSmstemReader',
                        processor: 'comptabilisationFraisSmsProcessor',
                        writer: 'comptabilisationFraisSmsItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.CCTT.name, next: ConstantesBatch.Programme.RFRRT.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'clotureCycleTontinesItemReader',
                        processor: 'clotureCycleTontinesItemProcessor',
                        writer: 'clotureCycleTontinesItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.RFRRT.name,  next: ConstantesBatch.Programme.COMPTA_DECOUVERT.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'recouvrementFraisItemReader',
                        processor: 'recouvrementFraisItemProcess',
                        writer: 'recouvrementFraisItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.COMPTA_DECOUVERT.name, next: ConstantesBatch.Programme.MISE_A_JOUR_STATUT_DECOUVERT.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'comptaDecouvertItemReader',
                        processor: 'comptaDecouvertItemProcessor',
                        writer: 'comptaDecouvertItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.MISE_A_JOUR_STATUT_DECOUVERT.name, next: ConstantesBatch.Programme.REJET_AUTOMATIQUE_FORCAGES.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'majStatutDecouvertItemReader',
                        processor: 'majStatutDecouvertItemProcessor',
                        writer: 'majStatutDecouvertItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.REJET_AUTOMATIQUE_FORCAGES.name, next: ConstantesBatch.Programme.TVRPT.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'rejetAutoForcageItemReader',
                        processor: 'rejetAutoForcageItemProcessor',
                        writer: 'rejetAutoForcageItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.TVRPT.name, next: ConstantesBatch.Programme.PREPARATION_COMPENSE_INTER_MUTUELLE.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'virementPermenantItemReader',
                        processor: 'virementPermenantItemProcessor',
                        writer: 'virementPermenantItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.PREPARATION_COMPENSE_INTER_MUTUELLE.name, next: ConstantesBatch.Programme.COMPENSE_TRANSACTIONS_INTER_MUTUELLE.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'preparationCompenseInterAgenceItemReader',
                        processor: 'preparationCompenseInterAgenceItemProcessor',
                        writer: 'preparationCompenseInterAgenceItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.COMPENSE_TRANSACTIONS_INTER_MUTUELLE.name, next: ConstantesBatch.Programme.MAJCPT.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'compenseTransactionInterAgenceItemReader',
                        processor: 'compenseTransactionInterAgenceItemProcessor',
                        writer: 'compenseTransactionInterAgenceItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.MAJCPT.name, next: ConstantesBatch.Programme.MAJSCG.name) {
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

        batch.step(id: ConstantesBatch.Programme.MAJSCG.name, next: ConstantesBatch.Programme.CALSTAT.name) {
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

        batch.step(id: ConstantesBatch.Programme.CALSTAT.name, next: ConstantesBatch.Programme.STATREM.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'calculStatItemReader',
                        processor: 'calculStatItemProcessor',
                        writer: 'calculStatItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.STATREM.name, next: ConstantesBatch.Programme.MSOUF.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'statRembItemReader',
                        processor: 'statRembItemProcessor',
                        writer: 'statRembItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.MSOUF.name, next: ConstantesBatch.Programme.CNBJR2.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'msoufItemReader',
                        processor: 'msoufItemProcessor',
                        writer: 'msoufRembItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.CNBJR2.name, next: ConstantesBatch.Programme.PENAL2.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'calculNbJourRetardItem2Reader',
                        processor: 'calculNbJourRetardItem2Processor',
                        writer: 'calculNbJourRetardItem2Writer',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.PENAL2.name, next: ConstantesBatch.Programme.GPENA2.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'calculPenaliteItem2Reader',
                        processor: 'calculPenaliteItem2Processor',
                        writer: 'calculPenaliteItem2Writer',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }
        batch.step(id: ConstantesBatch.Programme.GPENA2.name, next: ConstantesBatch.Programme.CPRET.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'calculPenaliteV2Item2Reader',
                        processor: 'calculPenaliteV2Item2Processor',
                        writer: 'calculPenaliteV2Item2Writer',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.CPRET.name, next: ConstantesBatch.Programme.CLCEN.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'clotureCreditItemReader',
                        processor: 'clotureCreditItemProcessor',
                        writer: 'clotureCreditItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.CLCEN.name, next: ConstantesBatch.Programme.BLOCPT.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'clotureCompteEpargneNantieIemReader',
                        processor: 'clotureCompteEpargneNantieItemProcessor',
                        writer: 'clotureCompteEpargneNantieItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.BLOCPT.name, next: ConstantesBatch.Programme.OCTT.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'blocageCptAuxItemReader',
                        processor: 'blocageCptAuxItemProcessor',
                        writer: 'blocageCptAuxItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.OCTT.name, next: ConstantesBatch.Programme.OCTTM.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'ouvertureCycleTontinesItemReader',
                        processor: 'ouvertureCycleTontinesItemProcessor',
                        writer: 'ouvertureCycleTontinesItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.OCTTM.name, next: ConstantesBatch.Programme.DSFIN.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'tontineAdhesionMiseItemReader',
                        processor: 'tontineAdhesionMiseItemProcessor',
                        writer: 'tontineAdhesionMiseItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.DSFIN.name, next: ConstantesBatch.Programme.VFCRD.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'verifDesiquilibreItemReader',
                        processor: 'verifDesiquilibreItemProcessor',
                        writer: 'verifDesiquilibreItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.VFCRD.name, next: ConstantesBatch.Programme.MAJPREMB.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'verifCoherenceCreditItemReader',
                        processor: 'verifCoherenceCreditItemProcessor',
                        writer: 'verifCoherenceCreditItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.MAJPREMB.name, next: ConstantesBatch.Programme.CREDITINV.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'promesseRembItemReader',
                        processor: 'promesseRembItemProcessor',
                        writer: 'promesseRembItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.CREDITINV.name, next: ConstantesBatch.Programme.CAUTION.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'inventaireCreditItemReader',
                        processor: 'inventaireCreditItemProcessor',
                        writer: 'inventaireCreditItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.CAUTION.name, next: ConstantesBatch.Programme.COMMISSION_CAUTION.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'cautionItemReader',
                        processor: 'cautionItemProcessor',
                        writer: 'cautionItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }
        batch.step(id: ConstantesBatch.Programme.COMMISSION_CAUTION.name, next: ConstantesBatch.Programme.COMPENSE_BUREAU.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'commissionCautionItemReader',
                        processor: 'commissionCautionItemProcessor',
                        writer: 'commissionCautionItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.COMPENSE_BUREAU.name, next: ConstantesBatch.Programme.SAUVB.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'compenseBureauItemReader',
                        processor: 'compenseBureauItemProcessor',
                        writer: 'compenseBureauItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }

        batch.step(id: ConstantesBatch.Programme.SAUVB.name) {
            batch.tasklet{
                batch.chunk(
                        reader: 'sauvDbApresItemReader',
                        processor: 'sauvDbApresItemProcess',
                        writer: 'sauvDbApresItemWriter',
                        'allow-start-if-complete': true,
                        'commit-interval': 1
                )
            }
        }
    }

    /**
     * Step de sauvegarde de base de données (asynchrone)
     */
    sauvDbItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.SAUVBA.code
        batchId = BatchList.BatchTFJ.id
    }
    sauvDbItemProcess(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    sauvDbItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

    /**
     * Step de comptabilisation de remise de chèque (synchrone)
     */
    comptaChequeItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.COMPTA_REMISE_CHEQUE.code
        batchId = BatchList.BatchTFJ.id
    }
    comptaChequeItemProcess(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    comptaChequeItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.COMPTA_REMISE_CHEQUE.code
        batchId = BatchList.BatchTFJ.id
        objet = "PortefeuilleCheque"
    }

    /**
     * Step charge refacture (synchrone)
     */
    chargeRefactureItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.REFCHARG.code
        batchId = BatchList.BatchTFJ.id
    }
    chargeRefactureItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    chargeRefactureItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.REFCHARG.code
        batchId = BatchList.BatchTFJ.id
        objet = "ref_charge"
    }


    /**
     * Step Rembourssement credits contentieux (asynchrone)
     */
    rembCreditCtxItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.RPCTX.code
        batchId = BatchList.BatchTFJ.id
    }
    rembCreditCtxItemProcess(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    rembCreditCtxItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.RPCTX.code
        batchId = BatchList.BatchTFJ.id
        objet = "rembCreditCTX"
    }

    /**
     * Step Calcul du nombre de jours de retard (asynchrone)
     */
    calculNbJourRetardItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.CNBJR.code
        batchId = BatchList.BatchTFJ.id
    }
    calculNbJourRetardItemProcessor(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    calculNbJourRetardItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

    /**
     * Step Calcul des pénalités de retard (synchrone)
     */
    calculPenaliteItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.PENAL.code
        batchId = BatchList.BatchTFJ.id
    }
    calculPenaliteItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    calculPenaliteItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.PENAL.code
        batchId = BatchList.BatchTFJ.id
        objet = "TableauAmortissement"
    }


    /**
     * Step génération des pénalités V2 (synchrone)
     */
    calculPenaliteV2ItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.GPENA.code
        batchId = BatchList.BatchTFJ.id
    }
    calculPenaliteV2ItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    calculPenaliteV2ItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.GPENA.code
        batchId = BatchList.BatchTFJ.id
        objet = "Credit"
    }

    /**
     * Step Bascule credit en souffrance (synchrone)
     */
    bsoufItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.BSOUF.code
        batchId = BatchList.BatchTFJ.id
    }
    bsoufItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    bsoufRembItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.BSOUF.code
        batchId = BatchList.BatchTFJ.id
        objet = "Credit"
    }

    /**
     * Step Récupération épargne credit en souffrance (synchrone)
     */
    vsoufItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.VSOUF.code
        batchId = BatchList.BatchTFJ.id
    }
    vsoufItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    vsoufRembItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.VSOUF.code
        batchId = BatchList.BatchTFJ.id
        objet = "Credit"
    }

    /**
     * Step Récupération intérêt credit en souffrance (synchrone)
     */
    rsoufItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.RSOUF.code
        batchId = BatchList.BatchTFJ.id
    }
    rsoufItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    rsoufRembItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.RSOUF.code
        batchId = BatchList.BatchTFJ.id
        objet = "Credit"
    }

    /**
     * Step Remboursement des pénalités V2 (synchrone)
     */
    rembPenaliteV2ItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.RPENA.code
        batchId = BatchList.BatchTFJ.id
    }
    rembPenaliteV2ItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    rembPenaliteV2RembItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.RPENA.code
        batchId = BatchList.BatchTFJ.id
        objet = "Credit"
    }

    /**
     * Step capitalisation ionteret differers (asynchrone)
     */
    capIntDiffItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.CAPIN.code
        batchId = BatchList.BatchTFJ.id
    }
    capIntDiffItemProcess(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    capIntDiffItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

    /**
     * Step Rembourssement prets impayés RPRET (synchrone)
     */
    rembImpayeItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.RPRET.code
        batchId = BatchList.BatchTFJ.id
    }
    rembImpayeItemProcess(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    rembImpayeItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.RPRET.code
        batchId = BatchList.BatchTFJ.id
        objet = "TableauAmortissement"
    }

    /**
     * Step des tombée d'échéance (synchrone) (synchrone)
     */
    tombeeEcheanceItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.TPRET.code
        batchId = BatchList.BatchTFJ.id
    }
    tombeeEcheanceItemProcess(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    tombeeEcheanceItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.TPRET.code
        batchId = BatchList.BatchTFJ.id
        objet = "TableauAmortissement"
    }

    /**
     * Step Tomber les écheances des crédits basculés en souffrance (synchrone)
     */
    tombeeEchGsoufItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.TEGSOUF.code
        batchId = BatchList.BatchTFJ.id
    }
    tombeeEchGsoufItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    tombeeEchGsoufItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.TEGSOUF.code
        batchId = BatchList.BatchTFJ.id
        objet = "TableauAmortissement"
    }

    /**
     * Step Mise à jour des échéances payées (synchrone)
     */
    pretPayeItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.PPRET.code
        batchId = BatchList.BatchTFJ.id
    }
    pretPayeItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    pretPayeItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.PPRET.code
        batchId = BatchList.BatchTFJ.id
        objet = "Operation"
    }

    /**
     * Step Traitement des impayes de prêts (synchrone)
     */
    impayesItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.IPRET.code
        batchId = BatchList.BatchTFJ.id
    }
    impayesItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    impayesItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.IPRET.code
        batchId = BatchList.BatchTFJ.id
        objet = "TableauAmortissement"
    }

    /**
     * Step Calcul intérêt périodique DAT (synchrone)
     */
    calculIntDATItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.CALINP.code
        batchId = BatchList.BatchTFJ.id
    }
    calculIntDATProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    calculIntDATItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.CALINP.code
        batchId = BatchList.BatchTFJ.id
        objet = "Operation"
    }

    /**
     * Step Tombées DAT (synchrone)
     */
    tombeeDatItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.TDAT.code
        batchId = BatchList.BatchTFJ.id
    }
    tombeeDatItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    tombeeDatItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.TDAT.code
        batchId = BatchList.BatchTFJ.id
        objet = "DepotTerme"
    }

    /**
     * Step Appro plan épargne (synchrone)
     */
    approPlanEpargneItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.APPRO_PLAN_EPARGNE.code
        batchId = BatchList.BatchTFJ.id
    }
    approPlanEpargneItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    approPlanEpargneItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.APPRO_PLAN_EPARGNE.code
        batchId = BatchList.BatchTFJ.id
        objet = "DepotTerme"
    }


    /**
     * Step Tombées DAG (synchrone)
     */
    tombeeDagItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.TDAG.code
        batchId = BatchList.BatchTFJ.id
    }
    tombeeDagItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    tombeeDagItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.TDAG.code
        batchId = BatchList.BatchTFJ.id
        objet = "DepotTerme"
    }

    /**
     * Step garantie main levée (synchrone)
     */
    mainLeveeGarantieItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.MAIN_LEVEE_GARANTIE.code
        batchId = BatchList.BatchTFJ.id
    }
    mainLeveeGarantieItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    mainLeveeGarantieItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.MAIN_LEVEE_GARANTIE.code
        batchId = BatchList.BatchTFJ.id
        objet = "Garantie"
    }

    /**
     * Step Déclassement des dossiers de crédit V2 (synchrone)
     */
    declassementV2ItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.DECLASSEMENT_CREDIT_V2.code
        batchId = BatchList.BatchTFJ.id
    }
    declassementV2ItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    declassementV2ItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.DECLASSEMENT_CREDIT_V2.code
        batchId = BatchList.BatchTFJ.id
        objet = "Credit"
    }

    /**
     * Step Déclassement automatique des crédits en contentieux  (synchrone)
     */
    dCotentieuxItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.PASSAGE_CONTENTIEUX.code
        batchId = BatchList.BatchTFJ.id
    }
    dCotentieuxItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    dCotentieuxItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.PASSAGE_CONTENTIEUX.code
        batchId = BatchList.BatchTFJ.id
        objet = "Credit"
    }


    /**
     * Step NOtification des crédits en contentieux  (synchrone)
     */
    notifCotentieuxItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.NOTIFICATION_PASSAGE_CONTENTIEUX.code
        batchId = BatchList.BatchTFJ.id
    }
    notifCotentieuxItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    notifCotentieuxItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.NOTIFICATION_PASSAGE_CONTENTIEUX.code
        batchId = BatchList.BatchTFJ.id
        objet = "Credit"
    }

    /**
     * Step Dnotification des crédits en contentieux  (synchrone)
     */
    majIndPaiementCarteMembreItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.MIPCM.code
        batchId = BatchList.BatchTFJ.id
    }
    majIndPaiementCarteMembreItemProcessor(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    majIndPaiementCarteMembreItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }


    /**
     * Step frais tenu de compte (asynchrone)
     */
    fraisDeTenuDeComptesItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.FRCPT.code
        batchId = BatchList.BatchTFJ.id
    }
    fraisDeTenuDeComptesItemProcessor(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    fraisDeTenuDeComptesItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

    /**
     * Step Génération compta frais tenu de compte (asynchrone)
     */
    genComptaFraisTenuCompteItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.GEN_COMPTA_FRCPT.code
        batchId = BatchList.BatchTFJ.id
    }
    genComptaFraisTenuCompteItemProcessor(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    genComptaFraisTenuCompteItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

    /**
     * Step Frais fond de garantie (asynchrone)
     */
    fraisFondGarantieItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.FFGAR.code
        batchId = BatchList.BatchTFJ.id
    }
    fraisFondGarantieItemProcessor(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    fraisFondGarantieItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

    /**
     * Step Alerte tombées échéances  (synchrone)
     */
    alertTombeeEchItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.ASMS.code
        batchId = BatchList.BatchTFJ.id
    }
    alertTombeeEchItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    alertTombeeEchItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.ASMS.code
        batchId = BatchList.BatchTFJ.id
        objet = "TableauAmortissement"
    }

    /**
     * Step Comptabilisation des frais sms (synchrone)
     */
    comptabilisationFraisSmstemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.CFSMS.code
        batchId = BatchList.BatchTFJ.id
    }
    comptabilisationFraisSmsProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    comptabilisationFraisSmsItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.CFSMS.code
        batchId = BatchList.BatchTFJ.id
        objet = "Frais"
    }

    /**
     * Step Cloture Cycle Tontines (synchrone)
     */
    clotureCycleTontinesItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.CCTT.code
        batchId = BatchList.BatchTFJ.id
    }
    clotureCycleTontinesItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    clotureCycleTontinesItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.CCTT.code
        batchId = BatchList.BatchTFJ.id
        objet = "Frais"
    }

    /**
     * Step des recouvrement frais (synchrone)
     */
    recouvrementFraisItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.RFRRT.code
        batchId = BatchList.BatchTFJ.id
    }
    recouvrementFraisItemProcess(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    recouvrementFraisItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.RFRRT.code
        batchId = BatchList.BatchTFJ.id
        objet = "FraisClient"
    }

    /**
     * Step Compta des découverts (synchrone)
     */
    comptaDecouvertItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.COMPTA_DECOUVERT.code
        batchId = BatchList.BatchTFJ.id
    }
    comptaDecouvertItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    comptaDecouvertItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.COMPTA_DECOUVERT.code
        batchId = BatchList.BatchTFJ.id
        objet = "Decouvert"
    }

    /**
     * Step Mise à jour statut decouvert (synchrone)
     */
    majStatutDecouvertItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.MISE_A_JOUR_STATUT_DECOUVERT.code
        batchId = BatchList.BatchTFJ.id
    }
    majStatutDecouvertItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    majStatutDecouvertItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.MISE_A_JOUR_STATUT_DECOUVERT.code
        batchId = BatchList.BatchTFJ.id
        objet = "Decouvert"
    }

    /**
     * Step Rejet automatiique des forçages sans issue (asynchrone)
     */
    rejetAutoForcageItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.REJET_AUTOMATIQUE_FORCAGES.code
        batchId = BatchList.BatchTFJ.id
    }
    rejetAutoForcageItemProcessor(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    rejetAutoForcageItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

    /**
     * Step Traitement virement permenant (synchrone)
     */
    virementPermenantItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.TVRPT.code
        batchId = BatchList.BatchTFJ.id
    }
    virementPermenantItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    virementPermenantItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.TVRPT.code
        batchId = BatchList.BatchTFJ.id
        objet = "Operation"
    }

    /**
     * Step preparation COmpenses automatisues (asynchrone)
     */
    preparationCompenseInterAgenceItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.PREPARATION_COMPENSE_INTER_MUTUELLE.code
        batchId = BatchList.BatchTFJ.id
    }
    preparationCompenseInterAgenceItemProcessor(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    preparationCompenseInterAgenceItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

   /**
     * Step COmpenses automatisues (synchrone)
     */
    compenseTransactionInterAgenceItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.COMPENSE_TRANSACTIONS_INTER_MUTUELLE.code
        batchId = BatchList.BatchTFJ.id
    }
    compenseTransactionInterAgenceItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    compenseTransactionInterAgenceItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.COMPENSE_TRANSACTIONS_INTER_MUTUELLE.code
        batchId = BatchList.BatchTFJ.id
        objet = "OperationCompense"
    }

    /**
     * Step Compta Journalier (asynchrone)
     */
    comptaJournalierItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.MAJCPT.code
        batchId = BatchList.BatchTFJ.id
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
        programCode = ConstantesBatch.Programme.MAJSCG.code
        batchId = BatchList.BatchTFJ.id
    }
    cmajCgItemProcessor(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    cmajCgItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

    /**
     * Step Calcul statistiques (asynchrone)
     */
    calculStatItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.CALSTAT.code
        batchId = BatchList.BatchTFJ.id
    }
    calculStatItemProcessor(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    calculStatItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

    /**
     * Step Statistiques de remboursements (asynchrone)
     */
    statRembItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.STATREM.code
        batchId = BatchList.BatchTFJ.id
    }
    statRembItemProcessor(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    statRembItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

    /**
     * Step mis à jour statut credit en souffrance (synchrone)
     */
    msoufItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.MSOUF.code
        batchId = BatchList.BatchTFJ.id
    }
    msoufItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    msoufRembItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.MSOUF.code
        batchId = BatchList.BatchTFJ.id
        objet = "Credit"
    }

    /**
     * Step Calcul du nombre de jours de retard (asynchrone)
     */
    calculNbJourRetardItem2Reader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.CNBJR2.code
        batchId = BatchList.BatchTFJ.id
    }
    calculNbJourRetardItem2Processor(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    calculNbJourRetardItem2Writer(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

    /**
     * Step Calcul des pénalités de retard (synchrone)
     */
    calculPenaliteItem2Reader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.PENAL2.code
        batchId = BatchList.BatchTFJ.id
    }
    calculPenaliteItem2Processor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    calculPenaliteItem2Writer(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.PENAL2.code
        batchId = BatchList.BatchTFJ.id
        objet = "TableauAmortissement"
    }


    /**
     * Step génération des pénalités V2 (synchrone)
     */
    calculPenaliteV2Item2Reader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.GPENA2.code
        batchId = BatchList.BatchTFJ.id
    }
    calculPenaliteV2Item2Processor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    calculPenaliteV2Item2Writer(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.GPENA2.code
        batchId = BatchList.BatchTFJ.id
        objet = "Credit"
    }



    /**
     * Step Clôture des dossiers de crédit (asynchrone)
     */
    clotureCreditItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.CPRET.code
        batchId = BatchList.BatchTFJ.id
    }
    clotureCreditItemProcessor(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    clotureCreditItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

    /**
     * Step CLoture compte épargne nantie (asynchrone)
     */
    clotureCompteEpargneNantieIemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.CLCEN.code
        batchId = BatchList.BatchTFJ.id
    }
    clotureCompteEpargneNantieItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    clotureCompteEpargneNantieItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.CLCEN.code
        batchId = BatchList.BatchTFJ.id
        objet = "Credit"
    }

    /**
     * Step Blocage compte auxiliaire inactif (asynchrone)
     */
    blocageCptAuxItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.BLOCPT.code
        batchId = BatchList.BatchTFJ.id
    }
    blocageCptAuxItemProcessor(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    blocageCptAuxItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

    /**
     * Step Ouverture Cycle Tontines (synchrone)
     */
    ouvertureCycleTontinesItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.OCTT.code
        batchId = BatchList.BatchTFJ.id
    }
    ouvertureCycleTontinesItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    ouvertureCycleTontinesItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.OCTT.code
        batchId = BatchList.BatchTFJ.id
        objet = "CycleTontine"
    }

    /**
     * Step Ouverture Cycle Tontines Mise (synchrone)
     */
    tontineAdhesionMiseItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.OCTTM.code
        batchId = BatchList.BatchTFJ.id
    }
    tontineAdhesionMiseItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    tontineAdhesionMiseItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.OCTTM.code
        batchId = BatchList.BatchTFJ.id
        objet = "CycleMise"
    }

    /**
     * Step Vérification déséquilibre (asynchrone)
     */
    verifDesiquilibreItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.DSFIN.code
        batchId = BatchList.BatchTFJ.id
    }
    verifDesiquilibreItemProcessor(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    verifDesiquilibreItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

    /**
     * Step Vérification de la cohérence des dossiers de crédits (asynchrone)
     */
    verifCoherenceCreditItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.VFCRD.code
        batchId = BatchList.BatchTFJ.id
    }
    verifCoherenceCreditItemProcessor(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    verifCoherenceCreditItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

    /**
     * Step Mise à jour des promesses de remboursement (synchrone)
     */
    promesseRembItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.MAJPREMB.code
        batchId = BatchList.BatchTFJ.id
    }
    promesseRembItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    promesseRembItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.MAJPREMB.code
        batchId = BatchList.BatchTFJ.id
        objet = "CycleMise"
    }

    /**
     * Step Inventaire des dossiers de crédit (asynchrone)
     */
    inventaireCreditItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.CREDITINV.code
        batchId = BatchList.BatchTFJ.id
    }
    inventaireCreditItemProcessor(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    inventaireCreditItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }

    /**
     * Step Caution main levée (synchrone)
     */
    cautionItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.CAUTION.code
        batchId = BatchList.BatchTFJ.id
    }
    cautionItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    cautionItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.CAUTION.code
        batchId = BatchList.BatchTFJ.id
        objet = "Caution"
    }
    /**
     * Step prise de commission (synchrone)
     */
    commissionCautionItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.COMMISSION_CAUTION.code
        batchId = BatchList.BatchTFJ.id
    }
    commissionCautionItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    commissionCautionItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.COMMISSION_CAUTION.code
        batchId = BatchList.BatchTFJ.id
        objet = "CommissionCaution"
    }

    /**
     * Step Préparation des compensations par bureau (synchrone)
     */
    compenseBureauItemReader(ItemReaderSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.COMPENSE_BUREAU.code
        batchId = BatchList.BatchTFJ.id
    }
    compenseBureauItemProcessor(ItemProcessorSync) { bean ->
        bean.autowire = "byName"
    }
    compenseBureauItemWriter(ItemWriterSync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.COMPENSE_BUREAU.code
        batchId = BatchList.BatchTFJ.id
        objet = "CycleMise"
    }

    /**
     * Step Sauvegarde de la base après traitement(asynchrone)
     */
    sauvDbApresItemReader(ItemReaderAsync) { bean ->
        bean.autowire = "byName"
        programCode = ConstantesBatch.Programme.SAUVBA.code
        batchId = BatchList.BatchTFJ.id
    }
    sauvDbApresItemProcess(ItemProcessorAsync) { bean ->
        bean.autowire = "byName"
    }
    sauvDbApresItemWriter(ItemWriterAsync) { bean ->
        bean.autowire = "byName"
    }
}
