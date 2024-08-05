package sn.sensoft.springbatch.utils

class ConstantesBatch {

    // Liste des programmes batch, TODO: à compléter
    enum Programme {


        //  START  ******   BATCH TFJ  *****   START
        SAUVBA("SAUVBA", "Sauvegarde de la base avant traitement", "Sauvegarde_Base_Avant", BatchList.BatchTFJ.id, 0),

        COMPTA_REMISE_CHEQUE("COMPTA_REMISE_CHEQUE", "Comptabilisation remise chèque", "Comptabilisation_remise_cheque",BatchList.BatchTFJ.id,  5),
        REFCHARG("REFCHARG", "Charge refacture", "charge_refacture",BatchList.BatchTFJ.id,  8),
        RPCTX("RPCTX", "Remboursement Credits Contentieux", "Remboursement_Credits_Ctx",BatchList.BatchTFJ.id,  10),

        CNBJR("CNBJR", "Calcul du nombre de jours de retard", "Calcul_Nb_J_Retard",BatchList.BatchTFJ.id,  15),
        PENAL("PENAL", "Calcul des pénalités de retard", "calculPenalite",BatchList.BatchTFJ.id,  20),
        GPENA("GPENA", "Génération des pénalités V2", "Generation_penalites_V2",BatchList.BatchTFJ.id,  25),

        BSOUF("BSOUF", "Bascule crédit en souffrance (bascule total du montant en souffrance)", "Bascule_credit_en_souffrance",BatchList.BatchTFJ.id,  30),
        VSOUF("VSOUF", "Récupération Épargne crédit en souffrance", "Recup_Epargne_credit_en_souffrance",BatchList.BatchTFJ.id,  35),
        RSOUF("RSOUF", "Récupération Interêts crédit en souffrance", "Recup_interet_credit_en_souffrance",BatchList.BatchTFJ.id,  40),

        RPENA("RPENA", "Remboursement des pénalités V2", "Remboursement_penalites_V2",BatchList.BatchTFJ.id,  45),

        CAPIN("CAPIN", "Capitalisation des intérêts de différés", "Capitalisation_Interets_Differes",BatchList.BatchTFJ.id,  50),
        RPRET("RPRET", "Remboursement impayés prêts", "Remboursement_Impayes_Prets",BatchList.BatchTFJ.id,  53),

        TPRET("TPRET", "Tombées d'écheances", "Tombees_Echeances","BatchAicha",  55),
        TEGSOUF("TEGSOUF", "Tomber les écheances des crédits basculés en souffrance", "Tombees_Echeances_GSOUF",BatchList.BatchTFJ.id,  60),
        PPRET("PPRET", "Mise à jour des échéances payées", "Prets_Payes",BatchList.BatchTFJ.id,  65),
        IPRET("IPRET", "Traitement des impayes de prêts", "Impayes_Prets",BatchList.BatchTFJ.id,  70),
        CALINP("CALINP", "Calcul intérêt périodique DAT", "calcul_interet_periode_dat",BatchList.BatchTFJ.id,  80),
        TDAT("TDAT", "Tombées DAT", "tombee_dat",BatchList.BatchTFJ.id,  85),
        APPRO_PLAN_EPARGNE("APPRO_PLAN_EPARGNE", "Approvisionnement plan épargne", "Approvionnement_plan_epargne",BatchList.BatchTFJ.id,  87),
        TDAG("TDAG", "Déblocage dépôt de garantie", "tombeeDag",BatchList.BatchTFJ.id,  90),
        MAIN_LEVEE_GARANTIE("MAIN_LEVEE_GARANTIE", "Main levée automatique sur les garanties ", "Main_levee_garantie",BatchList.BatchTFJ.id,  93),

        DECLASSEMENT_CREDIT_V2("DECLASSEMENT_CREDIT_V2", "Déclassement des dossiers de crédit V2", "declassement_credit_v2",BatchList.BatchTFJ.id,  95),
        PASSAGE_CONTENTIEUX("PASSAGE_CONTENTIEUX", "Passage dossiers de crédit en contentieux", "Passage_Cotentieux",BatchList.BatchTFJ.id,  100),
        NOTIFICATION_PASSAGE_CONTENTIEUX("NOTIFICATION_PASSAGE_CONTENTIEUX", "Notification passage en contentieux dans les prochains x jours", "Notification_Passage_Cotentieux",BatchList.BatchTFJ.id,  105),

