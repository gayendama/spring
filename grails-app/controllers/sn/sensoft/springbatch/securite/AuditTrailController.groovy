package sn.sensoft.springbatch.securite

import grails.gorm.transactions.Transactional
import sn.sensoft.springbatch.TypeEntite


@Transactional(readOnly = true)
class AuditTrailController {

    def securityUtils

    static allowedMethods = [save: "POST", update: "PUT"]

    def index() {

        List<AuditTrail> historyEntityList = AuditTrail.findAllByPersistedObjectIdAndClassName(params.objectId, params.objectName)
        TypeEntite typeEntite = TypeEntite.(params.objectName)
        [historyEntityList: historyEntityList, historyEntityCount: historyEntityList.size(), entityName: typeEntite.name, entityId: params.objectId, entityController: typeEntite.controller]
    }
}