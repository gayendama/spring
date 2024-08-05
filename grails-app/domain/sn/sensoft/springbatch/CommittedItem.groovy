package sn.sensoft.springbatch


class CommittedItem {

    String id = UUID.randomUUID().toString()

    String batchName
    String stepName
    String objet
    String objetId
    Date dateTraitement
    Integer responseCode //200 = ok, 400 = bad request, 401 = unhautorized, 500 = erreur serveur
    String responseMessage

    static constraints = {
        batchName nullable: false
        stepName nullable: false
        objetId nullable: false, unique: true
        responseCode nullable: false
    }

    static mapping = {
        id generator: 'assigned'
        responseMessage type: "text"
    }

    Date dateCreated
    def beforeInsert = {
        dateCreated = new Date()
    }
}