        MIPCM("MIPCM", "Maj de l'indicateur paiement carte de membre", "Maj_Indicateur_Paiement_Carte_Membre",BatchList.BatchTFJ.id,  110),
        FRCPT("FRCPT", "Frais de tenu de compte", "Frais_Tenu_Compte",BatchList.BatchTFJ.id,  120),
        GEN_COMPTA_FRCPT("GEN_COMPTA_FRCPT", "Génération compta frais de tenu de compte", "Gen_Compta_Frais_Tenu_Compte",BatchList.BatchTFJ.id,  122),
        FFGAR("FFGAR", "Frais fond de garantie", "Frais_fond_de_garantie",BatchList.BatchTFJ.id,  125),
        ASMS("ASMS", "Alerte tombées échéances", "Alerte_Tombees_Echeances",BatchList.BatchTFJ.id,  130),
        CFSMS("CFSMS", "Comptabilisation des frais sms", "Comptabilisation_frais_sms",BatchList.BatchTFJ.id,  135),
        CCTT("CCTT", "Cloture Cycle Tontines", "Cloture_Cycle_Tontines",BatchList.BatchTFJ.id,  140),
        RFRRT("RFRRT", "Recouvrement frais en retard", "recouvrementFraisRetard",BatchList.BatchTFJ.id,  145),

        COMPTA_DECOUVERT("COMPTA_DECOUVERT", "Compta des découverts", "Compta_decouvert", BatchList.BatchTFJ.id, 150),
        MISE_A_JOUR_STATUT_DECOUVERT("MISE_A_JOUR_STATUT_DECOUVERT", "Mise à jour statut decouvert", "Mise_a_jour_statut_decouvert", BatchList.BatchTFJ.id, 155),
        REJET_AUTOMATIQUE_FORCAGES("REJET_AUTOMATIQUE_FORCAGES", "Rejet automatiique des forçages sans issues", "Rejet_automatique_forcages", BatchList.BatchTFJ.id, 160),
        TVRPT("TVRPT", "Traitement virement permenant", "Traitement_Virement_Permenant",BatchList.BatchTFJ.id,  165),

        PREPARATION_COMPENSE_INTER_MUTUELLE("PREPARATION_COMPENSE_INTER_MUTUELLE", "Préparation des compensations Inter mutuelle ", "Preparation_compense_inter_mutuelle", BatchList.BatchTFJ.id, 166),
        COMPENSE_TRANSACTIONS_INTER_MUTUELLE("COMPENSE_TRANSACTIONS_INTER_MUTUELLE", "Préparation des compensations Inter mutuelle ", "Compense_transactions_inter_mutuelle", BatchList.BatchTFJ.id, 167),

        MAJCPT("MAJCPT", "Compta Journalier", "Compta_Journalier",BatchList.BatchTFJ.id,  170),
        MAJSCG("MAJSCG", "Mise a jour des soldes cg", "Maj_Solde_Cg",BatchList.BatchTFJ.id,  175),
        CALSTAT("CALSTAT", "Calcul statistiques", "Calcul_Statistiques",BatchList.BatchTFJ.id,  180),
        STATREM("STATREM", "Statistiques de remboursements", "Statistiques_Remboursements",BatchList.BatchTFJ.id,  185),
        MSOUF("MSOUF", "Mise à jous statut crédit en souffrance", "Maj_statut_credit_en_souffrance",BatchList.BatchTFJ.id,  190),

        CNBJR2("CNBJR2", "Calcul du nombre de jours de retard", "Calcul_Nb_J_Retard_2",BatchList.BatchTFJ.id,  192),
        PENAL2("PENAL2", "Calcul des pénalités de retard", "calculPenalite_2",BatchList.BatchTFJ.id,  193),
        GPENA2("GPENA2", "Génération des pénalités V2", "Generation_penalites_V2_2",BatchList.BatchTFJ.id,  194),

