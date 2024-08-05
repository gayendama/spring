package sn.sensoft.springbatch

import grails.gorm.transactions.Transactional
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.transaction.annotation.Propagation
import sn.sensoft.springbatch.apiResponse.aicha.SituationAgenceResponse
import sn.sensoft.springbatch.exception.SpringbatchException
import sn.sensoft.springbatch.parametrage.Batch
import sn.sensoft.springbatch.parametrage.ProgrammeBatch
import sn.sensoft.springbatch.securite.LogActivity
import sn.sensoft.springbatch.securite.LogBatch
import sn.sensoft.springbatch.securite.User
import sn.sensoft.springbatch.sql.RequestType
import sn.sensoft.springbatch.utils.*
import springbatch.model.StepExecutionModel
import springbatch.ui.PagedResult

import java.util.Date

class ToolsBatchService {

    def securityUtils
    def runtimeConfigService
    def messageSource
    def springBatchService
    def notificationService
    def grailsApplication
    def toolsBatchService
    def apiManagerService
    def sqlCatalogService


    def dumpDB(String dumpName) throws SpringbatchException {

        return true

    }

    void backupDB(String prefix) throws SpringbatchException {

        log.info(JsonOutput.toJson([method: "backupDB", backupName: prefix]))

        return
    }

    /**
     * Vérifie si on est en période d'inventaire pour archiver automatiquement les dosssiers d'inventaire et refaire la sauvegarde
     * @throws SpringbatchException
     */
    void checkInventaireDossier() throws SpringbatchException {

        log.info(JsonOutput.toJson([method: "checkInventaireDossier", message: "Start"]))

        return
    }

    void journaliser(User user, String evenement, String message, String ipAddressUser, String objectName, String objectId) {
        SituationAgenceResponse situationAgence = apiManagerService.getAichaSituationAgence()

        //Date dateTraitement = DateUtils.dateTraitementBatch(Constantes.CODE_DATE_JOUR)
        Date dateTraitement = Date.parse("yyyy-MM-dd", situationAgence.treatmentDate)
        LogActivity logActivity = new LogActivity()
        logActivity.user = user
        logActivity.codeUtilisateur = (user?.username == null) ? "NON_DEFINI" : user?.username
        logActivity.dateComptable = dateTraitement
        logActivity.typeLog = evenement
        logActivity.message = message
        logActivity.ipAddressUser = ipAddressUser
        logActivity.objectName = objectName
        logActivity.objectId = objectId
        logActivity.userCreate = user

        if (!logActivity.save(flush: true)) {
            throw new SpringbatchException("Erreur sauvegarde Log Activity ${logActivity.errors} ")
        }

    }

    def cleanUpGorm() {
        log.debug("Begin cleanin GORM")

        // sessionFactory.currentSession does not work, may be nod needed in Grails 3
        // https://stackoverflow.com/questions/29400067/grails-3-0-propertyinstancemap-for-a-batch-insert
//        def session = applicationContext.sessionFactory.currentSession
//
//        session.flush()
//        session.clear()
//        propertyInstanceMap.get().clear()
        log.debug("End cleanin GORM")
    }

    // Ajout Sauvegarde des messages
    @Deprecated
    void enregistreMessageBatch(String codeProgramme, String messageBatch) {

        def dateTraitement = DateUtils.dateTraitementBatch(Constantes.CODE_DATE_JOUR)
        enregistreMessageBatch(codeProgramme, messageBatch, dateTraitement)

    }

    // Ajout Sauvegarde des messages
    void enregistreMessageBatch(String codeProgramme, String messageBatch, Date dateTraitement) {
        log.debug("Enregistrement message batch pour : ${codeProgramme}")
        LogBatch logBatch = new LogBatch()
        logBatch.codeProgramme = codeProgramme
        logBatch.message = messageBatch
        logBatch.dateComptable = dateTraitement
        logBatch.dateMessage = new Date()
        if (!logBatch.save(flush: true)) {
            log.error(JsonOutput.toJson([method: "enregistreMessageBatch", message: logBatch.errors.toString()]))
            throw new SpringbatchException("Erreur sauvegarde Log Activity ${logBatch.errors.toString()} ")
        }

        // TODO: pour gérer la diffusion du message au channel batch
        //brokerMessagingTemplate.convertAndSend "/topic/batch", (logBatch as JSON).toString()
    }

