package sn.sensoft.springbatch

import com.opencsv.CSVReader
import grails.converters.JSON
import grails.gorm.transactions.Transactional
import grails.web.context.ServletContextHolder
import groovy.json.JsonOutput
import io.micronaut.http.HttpResponse
import org.apache.http.HttpStatus
import org.grails.web.json.JSONObject
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.multipart.MultipartFile
import sn.sensoft.springbatch.api.AichaClient
import sn.sensoft.springbatch.apiResponse.aicha.DateArreteResponse
import sn.sensoft.springbatch.date.DateArrete
import sn.sensoft.springbatch.dto.BureauDTO
import sn.sensoft.springbatch.dto.CompteDTO
import sn.sensoft.springbatch.dto.DateArreteDTO
import sn.sensoft.springbatch.exception.SpringbatchException
import sn.sensoft.springbatch.securite.User
import sn.sensoft.springbatch.utils.BatchList
import sn.sensoft.springbatch.utils.Constantes

@Transactional()
class TraitementBatchController {

    def traitementBatchService
    def toolsBatchService
    def apiManagerService
    def messageSource

    @Autowired
    final AichaClient aichaClient

    static allowedMethods = [save: "POST", update: "PUT"]


    def calculerSoldeArrete() {
        getDateArretes()
        dateArretes.each { entry ->
            entry.value.each { dateArreteDTO ->
                dateArreteDTO.setMessageSource(messageSource)
            }
        }
    }

