package sn.sensoft.springbatch.securite

import grails.gorm.transactions.Transactional
import sn.sensoft.springbatch.exception.SpringbatchException

import static org.springframework.http.HttpStatus.*

@Transactional()
class RoleController {

    static allowedMethods = [save: "POST", update: "PUT"]

    def habilitationService
    def keycloakHabilitationService

    def index() {
        respond roleList: Role.list(), model: [roleCount: Role.count()]
    }

    def show(Role roleInstance) {
        def moduleInstanceList = habilitationService.getAllModuleAccesses(roleInstance?.authority)

        [roleInstance: roleInstance, moduleInstanceList: moduleInstanceList]
    }

    def create() {
        respond roleInstance: new Role(params)
    }

    @Transactional
    def save(Role roleInstance) {
        if (roleInstance == null) {

            notFound()
            return
        }

        if (roleInstance.hasErrors() || !roleInstance.validate() || !roleInstance.save(flush: true)) {

            respond roleInstance.errors, view: 'create', model: [roleInstance: roleInstance]
            return
        }


        flash.message = message(code: 'default.created.message', args: [message(code: 'role.label', default: 'Role'), roleInstance.toString()])
        redirect roleInstance
    }

    def edit(Role roleInstance) {
        List<Module> moduleInstanceList = Module.list()
        def allModuleAccess = habilitationService.getAllModuleAccesses(roleInstance.authority)
        if (!roleInstance) {
            flash.error = "Il n'existe pas de rôle avec l'id ${params.id} dans la base!"
            redirect action: 'index'

            return
        }

        return [roleInstance: roleInstance, accessInstanceList: moduleInstanceList, moduleInstanceList: allModuleAccess]
    }

