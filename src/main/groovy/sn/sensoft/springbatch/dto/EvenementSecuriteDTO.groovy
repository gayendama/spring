package sn.sensoft.springbatch.dto

class EvenementSecuriteDTO {
    String typeEvenement
    String username // utilisateur concerné par la modifcation
    String usernameModif // Utilisateur ayant effectué la modification
    Map<String, String> deletedUserBureau
    List<Map<String, String>> newUserBureauList = []
    String userClient
    List<String> addedRoleList
    List<String> deletedRoleList
    String ipAddress
    String updatedRole
    String batchStep
    List<Map<String, String>> updatedConfigList = []
    List<Map<String, String>> newConfigList = []
    Map<String, String> userUpdatedFields
    Date dateTraitement

    def logInfos() {
        return [
                typeEvenement    : typeEvenement,
                username         : username,
                usernameModif    : usernameModif,
                addedRoleList    : addedRoleList,
                deletedRoleList  : deletedRoleList,
                ipAddress        : ipAddress,
                updatedConfigList: updatedConfigList,
                newConfigList    : newConfigList,
                userUpdatedFields: userUpdatedFields
        ]
    }

}
