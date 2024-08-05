package sn.sensoft.springbatch

import grails.converters.JSON
import groovy.json.JsonOutput
import io.micronaut.http.client.exceptions.HttpClientResponseException
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException
import sn.sensoft.springbatch.Monitoring.MonitoringBatchTFJ
import sn.sensoft.springbatch.apiResponse.aicha.SituationAgenceResponse
import sn.sensoft.springbatch.dto.EvenementSecuriteDTO
import sn.sensoft.springbatch.exception.SpringbatchException
import sn.sensoft.springbatch.parametrage.Batch
import sn.sensoft.springbatch.parametrage.ProgrammeBatch
import sn.sensoft.springbatch.securite.LogBatch
import sn.sensoft.springbatch.securite.User
import sn.sensoft.springbatch.utils.*
import springbatch.model.JobModel
import springbatch.ui.PagedResult

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeParseException

class BatchController {

    def springBatchUiService
    def springBatchService
    def toolsBatchService
    def apiManagerService
    def sqlCatalogService
    def notificationService
    // Scheduler quartzScheduler

    //static allowedMethods = [launchCalculSoldeArrete: "POST"]

    static String MODULE_NAME = "batch"

    static defaultAction = 'list'

    def index() {
        List<Batch> batchList = Batch.findAllByDeleted(false, [sort: "name", order: "asc"])
        [batchList: batchList]
    }

    def status() {
        if (!params.job) {
            render([success: false, message: 'No job submitted for status check']) as JSON
            return
        }

        Map statuses = [:]

        if (params.job instanceof String) {
            String job = params.job
            statuses.put(job, springBatchService.jobStatus(job))
        }
        else if (params.job instanceof String[]) {
            params.job.each { String job ->
                statuses.put(job, springBatchService.jobStatus(job))
            }
        }
        else {
            render([success: false, message: "Class: ${params.job.class.name}"])
            return
        }

        render([success: true, data: statuses])
    }

    def list() {

        try {
            SituationAgenceResponse situationAgence = apiManagerService.getAichaSituationAgence()
            Boolean currentlyRunning = false
            HashMap<Integer, PagedResult<JobModel>> modelInstancesGroup = new HashMap<Integer, PagedResult<JobModel>>()
            PagedResult<JobModel> jobModels = springBatchUiService.getJobModels(params)

            List<BatchType> batchTypes = BatchType.getBatchTypeInOrder(BatchType.findAll())
            batchTypes.each { BatchType type ->

                List<JobModel> newJobModels = new ArrayList<JobModel>()
                jobModels.each { jobModel ->


                    Batch batch = Batch.findByNameAndBatchTypeAndIndActif(jobModel.name, type.code, true)

                    if (batch != null) {
                        newJobModels.add(jobModel)
                    }
                    if (jobModel.currentlyRunning){
                        currentlyRunning = true
                    }
                }

                if (newJobModels.size() > 0) {
                    PagedResult<JobModel> jobModelsActif = new PagedResult<JobModel>(resultsTotalCount: newJobModels.size(), results: newJobModels)
                    modelInstancesGroup.put(type.ordre, jobModelsActif)
                }
            }

            [modelInstancesGroup: modelInstancesGroup, ready: springBatchService.ready, bureauCentrale: null, situationAgence: situationAgence, currentlyRunning: currentlyRunning]
        }
        catch (IllegalArgumentException e) {
            log.error(e.getMessage())
            flash.error = e.message
            redirect(action: 'index')
        }
        catch (HttpClientResponseException e) {
            log.error(e.getMessage())
            flash.error = e.getMessage()
            redirect(action: 'index')
        }
    }

    def journal() {
    }

    def show(String id) {
        if (id) {
            [job              : springBatchUiService.jobModel(id),
             jobModelInstances: springBatchUiService.getJobInstanceModels(id, params)]
        }
        else {
            flash.error = "Please supply a job name"
            redirect(controller: "springBatchJob", action: "list")
        }
    }


