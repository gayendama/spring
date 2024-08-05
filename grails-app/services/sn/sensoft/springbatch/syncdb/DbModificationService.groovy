package sn.sensoft.springbatch.syncdb

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.springframework.transaction.annotation.Transactional
import sn.sensoft.springbatch.communication.CommEvenement
import sn.sensoft.springbatch.communication.MsgTemplate
import sn.sensoft.springbatch.exception.SpringbatchException
import sn.sensoft.springbatch.parametrage.Batch
import sn.sensoft.springbatch.parametrage.ParamGen
import sn.sensoft.springbatch.parametrage.ProgrammeBatch
import sn.sensoft.springbatch.securite.MenuMap
import sn.sensoft.springbatch.securite.Module
import sn.sensoft.springbatch.securite.Requestmap
import sn.sensoft.springbatch.utils.BatchList
import sn.sensoft.springbatch.utils.Constantes
import sn.sensoft.springbatch.utils.ConstantesBatch
import sn.sensoft.springbatch.utils.ConstantesCommEvenement
import sn.sensoft.springbatch.utils.ConstantesMsgTemplate
import sn.sensoft.springbatch.utils.ConstantesParam

import java.sql.SQLException

@Transactional
class DbModificationService {

    def grailsApplication
    def sqlCatalogService
    def jsonSlurper = new JsonSlurper()

    String getAppPath(String name) {
        String keycloakFilePath
        def keycloakFile = grailsApplication.mainContext.getResource("WEB-INF/${name}.json")
        if (keycloakFile == null) {
            throw new SpringbatchException("initial file WEB-INF/${name}.json does not exists")
        }
        keycloakFilePath = keycloakFile.getURL()
        String url = keycloakFilePath.split(":")[1]
        if (url == null) {
            throw new SpringbatchException("initial file WEB-INF/${name}.json does not exists")
        }
        return url
    }

    def applyDbModification() {
        // =========================================================================
        // Execution des scripts de modification
        // =========================================================================
        log.debug(JsonOutput.toJson([method: "applyDbModification", message: "Start executing db modifications..."]))
        def versionDbScripts = VersionDbScript.findAll("from VersionDbScript as v where v.scriptStatus='SA' order by v.versionDb.versionDbNumber, v.seq")
        if (versionDbScripts.size() == 0) {
            log.debug(JsonOutput.toJson([method: "applyDbModification", message: "No DB modification script to apply"]))
        }
        versionDbScripts.each() {
            log.debug(JsonOutput.toJson([method: "applyDbModification", scriptCode: it.scriptCode]))
            try {
                sqlCatalogService.executeScript(it.scriptCode, [:])
                it.scriptStatus = Constantes.STATUT_OK
                it.dateExecuted = new Date()
                it.save(flush: true)
            }
            catch (SQLException ex) {
                it.scriptStatus = Constantes.STATUT_NOT_OK
                it.dateExecuted = new Date()
                it.scriptErrorMessage = ex.toString()
                it.save(flush: true)
            }

        }
        // =========================================================================
        // Fin Execution des scripts de modfication
        // =========================================================================

    }

    def updateSqlViews() {
        // =========================================================================
        // Mise à jour des vues
        // =========================================================================
        log.debug(JsonOutput.toJson([method: "updateSqlViews", message: "Start create or update views..."]))

        sqlCatalogService.executeScript("vues", [:])

        log.debug(JsonOutput.toJson([method: "updateSqlViews", message: "End create or update views..."]))

    }

    /**
     * Chargement des scripts SQL de maintenance automatique de la base de données
     * TODO : à configurer par type base à l'image de sqlCatalogService
     * @return
     */
    def loadDbModification() {

        // Modification du 07/10/2018, le text du scipt n'est plus chargé dans la base pour des raisons de sécurité,
        // en plus, il est chargé par sqlCata//logService et permet de customiser la requête en fonction de la base de données

        // Liste des modifications par version

        def listModifs = [

        ]

        // Chargement des requêtes dans la table si la requête n'est pas encore chargée
        def versionDb
        def versionDbDate
        def versionDbNumber
        def comments

        def appName = grailsApplication.metadata['info.app.name']
        def appGrailsVersion = grailsApplication.metadata['info.app.grails.version']
        def appCurrentVersion = grailsApplication.metadata['info.app.version']

        log.debug "Version de l'application : ${appCurrentVersion} "
        log.debug "Version de Grails : ${appGrailsVersion} "

        listModifs.collect { modif ->

            //loading all scripts to databases
            versionDbNumber = modif[0]
            versionDb = VersionDb.findByVersionDbNumber(versionDbNumber)

            if (versionDb == null) {

                versionDbDate = Date.parse("yyyy-MM-dd", (String) modif[1])
                comments = modif[2]

                versionDb = new VersionDb(
                        versionDbNumber: versionDbNumber, versionDbDate: versionDbDate, appGrailsVersion: appGrailsVersion, appName: appName, appVersion: appCurrentVersion,
                        comments: comments
                ).save(flush: true, failOnError: true)

                int seq = 1
                modif[3].collect { req ->
                    new VersionDbScript(
                            versionDb: versionDb, seq: seq++, scriptStatus: Constantes.STATUT_SAISI,
                            scriptCode: req
                    ).save(flush: true, failOnError: true)
                }
            }
        }
    }

