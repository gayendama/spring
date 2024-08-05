package sn.sensoft.springbatch.utils

class ConstantesSecurite {

    // Liste des programmes batch, TODO: à compléter
    enum Event {

        UPDATE_REQUEST_MAP("UPDATE_REQUEST_MAP", "Mise à jour habiliations d'un rôle", ),
        UPDATE_USER("UPDATE_USER", "Mise à jour d'un utilisateur", ),
        DELETE_USER_BUREAU("DELETE_USER_BUREAU", "Retrait utilisateur d'un bureau"),
        ADD_USER_BUREAU("ADD_USER_BUREAU", "Ajout utilisateur à un bureau"),
        DELETE_USER_CLIENT("DELETE_USER_CLIENT", "Retrait code client à un utilisateur"),
        ADD_USER_CLIENT("ADD_USER_CLIENT", "Ajout code client à un utilisateur"),
        ACTIVATE_USER("ACTIVATE_USER", "Activation utilisateur"),
        UPDATE_USER_FROM_KEYCLOAK("UPDATE_USER_FROM_KEYCLOAK", "Mise à jour d'un utilisateur à partir de keycloak"),
        NEW_USER_FROM_KEYCLOAK("NEW_USER_FROM_KEYCLOAK", "Nouvel utilisateur à partir de keycloak"),
        UPDATE_USER_ROLE_FROM_KEYCLOAK("UPDATE_USER_ROLE_FROM_KEYCLOAK", "Nouveaux rôles ajoutés à l'utilisateur à partir de keycloak"),
        ACTIVATION_SEQUENCE_BATCH("ACTIVATION_SEQUENCE_BATCH", "Activation séquence batch"),
        DESACTIVATION_SEQUENCE_BATCH("DESACTIVATION_SEQUENCE_BATCH", "Désactivation séquence batch"),
        FORCAGE_BATCH_COMPLET("FORCAGE_BATCH_COMPLET", "Forçage status du batch à complet"),
        FORCAGE_NEW_INSTANCE_MEME_JOURNEE("FORCAGE_NEW_INSTANCE_MEME_JOURNEE", "Forçage lancement d'une nouvelle instance dans la même journée"),
        FORCAGE_NEW_INSTANCE_DERIERE_SEQUENCE("FORCAGE_NEW_INSTANCE_DERIERE_SEQUENCE", "Forçage lancement d'une nouvelle instance en commençant par la dernière séquence en cours")

        private Event(String code, String libelle) {
            this.code = code
            this.libelle = libelle
        }
        final String code
        final String libelle

        static ArrayList<Event> findByCode(String code) {
            values().findAll() { it.code == code }
        }

        static ArrayList<Event> findAll() {
            values().findAll()
        }

        String getCode() {
            return this.code
        }

        String getLibelle() {
            return this.libelle
        }

        @Override
        String toString() {
            return this.libelle
        }
    }

}