    String dateComptablePlusHeure(dateComptableStr) {
        if (dateComptableStr == null || dateComptableStr.isEmpty()) {
            throw new IllegalArgumentException("La date comptable ne peut pas être nulle ou vide.")
        }

        Instant dateComptableInstant
        try {
            dateComptableInstant = Instant.parse(dateComptableStr)
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Le format de la date comptable est invalide : " + dateComptableStr, e)
        }

        LocalDateTime maintenant = LocalDateTime.now()
        LocalDateTime dateAvecHeureActuelle = LocalDateTime.ofInstant(dateComptableInstant, ZoneId.systemDefault())

        dateAvecHeureActuelle = dateAvecHeureActuelle.withHour(maintenant.getHour())
        dateAvecHeureActuelle = dateAvecHeureActuelle.withMinute(maintenant.getMinute())
        dateAvecHeureActuelle = dateAvecHeureActuelle.withSecond(maintenant.getSecond())
        dateAvecHeureActuelle = dateAvecHeureActuelle.withNano(maintenant.getNano())
        Date dateComptablePlusHeure = Date.from(dateAvecHeureActuelle.atZone(ZoneId.systemDefault()).toInstant())
        String dateComptablePlusHeureFormated = formatDate(dateComptablePlusHeure)
        dateComptablePlusHeureFormated
    }

    def formatDate(date) {
        def dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
        return dateFormat.format(date)
    }

    def calculateTimeDifference(def dateCompableParam, def mostRecentJobExecution) {
        if (dateCompableParam == null || mostRecentJobExecution == null) {
            throw new IllegalArgumentException("Les dates ne peuvent pas être nulles.")
        }

        def dateCompableFormated = formatDate(dateCompableParam)
        def mostRecentJobExecutionFormated = formatDate(mostRecentJobExecution)

        def dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")

        def dateCompableParsed = dateFormat.parse(dateCompableFormated)
        def mostRecentJobExecutionParsed = dateFormat.parse(mostRecentJobExecutionFormated)

        def differenceInMillis = Math.abs(mostRecentJobExecutionParsed.time - dateCompableParsed.time)

        return differenceInMillis / (1000 * 60 * 60 * 24) // Retourne la différence en jours
    }


