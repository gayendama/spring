package sn.sensoft.springbatch.utils

class Constantes {


    // CODE DATES
    static final String CODE_DATE_JOUR = '1'
    static final String CODE_DATE_VEILLE = '2'
    static final String CODE_DATE_LENDEMAIN = '3'

    // TYPE DE DONNEES PARAMATRES
    public static final String DATATYPE_STRING = "STRING"
    public static final String DATATYPE_NUMBER = "NUMBER"
    public static final String DATATYPE_INTEGER = "INTEGER"
    public static final String DATATYPE_DATE = "DATE"
    public static final String DATATYPE_BOOLEAN = "BOOLEAN"
    public static final String DATATYPE_OPTION = "OPTION"

    /**
     * CODES PROGRAMMES BATCH
     */
    static final String BATCH_DUMP_DB_BEFORE = 'SAUVBA'
    static final String BATCH_DUMP_DB_AFTER = 'SAUVB'
    static final String BATCH_UPDATE_ACCOUNTS_BALANCE = 'MAJCPT'
    static final String BATCH_ARCHIVAGE_DOSSIER = 'ARCHIV'

    /**
     * DATE FORMATS
     */
    static final String SQL_DATE_FORMAT = 'yyyy-MM-dd HH:mm:ss'


    // STATUT GENERIQUES
    public static final String STATUT_EMIS = "EM"
    public static final String STATUT_RECU = "RC"
    public static final String STATUT_PAYE = "PA"
    public static final String STATUT_IMPAYE = "IM"
    public static final String STATUT_AMORTI = "AM"
    public static final String STATUT_NON_SAISI = "NS"
    public static final String STATUT_EN_COURS = "EC"
    public static final String STATUT_CHARGE = "CH"
    public static final String STATUT_TRAITE = "TR"
    public static final String STATUT_VALIDE = "VA"
    public static final String STATUT_FORCE = "FO"
    public static final String STATUT_SYNCHRONISE = "SY"
    public static final String STATUT_VALIDE_EN_ATTENTE_DE_TRAITEMENT = "VT"
    public static final String STATUT_ANNULE = "AN"
    public static final String STATUT_LEVEE = "LE" // Pour la garanties par exemple
    public static final String STATUT_RETARD = "RT"
    public static final String STATUT_RESILIE = "RS"
    public static final String STATUT_CLOTURE = "CL"
    public static final String STATUT_INSTANCE_CLOTURE = "IC"
    public static final String STATUT_SAISI = "SA"
    public static final String STATUT_COMPTABILISE = "CP"
    public static final String STATUT_REJETE = "RJ"
    public static final String STATUT_VALIDE_PARTIELLEMENT = "VP"
    public static final String STATUT_SOUFFRANCE = "SO"
    public static final String STATUT_PRET_CONTENTIEUX = "PX"
    public static final String STATUT_CONTENTIEUX = "CX"
    public static final String STATUT_OUVERT = "O"
    public static final String STATUT_FERME = "F"
    public static final String STATUT_OK = "OK"
    public static final String STATUT_NOT_OK = "NOK"
    public static final String STATUT_REGLE = "RG"
    public static final String STATUT_REPORTE = "RP"
    public static final String STATUT_ERREUR_CREDIT_REMBOURSEMENT = "CR"
    public static final String STATUT_SUCCESS = "SUCCESS"
    public static final String STATUT_FAILED = "FAILED"
    public static final String STATUT_STARTED = "STARTED"
    public static final String STATUT_COMPLETED = "COMPLETED"

    public static final String STATUT_SEND_APICOM_OK = "OK"

    // RestBuilder params
    public static int CONNECTION_TIME_OUT = 30000
    public static int READ_TIME_OUT = 80000

    // TYPE MESSAGE
    public static final String MESSAGE_SMS = "SMS"
    public static final String MESSAGE_MAIL = "MAIL"
    public static final String MESSAGE_SMS_MAIL = "SMS_MAIL"
    public static final String MESSAGE_SOURCE = "AICHA"

    public static final CODE_BUREAU_CENTRAL = "999"
    public static final CODE_MUTUELLE_CENTRALE = "Bureau Central"

    //Batch  type Lacemennt
    public static final String BATCH_TYPE_LANCEMENT_AUTOMATIQUE = "AUTOMATIQUE"
    public static final String BATCH_TYPE_LANCEMENT_MANUEL = "MANUEL"