        CPRET("CPRET", "Clôture des dossiers de crédit", "Cloture_Dossier_Credit",BatchList.BatchTFJ.id,  195),
        CLCEN("CLCEN", "Clôture des comptes épargne nanties", "Cloture_Compte_Epargne_Nantie",BatchList.BatchTFJ.id,  196),
        BLOCPT("BLOCPT", "Blocage compte auxiliaire inactif", "blocage_compte_auxiliaire_inactif",BatchList.BatchTFJ.id,  200),
        OCTT("OCTT", "Ouverture Cycle Tontines", "Ouverture_Cycle_Tontines",BatchList.BatchTFJ.id,  205),
        OCTTM("OCTTM", "Ouverture Cycle Tontines Mise", "Ouverture_Cycle_Tontines_Mise",BatchList.BatchTFJ.id,  210),
        DSFIN("DSFIN", "Vérification déséquilibre", "verifDesiquilibre",BatchList.BatchTFJ.id,  220),
        VFCRD("VFCRD", "Vérification de la cohérence des dossiers de crédits", "Verification_coherence_credits",BatchList.BatchTFJ.id,  225),
        MAJPREMB("MAJPREMB", "Mise à jour des promesses de remboursement", "Maj_promesses_rembourssement",BatchList.BatchTFJ.id,  230),
        CREDITINV("CREDITINV", "Inventaire des dossiers de crédit", "Iventaire_Dossier_Credit", BatchList.BatchTFJ.id, 235),
        CAUTION("CAUTION", "Main levée caution", "main_levee_caution",BatchList.BatchTFJ.id,  236),
        COMMISSION_CAUTION("COMMISSION_CAUTION", "Calcul commission de caution", "commission_caution",BatchList.BatchTFJ.id,  238),

        COMPENSE_BUREAU("COMPENSE_BUREAU", "Préparation des compensations par bureau ", "Preparation_compenses_bureau", BatchList.BatchTFJ.id, 240),
        SAUVB("SAUVB", "Sauvegarde de la base après traitement", "Sauvegarde_Base",BatchList.BatchTFJ.id,  245),

        //  START  ******   BATCH TFJ  *****   START

        //TODO savoir pourquoi ne figure pas dans la liste des batchs fournis par khalifa
        ALSMC("ALSMC", "Alerte solde compte insuffisant échéances", "Alert_Solde_Compte_Insuffisant_Echeance",BatchList.BatchTFJ.id,  -1),

        //Reprise Credit
        RPCRD("RPCRD", "Reprise dossiers crédit avec validation et financement", "Reprise_Credits", BatchList.BatchRepriseCredit.id,  1),
        COMPTARC("COMPTARC", "Compta Journalier", "Reprise_Compta_Journalier",BatchList.BatchRepriseCredit.id,  10),
        COMPTACGRC("COMPTACGRC", "Mise a jour des soldes cg", "Reprise_Maj_Solde_Cg",BatchList.BatchRepriseCredit.id,  20),

        //Reprise Echeances
        RPECHP("RPECHP", "Reprise des échéances payées", "Reprise_Echeance_payees", BatchList.BatchRepriseEcheance.id,  1),
        COMPTA("COMPTA", "Compta Journalier", "Reprise_Echeance_Compta_Journalier",BatchList.BatchRepriseEcheance.id,  10),
        COMPTACG("COMPTACG", "Mise a jour des soldes cg", "Reprise_Echeance_Maj_Solde_Cg",BatchList.BatchRepriseEcheance.id,  20),

        //Reprise COrrection financement
        REPRISE_CORR_FINANCEMENT("REPRISE_CORR_FINANCEMENT", "Correction financement reprise crédit", "Reprise_credit_correction_financement", BatchList.BatchRepriseCreditCorrFc.id,  1),


        //Batch Récupération provsion des dossiers de crédits avec financement externe
        APECRTX("APECRTX", "Appel Crédits Contentieux", "Appel_Credit_Contentieux", BatchList.BatchRecuperationProvison.id,  1),
        APECHIMP("APECHIMP", "Appel Echance Impayes", "Appel_Echance_Impayes", BatchList.BatchRecuperationProvison.id,  2),
        APECH("APECH", "Appel Echance", "Appel_Echance", BatchList.BatchRecuperationProvison.id, 3),

        //Batch Calcul solde arrete
        CALSD("CALSD", "Calcul solde arrete", "Calcul_Solde_Arrete", BatchList.BatchCalculSoldeArrete.id, 1),

        //Batch Calcul creances ratachees
        CALCR("CALCR", "Calcul créances Rattachées", "Calcul_Creances_Rattachees", BatchList.BatchCalculCreancesRattachees.id, 1),

        //Batch Apurement Compte Gestion
        APCG("APCG", "Apurement Compte Gestion", "Apurement_Compte_Gestion", BatchList.BatchApurementCompteGestion.id, 1),

        //Batch Calcul Poste Budgetaire
        CALPB("CALPB", "Calcul  Poste Budgetaire", "Calcul_Poste_Budgetaire", BatchList.BatchCalculPosteBudgetaire.id, 1),