    def launch(String id) {
        try {
            Date mostRecentJobExecution
            def dateComptableStr = params.dateTraitement
            Date dateComptablePlusHeureToDate
            if(dateComptableStr){
                String dateComptablePlusHeure = dateComptablePlusHeure(dateComptableStr)
                dateComptablePlusHeureToDate = Date.parse("yyyy-MM-dd HH:mm:ss.SSS", dateComptablePlusHeure)
            }
            MonitoringBatchTFJ monitoringBatch = MonitoringBatchTFJ.findByIdBatch(id)
            // si la première je crée le monitoring
            if (!monitoringBatch) {
                monitoringBatch = new MonitoringBatchTFJ(
                        idBatch: id,
                        batchlaunched : false,
                        lastExcecutionDate: dateComptablePlusHeureToDate
                )
                if (!monitoringBatch.save(flush: true)) {
                    monitoringBatch.errors.allErrors.each { println it }
                    throw new SpringbatchException("un problème est survenu lors de l'enregistrement de MonitoringBatchTFJ")
                }
            }else {
                if(monitoringBatch.lastExcecutionDate != null && dateComptablePlusHeureToDate != null ){
                    def difference = calculateTimeDifference(dateComptablePlusHeureToDate,monitoringBatch.lastExcecutionDate)
                    if (difference < 1) {
                        throw new SpringbatchException("La procédure TRAITEMENT FIN JOURNÉE ne peut être exécutée qu'une fois par journée Comptable.")
                    }
                }
                monitoringBatch.lastExcecutionDate = dateComptablePlusHeureToDate
                if (!monitoringBatch.merge(flush: true)) {
                    monitoringBatch.errors.allErrors.each { println it }
                    throw new SpringbatchException("un problème est survenu lors de la modification de  MonitoringBatchTFJ")
                }
            }

            User currentUser = session.currentUser
            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_TYPE_LANCEMENT, Constantes.BATCH_TYPE_LANCEMENT_MANUEL)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_USER, currentUser?.username)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_REMOTE_ADDRESS, request.remoteAddr)

            Integer option = params.option != null ? params.option as Integer : 0

            EvenementSecuriteDTO evenementSecuriteDTO = new EvenementSecuriteDTO()
            evenementSecuriteDTO.username = currentUser?.username
            evenementSecuriteDTO.ipAddress = params.remoteAddr

            if (option == Constantes.LANCEMENT_BATCH_OPTION_NOUVELLE_INSTANCE) {
                ItemToProcess.deleteAll()
                CommittedItem.deleteAll()
                sqlCatalogService.executeScript('init_batch_status_script', [status: ConstantesBatch.BATCH_JOB_INSTANCE_COMPLETED_STATUS, instanceStatus: ConstantesBatch.BATCH_JOB_INSTANCE_SUCCESS_STATUS, jobExecutionId: params.jobExecutionId as Integer])
                toolsBatchService.journaliser(
                        currentUser,
                        Constantes.LANCEMENT_BATCH,
                        "Forçage lancement d'une nouvelle instance ${id} dans la même journée",
                        request.getRemoteAddr(),
                        "BATCH",
                        id
                )
                evenementSecuriteDTO.typeEvenement = ConstantesSecurite.Event.FORCAGE_NEW_INSTANCE_MEME_JOURNEE.code
            }
            else if (option == Constantes.LANCEMENT_BATCH_OPTION_RESTART_INSTANCE) {
                ItemToProcess.deleteAll()
                sqlCatalogService.executeScript('init_batch_status_script', [status: ConstantesBatch.BATCH_JOB_INSTANCE_COMPLETED_STATUS, instanceStatus: ConstantesBatch.BATCH_JOB_INSTANCE_SUCCESS_STATUS, jobExecutionId: params.jobExecutionId as Integer])
                toolsBatchService.journaliser(
                        currentUser,
                        Constantes.LANCEMENT_BATCH,
                        "Forçage lancement d'une nouvelle instance en commençant par la dernière séquence en cours",
                        request.getRemoteAddr(),
                        "BATCH",
                        id
                )
                evenementSecuriteDTO.typeEvenement = ConstantesSecurite.Event.FORCAGE_NEW_INSTANCE_DERIERE_SEQUENCE.code
            }

            Map result = toolsBatchService.launchBatch(id, jobParametersBuilder, request.getRemoteAddr(), option)

            //jobParameters.getDate(Constantes.BATCH_JOB_PARAM_NAME_DATE_TRAITEMENT)
            evenementSecuriteDTO.dateTraitement = result.dateTraitement as Date
            notificationService.sendAlerteSecurite(evenementSecuriteDTO)

            redirect(controller: 'batchJobExecution', action: 'show', id: result.jobExecutionId)
        }
        catch (SpringbatchException e) {
            log.error((JsonOutput.toJson([method: "launch", message: e.message])))
            flash.error = e.message
            redirect(action: 'list', id: id)
        }
        catch (JobExecutionAlreadyRunningException e) {
            log.error((JsonOutput.toJson([method: "launch", message: e.message])))
            flash.error = e.message
            redirect(action: 'list', id: id)
        }
        catch (IllegalArgumentException e) {
            log.error((JsonOutput.toJson([method: "launch", message: e.message])))
            flash.error = e.message
            redirect(action: 'list', id: id)
        }
    }



    def enableLaunching() {
        springBatchService.ready = true
        redirect action: 'list'
    }

    def disableLaunching() {
        springBatchService.ready = false
        redirect action: 'list'
    }

    def stopAllExecutions(String id) {
        if (id) {
            springBatchService.stopAllJobExecutions(id)
            flash.message = "Stopped all Job Executions for Job $id"
            redirect(action: 'show', id: id)
        }
        else {
            springBatchService.stopAllJobExecutions()
            flash.message = 'Stopping all Job Executions for all Jobs'
            redirect(action: "list")
        }
    }

    def showStepSettings(String id) {
        List<ProgrammeBatch> programmeBatchList = ProgrammeBatch.findAll("from ProgrammeBatch where batchId =:batch_id AND sequenceNumber != -1 order by sequenceNumber", [batch_id: id])
        [programmeBatchList: programmeBatchList]
    }

    def updateStepSettings() {
        def json = request.JSON;
        json.each { programmeBatch ->
            if (programmeBatch.sequenceNumber != null && programmeBatch.sequenceNumber != "") {
                ProgrammeBatch.executeUpdate("update ProgrammeBatch set sequenceNumber =:sequence, batchStepStatus =:status WHERE codeProgramme = :code", [sequence: Integer.parseInt(programmeBatch.sequenceNumber), status: BatchStepStatus.ACTIVER, code: programmeBatch.code])
            }
            else {
                ProgrammeBatch.executeUpdate("update ProgrammeBatch set sequenceNumber =:sequence, batchStepStatus =:status WHERE codeProgramme = :code", [sequence: null, status: BatchStepStatus.ACTIVER, code: programmeBatch.code])
            }
        }

        List<ProgrammeBatch> programmeBatchList = ProgrammeBatch.findAll("from ProgrammeBatch order by sequenceNumber")
        [programmeBatchList: programmeBatchList]
    }

    def updateStepStatus() {
        def json = request.JSON;

        if (json != null) {

            if (BatchStepStatus.DESACTIVER.id.equals(json.status)) {
                ProgrammeBatch.executeUpdate("update ProgrammeBatch set batchStepStatus =:status WHERE codeProgramme = :code", [status: BatchStepStatus.DESACTIVER, code: json.idStep])
            }
            else {
                ProgrammeBatch.executeUpdate("update ProgrammeBatch set batchStepStatus =:status WHERE codeProgramme = :code", [status: BatchStepStatus.ACTIVER, code: json.idStep])
            }

            def responseData = [
                    'status' : "success",
                    'message': "cycleMise à jour effectuée avec succès"
            ]
            render responseData as JSON

        }
        else {
            def responseData = [
                    'status' : "failed",
                    'message': "Absence de paramètres !"
            ]
            render responseData as JSON
        }
    }

    def getDetailJobTriger() {
        def json = request.JSON;
        def reponse = [:]
        /*
        Trigger trigger = toolsBatchService.getTrigger(json?.triggerName)


        if (trigger != null) {
            reponse.status = "success"
            reponse.nextExecutionDate = trigger.nextFireTime.format("dd MMM yyyy hh:mm:ss")
            reponse.cronExpression = trigger.cronExpression
            reponse.triggerName = trigger.name
            reponse.triggerGroup = trigger.group
        }
        else {
            reponse.status = "failed"
        }
         */
        reponse.status = "failed"
        render reponse as JSON
    }

    def saveCronTrigger = {
        /*
        if (!params.triggerName || !params.triggerGroup) {
            flash.error = "Invalid trigger parameters"
            redirect(action: "list")
            return
        }

        CronTrigger trigger = quartzScheduler.getTrigger(new TriggerKey(params.triggerName, params.triggerGroup)) as CronTrigger
        if (!trigger) {
            flash.error = "No such trigger"
            redirect(action: "list")
            return
        }

        try {
            trigger.cronExpression = params.cronexpression
            quartzScheduler.rescheduleJob(new TriggerKey(params.triggerName, params.triggerGroup), trigger)
            flash.message = "${params.triggerName} cron expression mis à jour"
        } catch (Exception ex) {
            flash.message = "cron expression (${params.cronexpression}) was not correct: $ex"
            render(view: "editCronTrigger", model: [trigger: trigger])
            return
        }

         */
        redirect(action: "list")
    }

    //Utiliser pour forcer le statut du batch en cours à complét
    def initBatch() {
        String methodName = "initBatch"
        try {
            if (params.jobExecutionId == null) {
                throw new SpringbatchException("Veuillez renseigner le job exection id")
            }

            if (params.status == null) {
                throw new SpringbatchException("Veuillez renseigner le statut à forcer pour le job, Possible value: ${ConstantesBatch.BATCH_JOB_INSTANCE_COMPLETED_STATUS} or ${ConstantesBatch.BATCH_JOB_INSTANCE_STOPED_STATUS}")
            }

            def jobExecution = springBatchService.jobExecution(Long.parseLong(params.jobExecutionId))
            String jobName = jobExecution.jobInstance.jobName

            if (!ConstantesBatch.BATCH_JOB_INSTANCE_COMPLETED_STATUS.equals(params.status)
                    && !ConstantesBatch.BATCH_JOB_INSTANCE_STOPED_STATUS.equals(params.status)) {
                throw new SpringbatchException("invalid status. Possible value: ${ConstantesBatch.BATCH_JOB_INSTANCE_COMPLETED_STATUS} or ${ConstantesBatch.BATCH_JOB_INSTANCE_STOPED_STATUS}")
            }

            String instanceStatus
            if (ConstantesBatch.BATCH_JOB_INSTANCE_COMPLETED_STATUS.equals(params.status)) {
                instanceStatus = ConstantesBatch.BATCH_JOB_INSTANCE_SUCCESS_STATUS
            }
            else {
                instanceStatus = params.status
            }
            sqlCatalogService.executeScript('init_batch_status_script', [status: params.status, instanceStatus: instanceStatus, jobExecutionId: params.jobExecutionId as Integer])

            ItemToProcess.deleteAll()
            CommittedItem.deleteAll()

            flash.message = "La status du batch a été mis à jour avec succès"

            EvenementSecuriteDTO evenementSecuriteDTO = new EvenementSecuriteDTO()
            evenementSecuriteDTO.username = session.currentUser?.username
            evenementSecuriteDTO.ipAddress = params.remoteAddr

            toolsBatchService.journaliser(
                    session.currentUser as User,
                    Constantes.LANCEMENT_BATCH,
                    "Relancement batch ${jobName} par l'option initialisation",
                    request.getRemoteAddr(),
                    "BATCH",
                    jobName
            )

            evenementSecuriteDTO.typeEvenement = ConstantesSecurite.Event.FORCAGE_BATCH_COMPLET.code
            notificationService.sendAlerteSecurite(evenementSecuriteDTO)
        }
        catch (SpringbatchException e) {
            log.error((JsonOutput.toJson([method: methodName, message: e.message])))
            flash.error = e.message
        }
        catch (JobExecutionAlreadyRunningException e) {
            log.error((JsonOutput.toJson([method: methodName, message: e.message])))
            flash.error = e.message
        }

        redirect(action: 'list', id: params.jobExecutionId)
    }

    def ajaxUpDatBatcheIndActif() {
        def json = request.JSON
        def reponse = [:]
        try {
            if (json == null) {
                throw new SpringbatchException("Veuillez renseigner l'id du batch et le nouveau statut")
            }

            if (json.id == null && json.id == "") {
                throw new SpringbatchException("Veuillez renseigner l'id du batch concerné")
            }

            if (json.status != true && json.status != false) {
                throw new SpringbatchException("Veuillez renseigner le nouveau statut du batch (indicateur actif = true ou falsa)")
            }

            Batch batch = Batch.get(json.id)
            if (batch == null) {
                throw new SpringbatchException("Le batch ${json.id} 'existe pas")
            }

            Batch.executeUpdate("UPDATE Batch SET indActif = :status WHERE id = :id", [status: json.status, id: json.id])
            reponse.status = "success"
            reponse.message = "mise à jour effectuée avec succés"
        }
        catch (SpringbatchException e) {
            log.error((JsonOutput.toJson([method: "ajaxUpDatBatcheIndActif", message: e.message])))
            reponse.status = "failed"
            reponse.message = e.getMessage()
        }

        render reponse as JSON

    }

    def ajaxTabJournal() {
        try {
            Date dateComptable
            if (params.dateComptable != null && !"".equals(params.dateComptable)) {
                dateComptable = new SimpleDateFormat("dd/MM/yyyy").parse(params.dateComptable)
            }
            List<LogBatch> batchList = LogBatch.findAllByDateComptable(dateComptable)
            render template: "ajaxTabJournal", model: [batchList: batchList]

        }
        catch (SpringbatchException ex) {
            log.error(JsonOutput.toJson([method: "ajaxTabJournal", status: "error", message: ex.message]))
            flash.error = ex.message
            response.status = 500
            render ex.toString()
        }
        catch (IllegalArgumentException ex) {
            log.error(JsonOutput.toJson([method: "ajaxTabJournal", status: "error", message: ex.message]))
            flash.error = ex.message
            response.status = 500
            render ex.toString()
        }

    }

    def ajaxTabJournalDetail() {
        try {

            java.sql.Date sqlDate
            if (params.dateComptable != null && !"".equals(params.dateComptable)) {
                Date dateComptable = new SimpleDateFormat("dd/MM/yyyy").parse(params.dateComptable)
                sqlDate = new java.sql.Date(dateComptable.getTime());
            }
            render ""

        }
        catch (SpringbatchException ex) {
            log.error(JsonOutput.toJson([method: "ajaxTabJournal", status: "error", message: ex.message]))
            flash.error = ex.message
            response.status = 500
            render ex.toString()
        }
        catch (IllegalArgumentException ex) {
            log.error(JsonOutput.toJson([method: "ajaxTabJournal", status: "error", message: ex.message]))
            flash.error = ex.message
            response.status = 500
            render ex.toString()
        }

    }


}
