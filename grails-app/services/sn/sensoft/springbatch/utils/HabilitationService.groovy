package sn.sensoft.springbatch.utils

import com.fasterxml.jackson.databind.Module
import grails.gorm.transactions.Transactional
import grails.web.context.ServletContextHolder
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import sn.sensoft.springbatch.securite.MenuMap
import sn.sensoft.springbatch.securite.Requestmap

@Transactional
class HabilitationService {


    // def keycloakHabilitationService
    def grailsApplication
    def gsps = []

    def getAllModule() {
        return Module.list()
    }

    @Transactional
    def parcourDos() {
        log.info(JsonOutput.toJson([method: "parcourDos", message: "start"]))
        def result
        if (grailsApplication.isWarDeployed()) {
            result = findWarGsps('/WEB-INF/classes', gsps)
        }
        else {
            result = findDevGsps('grails-app/views', gsps)
        }

        result.each() { res ->

            String completeUrl = res
            String labelMenu = "${completeUrl.replace("/", ".")}"
            if (res.contains('/') && !res.contains('ajaxGet')) {
                def splitMap = completeUrl.split('/')
                String labelParent = "${splitMap[0].replace("/", ".")}"
                if (!splitMap[0].contains('layouts')) {
                    def menuMapExist = MenuMap.findByUrl("/" + splitMap[0])
                    if (!menuMapExist) {
                        def menuMapParent = new MenuMap()
                        menuMapParent.label = labelParent
                        menuMapParent.url = "/" + splitMap[0]

                        menuMapParent.save(flush: true)
                    }

                    def menuFilsExist = MenuMap.findByUrl("/" + res)
                    if (!menuFilsExist) {
                        def menuMapFils = new MenuMap()
                        menuMapFils.parent = MenuMap.findByUrl("/" + splitMap[0])
                        menuMapFils.url = "/" + res

                        // Renseignement automatique du champ label pour le fils
                        menuMapFils.label = labelMenu
                        // End Renseignement automatique du champ label pour le fils
                        menuMapFils.save(flush: true)

                    }
                    else if (menuFilsExist.label == null || menuFilsExist.label == "") {
                        // Renseignement automatique du champ label pour le fils
                        menuFilsExist.label = labelMenu
                        // End Renseignement automatique du champ label pour le fils
                        menuFilsExist.save(flush: true)

                    }
                }
            }
        }

        log.info(JsonOutput.toJson([method: "parcourDos", message: "end"]))
    }

    private findDevGsps(current, gsps) {
        for (file in new File(current).listFiles()) {
            if (file.path.endsWith('.gsp') && !file.path.contains('_')) {
                gsps << file.path - 'grails-app/views/' - '.gsp'
            }
            else {
                findDevGsps file.path, gsps
            }
        }
        return gsps
    }

    private findWarGsps(current, gsps) {
        def servletContext = ServletContextHolder.servletContext
        for (path in servletContext.getResourcePaths(current)) {
            if (path.endsWith('.gsp') && !path.contains('_')) {
                gsps << path - '/WEB-INF/classes/' - '.gsp'
            }
            else {
                findWarGsps path, gsps
            }
        }
        return gsps
    }
    def getRoleSubMenu(String url) {

        def servletContext = ServletContextHolder.servletContext

        def jsonSlurper = new JsonSlurper()
        def reader = new BufferedReader(new InputStreamReader(new FileInputStream(servletContext.getRealPath("WEB-INF/Requestmap.json")), "UTF-8"))
        def data = jsonSlurper.parse(reader)

        def role = "ROLE_USER"
        for (requestmap in data) {
            if (url.equalsIgnoreCase(requestmap.url)) {
                role = "${requestmap.configAttribute}"
                break
            }
        }
        return role
    }

    def getRoleMenu(List urlList) {

        def servletContext = ServletContextHolder.servletContext

        def jsonSlurper = new JsonSlurper()
        def reader = new BufferedReader(new InputStreamReader(new FileInputStream(servletContext.getRealPath("WEB-INF/Requestmap.json")), "UTF-8"))
        def data = jsonSlurper.parse(reader)

        def roles = ""
        def tabRoles = []
        for (requestmap in data) {
            if (urlList.contains(requestmap.url)) {

                requestmap.configAttribute.split(",").each { configAttribute ->
                    if (!tabRoles.contains(configAttribute.trim())) {
                        tabRoles << configAttribute.trim()
                    }
                }
            }
        }

        tabRoles.each {
            roles += "," + it
        }

        return roles
    }