        //Batch calcul Provisions Prets
        CALPP("CALPP", "Calcul Provisions Prets", "Calcul_Provisions_Prets", BatchList.BatchCalculProvisionsPrets.id, 1),

        //Batch Generation Ecriture Provision
        GEEP("GEEP", "Generation Ecriture Provision", "Generation_Ecriture_Provision", BatchList.BatchGenerationEcritureProvision.id, 1),

        //Batch Calcul Interet Compte Bloque
        CALICB("CALICB", "Calcul Interet Compte Bloque", "Calcul_Interet_Compte_Bloque", BatchList.BatchCalculInteretCompteBloque.id, 1),

        //Batch Imputation Interet
        IMPINT("IMPINT", "Imputation Interet", "Imputation_Interet", BatchList.BatchImputationInteret.id, 1),

        //Batch calcul des immobilisations
        CALCUL_IMMO("CALCUL_IMMO", "Calcul des Immobilisations", "Calcul_Immobilisations", BatchList.BatchCalculImmobilisations.id, 1),

        //Batch comptabilisation des immobilisations
        COMPTA_IMMO("COMPTA_IMMO", "Comptabilisation Immv4(o", "Comptabilisation_Immo", BatchList.BatchComptabilisationImmo.id, 1),

        //Batch Initialiser une liste de dossiers de crédits
        SAUV_BASE_AVANT_INIT_CREDITS("SAUV_BASE_AVANT_INIT_CREDITS", "Sauvegarder la base avant initialisation credits", "sauv_base_avant_init_credits", BatchList.BatchInitialisationCredit.id, 1),
        SELECTION_OPERATIONS_A_EXTOURNER("SELECTION_OPERATIONS_A_EXTOURNER", "Selection des opérations à extourner", "Operations_a_extournner", BatchList.BatchInitialisationCredit.id, 2),
        EXTOURNES_OPERATIONS("EXTOURNES_OPERATIONS", "Extournes des opérations selectionnées", "Extournes_operations", BatchList.BatchInitialisationCredit.id, 3),
        MISES_A_JOUR_CREDITS_EXTOURNES("MISES_A_JOUR_CREDITS_EXTOURNES", "Mettre à jour les statuts des dossiers de crédits", "Mises_a_jour_statuts_credits", BatchList.BatchInitialisationCredit.id, 4),
        SAUV_BASE_APRES_INIT_CREDITS("SAUV_BASE_APRES_INIT_CREDITS", "Sauvegarder la base aprés initialisation credits", "sauv_base_apres_init_credits", BatchList.BatchInitialisationCredit.id, 5),

        //Batch extourneFromFile opérations à partir d'un fichier csv
        EXTOURNE_OPERATION_FROM_FILE("EXTOURNE_OPERATION_FROM_FILE", "Extourner des operations à partir d'un fichier csv", "Extourne_Operations_From_csv_File", BatchList.BatchExtourneOperationsFromFile.id, 1),

        //Batch corrections crédits
        CORRECTION_CREDITS_STEP("CORRECTION_CREDITS_STEP", "Correction crédits step", "Correction_Credits_step", BatchList.CorrectionCredits.id, 1),
        STATREM_CORRECTION("STATREM_CORRECTION", "Statistiques de remboursements", "Stat_Remb_after_correction",BatchList.CorrectionCredits.id,  2),
        CNBJR_CORRECTION("CNBJR_CORRECTION", "Calcul du nombre de jours de retard", "Calcul_Nb_J_Retard_after_correction",BatchList.CorrectionCredits.id,  3),
        MAJCPT_CORRECTION("MAJCPT_CORRECTION", "Compta Journalier", "Compta_Journalier_after_correction",BatchList.CorrectionCredits.id,  4),
        MAJSCG_CORRECTION("MAJSCG_CORRECTION", "Mise a jour des soldes cg", "Maj_Solde_Cg_after_correction",BatchList.CorrectionCredits.id,  5),