    // Créer tous les paramaètres généraux, il n'est pas permis d'en créer ou de supprimer via l'application
    def syncParamGen() {

        // Les paramètres par défaut
        // Paramètres généraux
        def defaultParamValues = [:]
        // Paramètres KAFKA
        defaultParamValues.put(ConstantesParam.Param.KAFKA_USE_KAFKA_CHANNEL.getCode(), "true")
        defaultParamValues.put(ConstantesParam.Param.KAFKA_USE_SECURITY.getCode(), "true")
        defaultParamValues.put(ConstantesParam.Param.KAFKA_TOPIC_NAME.getCode(), "apicomm")
        defaultParamValues.put(ConstantesParam.Param.KAFKA_BOOTSTRAP_SERVERS.getCode(), "localhost")
        defaultParamValues.put(ConstantesParam.Param.KAFKA_BOOTSTRAP_PORT.getCode(), "10092")
        defaultParamValues.put(ConstantesParam.Param.KAFKA_TRUSTSTORE_LOCATION.getCode(), "/etc/kafka/apicomm.truststore")
        defaultParamValues.put(ConstantesParam.Param.KAFKA_TRUSTSTORE_PASSWORD.getCode(), "kafka2020")
        defaultParamValues.put(ConstantesParam.Param.KAFKA_KEYSTORE_LOCATION.getCode(), "/etc/kafka/apicomm.keystore")
        defaultParamValues.put(ConstantesParam.Param.KAFKA_KEYSTORE_PASSWORD.getCode(), "kafka2020")
        defaultParamValues.put(ConstantesParam.Param.KAFKA_JAAS_CONSUMER_LOGIN_FILE.getCode(), "/etc/kafka/apicomm_consumer_jaas.conf")
        defaultParamValues.put(ConstantesParam.Param.KAFKA_JAAS_PRODUCER_LOGIN_FILE.getCode(), "/etc/kafka/apicomm_producer_jaas.conf")
        defaultParamValues.put(ConstantesParam.Param.KAFKA_NOTIF_TOPIC_NAME.getCode(), "apicomm-notif")

        // Paramètres communication
        defaultParamValues.put(ConstantesParam.Param.COMM_SENDER_EMAIL.getCode(), "test@sensoft.sn")

        // int sequence = ParamGen.count()
        for (param in ConstantesParam.Param.values()) {

            def paramGen = ParamGen.findByCode(param.code)
            if (paramGen == null) {
                paramGen = new ParamGen(
                        code: param.code,
                        valeur: defaultParamValues.getAt(param.code),
                        // sequence: sequence++,
                        libelle: param.libelle,
                        dataType: param.dataType,
                        context: param.contexte
                ).save(flush: true)

            }
        }

    }


    // Créer tous les templates par défautit
    @Transactional
    def syncMsgTemplate() {
        for (param in ConstantesMsgTemplate.Template.values()) {
            for (lang in [Locale.FRENCH.toString(), Locale.ENGLISH.toString()]) {
                MsgTemplate msgTemplate = MsgTemplate.findByCodeAndLang(param.code, lang)
                if (msgTemplate == null) {
                    log.debug(JsonOutput.toJson([method: "syncMsgTemplate", motif: "création msgTemplate", code: param.code, message: param.message, lang: lang]))
                    msgTemplate = new MsgTemplate(
                            code: param.code,
                            lang: lang,
                            message: param.message,
                            categorie: param.categorie,
                            description: param.description
                    )
                    if (!msgTemplate.validate()) {
                        log.error(JsonOutput.toJson([method: "syncMsgTemplate", message: msgTemplate.errors.toString()]))
                    }
                    if (!msgTemplate.save(flush: true)) {
                        log.error(JsonOutput.toJson([method: "syncMsgTemplate", message: msgTemplate.errors.toString()]))
                    }
                } else if (!msgTemplate.message.equals(param.message)) {
                    msgTemplate.message = param.message
                    msgTemplate.save(flush: true)
                }
            }
        }

    }