    //Batch Job parameters name (avant lancement)
    public static final String BATCH_JOB_PARAM_NAME_DATE_TRAITEMENT = "dateTraitement"
    public static final String BATCH_JOB_PARAM_NAME_TYPE_LANCEMENT = "typeLancement"
    public static final String BATCH_JOB_PARAM_NAME_USER = "username"
    public static final String BATCH_JOB_PARAM_NAME_USER_EMAIL = "email"
    public static final String BATCH_JOB_PARAM_NAME_REMOTE_ADDRESS = "remoteAddress"
    public static final String BATCH_JOB_PARAM_NAME_DATE_ARRETE_ID = "dateArreteId"
    public static final String BATCH_JOB_PARAM_NAME_MUTUELLE_ID = "mutuelleId"
    public static final String BATCH_JOB_PARAM_NAME_DATE_ARRETE = "dateArrete"
    public static final String BATCH_JOB_PARAM_NAME_BUREAU_ID = "bureauId"
    public static final String BATCH_JOB_PARAM_NAME_ANNEE = "annee"
    public static final String BATCH_JOB_PARAM_NAME_COMPTE_ID= "compteId"
    public static final String BATCH_JOB_PARAM_NAME_COMPTE_ID_CIBLLE= "compteCible"
    public static final String BATCH_JOB_PARAM_NAME_DATE_DEBUT= "dateDebut"
    public static final String BATCH_JOB_PARAM_NAME_DATE_FIN= "dateFin"
    public static final String BATCH_JOB_PARAM_DATE_TIME= "dateTime"
    public static final String BATCH_JOB_PARAM_CODE_BUREAU= "codeBureau"
    public static final String BATCH_JOB_PARAM_APP_VERSION= "appVersion"
    public static final String BATCH_JOB_PARAM_APP_CONTEXT = "appContext"


    // CODE DATE TRAITEMENT
    public static final String DATE_TRAITEMENT_JOUR = "JOUR"
    public static final String DATE_TRAITEMENT_LENDEMAIN = "LENDEMAIN"
    public static final String DATE_TRAITEMENT_MOIS = "MOIS"
    public static final String DATE_TRAITEMENT_VEILLE = "VEILLE"

    // PERIODICITE
    public static final String PERIODICITE_JOURNALIERE = "J"
    public static final String PERIODICITE_HEBDOMADAIRE = "H"
    public static final String PERIODICITE_BIMENSUELLE = "BI"  //chaque 15 jours
    public static final String PERIODICITE_MENSUELLE = "M"
    public static final String PERIODICITE_BIMESTRIELLE = "B"  //chaque 2 mois
    public static final String PERIODICITE_TRIMESTRIELLE = "T"
    public static final String PERIODICITE_SEMESTRIELLE = "S"
    public static final String PERIODICITE_ANNUELLE = "A"
    public static final String PERIODICITE_SPOT = "SP"
    public static final String PERIODICITE_A_TERME = "Z"
    public static final String PERIODICITE_AUTRE = "AT"
    public static final String PERIODICITE_CINQ_MOIS = "C"
    public static final String PERIODICITE_QUADRIMESTRIELLE = "Q"

    // API...
    static final String APP_PARTNER_AICHA = "SELATIS"
    static final String APP_PARTNER_APICOM = "APICOM"
    static final String APP_PARTNER_DEFAULT_LOGIN_URI = "api/login"
    static final String APP_PARTNER_DEFAULT_REFRESH_TOKEN_URI = "oauth/access_token"

    //Batch
    public static final String LANCEMENT_BATCH = "LANCEMENT_BATCH"

    // PROPRIETE IMAGE
    public static final Integer MAX_FILE_UPLOAD = 500000
    public static final Integer MAX_FILE_EXCEL_UPLOAD = 10000000000
    public static final String[] TYPE_IMAGE = ["image/jpg", "image/jpeg", "image/png", "image/tiff", "image/vnd.microsoft.icon"]

    // PROPRIETE FICHIER EXCEL
    public static final String[] TYPE_EXCEL = ["text/csv", "application/vnd.ms-excel", "application/msexcel", "application/x-msexcel", "application/x-ms-excel", "application/x-excel", "application/x-dos_ms_excel", "application/xls", "application/x-xls", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"]
    public static final String CSV_EXTENSION = "csv"
    public static final String MD5_EXTENSION = "md5"
    public static final String EXTOURNE_FILE_NAME = "extourneOperation.csv"

    public static final Integer LANCEMENT_BATCH_OPTION_COMPLETED_STATUS = 1
    public static final Integer LANCEMENT_BATCH_OPTION_NOUVELLE_INSTANCE = 2
    public static final Integer LANCEMENT_BATCH_OPTION_RESTART_INSTANCE = 3

}