        //Batch corrections Fiancement
        CORRECTION_FINANCEMENT_STEP("CORRECTION_FINANCEMENT_STEP", "Correction financement", "Correction_Financement_Step", BatchList.CorrectionFinancementCredit.id, 1),
        STATREM_CORRECTION_FINANCEMENT("STATREM_CORRECTION_FINANCEMENT", "Statistiques de remboursements", "Stat_Remb_aftert_refinancement",BatchList.CorrectionFinancementCredit.id,  2),
        MAJCPT_CORRECTION_FINANCEMENT("MAJCPT_CORRECTION_FINANCEMENT", "Compta Journalier", "Compta_Journalier_aftert_refinancement",BatchList.CorrectionFinancementCredit.id,  3),
        MAJSCG_CORRECTION_FINANCEMENT("MAJSCG_CORRECTION_FINANCEMENT", "Mise a jour des soldes cg", "Maj_Solde_Cg_aftert_refinancement",BatchList.CorrectionFinancementCredit.id,  4),

        //Batch corrections en recréant le dossier de crédit
        RECREER_DOSSIER_CREDIT_STEP("RECREER_DOSSIER_CREDIT_STEP", "Recréer dossier de crédit step", "Recreer_Dossier_Credit_Step", BatchList.RecreerDossierCredit.id, 1),
        STATREM_CORRECTION_RECREATION("STATREM_CORRECTION_RECREATION", "Statistiques de remboursements", "Stat_Remb_aftert_recreation",BatchList.RecreerDossierCredit.id,  2),
        MAJCPT_CORRECTION_RECREATION("MAJCPT_CORRECTION_RECREATION", "Compta Journalier", "Compta_Journalier_aftert_recreation",BatchList.RecreerDossierCredit.id,  3),
        MAJSCG_CORRECTION_RECREATION("MAJSCG_CORRECTION_RECREATION", "Mise a jour des soldes cg", "Maj_Solde_Cg_aftert_recreation",BatchList.RecreerDossierCredit.id,  4),

        //Batch sectionner les frais tenu de compte non recyclés
        SELECT_FRAIS_TCP_NON_RECYCLES("SELECT_FRAIS_NON_RECYCLES", "Selection frais TCP non recyclés", "Select_ftcp_non_recycles", BatchList.SelcectFraisTcpNonRecycles.id,  1),
        RECYCLE_LES_FRAIS("RECYCLE_LES_FRAIS", "Recycle lesfrais", "Recycle_les_frais", BatchList.SelcectFraisTcpNonRecycles.id,  2),

        //Batch traitement des transactions par fichier
        VERIFICATION_PREPARATION_TRAITEMENT("VERIFICATION_PREPARATION_TRAITEMENT", "Vérifier l'état des fichier, des données et préparation traitements", "Verificcation_preparation_traitement", BatchList.BatchTraitementTransationsFichiers.id,  1),
        COMPTA_TRANSCTION_PAR_FICHIER("COMPTA_TRANSCTION_PAR_FICHIER", "Comptabilisation des transacttions", "Comptabilisation_transacttions_fichiers", BatchList.BatchTraitementTransationsFichiers.id,  2),

        //Fusion de caisse, liquidation bureau et transfert client
        COMPTA_JOURNALIER_FC_A("COMPTA_JOURNALIER_FC_AVANT", "Compta journalier avant liquidation de bureau et fusion caisse", "compta_journalier_FC_a", BatchList.BatchFusionCaisse.id,  1),
        MAJ_SOLDE_COMPTES_GENEAUX_FC_A("MAJ_SOLDE_COMPTES_GENEAUX_FC_AVANT", "Mise à jour solde comptes généraux avant liquidation de bureau et fusion caisses", "Maj_Solde_Comptes_generaux_FC_a", BatchList.BatchFusionCaisse.id,  5),
        FUSION_CAISSES("FUSION_CAISSES", "Fusion caisses: transfert de fonds, cloture comptes et fermetures caisses ", "Fusion_caisse", BatchList.BatchFusionCaisse.id,  10),
        LIQUIDATION_BUREAUX("LIQUIDATION_BUREAUX", "Liquidation des bureaux liés au caisse cloturées", "Liquidation_bureaux", BatchList.BatchFusionCaisse.id,  15),
        DUPLICATION_COMPTES_GENERAUX("DUPLICATION_COMPTES_GENERAUX", "Duplication comptes généraux du bureau liquidé dans le bureau destinataire", "Duplication_comptes_generaux", BatchList.BatchFusionCaisse.id,  20),
        DUPLICATION_COMPTES_AUXILIAIRES("DUPLICATION_COMPTES_AUXILIAIRES", "Duplication comptes auxiliaires du bureau liquidé dans le bureau destinataire", "Duplication_comptes_auxiliaires", BatchList.BatchFusionCaisse.id,  30),
        TRANSFERT_CLIENT("TRANSFERT_CLIENT", "Transfert des clients des bureaux liquidés", "transfert_client", BatchList.BatchFusionCaisse.id,  35),
        COMPTA_JOURNALIER_FC("COMPTA_JOURNALIER_FC", "Compta journalier après liquidation de bureau et fusion caisse", "compta_journalier_FC", BatchList.BatchFusionCaisse.id,  40),
        MAJ_SOLDE_COMPTES_GENEAUX_FC("MAJ_SOLDE_COMPTES_GENEAUX_FC", "Mise à jour solde comptes généraux après liquidation de bureau et fusion caisses", "Maj_Solde_Comptes_generaux_FC", BatchList.BatchFusionCaisse.id,  45),
        CALCUL_STATISTIQUE_FC("CALCUL_STATISTIQUE_FC", "Calcul statistiques après liquidation de bureau et fusion caisses", "Calcul_Statistiques_FC", BatchList.BatchFusionCaisse.id,  50),