    @Transactional
    def update(Role roleInstance) {
        if (roleInstance == null) {

            notFound()
            return
        }

        if (roleInstance.hasErrors() || !roleInstance.validate() || !roleInstance.save(flush: true)) {

            respond roleInstance.errors, view: 'edit', model: [roleInstance: roleInstance]
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'role.label', default: 'Role'), roleInstance.toString()])
        redirect roleInstance
    }

    @Transactional
    def delete(Role roleInstance) {

        if (roleInstance == null) {

            notFound()
            return
        }

        roleInstance.delete()

        flash.message = message(code: 'default.deleted.message', args: [message(code: 'role.label', default: 'Role'), roleInstance.toString()])
        redirect action: "index"
    }

    protected void notFound() {
        flash.error = message(code: 'default.not.found.message', args: [message(code: 'role.label', default: 'Role'), params.id])
        redirect action: "index"
    }

    def orgGetParentNode() {

        Role roleInstance = Role.get(params.idRole)
        Module moduleInstance = Module.get(params.idModule)
        List<MenuMap> parentInstanceList = MenuMap.findAll("from MenuMap m where m.module.id=:moduleId and m.parent is null order by m.url, m.label ", [moduleId: moduleInstance.id])

        def allAccess = habilitationService.getAllAccess(roleInstance.authority)

        if (params.actionToDo && params.actionToDo == "EDIT") {
            log.debug("editAccess : roleInstance=${roleInstance}, parentInstanceList.size()= ${parentInstanceList.size()}, moduleInstance= ${moduleInstance}, allAccess.size()= ${allAccess.size()} ")
            render(template: "formEditAccess", model: [roleInstance: roleInstance, parentInstanceList: parentInstanceList, moduleInstance: moduleInstance, accessInstanceList: allAccess])
        }
        else {
            render(template: "parent", model: [parentInstanceList: parentInstanceList, moduleInstance: moduleInstance, accessInstanceList: allAccess])
        }

    }

    def orgGetfilsNode() {
        MenuMap parentInstance = MenuMap.get(params.parentId)

        List<MenuMap> filsInstanceList = MenuMap.findAll("from MenuMap mm where mm.parent=:par order by mm.url asc", [par: parentInstance])

        render(template: "fils", model: [filsInstanceList: filsInstanceList, parentInstance: parentInstance])
    }

    def orgGetfilsAndParentNode() {
        log.debug("Paramétre orgGetfilsAndParentNode >> ${params}")
        Module moduleInstance = Module.get(params.moduleId)
        List<MenuMap> parentInstanceList = MenuMap.findAll("from MenuMap mm where mm.module=:mod order by mm.url asc", [mod: moduleInstance])
        render(template: "formEditAccess", model: [parentInstanceList: parentInstanceList, moduleInstance: moduleInstance])
    }


    @Transactional
    def saveEditAccess() {

        try {
            def objet = params.url?.id
            Role roleInstance = Role.get(params.roleId)
            Module moduleInstance = Module.get(params.moduleId)
            def accesses = habilitationService.getAllAccess(roleInstance.authority)

            if (objet instanceof String) {

                def menuMapInstance = MenuMap.get(objet)
                def requestmapInstance = Requestmap.findByUrl(menuMapInstance.url)

                if (params.access && params."access.${objet}" && params."access.${objet}" != "") {
                    def accessUrl = params."access.${objet}"

                    if (accessUrl.equalsIgnoreCase("on")) {
                        if (requestmapInstance) {
                            if (!accesses?.url.contains(requestmapInstance?.url)) {
                                requestmapInstance.configAttribute += ", ${roleInstance.authority}"

                                requestmapInstance.save(flush: true)
                            }

                        }
                        else {
                            new Requestmap(
                                    url: menuMapInstance?.url,
                                    configAttribute: roleInstance?.authority
                            ).save(flush: true)
                        }

                    }
                    else {
                        if (requestmapInstance && accesses?.url.contains(requestmapInstance?.url)) {
                            if (!habilitationService.updateAccess(requestmapInstance, roleInstance.authority)) {
                                throw new SpringbatchException("Révocation accès impossible!")
                            }
                        }
                    }

                }
                else {
                    if (requestmapInstance && accesses?.url.contains(requestmapInstance?.url)) {

                        if (!habilitationService.updateAccess(requestmapInstance, roleInstance.authority)) {
                            throw new SpringbatchException("Révocation accès impossible!")
                        }
                    }
                }

            }
            else {

                objet.each { menuMapId ->

                    def menuMapInstance = MenuMap.get(menuMapId)
                    def requestmapInstance = Requestmap.findByUrl(menuMapInstance.url)

                    if (params.access && params."access.${menuMapId}" && params."access.${menuMapId}" != "") {
                        def accessUrl = params."access.${menuMapId}"

                        if (accessUrl.equalsIgnoreCase("on")) {

                            if (requestmapInstance == null) {
                                requestmapInstance = new Requestmap(
                                        url: menuMapInstance?.url,
                                        configAttribute: roleInstance?.authority
                                ).save(flush: true)
                            }

                            if (!accesses?.url.contains(requestmapInstance?.url)) {
                                requestmapInstance.configAttribute += ", ${roleInstance.authority}"

                                requestmapInstance.save(flush: true)
                            }

                        }
                        else {
                            if (requestmapInstance && accesses?.url.contains(requestmapInstance?.url)) {
                                if (!habilitationService.updateAccess(requestmapInstance, roleInstance.authority)) {
                                    throw new SpringbatchException("Révocation accès impossible!")
                                }
                            }
                        }

                    }
                    else {
                        if (requestmapInstance && accesses?.url.contains(requestmapInstance?.url)) {
                            if (!habilitationService.updateAccess(requestmapInstance, roleInstance.authority)) {
                                throw new SpringbatchException("Révocation accès impossible!")
                            }
                        }
                    }
                }

//                keycloakHabilitationService.clearCachedRequestmaps()
                flash.message = "L'édition des droits d'accès sur le module ${moduleInstance?.libelle} s'est déroulé avec succès !"
            }

            keycloakHabilitationService.saveRequestmapToFile()

        }
        catch (SpringbatchException e) {
            log.error(e.message)

            flash.error = "Erreur rencontrée lors de la sauvegarde des modifications des droits d'accès du role : ${e.message}"
        }

        redirect action: 'edit', id: params.roleId
    }

}