    def launchCalculSoldeArrete() {

        String id = BatchList.BatchCalculSoldeArrete.id

        try {
            if (params.dateArreteId == null || "".equals(params.dateArrete)) {
                throw new SpringbatchException("Veuillez renseigner la dateArrete")
            }

            Date dateTime = new Date()
            String dateTimestring = dateTime.format("dd/MM/yyyy HH:mm:ss")

            User currentUser = session.currentUser
            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_USER, currentUser.username)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_TYPE_LANCEMENT, Constantes.BATCH_TYPE_LANCEMENT_MANUEL)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_REMOTE_ADDRESS, request.remoteAddr)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_APP_VERSION, params.appVersion)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_DATE_ARRETE_ID, params.dateArreteId)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_MUTUELLE_ID, params.mutuelleId)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_USER_EMAIL, session.currentUser.email)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_DATE_TIME, dateTimestring)

            Integer option = params.option != null ? params.option as Integer : 0
            Map result = toolsBatchService.launchBatch(id, jobParametersBuilder, request.getRemoteAddr(), option)

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
    }

    def calculCreancesRattachees() {
        getDateArretes()
        dateArretes.each { entry ->
            entry.value.each { dateArreteDTO ->
                dateArreteDTO.setMessageSource(messageSource)
            }
        }
    }

    def launchCalculCreancesRatachees() {

        String id = BatchList.BatchCalculCreancesRattachees.id

        try {
            if (params.dateArreteId == null || "".equals(params.dateArrete)) {
                throw new SpringbatchException("Veuillez renseigner la dateArrete")
            }

            Date dateTime = new Date()
            String dateTimestring = dateTime.format("dd/MM/yyyy HH:mm:ss")

            User currentUser = session.currentUser
            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_USER, currentUser.username)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_TYPE_LANCEMENT, Constantes.BATCH_TYPE_LANCEMENT_MANUEL)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_REMOTE_ADDRESS, request.remoteAddr)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_APP_VERSION, params.appVersion)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_DATE_ARRETE_ID, params.dateArreteId)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_USER_EMAIL, session.currentUser.email)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_DATE_TIME, dateTimestring)

            Integer option = params.option != null ? params.option as Integer : 0
            Map result = toolsBatchService.launchBatch(id, jobParametersBuilder, request.getRemoteAddr(), option)

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
    }

    def apurementCompteGestion() {
        getDateArretes()
        dateArretes.each { entry ->
            entry.value.each { dateArreteDTO ->
                dateArreteDTO.setMessageSource(messageSource)
            }
        }
    }

    def launchApurementCompteGestion() {

        String id = BatchList.BatchApurementCompteGestion.id

        try {
            if (params.dateArreteId == null || "".equals(params.dateArreteId)) {
                throw new SpringbatchException("Veuillez renseigner dateArreteId")
            }

            Date dateTime = new Date()
            String dateTimestring = dateTime.format("dd/MM/yyyy HH:mm:ss")

            User currentUser = session.currentUser
            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_USER, currentUser.username)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_TYPE_LANCEMENT, Constantes.BATCH_TYPE_LANCEMENT_MANUEL)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_REMOTE_ADDRESS, request.remoteAddr)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_APP_VERSION, params.appVersion)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_DATE_ARRETE_ID, params.dateArreteId)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_USER_EMAIL, session.currentUser.email)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_DATE_TIME, dateTimestring)

            Integer option = params.option != null ? params.option as Integer : 0
            Map result = toolsBatchService.launchBatch(id, jobParametersBuilder, request.getRemoteAddr(), option)

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
    }

    def calculPosteBudgetaire() {

        HttpResponse httpResponse = aichaClient.branches(apiManagerService.aichaBearerToken())
        if (httpResponse.code() != HttpStatus.SC_OK) {
            flash.error = "${httpResponse.body.toString()}"

            redirect(controller: "batch", action: "list")
            return
        }

        List<BureauDTO> bureauDTOList = httpResponse.body()?.branches

        if (bureauDTOList == null) {
            flash.error = "bureauDTOList null"

            redirect(controller: "batch", action: "list")
            return
        }
        [bureauList: bureauDTOList]
    }

    def launchCalculPosteBudgetaire() {

        String id = BatchList.BatchCalculPosteBudgetaire.id

        try {
            if (params.bureauId == null || "".equals(params.bureauId)) {
                throw new SpringbatchException("Veuillez renseigner le bureau")
            }

            if (params.annee == null || "".equals(params.annee)) {
                throw new SpringbatchException("Veuillez renseigner l'annee")
            }

            Date dateTime = new Date()
            String dateTimestring = dateTime.format("dd/MM/yyyy HH:mm:ss")

            String anneeString = "01/01/${params.annee_year}"

            User currentUser = session.currentUser
            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_USER, currentUser.username)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_TYPE_LANCEMENT, Constantes.BATCH_TYPE_LANCEMENT_MANUEL)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_REMOTE_ADDRESS, request.remoteAddr)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_APP_VERSION, params.appVersion)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_DATE_ARRETE_ID, params.dateArreteId)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_USER_EMAIL, session.currentUser.email)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_DATE_TIME, dateTimestring)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_BUREAU_ID, params.bureauId)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_ANNEE, anneeString)

            Integer option = params.option != null ? params.option as Integer : 0
            Map result = toolsBatchService.launchBatch(id, jobParametersBuilder, request.getRemoteAddr(), option)

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
    }

    def calculProvisionsPrets() {
        getDateArretes()
        dateArretes.each { entry ->
            entry.value.each { dateArreteDTO ->
                dateArreteDTO.setMessageSource(messageSource)
            }
        }
    }

    def launchCalculProvisionsPrets() {

        String id = BatchList.BatchCalculProvisionsPrets.id

        try {
            if (params.dateArreteId == null || "".equals(params.dateArreteId)) {
                throw new SpringbatchException("Veuillez renseigner dateArreteId")
            }

            Date dateTime = new Date()
            String dateTimestring = dateTime.format("dd/MM/yyyy HH:mm:ss")

            User currentUser = session.currentUser
            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_USER, currentUser.username)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_TYPE_LANCEMENT, Constantes.BATCH_TYPE_LANCEMENT_MANUEL)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_REMOTE_ADDRESS, request.remoteAddr)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_APP_VERSION, params.appVersion)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_DATE_ARRETE_ID, params.dateArreteId)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_USER_EMAIL, session.currentUser.email)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_DATE_TIME, dateTimestring)

            Integer option = params.option != null ? params.option as Integer : 0
            Map result = toolsBatchService.launchBatch(id, jobParametersBuilder, request.getRemoteAddr(), option)

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
    }

    def generationEcritureProvision() {
        getDateArretes()
        dateArretes.each { entry ->
            entry.value.each { dateArreteDTO ->
                dateArreteDTO.setMessageSource(messageSource)
            }
        }
    }

    def launchGenerationEcritureProvision() {

        String id = BatchList.BatchGenerationEcritureProvision.id

        try {
            if (params.dateArreteId == null || "".equals(params.dateArreteId)) {
                throw new SpringbatchException("Veuillez renseigner dateArreteId")
            }

            Date dateTime = new Date()
            String dateTimestring = dateTime.format("dd/MM/yyyy HH:mm:ss")

            User currentUser = session.currentUser
            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_USER, currentUser.username)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_TYPE_LANCEMENT, Constantes.BATCH_TYPE_LANCEMENT_MANUEL)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_REMOTE_ADDRESS, request.remoteAddr)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_APP_VERSION, params.appVersion)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_DATE_ARRETE_ID, params.dateArreteId)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_USER_EMAIL, session.currentUser.email)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_DATE_TIME, dateTimestring)

            Integer option = params.option != null ? params.option as Integer : 0
            Map result = toolsBatchService.launchBatch(id, jobParametersBuilder, request.getRemoteAddr(), option)

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
    }

    def calculImmo() {
        HttpResponse<DateArreteResponse> httpResponse = aichaClient.dateArretes(apiManagerService.aichaBearerToken())
        if (httpResponse.code() != HttpStatus.SC_OK) {
            flash.message = "${httpResponse.body.toString()}"

            redirect(controller: "batch", action: "list")
            return
        }
        List<DateArreteDTO> dateArreteDTOList = httpResponse.body()?.data

        if (dateArreteDTOList == null) {
            flash.error = "dateArreteDTOList null"

            redirect(controller: "batch", action: "list")
            return
        }
        httpResponse = aichaClient.branches(apiManagerService.aichaBearerToken())
        if (httpResponse.code() != HttpStatus.SC_OK) {
            flash.error = "${httpResponse.body.toString()}"

            redirect(controller: "batch", action: "list")
            return
        }


        List<BureauDTO> bureauDTOList = httpResponse.body()?.branches

        if (bureauDTOList == null) {
            flash.error = "bureauDTOList null"

            redirect(controller: "batch", action: "list")
            return
        }
        dateArreteDTOList.each { dateArreteDTO ->
                dateArreteDTO.setMessageSource(messageSource)
            }
        [dateArreteList: dateArreteDTOList, bureauList: bureauDTOList]
    }

    def launchCalculImmo() {

        String id = BatchList.BatchCalculImmobilisations.id

        try {

            if (params.dateArreteId == null || "".equals(params.dateArreteId)) {
                throw new SpringbatchException("Veuillez choisir une date arrêté")
            }

            if (params.bureauId == null || "".equals(params.bureauId)) {
                throw new SpringbatchException("Veuillez choisir un bureau")
            }

            Date dateTime = new Date()
            String dateTimestring = dateTime.format("dd/MM/yyyy HH:mm:ss")

            User currentUser = session.currentUser
            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_USER, currentUser.username)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_TYPE_LANCEMENT, Constantes.BATCH_TYPE_LANCEMENT_MANUEL)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_REMOTE_ADDRESS, request.remoteAddr)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_APP_VERSION, params.appVersion)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_DATE_ARRETE_ID, params.dateArreteId)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_USER_EMAIL, session.currentUser.email)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_DATE_TIME, dateTimestring)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_BUREAU_ID, params.bureauId)

            Integer option = params.option != null ? params.option as Integer : 0
            Map result = toolsBatchService.launchBatch(id, jobParametersBuilder, request.getRemoteAddr(), option)

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
    }

    def comptaImmo() {
        HttpResponse<DateArreteResponse> httpResponse = aichaClient.dateArretes(apiManagerService.aichaBearerToken())
        if (httpResponse.code() != HttpStatus.SC_OK) {
            flash.message = "${httpResponse.body.toString()}"

            redirect(controller: "batch", action: "list")
            return
        }
        List<DateArreteDTO> dateArreteDTOList = httpResponse.body()?.data

        if (dateArreteDTOList == null) {
            flash.error = "dateArreteDTOList null"

            redirect(controller: "batch", action: "list")
            return
        }
        httpResponse = aichaClient.branches(apiManagerService.aichaBearerToken())
        if (httpResponse.code() != HttpStatus.SC_OK) {
            flash.error = "${httpResponse.body.toString()}"

            redirect(controller: "batch", action: "list")
            return
        }

        List<BureauDTO> bureauDTOList = httpResponse.body()?.branches

        if (bureauDTOList == null) {
            flash.error = "bureauDTOList null"

            redirect(controller: "batch", action: "list")
            return
        }

        dateArreteDTOList.each { dateArreteDTO ->
            dateArreteDTO.setMessageSource(messageSource)
        }
        [dateArreteList: dateArreteDTOList, bureauList: bureauDTOList]
    }

    def launchComptaImmo() {

        String id = BatchList.BatchComptabilisationImmo.id


        try {

            if (params.dateArreteId == null || "".equals(params.dateArreteId)) {
                throw new SpringbatchException("Veuillez choisir une date arrêté")
            }

            Date dateTime = new Date()
            String dateTimestring = dateTime.format("dd/MM/yyyy HH:mm:ss")
            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_DATE_ARRETE_ID, params.dateArreteId)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_BUREAU_ID, params.bureauId)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_TYPE_LANCEMENT, Constantes.BATCH_TYPE_LANCEMENT_MANUEL)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_USER, session.currentUser.username)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_USER_EMAIL, session.currentUser.email)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_REMOTE_ADDRESS, request.remoteAddr)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_DATE_TIME, dateTimestring)

            Integer option = params.option != null ? params.option as Integer : 0
            Map result = toolsBatchService.launchBatch(id, jobParametersBuilder, request.getRemoteAddr(), option)

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
    }

    def calculInteretCompteBloque() {
        return null
    }

    def launchCalculInteretCompteBloque() {

        String id = BatchList.BatchCalculInteretCompteBloque.id
        try {
            if (params.compteCible == null || "".equals(params.compteCible)) {
                throw new SpringbatchException("Veuillez renseigner compteCible")
            }

            if (params.dateDebut == null || "".equals(params.dateDebut)) {
                throw new SpringbatchException("Veuillez renseigner dateDebut")
            }

            if (params.dateFin == null || "".equals(params.dateFin)) {
                throw new SpringbatchException("Veuillez renseigner dateFin")
            }

            Date dateTime = new Date()
            String dateTimestring = dateTime.format("dd/MM/yyyy HH:mm:ss")

            User currentUser = session.currentUser
            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_USER, currentUser.username)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_TYPE_LANCEMENT, Constantes.BATCH_TYPE_LANCEMENT_MANUEL)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_REMOTE_ADDRESS, request.remoteAddr)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_APP_VERSION, params.appVersion)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_DATE_ARRETE_ID, params.dateArreteId)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_USER_EMAIL, session.currentUser.email)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_DATE_TIME, dateTimestring)

            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_COMPTE_ID, params.compteId)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_COMPTE_ID_CIBLLE, params.compteCible)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_BUREAU_ID, params.bureauId)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_CODE_BUREAU, params.codeBureau)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_DATE_DEBUT, params.dateDebut)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_DATE_FIN, params.dateFin)

            Integer option = params.option != null ? params.option as Integer : 0
            Map result = toolsBatchService.launchBatch(id, jobParametersBuilder, request.getRemoteAddr(), option)

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
    }

    def imputationInteret() {
        return null
    }

    def launchImputationInteret() {

        String id = BatchList.BatchImputationInteret.id

        try {

            if (params.compteCible == null || "".equals(params.compteCible)) {
                throw new SpringbatchException("Rentrez le compte cible")
            }

            Date dateTime = new Date()
            String dateTimestring = dateTime.format("dd/MM/yyyy HH:mm:ss")

            User currentUser = session.currentUser
            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_USER, currentUser.username)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_TYPE_LANCEMENT, Constantes.BATCH_TYPE_LANCEMENT_MANUEL)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_REMOTE_ADDRESS, request.remoteAddr)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_APP_VERSION, params.appVersion)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_DATE_ARRETE_ID, params.dateArreteId)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_USER_EMAIL, session.currentUser.email)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_DATE_TIME, dateTimestring)

            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_COMPTE_ID, params.compteId)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_COMPTE_ID_CIBLLE, params.compteCible)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_BUREAU_ID, params.bureauId)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_CODE_BUREAU, params.codeBureau)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_DATE_DEBUT, params.dateDebut)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_DATE_FIN, params.dateFin)

            Integer option = params.option != null ? params.option as Integer : 0
            Map result = toolsBatchService.launchBatch(id, jobParametersBuilder, request.getRemoteAddr(), option)

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
    }

    def getDateArretes() {
        HttpResponse<DateArreteResponse> httpResponse = aichaClient.dateArretes(apiManagerService.aichaBearerToken())
        if (httpResponse.code() != HttpStatus.SC_OK) {
            flash.message = "${httpResponse.body.toString()}"

            redirect(controller: "batch", action: "list")
            return
        }

        List<DateArreteDTO> dateArreteDTOList = httpResponse.body()?.data

        dateArreteDTOList.sort { it.codeMutuelle }

        if (dateArreteDTOList == null) {
            flash.message = "dateArreteDTOList null"

            redirect(controller: "batch", action: "list")
            return
        }
        dateArreteDTOList.each {
            log.info(JsonOutput.toJson([method         : "getDateArretes",
                                        dateArrete     : it.dateArrete,
                                        codeMutuelle   : it.codeMutuelle,
                                        libelleMutuelle: it.libelleMutuelle
            ]))
        }

        [dateArreteList: dateArreteDTOList]
    }

    def renderCompteCible() {

        HttpResponse<DateArreteResponse> httpResponse = aichaClient.branches(apiManagerService.aichaBearerToken())
        if (httpResponse.code() != HttpStatus.SC_OK) {
            flash.message = "${httpResponse.body.toString()}"

            redirect(controller: "batch", action: "list")
            return
        }

        List<BureauDTO> bureauDTOList = httpResponse.body()?.branches

        if (bureauDTOList == null) {
            flash.message = "bureauDTOList null"

            redirect(controller: "batch", action: "list")
            return
        }

        if (params.compteCible == 'C') {
            render(template: 'pourUncompte', model: [bureauList: bureauDTOList])
        }
        else if (params.compteCible == 'B') {
            render(template: 'pourTousLesBureaux')
        }
        else {
            render ""
        }
    }


    def renderCompteCibleImputationInteret() {
        HttpResponse<DateArreteResponse> httpResponse = aichaClient.branches(apiManagerService.aichaBearerToken())
        if (httpResponse.code() != HttpStatus.SC_OK) {
            flash.message = "${httpResponse.body.toString()}"

            redirect(controller: "batch", action: "list")
            return
        }

        List<BureauDTO> bureauDTOList = httpResponse.body()?.branches

        if (bureauDTOList == null) {
            flash.message = "bureauDTOList null"

            redirect(controller: "batch", action: "list")
            return
        }

        httpResponse = aichaClient.dateArretes(apiManagerService.aichaBearerToken())
        if (httpResponse.code() != HttpStatus.SC_OK) {
            flash.message = "${httpResponse.body.toString()}"

            redirect(controller: "batch", action: "list")
            return
        }

        List<DateArreteDTO> dateArreteDTOList = httpResponse.body()?.data

        if (dateArreteDTOList == null) {
            flash.message = "dateArreteDTOList null"

            redirect(controller: "batch", action: "list")
            return
        }

        if (params.compteCible == 'C') {
            render(template: 'imputationInteret', model: [bureauList: bureauDTOList])
        }
        else if (params.compteCible == 'B') {
            render(template: 'imputationInteretTousLesBureaux', model: [dateArreteList: dateArreteDTOList])
        }
        else {
            render ""
        }
    }

    def rechercheCompte() {
        if (params.codeBureau == null) {
            redirect(controller: "batch", action: "list")
            return
        }

        HttpResponse<DateArreteResponse> httpResponse = aichaClient.searchAccount(apiManagerService.aichaBearerToken(), params.codeBureau, params.numeroCompte)
        if (httpResponse.code() != HttpStatus.SC_OK) {
            flash.message = "${httpResponse.body.toString()}"

        }

        List<CompteDTO> compteList = httpResponse.body()

        if (compteList == null) {
            flash.message = "compteList null"

            redirect(controller: "batch", action: "list")
            return
        }

        render compteList as JSON
    }

    def extourneOperationsFile() {
        [appVersion: params.appVersion, dateTraitement: params.dateTraitement]
    }

    def launchExtourneEnMasse() {
        String id = BatchList.BatchExtourneOperationsFromFile.id
        try {
            if (params.data == null) {
                throw new SpringbatchException("Veuillez renseigner le ficher contenant les opérations à extournées")
            }

            Object[] args0 = [params]
            Object[] args = []
            MultipartFile file = params.data

            InputStream inputStream = new BufferedInputStream(file.getInputStream())
            String typeFile = fileService.detectDocTypeUsingTika(inputStream)

            if (typeFile != file.getContentType() && typeFile != "text/plain") {
                throw new SpringbatchException("Le type de contenu < $typeFile >  du fichier  <${file.originalFilename}> n'est pas conforme avec l'extension <${file.getContentType()}> ")
            }

            if (file.size > Constantes.MAX_FILE_EXCEL_UPLOAD) {
                throw new SpringbatchException("La taille de fichier autorisée est ${Constantes.MAX_FILE_EXCEL_UPLOAD} Bytes")
            }

            if (!Constantes.TYPE_EXCEL.contains(file.getContentType())) {
                args = [file.getContentType(), Constantes.TYPE_EXCEL]
                throw new SpringbatchException()
            }

            Document documentInstance = new Document(params)

            //Chargement du fichier
            fileService.createXLSFile(Constantes.EXTOURNE_FILE_NAME, documentInstance.data)

            String filename = "${ServletContextHolder.servletContext.getRealPath("/")}${Constantes.EXTOURNE_FILE_NAME}"
            File fileCsv = new File(filename)
            CSVReader reader = new CSVReader(new FileReader(fileCsv))
            List<String[]> dataRows

            reader.collect { it[0].split(';') }.with { rows ->
                def header = rows.head()
                dataRows = rows.tail()

                dataRows.collect { row ->
                    [header, row].transpose().collectEntries()
                }
            }

            dataRows.eachWithIndex { fichierParams, index ->

                JSONObject jsonObject = new JSONObject()
                jsonObject.put("numeroOperation", fichierParams[0])
                jsonObject.put("motif", fichierParams[1])

                ItemToProcess itemToProcess = ItemToProcess.findByObjetId(fichierParams[0])
                if (itemToProcess == null) {
                    itemToProcess = new ItemToProcess()
                    itemToProcess.objetId = fichierParams[0]
                }

                itemToProcess.jsonString = jsonObject.toString()
                if (!itemToProcess.save(flush: true)) {
                    throw new SpringbatchException("Impossible d'enregistre l'opération ${fichierParams[0]} à la ligne ${index}")
                }
            }

            Date dateTime = new Date()
            String dateTimestring = dateTime.format("dd/MM/yyyy HH:mm:ss")

            User currentUser = session.currentUser
            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_USER, currentUser.username)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_TYPE_LANCEMENT, Constantes.BATCH_TYPE_LANCEMENT_MANUEL)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_REMOTE_ADDRESS, request.remoteAddr)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_APP_VERSION, params.appVersion as String)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_USER_EMAIL, session.currentUser.email)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_DATE_TIME, dateTimestring)

            Integer option = params.option != null ? params.option as Integer : 0
            Map result = toolsBatchService.launchBatch(id, jobParametersBuilder, request.getRemoteAddr(), option)
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
    }

    def downloadModeleExtourneCsv() {

        String path = "${ServletContextHolder.servletContext.getRealPath("/")}"
        File f = new File(path + "modelesCsv/modeleFichierExtourne.csv")
        if (f.exists()) {
            response.setHeader("Content-disposition", "attachment; filename = ${"modeleFichierExtourne.csv"}")
            response.setContentType("text/csv")
            response.outputStream << f.getBytes()
            response.outputStream.flush()
        }
        else {
            response.sendError(404)
        }
    }

    def extractionBic() {
        HttpResponse<DateArreteResponse> httpResponse = aichaClient.dateArretes(apiManagerService.aichaBearerToken())
        if (httpResponse.code() != HttpStatus.SC_OK) {
            flash.message = "${httpResponse.body.toString()}"

            redirect(controller: "batch", action: "list")
            return
        }
        List<DateArreteDTO> dateArreteDTOList = httpResponse.body()?.data

        if (dateArreteDTOList == null) {
            flash.error = "dateArreteDTOList null"

            redirect(controller: "batch", action: "list")
            return
        }

        dateArreteDTOList.each { dateArreteDTO ->
             dateArreteDTO.setMessageSource(messageSource)
        }

        [dateArreteList: dateArreteDTOList]
    }

    def launchExtractionBic() {

        String id = BatchList.BatchExtractionBic.id

        try {
            if (params.dateArreteId == null || "".equals(params.dateArreteId)) {
                throw new SpringbatchException("Veuillez renseigner dateArreteId")
            }

            Date dateTime = new Date()
            String dateTimestring = dateTime.format("dd/MM/yyyy HH:mm:ss")

            User currentUser = session.currentUser

            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_TYPE_LANCEMENT, Constantes.BATCH_TYPE_LANCEMENT_MANUEL)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_USER, currentUser.username)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_REMOTE_ADDRESS, request.remoteAddr)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_DATE_ARRETE_ID, params.dateArreteId)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_USER_EMAIL, currentUser.email)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_DATE_TIME, dateTimestring)
            String appContext = "${request.scheme}://${request.serverName}:${request.serverPort}${request.contextPath}/"
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_APP_CONTEXT, appContext)

            Integer option = params.option != null ? params.option as Integer : 0
            Map result = toolsBatchService.launchBatch(id, jobParametersBuilder, request.getRemoteAddr(), option)

            DateArrete dateArrete = null
            DateArrete.withNewSession {
                dateArrete = DateArrete.get(params.dateArreteId)
                if (dateArrete == null) {
                    throw new SpringbatchException("Date arrete null")
                }
            }
            bicService.extractBicNotification(dateArrete.dateArrete, session.currentUser.username, session.currentUser.email, appContext)

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
    }


    def launchCorrectionCredit(String id) {

        try {

            Date dateTime = new Date()
            String dateTimestring = dateTime.format("dd/MM/yyyy HH:mm:ss")

            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_TYPE_LANCEMENT, Constantes.BATCH_TYPE_LANCEMENT_MANUEL)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_USER, session.currentUser.username)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_REMOTE_ADDRESS, request.remoteAddr)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_DATE_TIME, dateTimestring)

            Integer option = params.option != null ? params.option as Integer : 0
            Map result = toolsBatchService.launchBatch(id, jobParametersBuilder, request.getRemoteAddr(), option)

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
    }

    def correctionFraisTcp() {

    }

    def launchCorrectionFraisTcp() {
        String id = BatchList.SelcectFraisTcpNonRecycles.id
        try {
            if (params.moisConcerne == null || "".equals(params.moisConcerne)) {
                throw new SpringbatchException("Veuillez renseigner le mois concerné (YYYY-MM)")
            }

            Date dateTime = new Date()
            String dateTimestring = dateTime.format("dd/MM/yyyy HH:mm:ss")

            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_TYPE_LANCEMENT, Constantes.BATCH_TYPE_LANCEMENT_MANUEL)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_USER, session.currentUser.username)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_REMOTE_ADDRESS, request.remoteAddr)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_DATE_TIME, dateTimestring)
            jobParametersBuilder.addString("MOIS_CONCERNE", params.moisConcerne)

            Integer option = params.option != null ? params.option as Integer : 0
            Map result = toolsBatchService.launchBatch(id, jobParametersBuilder, request.getRemoteAddr(), option)

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
    }

    //A faire
    def fusionCaisse() {

    }


    def launchFusionCaisse() {
        String id = BatchList.BatchFusionCaisse.id

        try {
            Date dateTime = new Date()
            String dateTimestring = dateTime.format("dd/MM/yyyy HH:mm:ss")

            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_TYPE_LANCEMENT, Constantes.BATCH_TYPE_LANCEMENT_MANUEL)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_USER, session.currentUser.username)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_REMOTE_ADDRESS, request.remoteAddr)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_DATE_TIME, dateTimestring)

            Integer option = params.option != null ? params.option as Integer : 0
            Map result = toolsBatchService.launchBatch(id, jobParametersBuilder, request.getRemoteAddr(), option)

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
    }

    def launchVerifCompensesCaisses() {

        String id = BatchList.BatchExtractionBic.id

        try {
            if (params.dateArreteId == null || "".equals(params.dateArreteId)) {
                throw new SpringbatchException("Veuillez renseigner dateArreteId")
            }

            Date dateTime = new Date()
            String dateTimestring = dateTime.format("dd/MM/yyyy HH:mm:ss")

            User currentUser = session.currentUser

            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_TYPE_LANCEMENT, Constantes.BATCH_TYPE_LANCEMENT_MANUEL)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_USER, currentUser.username)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_REMOTE_ADDRESS, request.remoteAddr)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_DATE_ARRETE_ID, params.dateArreteId)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_NAME_USER_EMAIL, currentUser.email)
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_DATE_TIME, dateTimestring)
            String appContext = "${request.scheme}://${request.serverName}:${request.serverPort}${request.contextPath}/"
            jobParametersBuilder.addString(Constantes.BATCH_JOB_PARAM_APP_CONTEXT, appContext)

            Integer option = params.option != null ? params.option as Integer : 0
            Map result = toolsBatchService.launchBatch(id, jobParametersBuilder, request.getRemoteAddr(), option)

            DateArrete dateArrete = null
            DateArrete.withNewSession {
                dateArrete = DateArrete.get(params.dateArreteId)
                if (dateArrete == null) {
                    throw new SpringbatchException("Date arrete null")
                }
            }
            bicService.extractBicNotification(dateArrete.dateArrete, session.currentUser.username, session.currentUser.email, appContext)

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
    }

}