    // Créer tous les code template par défaut
    @Transactional
    def syncCommEvenement() {
        for (param in ConstantesCommEvenement.Template.values()) {
            CommEvenement commEvenement = CommEvenement.findByCode(param.code)
            if (commEvenement == null) {
                log.debug(JsonOutput.toJson([method: "syncCommEvenement", motif: "création commEvenement", code: param.code]))
                commEvenement = new CommEvenement(
                        code: param.code,
                        codeTemplate: param.code,
                        indContrePartie: param.indContrepartie,
                        description: param.description
                )
                if (!commEvenement.validate()) {
                    log.error(JsonOutput.toJson([method: "syncCommEvenement", message: commEvenement.errors.toString()]))
                }
                if (!commEvenement.save(flush: true)) {
                    log.error(JsonOutput.toJson([method: "syncCommEvenement", message: commEvenement.errors.toString()]))
                }
            }
        }

    }

    // Créer la listes des batchs par defaut
    def syncBatchList() {
        for (param in BatchList.values()) {
            Batch batch = Batch.findByName(param.id)
            if (batch == null) {
                log.info(JsonOutput.toJson([method: "syncBatchList", motif: "créatiion batch", batchName: param.id]))
                batch = new Batch(
                        name: param.id,
                        description: param.description,
                        indActif: param.defaultStatus,
                        batchType: param.type,
                        deleted: false
                )

                if (!batch.validate()) {
                    log.error(JsonOutput.toJson([method: "syncBatchList", message: batch.errors.toString()]))
                }
                if (!batch.save(flush: true)) {
                    log.error(JsonOutput.toJson([method: "syncBatchList", message: batch.errors.toString()]))
                }

            } else if (!param.type.equals(batch.batchType) || !param.description.equals(batch.description)) {
                batch.batchType = param.type
                //batch.indActif = param.defaultStatus
                batch.description = param.description

                if (!batch.save(flush: true)) {
                    log.error(JsonOutput.toJson([method: "syncBatchList", message: syncBatchList.errors.toString()]))
                }
            }
        }
    }

    // Créer tous les code template par défaut
    def syncConstantesBatch() {
        for (param in ConstantesBatch.Programme.values()) {
            ProgrammeBatch programmeBatch = ProgrammeBatch.findByCodeProgramme(param.code)
            if (programmeBatch == null) {
                log.info(JsonOutput.toJson([method: "syncConstantesBatch", motif: "création programmeBatch", code: param.code]))
                programmeBatch = new ProgrammeBatch(
                        codeProgramme: param.code,
                        nomProgramme: param.name,
                        libelleProgramme: param.libelle,
                        typeProgramme: 't',
                        sequenceNumber: param.sequence,
                        batchId: param.batch_id
                )
                if (!programmeBatch.validate()) {
                    log.error(JsonOutput.toJson([method: "syncConstantesBatch", message: programmeBatch.errors.toString()]))
                }
                if (!programmeBatch.save(flush: true)) {
                    log.error(JsonOutput.toJson([method: "syncConstantesBatch", message: programmeBatch.errors.toString()]))
                }
            } else if (programmeBatch.sequenceNumber != param.sequence || programmeBatch.batchId != param.batch_id) {
                ProgrammeBatch.executeUpdate("update ProgrammeBatch set sequenceNumber = :seq, batchId = :batchId where id = :id", [seq: param.sequence, batchId: param.batch_id, id: programmeBatch.id])
            }
        }

    }


    // Créer tous les code template par défaut
    def syncMenus() {
        //Chargement de fichier modules
        saveChargementModule()

        //Chargement de fichier menus maps
        saveChargementMenuMap()

        //Suppression de request Map
        deleteRequestMapNotInMenuMap()

    }


    def saveChargementModule() {
        log.info(JsonOutput.toJson([method: "saveChargementModule", message: "Début de la sauvegarde de module"]))

        def returnMap = []
        //def jsonFile = new File("${path}WEB-INF/moduleFile.json")
        String path = getAppPath("moduleFile")
        def jsonFile = new File(path)
        if (jsonFile.exists()) {
            returnMap = jsonSlurper.parse(jsonFile)
            returnMap.collect {

                Module moduleInstance = Module.findByCode(it.code?.toString())

                if (moduleInstance == null) {
                    moduleInstance = new Module()
                    moduleInstance.code = it.code?.toString()
                    moduleInstance.libelle = it.libelle?.toString()
                    moduleInstance.save(flush: true)

                    log.debug(JsonOutput.toJson([method: "saveChargementModule", code: moduleInstance.code, libelle: moduleInstance.libelle, message: "module created"]))

                }
            }
        } else {
            log.warn(JsonOutput.toJson([method: "saveChargementModule", message: "json file moduleFile.json does not exists", path: "${path}"]))
        }

        log.info(JsonOutput.toJson([method: "saveChargementModule", message: "Fin de la sauvegarde de module"]))
    }