    @Transactional()
    def createRecursiveRequestmapOnParentViewDir(String viewDir, String configAttribute, Collection exclude) {
        def menuMapList
        if (exclude != null && exclude.size() > 0) {
            String excludeOption = ""
            exclude.each { excludedUrl ->
                excludeOption += " and m.url not like '/${viewDir}/${excludedUrl}%'"
            }
            menuMapList = MenuMap.findAll("from MenuMap m where m.url like :url${excludeOption}", [url: "/${viewDir}/%"])

        }
        else {
            menuMapList = MenuMap.findAll("from MenuMap m where m.url like :url", [url: "/${viewDir}%"])
        }
        if (menuMapList.size() == 0) {
            return false
        }

        menuMapList.url.each { url ->
            configAttribute.replace(" ", "").split(",").each {
                def requestMap = Requestmap.findByUrl(url)
                if (requestMap) {
                    def listConfigAttribute = requestMap.configAttribute.replace(" ", "").split(",")
                    if (!listConfigAttribute.contains(it)) {
                        requestMap.configAttribute = requestMap.configAttribute.trim() + ", " + it
                    }
                }
                else {
                    requestMap = new Requestmap()
                    requestMap.configAttribute = it
                    requestMap.url = url

                }
                requestMap.save(flush: true)
            }
        }
    }

    def getAllAccess(String role) {
        def linkList = []
        def accessList = Requestmap.findAll("from Requestmap r WHERE r.configAttribute like :role", [role: "%${role}%"])

        accessList.each {
            def menuMap = MenuMap.findByUrl(it.url)
            if (menuMap)
                linkList << menuMap
        }

        return linkList
    }

    def getAllModuleAccesses(String role) {
        def linkList = []
        def accessList = Requestmap.findAllByConfigAttribute(role)

        accessList.each {
            def menuMap = MenuMap.findByUrl(it.url)
            if (menuMap?.parent?.module && !linkList.contains(menuMap?.parent?.module))
                linkList.add(menuMap?.parent?.module)
        }

        return linkList
    }

    def getAllAccessesByModule(String role, Module module) {
        def linkList = []
        def accessList = Requestmap.findAll("from Requestmap r WHERE r.configAttribute like :role", [role: "%${role}%"])

        accessList.each {
            def menuMap = MenuMap.findByUrl(it.url)
            if (menuMap && module.equals(menuMap.module))
                linkList << menuMap
        }

        return linkList
    }

    @Transactional
    def updateAccess(Requestmap requestmap, String authority) {

        if (requestmap) {
            def stri = requestmap?.configAttribute?.replaceAll(/\s+/, "")?.split(",") as Collection
            stri.removeAll(authority)
            stri = stri.join(", ")

            if (stri != "") {
                requestmap.configAttribute = stri
                requestmap.save(flush: true)
            }
            else {
                requestmap.delete()
            }
            // keycloakHabilitationService.saveRequestmapToFile()
            return true
        }

    }

    def updateAuthorityOnRequestmap(String oldAuthority, String newAuthority) {
        if (oldAuthority == null || "".equals(oldAuthority?.trim())) {
            throw new IllegalArgumentException("Le rôle à modifier est manquant")
        }
        if (newAuthority == null || "".equals(newAuthority?.trim())) {
            throw new IllegalArgumentException("Le nouveau rôle est manquant")
        }
        Requestmap.findAll().grep { it.configAttribute.matches(/.*${oldAuthority}(?![A-Z]),?\s?.*/) }.each {
            log.debug(JsonOutput.toJson([method: "updateAuthorityOnRequestmap", message: it.configAttribute]))
            it.configAttribute.replaceAll(/${oldAuthority}(?![A-Z])/, newAuthority)
            log.debug(JsonOutput.toJson([method: "updateAuthorityOnRequestmap", message: it.configAttribute]))
            it.save(flush: true)
        }

    }

}
