package sn.sensoft.springbatch.securite

import grails.gorm.transactions.Transactional
import sn.sensoft.springbatch.exception.SpringbatchException

@Transactional
class RoleService {

    def habilitationService

    void saveOrUpdateRequestmap(String menuMapId,String accessUrl, List<MenuMap> accesses) throws SpringbatchException, IllegalArgumentException {

        def menuMapInstance = MenuMap.get(menuMapId.toLong())
        def requestmapInstance = Requestmap.findByUrl(menuMapInstance.url)

        // Revocation d'acces
        if ((accessUrl==null) || ("".equals(accessUrl.trim())) || !accessUrl.equalsIgnoreCase("on")){
            if (requestmapInstance && accesses?.url.contains(requestmapInstance?.url)) {
                if (!habilitationService.updateAccess(requestmapInstance, roleInstance.authority)) {
                    throw new SpringbatchException("Révocation accès impossible!")
                }
            }
        }

        else{
            if (requestmapInstance) {
                if (!accesses?.url.contains(requestmapInstance?.url)) {
                    requestmapInstance.configAttribute += ", ${roleInstance.authority}"
                    if(!requestmapInstance.save()){
                        throw new SpringbatchException(" Erreur sur la creation request Map   =>>  ${requestmapInstance.errors}")
                    }
                }
            }
            else {
               Requestmap request = new Requestmap(
                        url: menuMapInstance?.url,
                        configAttribute: roleInstance?.authority,
                        isForSystem: false
                )
                if(!request.save()){
                    throw new SpringbatchException(" Erreur sur la creation request  =>>  ${request.errors}")
                }
            }
        }



    }
}