    def deleteRequestMapNotInMenuMap() {
        log.info(JsonOutput.toJson([method: "deleteRequestMapNotInMenuMap", message: "Début de suppression requestes maps inexistant dans menu map"]))
        StringBuffer stringBuffer = new StringBuffer()
        def returnMap = []
        //def jsonFile = new File("${path}WEB-INF/menuMapFile.json")
        String path = getAppPath("menuMapFile")
        def jsonFile = new File(path)

        if (!jsonFile.exists()) {
            log.warn(JsonOutput.toJson([method: "deleteRequestMapNotInMenuMap", message: "json file moduleFile.json does not exists", path: "${path}"]))
            return
        }

        returnMap = jsonSlurper.parse(jsonFile)

        returnMap.collect {
            it.menuMapParent.each {
                MenuMap menuMapInstance = MenuMap.findByUrl(it.url?.toString())
                if (menuMapInstance == null) {
                    Requestmap requestMapToDelete = Requestmap.findByUrl(it.url?.toString())
                    if (requestMapToDelete) {
                        stringBuffer.append(requestMapToDelete.url.toString())
                        stringBuffer.append("\n")
                        requestMapToDelete.delete(flush: true)
                        log.debug(JsonOutput.toJson([method: "deleteRequestMapNotInMenuMap", message: " Request map ${it.url?.toString()} supprimé avec succès"]))
                    }
                }
            }

            it.menuMapChild.each {
                MenuMap menuMapInstance = MenuMap.findByUrl(it.url?.toString())
                if (menuMapInstance == null) {
                    Requestmap requestMapToDelete = Requestmap.findByUrl(it.url?.toString())
                    if (requestMapToDelete) {
                        stringBuffer.append(requestMapToDelete.url.toString())
                        stringBuffer.append("\n")
                        // Supprimer le requestMap
                        requestMapToDelete.delete(flush: true)
                        log.debug(JsonOutput.toJson([method: "deleteRequestMapNotInMenuMap", message: " Request map ${it.url?.toString()} supprimé avec succès"]))
                    }
                }
            }
        }

        log.info(JsonOutput.toJson([method: "deleteRequestMapNotInMenuMap", message: "Fin de suppression requestes maps inexistant dans menu map"]))
    }


    def writeTofile(File file, String contenu) {
        try {

            FileWriter fw = new FileWriter(file, true)
            fw.write(contenu)
            fw.flush()
            fw.close()
        }
        catch (IOException ioe) {
            log.error(IOException: ioe.getMessage())
        }
    }


    def saveChargementMenuMap() {

        log.debug(JsonOutput.toJson([method: "saveChargementMenuMap", message: "Début de chargement des menu maps"]))

        MenuMap menuMapParent = null
        def menuMapParentIsExist = null
        def menuMapIsToDeletedParent = false
        def menuMapIsToDeletedChild = false

        def resultParent
        def returnMap = []
        //def jsonFile = new File("${path}WEB-INF/menuMapFile.json")
        String path = getAppPath("menuMapFile")
        def jsonFile = new File(path)

        if (jsonFile.exists()) {
            returnMap = jsonSlurper.parse(jsonFile)
            returnMap.collect {
                //Appel de la fonction : creation menu Map parent
                resultParent = createMenuMapParent(it.menuMapParent, menuMapParent, menuMapParentIsExist, menuMapIsToDeletedParent)

                log.debug(JsonOutput.toJson([method: "saveChargementMenuMap", menuMapParent: it.menuMapParent]))

                //Appel de la fonction : creation menu Map child
                if (!resultParent.menuMapIsToDeletedParent) {
                    createMenuMapChild(it.menuMapChild, resultParent.menuMapParent, resultParent.menuMapParentIsExist, menuMapIsToDeletedChild)
                }
            }
        }

        log.debug(JsonOutput.toJson([method: "saveChargementMenuMap", message: "Fin de chargement des menu maps"]))

    }