    def isEnabled(String codeProgramme) {
        ProgrammeBatch.withNewSession {
            ProgrammeBatch programmeBatch = ProgrammeBatch.findByCodeProgramme(codeProgramme)
            if (programmeBatch != null) {
                if (programmeBatch.batchStepStatus.equals(BatchStepStatus.ACTIVER)) {
                    return true
                }
            }
            else {
                log.warn("Le Code ${codeProgramme} n'a pas de programme batch !!! ")
            }
            return false
        }

    }

    def getEnabledList(PagedResult<StepExecutionModel> stepExecutionList, String jobName) {
        def stepMap = [:]
        List<ProgrammeBatch> programmeBatchList = ProgrammeBatch.findAll()
        for (ProgrammeBatch programmeBatch : programmeBatchList) {
            stepMap.("${programmeBatch.nomProgramme}") = programmeBatch.codeProgramme
        }

        List<StepExecutionModel> stepExecutionEnabledList = new ArrayList<StepExecutionModel>()

        stepExecutionList.each { step ->
            String code = stepMap.get(step.name)
            if (code != null) {
                if (isEnabled(code)) {
                    stepExecutionEnabledList.add(step)
                }
            }
            else {
                log.info("Le batch ${step.name} n'a pas de code spicifié !!!")
            }
        }

        return sortTep(stepExecutionEnabledList, jobName)
    }

    List<StepExecutionModel> sortTep(List<StepExecutionModel> stepExecutionModelList, String jobName){
        List<StepExecutionModel> sortedStepExecutionModelList = new ArrayList<StepExecutionModel>()

        List<ProgrammeBatch> programmeBatchList = ProgrammeBatch.findAllByBatchId(jobName, [sort: "sequenceNumber", order: "asc"])
        programmeBatchList.each {programmeBatch ->
            stepExecutionModelList.each {step ->
                if(step.name == programmeBatch.nomProgramme){
                    step.sequenceNumber = programmeBatch.sequenceNumber
                    sortedStepExecutionModelList.add(step)
                }
            }
        }
        return sortedStepExecutionModelList
    }

    boolean isAlreadyDone(String objetId) {
        CommittedItem committedItem = CommittedItem.findByObjetId(objetId)
        if (committedItem == null) {
            return false
        }
        return true
    }

    def committedItem(CommittedItem committedItem) {
        if (committedItem == null) {
            throw new SpringbatchException("la valeur du committedItem est null")

        }
        committedItem.dateTraitement = new java.util.Date()
        if (!committedItem.save(flush: true)){
            log.error(committedItem.errors.toString())
            log.error(JsonOutput.toJson([method: "committedItem",id: committedItem.objetId, error:committedItem.errors.toString()]))
            throw new SpringbatchException("Erreur lors de l'enregistrement de l'item traité")
        }
    }

    public static String getTempDir() {
        String tempDir = System.getProperty('java.io.tmpdir')  // Ecrire ces fchiers dans le répertoire temporaire
        if (!tempDir.endsWith(File.separator)) {
            tempDir += File.separator
        }
        return tempDir
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    def launchBatch(String jobName, JobParametersBuilder jobParametersBuilder, String ipAddressUser, Integer option = 0){

        SituationAgenceResponse situationAgence = toolsBatchService.getSituationAgence()
        Date dateTraitement = Date.parse('yyyy-MM-dd', situationAgence.treatmentDate)
        jobParametersBuilder.addDate(Constantes.BATCH_JOB_PARAM_NAME_DATE_TRAITEMENT, dateTraitement)

        Boolean forceNewInstance = false

        if (option == Constantes.LANCEMENT_BATCH_OPTION_NOUVELLE_INSTANCE){
            forceNewInstance = true
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_DATE_TIME, (new Date()).format("yyyy-MM-dd hh:mm:ss"))
        }
        else if (option == Constantes.LANCEMENT_BATCH_OPTION_RESTART_INSTANCE){
            forceNewInstance = true
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_DATE_TIME, (new Date()).format("yyyy-MM-dd hh:mm:ss"))

            JsonSlurper jsonSlurper = new JsonSlurper()
            String result = sqlCatalogService.executeRequete(RequestType.SELECT.code, "batch_current_step", [jobName: jobName])
            def jsonResult = jsonSlurper.parseText(result)
            String startingStepName = null
            jsonResult.data.each{step ->
                startingStepName = step.step_name
            }
            ProgrammeBatch programmeBatch = ProgrammeBatch.findByBatchIdAndNomProgramme(jobName, startingStepName)
            ProgrammeBatch.executeUpdate("update ProgrammeBatch set batchStepStatus = :batchStepStatus, indReactiverApresBatch = true where batchId = :jobName and sequenceNumber < :sequence and batchStepStatus = :activeStatus", [batchStepStatus: BatchStepStatus.DESACTIVER, jobName: jobName, sequence: programmeBatch.sequenceNumber, activeStatus: BatchStepStatus.ACTIVER])
        }

