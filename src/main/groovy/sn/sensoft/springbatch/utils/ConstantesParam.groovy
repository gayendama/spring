package sn.sensoft.springbatch.utils

class ConstantesParam {

    // Liste des paramètres , TODO: à compléter
    enum Param {
        // Param généraux
        MAIL_NOTIF_BATCH("MAIL_NOTIF_BATCH", "Adresse email de notification évènements batch", Constantes.DATATYPE_STRING, "GENERAL", false),
        MAIL_NOTIF_SEC("MAIL_NOTIF_SEC", "Adresse email de notification évènements sécurité", Constantes.DATATYPE_STRING, "GENERAL", false),
        INDICATIF_TELEPHONE("INDICATIF_TELEPHONE", "Indicatif par défaut téléphone", Constantes.DATATYPE_STRING, "GENERAL", false),

        // Param communication
        COMM_SENDER_EMAIL("COMM_SENDER_EMAIL", "L'adresse Email de l'envoyeur des meessages ", Constantes.DATATYPE_STRING, "COMM", false),

        //Param kafka
        KAFKA_USE_KAFKA_CHANNEL("KAFKA_USE_KAFKA_CHANNEL", "Indicateur pour savoir si oui ou non le canal Kafka est ouvert", Constantes.DATATYPE_BOOLEAN, "KAFKA", false),
        KAFKA_USE_SECURITY("KAFKA_USE_SECURITY", "Indicateur pour savoir si oui ou non on utilise la can sécurisé SASL_SSL", Constantes.DATATYPE_BOOLEAN, "KAFKA", false),
        KAFKA_TOPIC_NAME("KAFKA_TOPIC_NAME", "Topic name for messages sent to apicomm", Constantes.DATATYPE_STRING, "KAFKA", false),
        KAFKA_NOTIF_TOPIC_NAME("KAFKA_NOTIF_TOPIC_NAME", "Topic name for sending apicomm notification", Constantes.DATATYPE_STRING, "KAFKA", false),
        KAFKA_BOOTSTRAP_SERVERS("KAFKA_BOOTSTRAP_SERVERS", "Kafka Host", Constantes.DATATYPE_STRING, "KAFKA", false),
        KAFKA_BOOTSTRAP_PORT("KAFKA_BOOTSTRAP_PORT", "Kafka port", Constantes.DATATYPE_INTEGER, "KAFKA", false),
        KAFKA_TRUSTSTORE_LOCATION("KAFKA_TRUSTSTORE_LOCATION", "Kafka truststore location", Constantes.DATATYPE_STRING, "KAFKA", false),
        KAFKA_TRUSTSTORE_PASSWORD("KAFKA_TRUSTSTORE_PASSWORD", "Kafka truststore location", Constantes.DATATYPE_STRING, "KAFKA", true),
        KAFKA_KEYSTORE_LOCATION("KAFKA_KEYSTORE_LOCATION", "Kafka keystore location", Constantes.DATATYPE_STRING, "KAFKA", false),
        KAFKA_KEYSTORE_PASSWORD("KAFKA_KEYSTORE_PASSWORD", "Kafka keystore password", Constantes.DATATYPE_STRING, "KAFKA", true),
        KAFKA_JAAS_CONSUMER_LOGIN_FILE("KAFKA_JAAS_CONSUMER_LOGIN_FILE", "Kafka Java Authentication and Authorization Service File location for Consumer", Constantes.DATATYPE_STRING, "KAFKA", false),
        KAFKA_JAAS_PRODUCER_LOGIN_FILE("KAFKA_JAAS_PRODUCER_LOGIN_FILE", "Kafka Java Authentication and Authorization Service File location for Producer", Constantes.DATATYPE_STRING, "KAFKA", false),

        // BATCH
        LANCEMENT_AUTOMATIQUE_BATCH_TFJ("LANCEMENT_AUTOMATIQUE_BATCH_TFJ", "Activation ou désactivation du lancement automatique du batch TFJ", Constantes.DATATYPE_BOOLEAN, "BATCH", false),
        LANCEMENT_AUTOMATIQUE_BATCH_RECUPERATION_PROVISION("LANCEMENT_AUTOMATIQUE_BATCH_RECUPERATION_PROVISION", "Activation ou désactivation du lancement automatique du batch récupération provision", Constantes.DATATYPE_BOOLEAN, "BATCH", false),
        FERMETURE_AUTOMATIQUE_DES_BUREAUX("FERMETURE_AUTOMATIQUE_DES_BUREAUX", "Fermeture automatique des bureaux", Constantes.DATATYPE_BOOLEAN, "BATCH", false),

        private Param(String code, String libelle, String dataType, String contexte, Boolean isValueEncrypted) {
            this.code = code
            this.libelle = libelle
            this.dataType = dataType
            this.contexte = contexte
            this.isValueEncrypted = isValueEncrypted
        }
        
        final String code
        final String libelle
        final String dataType
        final String contexte
        final Boolean isValueEncrypted

        static ArrayList<Param> findByCode(String code) {
            values().findAll() { it.code == code }
        }

        static ArrayList<Param> findAll() {
            values().findAll()
        }

        String getCode() {
            return this.code
        }

        String getLibelle() {
            return this.libelle
        }

        String getContexte() {
            return this.contexte
        }

        String getDataType() {
            return this.dataType
        }

        Boolean getIsValueEncrypted() {
            return this.isValueEncrypted
        }

        @Override
        String toString() {
            return this.libelle
        }
    }

    enum ModeCalculRembAnticipe {

        NORMAL("NORMAL", "Normal"),
        INTERET_SUR_LA_PREMIERE_ECHEANCE("INTERET_SUR_LA_PREMIERE_ECHEANCE", "Intérêt sur la première échéance"),
        INTERET_SUR_LES_TROIS_PREMIERES_ECHEANCES("INTERET_SUR_LES_TROIS_PREMIERES_ECHEANCES", "Intérêt sur les trois premières échéances")


        final String code
        final String libelle

        ModeCalculRembAnticipe(String code, String libelle) {
            this.code = code
            this.libelle = libelle
        }

        String getCode() {
            return code
        }

        String getLibelle() {
            return libelle
        }

        static ModeCalculRembAnticipe findByCode(String code) {
            values().findAll() { it.code == code }
        }

        static ArrayList<ModeCalculRembAnticipe> findAll() {
            values().findAll()
        }

        static Map getMapEnum() {
            def map = [:]
            def list = values().findAll()

            for (item in list){
                map.put(item.code, item.libelle)
            }

            return map
        }

        @Override
        String toString() {
            return this.libelle
        }
    }

}