    def createMenuMapParent(List<MenuMap> menuMapList, MenuMap menuMapParent, MenuMap menuMapParentIsExist, def menuMapIsToDeletedParent) {

        menuMapList.each {
            Module module = Module.findByCode(it?.module?.toString())
            menuMapParentIsExist = MenuMap.findByUrl(it.url?.toString())
            menuMapIsToDeletedParent = (it.deleted as boolean)

            log.debug("********* menuMapIsToDeleted boolean *********** " + menuMapIsToDeletedParent)
            log.debug("********* menuMapParentIsExist *********** " + menuMapParentIsExist + " id " + menuMapParentIsExist?.id)

            if (it.containsKey('deleted') && menuMapIsToDeletedParent && menuMapParentIsExist) {
                deleteMenuParent(menuMapParentIsExist)
            } else {

                if (!menuMapIsToDeletedParent && menuMapParentIsExist == null) {
                    menuMapParent = new MenuMap()
                    menuMapParent = createNewParent(menuMapParent, it, module)
                } else {
                    updateMenuMap(menuMapParentIsExist, it)
                }
            }
        }
        return [menuMapParentIsExist: menuMapParentIsExist, menuMapParent: menuMapParent, menuMapIsToDeletedParent: menuMapIsToDeletedParent]
    }

    def createMenuMapChild(List<MenuMap> menuMapList, MenuMap menuMapParent, MenuMap menuMapParentIsExist, def menuMapIsToDeletedChild) {

        menuMapList.each {

            MenuMap menuMapChild = new MenuMap()
            menuMapIsToDeletedChild = (it.deleted as boolean)

            if (menuMapParentIsExist == null && menuMapParent != null) {

                def menuMapChildIsExist = MenuMap.findByUrlAndParent(it.url, menuMapParent)

                if (it.containsKey('deleted') && menuMapIsToDeletedChild && menuMapChildIsExist) {
                    menuMapChildIsExist.delete(flush: true)
                } else {

                    if (menuMapChildIsExist == null) {

                        createNewChild(menuMapChild, it, menuMapParent)
                    } else {
                        updateMenuMap(menuMapChildIsExist, it)
                    }
                }
            } else {
                def menuMapChildIsExist = MenuMap.findByUrlAndParent(it.url, menuMapParentIsExist)

                if (it.containsKey('deleted') && menuMapIsToDeletedChild && menuMapChildIsExist) {
                    menuMapChildIsExist.delete(flush: true)
                } else {

                    if (menuMapChildIsExist == null) {
                        createNewChild(menuMapChild, it, menuMapParentIsExist)
                    } else {
                        updateMenuMap(menuMapChildIsExist, it)
                    }
                }
            }
        }
    }

    def deleteMenuParent(MenuMap menuMapParentIsExist) {

        log.debug("****************** -- Début de suppression des menus maps ${menuMapParentIsExist} -- ******************")

        def menuMapChildList = MenuMap.findAllByParent(menuMapParentIsExist)

        menuMapChildList.each {
            log.debug(JsonOutput.toJson([method: "deleteMenuParent", message: "deleting menumap..", url: it.url, label: it.label]))
            it.delete(flush: true)
        }

        menuMapParentIsExist.delete(flush: true)

        log.debug("****************** -- Fin de suppression des menus maps effectuées avec succès -- ******************")
    }

    def updateMenuMap(MenuMap menuMapExist, def menuMapInFileJSon) {

        log.debug("****************** -- Debut de la cycleMise à jours des menus maps -- ******************")

        log.debug(" menuMapExist ==> " + menuMapExist)

        if (menuMapExist != null) {
            log.debug(" menuMapInFileJSon?.defaultMessage?.toString() " + menuMapInFileJSon?.defaultMessage?.toString())
            menuMapExist.save(flush: true)
            log.debug(JsonOutput.toJson([method: "updateMenuMap", label: menuMapExist.label, url: menuMapExist.url, message: "update OK"]))
        }

        log.debug("****************** -- Fin de la cycleMise à jours des menus maps -- ******************")

    }

    def createNewParent(MenuMap menuMapParent, def menuMapInFileJSon, Module module) {
        menuMapParent.label = menuMapInFileJSon.label?.toString()
        menuMapParent.url = menuMapInFileJSon.url?.toString()
        menuMapParent.module = module
        menuMapParent.save(flush: true)

        return menuMapParent
    }

    def createNewChild(MenuMap menuMapChild, def menuMapInFileJSon, MenuMap menuMapParent) {
        menuMapChild.label = menuMapInFileJSon.label
        menuMapChild.url = menuMapInFileJSon.url
        menuMapChild.parent = menuMapParent
        menuMapChild.save(flush: true)

    }


}