        JobParameters jobParams = jobParametersBuilder.toJobParameters()

        String username = jobParams.getString(Constantes.BATCH_JOB_PARAM_NAME_USER)

        User.withNewSession{

            User user = User.findByUsername(username)

            Batch batch = Batch.findByName(jobName)
            if(!batch.indActif){
                throw new SpringbatchException(messageSource.getMessage("batch.message.batchDesactive", [jobName].toArray(), "Le batch ${jobName} n'est pas activé, batch.indActif: false", LocaleContextHolder.locale))
            }

            BatchList batchList = BatchList.findById(jobName)
            def batchTfjStatus = springBatchService.jobStatus(BatchList.BatchTFJ.id)
            if(batchList.fermetureBureauObligatoire){
                if (!situationAgence) {
                    throw new SpringbatchException(messageSource.getMessage("batch.message.bureauOuvert", null, "Veuillez attendre la fermeture des bureaux avant de lancer le batch", LocaleContextHolder.locale))
                }

                if (batchTfjStatus.running && !forceNewInstance) {
                    throw new SpringbatchException(messageSource.getMessage("batch.message.enCours", null, "Un batch est en cours d'éxécution. Veuillez attendre la fin de ce dernier", LocaleContextHolder.locale))
                }
            }

            Date dateCompta = new java.sql.Date(dateTraitement.getTime())

            if(batchTfjStatus.running && !forceNewInstance && [BatchList.BatchTFJ.id, BatchList.BatchRecuperationProvison.id].contains(jobName)){
                throw new SpringbatchException(messageSource.getMessage("batch.message.dejaLancer", null, " Batch déjà exécuté avec succès. Impossible de relancer une nouvelle instance dans la même journée", LocaleContextHolder.locale))
            }

            Map result = springBatchService.launch(jobName, false, jobParams)

            if (!result.success) {
                throw new SpringbatchException(result.message)
            }

            toolsBatchService.journaliser(
                    user,
                    Constantes.LANCEMENT_BATCH,
                    "Lancement batch ${jobName} du ${new Date()}, date comptable ${jobParams.getDate(Constantes.BATCH_JOB_PARAM_NAME_DATE_TRAITEMENT)}",
                    ipAddressUser,
                    "BATCH",
                    jobName
            )

            result.("dateTraitement") = result
            return result
        }
    }

    def notificationBatchStart(
            String typeLancementBatch,
            User user,
            String ipAddress,
            String batch,
            String appVersion,
            Date dateTraitement
    ) {
        String dateSysteme = (new Date())?.format("dd/MM/yyyy HH:mm")
        String mailObjet = "Début du lancement batch ${batch} "

        String contenuMail

        if (typeLancementBatch.equals(Constantes.BATCH_TYPE_LANCEMENT_AUTOMATIQUE)) {
            contenuMail = "<div>Bonjour,</div>" +
                    "<div>&nbsp;</div>" +
                    "<div>Le batch <b>${batch}</b> a été lancé automatiquement. le ${dateSysteme}</div>" +
                    "<div>Date Système :<strong>${dateSysteme}</strong> </div>" +
                    "<div>Date Traitement :<strong>${dateTraitement}</strong> </div>" +
                    "<div>&nbsp;</div>" +
                    "<div>Merci de patienter, vous serez notifié à la fin du batch </div>" +
                    "<div>&nbsp;</div>" +
                    "<div>Cordialement.</div>" +
                    "<div>&nbsp;</div>" +
                    "<div>Aicha Version: <b>${appVersion}</b></div>"
        }
        else {
            contenuMail = "<div>Bonjour,</div>" +
                    "<div>&nbsp;</div>" +
                    "<div>Le batch <b>${batch}</b> a été lancé à partir de ${ipAddress}</div>" +
                    "<div>Batch lancé par : <strong> ${user?.prenom} ${user?.nom} / ${user?.username}</strong> </div>" +
                    "<div>Date Système :<strong>${dateSysteme}</strong> </div>" +
                    "<div>Date Traitement :<strong>${dateTraitement}</strong> </div>" +
                    "<div>&nbsp;</div>" +
                    "<div>Merci de patienter, vous serez notifié à la fin du batch </div>" +
                    "<div>&nbsp;</div>" +
                    "<div>Cordialement.</div>" +
                    "<div>&nbsp;</div>" +
                    "<div>Aicha Version: <b>${appVersion}</b></div>"
        }

        notificationService.sendBatchEmailToAdmin(mailObjet, contenuMail)
    }

    def notificationBatchError(
            String typeLancementBatch,
            User user,
            String ipAddress,
            String programmeBatch,
            String errorMessage,
            String batch,
            String appVersion,
            Date dateTraitement
    ) {
        String dateSysteme = (new Date())?.format("dd/MM/yyyy HH:mm")
        String mailObjet = "Erreur batch  ${batch} sur le programme ${programmeBatch}"

        String contenuMail

        if (typeLancementBatch.equals(Constantes.BATCH_TYPE_LANCEMENT_AUTOMATIQUE)) {
            contenuMail = "<div>Bonjour,</div>" +
                    "<div>&nbsp;</div>" +
                    "<div>Le batch <b>${batch}</b> s'est arrêté de manière prématurée à l'étape : <strong>${programmeBatch}</strong></div>" +
                    "<div>Date Système :<strong>${dateSysteme}</strong> </div>" +
                    "<div>Date Traitement :<strong>${dateTraitement}</strong> </div>" +
                    "<div>&nbsp;</div>" +
                    "<div>Cause : <strong>${errorMessage}</strong> </div>" +
                    "<div>&nbsp;</div>" +
                    "<div>Veuillez contacter votre administrateur. </div>" +
                    "<div>&nbsp;</div>" +
                    "<div>Cordialement.</div>" +
                    "<div>&nbsp;</div>" +
                    "<div>Aicha Version: <b>${appVersion}</b></div>"
        }
        else {
            contenuMail = "<div>Bonjour,</div>" +
                    "<div>&nbsp;</div>" +
                    "<div>Le batch <b>${batch}</b> s'est arrêté de manière prématurée à l'étape : <strong>${programmeBatch}</strong></div>" +
                    "<div>Batch lancé par : <strong>${user?.prenom} ${user?.nom} / ${user?.username}</strong> </div>" +
                    "<div>Date Système :<strong>${dateSysteme}</strong> </div>" +
                    "<div>Date Traitement :<strong>${dateTraitement}</strong> </div>" +
                    "<div>&nbsp;</div>" +
                    "<div>Cause : <strong>${errorMessage}</strong> </div>" +
                    "<div>&nbsp;</div>" +
                    "<div>Veuillez contacter votre administrateur. </div>" +
                    "<div>&nbsp;</div>" +
                    "<div>Cordialement.</div>" +
                    "<div>&nbsp;</div>" +
                    "<div>Aicha Version: <b${appVersion}</b></div>"
        }

        notificationService.sendBatchEmailToAdmin(mailObjet, contenuMail)
    }

    def notificationBatchSucces(
            String typeLancementBatch,
            User user,
            String ipAddress,
            String batch,
            String appVersion,
            Date dateTraitement
    ) {
        String dateSysteme = (new Date())?.format("dd/MM/yyyy HH:mm")
        String mailObjet = "Succés lancement batch ${batch} "

        String contenuMail

        if (typeLancementBatch.equals(Constantes.BATCH_TYPE_LANCEMENT_AUTOMATIQUE)) {
            contenuMail = "<div>Bonjour,</div>" +
                    "<div>&nbsp;</div>" +
                    "<div>Le batch <b>${batch}</b> s'est déroulé avec succès</div>" +
                    "<div>Batch lancé <strong>automatiquement</strong> </div>" +
                    "<div>Date Système :<strong>${dateSysteme}</strong> </div>" +
                    "<div>Date Traitement :<strong>${dateTraitement}</strong> </div>" +
                    "<div>&nbsp;</div>" +
                    "<div>La journée comptable suivante peut être réouverte </div>" +
                    "<div>&nbsp;</div>" +
                    "<div>Cordialement.</div>" +
                    "<div>&nbsp;</div>" +
                    "<div>Aicha Version: <b>${appVersion}</b></div>"
        }
        else {
            contenuMail = "<div>Bonjour,</div>" +
                    "<div>&nbsp;</div>" +
                    "<div>Le batch <b>${batch}</b> s'est déroulé avec succès</div>" +
                    "<div>Batch lancé par : <strong>${user?.prenom} ${user?.nom} / ${user?.username}</strong> </div>" +
                    "<div>Date Système :<strong>${dateSysteme}</strong> </div>" +
                    "<div>Date Traitement :<strong>${dateTraitement}</strong> </div>" +
                    "<div>&nbsp;</div>" +
                    "<div>La journée comptable suivante peut être réouverte </div>" +
                    "<div>&nbsp;</div>" +
                    "<div>Cordialement.</div>" +
                    "<div>&nbsp;</div>" +
                    "<div>Aicha Version: <b>${appVersion}</b></div>"
        }

        notificationService.sendBatchEmailToAdmin(mailObjet, contenuMail)
    }

    def sendBatchnotification(String status, JobParameters jobParameters, String programmeBatch, String message, String batch) {
        String typeLancement = jobParameters.getString(Constantes.BATCH_JOB_PARAM_NAME_TYPE_LANCEMENT)
        String username = jobParameters.getString(Constantes.BATCH_JOB_PARAM_NAME_USER)
        String remoteAddress = jobParameters.getString(Constantes.BATCH_JOB_PARAM_NAME_REMOTE_ADDRESS)
        String appVersion = jobParameters.getString(Constantes.BATCH_JOB_PARAM_APP_VERSION)
        Date dateTraitement = jobParameters.getDate(Constantes.BATCH_JOB_PARAM_NAME_DATE_TRAITEMENT)

        User.withNewSession {

            User user = null
            if (typeLancement.equals(Constantes.BATCH_TYPE_LANCEMENT_MANUEL)) {
                user = User.findByUsername(username)
            }

            if(status == BatchStatus.STARTED.name()){
                notificationBatchStart(typeLancement, user, remoteAddress, batch, appVersion, dateTraitement)
            }
            else if(status == BatchStatus.COMPLETED.name()){
                notificationBatchSucces(typeLancement, user, remoteAddress, batch, appVersion, dateTraitement)
            }
            else if(status == BatchStatus.FAILED.name()){
                notificationBatchError(typeLancement, user, remoteAddress, programmeBatch, message, batch, appVersion, dateTraitement)
            }
        }
    }

    SituationAgenceResponse getSituationAgence(Boolean verifierOuvertureBureau = false){
        SituationAgenceResponse situationAgence = apiManagerService.getAichaSituationAgence()
        if(situationAgence.treatmentDate == null){
            throw new SpringbatchException("Impossible de recupérer la date de traitement. Veuillez vérifier votre connexion internet ou vérifier si le sereur est disponible")
        }

        if(verifierOuvertureBureau && situationAgence.openAgency){
            throw new SpringbatchException("Veuillez Fermer le bureau avant de lancer le BATCH du traitement de fin de journée")
        }

        return situationAgence
    }


}
