package sn.sensoft.springbatch.utils
import sn.sensoft.springbatch.utils.BatchType

public enum BatchList {
    BatchTFJ("BatchAicha", BatchType.TRAITEMENT_FIN_JOURNEE.code, "Batch traitement fin journée", true, true),
    BatchRecuperationProvison("Batch Recuperation Provison",  BatchType.RECUPERATION_PROVISION.code,"Batch récuperation provison pour les dossiers de crédit avec financement externe", false, false),
    BatchRepriseV3("Batch Reprise V3",  BatchType.REPRISE_V3.code,"Batch pour la reprise aichav3 vers aichav4", false, false),
    BatchRepriseCredit("BatchRepriseCredit",  BatchType.REPRISE_CREDIT.code,"Batch pour la reprise de dossier de crédiits", true, false),
    BatchRepriseEcheance("Batch Reprise Echeances", BatchType.REPRISE_ECHEANCE.code, "Batch pour la reprise des échéances des dossier de crédiits", false, false),
    BatchCalculSoldeArrete("Batch Calcul Solde Arrete",  BatchType.CALCUL_DATE_ARRETE.code,"Batch Calcul Solde Arrete", true, false),
    BatchCalculCreancesRattachees("Batch Calcul Creances Rattachees",  BatchType.CALCUL_DATE_ARRETE.code,"Batch Calcul créances rattachées", true, false),
    BatchApurementCompteGestion("Apurement Compte Gestion",  BatchType.CALCUL_DATE_ARRETE.code,"Apurement Compte Gestion", true, false),
    BatchCalculPosteBudgetaire("Calcul Poste Budgetaire",  BatchType.POST_BUDGETAIRE.code,"Calcul Poste Budgetaire", true, false),
    BatchCalculProvisionsPrets("Calcul Provisions Prets",  BatchType.PROVISION_PRET.code,"Calcul Provisions Prets", true, false),
    BatchGenerationEcritureProvision("Generation Ecriture Provision",  BatchType.PROVISION_PRET.code,"Generation Ecriture Provision", true, false),
    BatchCalculInteretCompteBloque("Calcul Interet Compte Bloque",  BatchType.INTERET_BLOQUE.code,"Calcul Interet Compte Bloque", true, false),
    BatchImputationInteret("Imputation Interet",  BatchType.INTERET_BLOQUE.code,"Imputation Interet", true, false),
    BatchCalculImmobilisations("Calcul Immobilisations",  BatchType.IMMOBILISATION.code ,"Calcul des Immobilisations", true, false),
    BatchComptabilisationImmo("Comptabilisation Immo",  BatchType.IMMOBILISATION.code,"Comptabilisation des Immobilisations", true, false),
    BatchRepriseCreditCorrFc("Correction_Financement",  BatchType.CORR_REPRISE_FINANCEMENT.code,"Correction finanncement reprise crédit", false, false),
    BatchInitialisationCredit("Initialisation_Credit", BatchType.INITIALISER_DOSSIERS_CREDITS.code,"Initialisations d'une liste de dossiers de crédit", false, true),
    BatchExtourneOperationsFromFile("Extourne_Operations_From_File", BatchType.EXTOURNE_OPERATION_FROM_FILE.code,"Extournes d'opérations à partir d'un fichier csv", false, false),
    CorrectionCredits("Correction_credits", BatchType.CORRECTION_CREDITS_TA.code," Correction crédits TA", false, false),
    CorrectionFinancementCredit("Correction_Financement_Credit", BatchType.CORRECTION_FINANCEMENT.code," Correction Financement dossier de credit", false, false),
    RecreerDossierCredit("Recreer_Dossier_Credit", BatchType.RECREER_DOSSIER_CREDIT.code," Recreer Dossier Credit", false, false),
    SelcectFraisTcpNonRecycles("Selcect_FTCP_Non_Recycles", BatchType.FRAIS_TCP_NON_RECYCLES.code," Selection frais TCP non recyclés", false, false),
    BatchTraitementTransationsFichiers("Traitement_Transations_Fichiers", BatchType.TRAITEMENT_TRANSACTION_FICHIER.code,"Traitement des transactions reçcu par fichiers", false, false),
    BatchFusionCaisse("Fusion_Caisses", BatchType.FUSION_CAISSE.code,"Fusion de caisses, liquidation de bureau et transfert de client", false, true),
    BatchCorrectionBasculeCrediteErreur("Bascule_Credite_Erreur", BatchType.CREDIT_CORRECTION.code,"Correction des crédits basculés en soufrance (GSOUF) par erreur (établissement non concérné)", false, false),
    BatchRattrapageFraisSMS("Rattrapage_FraisSms", BatchType.CREDIT_CORRECTION.code,"Rattrapage de frais SMS pour un mois donné", false, false),
    BatchExtractionBic("Batch_extraction_Bic", BatchType.EXTRACTION_BIC.code, "Extractions BIC", true, false),
    BatchVerifComptesCompensesCaisses("Vérification comptes compenses par caisse(mutuelle)", BatchType.VERIF_COMPTES_COMPENSES.code, "Vérification comptes compenses par caisse(mutuelle)", true, false)


    private BatchList(String id, String type, String description, Boolean defaultStatus, Boolean fermetureBureauObligatoire) {
        this.id = id
        this.type = type
        this.description = description
        this.defaultStatus = defaultStatus
        this.fermetureBureauObligatoire = fermetureBureauObligatoire
    }

    final String id
    final String type
    final String description
    final Boolean defaultStatus
    final Boolean fermetureBureauObligatoire

    static BatchList findById(String id) {
        values().find { it.id == id }
    }

    String getId() {
        return id
    }

    String getDescription() {
        return description
    }

    Boolean getDefaultStatus() {
        return defaultStatus
    }

    @Override
    String toString() {
        return this.id
    }
}
