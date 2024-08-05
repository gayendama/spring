package sn.sensoft.springbatch.utils

class ConstantesMsgTemplate {


    // Liste des messages templates , TODO: à compléter
    enum Template {

        // Messages crédit
        VIREMENT_PERMENANT_INETER_AGENCE("VIREMENT_PERMENANT_INETER_AGENCE", "Virement permanent inter agence: compte débité \${virementPermanent.intituleClient}-\${virementPermanent.compteDebit}, compte crédité \${virementPermanent.intituleClientDebite} \${virementPermanent.compteCredit}, periodicite \${virementPermanent.periodicite}, dateTombee \${virementPermanent.dateProchaineTombee}", "VIREMENT","")

        private Template(String code, String message, String categorie,  String description) {
            this.code = code
            this.message = message
            this.categorie = categorie
            this.description = description
        }

        final String code
        final String message
        final String categorie
        final String description

        static ArrayList<Template> findByCode(String code) {
            values().findAll() { it.code == code }
        }

        static ArrayList<Template> findAll() {
            values().findAll()
        }

        String getCode() {
            return this.code
        }

        String getMessage() {
            return this.message
        }

        String getCategorie() {
            return this.categorie
        }

        String getDescription() {
            return this.description
        }

        @Override
        String toString() {
            return this.message
        }
    }


}