        //Batch correction crédit
        CORRECTION_CREDIT_BASCULE_EN_SOUF("CORRECTION_CREDIT_BASCULE_EN_SOUF", "Selection et correction des crédit basculés en souffrance", "Correction_Credit_Bascule_en_Souf", BatchList.BatchCorrectionBasculeCrediteErreur.id,  1),
        RATTRAPAGE_FRAIS_SMS("RATTRAPAGE_FRAIS_SMS", "Rattrapage de frais SMS pour un mois donné", "Rattrapage_Frais_SMS", BatchList.BatchRattrapageFraisSMS.id,  5),

        //Batch extractions bic
        EXTRACTION_SOCIETE("EXTRACTION_SOCIETE", "Extraction socièté BIC", "EXTRACTION_SOCIETE", BatchList.BatchExtractionBic.id,  0),
        EXTRACTION_INDIVIDU("EXTRACTION_INDIVIDU", "Extractions individu BIC", "EXTRACTION_INDIVIDU", BatchList.BatchExtractionBic.id,  10),
        EXTRACTION_GARANTIE("EXTRACTION_GARANTIE", "Extractions garantie BIC", "EXTRACTION_GARANTIE", BatchList.BatchExtractionBic.id,  20),
        EXTRACTION_CREDIT("EXTRACTION_CREDIT", "Extractions crédit BIC", "EXTRACTION_CREDIT", BatchList.BatchExtractionBic.id,  30),
        EXTRACTION_BIC_NOTIFICATION("EXTRACTION_BIC_NOTIFICATION", "Extractions notification ", "EXTRACTION_BIC_NOTIFICATION", BatchList.BatchExtractionBic.id,  40),

        VERIF_COMPTES_COMPENSES_MUTUELLE("VERIF_COMPTES_COMPENSES_MUTUELLE", "Vérification des comptes de compenses mutuelles", "Verif_comptes_compenses_mutuelles", BatchList.BatchVerifComptesCompensesCaisses.id, 0),


        private Programme(String code, String libelle, String name, String batch_id, int sequence) {
            this.code = code
            this.libelle = libelle
            this.name = name
            this.sequence = sequence
            this.batch_id = batch_id
        }
        final String code
        final String libelle
        final String name
        final String batch_id
        final int sequence

        static ArrayList<Programme> findByCode(String code) {
            values().findAll() { it.code == code }
        }

        static ArrayList<Programme> findAll() {
            values().findAll()
        }


        String getCode() {
            return this.code
        }

        String getLibelle() {
            return this.libelle
        }

        String getName() {
            return this.name
        }

        String getBatchId() {
            return this.batch_id
        }

        int getSequence() {
            return sequence
        }

        @Override
        String toString() {
            return this.libelle
        }
    }

    //Name des batch crée
    public static final String BATCH_AICHA_JOB_NAME = "BatchAicha"

    public static final String BATCH_JOB_INSTANCE_STARTED_STATUS = "STARTED"
    public static final String BATCH_JOB_INSTANCE_INPROGRESS_STATUS = "EXECUTING"
    public static final String BATCH_JOB_INSTANCE_STOPED_STATUS = "STOPPED"
    public static final String BATCH_JOB_INSTANCE_STOPPING_STATUS = "STOPPING"
    public static final String BATCH_JOB_INSTANCE_SUCCESS_STATUS = "SUCCESS"
    public static final String BATCH_JOB_INSTANCE_COMPLETED_STATUS = "COMPLETED"
    public static final String BATCH_JOB_INSTANCE_FAILED_STATUS = "FAILED"
}
