package sn.sensoft.springbatch.communication

import sn.sensoft.springbatch.utils.Constantes

import javax.persistence.Version

class ComMessage {

    String id = UUID.randomUUID().toString()
    @Version
    Long version

    String uid = UUID.randomUUID().toString();
    String typeMessage
    Date dateDepot
    Date dateTraitement
    String refTraitement
    String statut = Constantes.STATUT_SAISI
    String errorMessage
    String emailUid // L'uid du message email retourné par ApiComm
    String smsUid // L'uid du message sms retourné par ApiComm
    byte[] messageContains // objet json contenant le message à envoyer de la forme suivante :
    Boolean toSend = true

    static mapping = {
        errorMessage type: "text"
        id generator: 'assigned'
    }

    static constraints = {
        uid blank: false, nullable: false, unique: true
    }

    String toString() {
        return "${uid}-${typeMessage}"
    }

}
