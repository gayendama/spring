package sn.sensoft.springbatch.utils

public enum BatchType{
    TRAITEMENT_FIN_JOURNEE("TRAITEMENT_FIN_JOURNEE", "Traitement fin journée", 0),
    RECUPERATION_PROVISION("RECUPERATION_PROVISION", "Récupération provision", 1),
    CALCUL_DATE_ARRETE("CALCUL_DATE_ARRETE", "Calcul date arrêté", 2),
    IMMOBILISATION("IMMOBILISATION", "Immobilisation", 3),
    PROVISION_PRET("PROVISION_PRET", "Calcul provision prêt", 4),
    POST_BUDGETAIRE("POST_BUDGETAIRE", "Calcul Poste Budgetaire", 5),
    INTERET_BLOQUE("INTERET_BLOQUE", "Calcul interêt bloqué", 6),
    REPRISE_V3("REPRISE_V3", "Reprise aicha v3", 7),
    REPRISE_CREDIT("REPRISE_CREDIT", "Reprise crédit", 8),
    REPRISE_ECHEANCE("REPRISE_ECHEANCE", "Reprise des échéances", 9),
    CORR_REPRISE_FINANCEMENT("CORR_REPRISE_FINANCEMENT", "Correction finanncement reprise crédit", 10),
    EXTOURNE_OPERATION_FROM_FILE("EXTOURNE_OPERATION_FROM_FILE", "Extourner des opérations à partir d'un fichier csv", 11),
    INITIALISER_DOSSIERS_CREDITS("INITIALISER_DOSSIERS_CREDITS", "Initialiser une liste de dossiers de crédits", 12),
    CORRECTION_CREDITS_TA("CORRECTION_CREDITS_TA", "Corrections crédits TA", 13),
    CORRECTION_FINANCEMENT("CORRECTION_FINANCEMENT", "Corrections Financement", 14),
    RECREER_DOSSIER_CREDIT("RECREER_DOSSIER_CREDIT", "Recréer dossier de crédit", 15),
    FRAIS_TCP_NON_RECYCLES("FRAIS_TCP_NON_RECYCLES", "Frais tenu de comppte non recyclés", 16),
    TRAITEMENT_TRANSACTION_FICHIER("TRAITEMENT_TRANSACTION_FICHIER", "Traitement des transactions reçcu par fichiers", 17),
    FUSION_CAISSE("FUSION_CAISSE", "Fusion de caisses, liquidation de bureau et transfert de client", 18),
    CREDIT_CORRECTION("CREDIT_CORRECTION", "Séquence de Correction crédit", 19),
    EXTRACTION_BIC("EXTRACTION_BIC", "Extractions BIC", 20),
    VERIF_COMPTES_COMPENSES("VERIF_COMPTES_COMPENSES", "Vérification comptes compenses caisses", 21)




    private BatchType(String code, String libelle, Integer ordre) {
        this.code = code
        this.libelle = libelle
        this.ordre = ordre
    }

    final String code
    final String libelle
    final Integer ordre

    static BatchType findByCode(String code) {
        values().find { it.code == code }
    }

    static BatchType findByOrder(Integer order) {
        values().find { it.ordre == order }
    }

    String getCode() {
        return code
    }

    String getLibelle() {
        return libelle
    }

    @Override
    String toString() {
        return this.libelle
    }

    static List<BatchType> getBatchTypeInOrder(List<BatchType> batchTypeList){

        List<BatchType> batchTypes = new ArrayList<BatchType>()
        List<Integer>orders = new ArrayList<Integer>()

        for (BatchType batchType: batchTypeList){
            orders.add(batchType.ordre)
        }
        orders.sort()

        for (int i=0; i <orders.size(); i++){
            batchTypes.add(BatchType.findByOrder(orders.get(i)))
        }

        return batchTypes
    }
}
