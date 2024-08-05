package sn.sensoft.springbatch.utils

class ConstantesCommEvenement {


    // Liste des messages templates , TODO: à compléter
    enum Template {

        NOTIF_TRANSFERT_DARGENT_EMIS("NOTIF_TRANSFERT_DARGENT_EMIS", false,"Notification d'un transfert d'argent emis au bénéficiaire"),

        private Template(String code, Boolean indContrepartie, String description) {
            this.code = code
            this.indContrepartie = indContrepartie
            this.description = description
        }

        final String code
        final Boolean indContrepartie
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

        Boolean getIndContrepartie() {
            return this.indContrepartie
        }

        String getDescription() {
            return this.description
        }

        @Override
        String toString() {
            return this.code
        }
    }


}

