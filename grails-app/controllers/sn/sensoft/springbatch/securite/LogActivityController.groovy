package sn.sensoft.springbatch.securite

import grails.converters.JSON
import grails.gorm.transactions.Transactional
import groovy.json.JsonOutput
import sn.sensoft.springbatch.TypeEntite

@Transactional(readOnly = true)
class LogActivityController {

    def securityUtils
    def runtimeConfigService

    static allowedMethods = [save: "POST", update: "PUT"]

    def index() {

        List<LogActivity> historyEntityList = LogActivity.findAllByObjectIdAndObjectName(params.objectId, params.objectName, [sort: "dateCreated", order: "desc"])
        TypeEntite typeEntite = TypeEntite.(params.objectName)
        [historyEntityList: historyEntityList, historyEntityCount: historyEntityList.size(), entityName: typeEntite.name, entityId: params.objectId, entityController: typeEntite.controller]
    }

    def ajaxLogActivity() {
        TypeEntite typeEntite = TypeEntite.(params.objectName)
        List<LogActivity> logActivityList = LogActivity.findAllByObjectIdAndObjectName(params.objectId, params.objectName, [sort: "dateCreated", order: "desc"])
        List<AuditTrail> auditTraitList = AuditTrail.findAllByPersistedObjectIdAndClassName(params.objectId, typeEntite.controller.capitalize(), [sort: "dateCreated", order: "desc"])
        render(template: "/layouts/historyObject", model: [logActivityList: logActivityList, auditTraitList: auditTraitList])
    }

    def list() {
        log.debug(JsonOutput.toJson([method: "list", params: params]))
        def parametres = null
        if ("1".equals(params.fromSearchView)) {
            session.searchParams = params
            parametres = params
        }
        else {
            parametres = session.searchParams
        }
        return [searchParams: parametres]
    }

    def loadData(){
        // Recuperation des information provenant du formulaire de recherche
        Map searchParams = [:]
        if (params.searchParams != null) {
            searchParams = runtimeConfigService.searchParams(params.searchParams)
        }

        def sortIndex = params.sort ? params.sort : 'dateCreated'
        def sortOrder = params.order ? params.order : 'asc'
        def maxRows = params.limit ? Integer.valueOf(params.limit) : 10
        def offset = params.offset ? Integer.valueOf(params.offset) : 0


        def logActivityList = LogActivity.createCriteria().list(max: maxRows, offset: offset) {

            def dateDebut
            def dateFin

            if(searchParams.dateCreated != null && !"".equals(searchParams.dateCreated)) {
                dateDebut = DateUtils.stringToDate(searchParams.dateCreated, "dd/MM/yyyy")
                use(groovy.time.TimeCategory) {
                    dateFin = dateDebut + 1.days
                }
            }

            //******* DEB formulaire de recherche **********
            if (searchParams.size() > 0) {
                if (searchParams?.typeLog != null && !"".equals(searchParams.typeLog)) {
                    ilike('typeLog', "%${searchParams?.typeLog}%")
                }
                if (searchParams?.objectName != null && !"".equals(searchParams.objectName)) {
                    ilike('objectName', "%${searchParams?.objectName}%")
                }
                if (searchParams?.message != null && !"".equals(searchParams.message)) {
                    ilike('message', "%${searchParams?.message}%")
                }
                if (searchParams?.dateCreated != null && !"".equals(searchParams.dateCreated)) {
                    between("dateCreated", dateDebut, dateFin)
                }
            }
            //******* FIN formulaire de ccrecherche **********

            if (params.search != null && !"".equals(params.search)) {
                or {
                    ilike('typeLog', "%${params.search}%")

                    ilike('objectName', "%${params.search}%")

                    ilike('message', "%${params.search}%")
                }
            }

            order(sortIndex, sortOrder)
        }


        def totalRows = logActivityList.totalCount
        def collection = logActivityList.collect {
            [
                    id                  : it.id,
                    typeLog             : it.typeLog,
                    objectName          : it.objectName,
                    message             : it.message,
                    username            : it.user?.username,
                    dateComptable       : it.dateComptable?.format("dd/MM/yyyy"),
                    dateCreated         : it.dateCreated?.format("dd/MM/yyyy")
            ]
        }

        def result = [total: totalRows, rows: collection]


        render result as JSON
    }

    def recherche() {
        def objetNameList = LogActivity.executeQuery("select objectName from LogActivity group by objectName  order by objectName")
        def typeLogList = LogActivity.executeQuery("select typeLog from LogActivity group by typeLog order by typeLog")

        log.debug(JsonOutput.toJson([method: "recherche", params: params]))
        [objetNameList: objetNameList, typeLogList: typeLogList]
    }
}
