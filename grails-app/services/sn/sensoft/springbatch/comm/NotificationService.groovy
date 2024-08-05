package sn.sensoft.springbatch.comm

import groovy.json.JsonOutput
import io.micronaut.http.client.exceptions.HttpClientResponseException
import org.grails.web.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.SerializationUtils
import org.springframework.web.client.ResourceAccessException
import sn.sensoft.springbatch.api.ApicomClient
import sn.sensoft.springbatch.communication.ComMessage
import sn.sensoft.springbatch.communication.CommEvenement
import sn.sensoft.springbatch.dto.EvenementSecuriteDTO
import sn.sensoft.springbatch.exception.SpringbatchException
import sn.sensoft.springbatch.securite.AppPartner
import sn.sensoft.springbatch.securite.User
import sn.sensoft.springbatch.utils.Constantes
import sn.sensoft.springbatch.utils.ConstantesSecurite
import sn.sensoft.springbatch.utils.DateUtils


@Transactional
class NotificationService {

    def runtimeConfigService
    def apiManagerService
    def tools

    @Autowired
    final ApicomClient apicomClient

    /**
     * Traiter tous les messages en attente d'envoi.
     */
    def processMessage() {

        def comMessageList = ComMessage.findAllByStatutAndToSend(Constantes.STATUT_SAISI, true)

        comMessageList.each { comMessage ->
            notifier(comMessage)
        }

    }

    /**
     * Envoyer les messages à l'API_COMM
     *
     * @param comMessageJsonObject
     * @return boolean
     */
    boolean notifier(ComMessage comMessage) {
        final String methodName = 'notifier'

        log.debug(JsonOutput.toJson([method: methodName]))

        try {

            String contenuJson = SerializationUtils.deserialize(comMessage.messageContains)
            log.debug(JsonOutput.toJson([method: "notifier", contenuJson: contenuJson]))
            NotificationResponseDTO notificationResponseDTO = callApiComm(contenuJson)

            if (notificationResponseDTO.code == Constantes.STATUT_SEND_APICOM_OK) {
                comMessage.statut = Constantes.STATUT_VALIDE
                comMessage.dateTraitement = new Date()
                comMessage.emailUid = notificationResponseDTO.emailUid
                comMessage.smsUid = notificationResponseDTO.smsUid
                if(!comMessage.save(flush: true)){
                    throw new SpringbatchException("Error mis à jour comMessage ${comMessage.id}, ${comMessage.errors.allErrors.join('\\n')}")
                }
            }
            else {
                // Erreur fonctionnelle, toper le message.
                comMessage.statut = Constantes.STATUT_NOT_OK
                comMessage.dateTraitement = new Date()
                comMessage.errorMessage = notificationResponseDTO.message
                if(!comMessage.save(flush: true)){
                    throw new SpringbatchException("Error mis à jour comMessage ${comMessage.id}, ${comMessage.errors.allErrors.join('\\n')}")
                }
            }

            return true

        }
        catch (SpringbatchException ex) {
//            log.error(JsonOutput.toJson([method: "notifier", status: "error", message: ex.message]))
            return false
        }
        catch (ResourceAccessException ex) {
//            log.error(JsonOutput.toJson([method: "notifier", status: "error", message: ex.message]))
            return false
        }
        catch (SocketTimeoutException ex) {
//            log.error(JsonOutput.toJson([method: "notifier", status: "error", message: ex.message]))
            return false
        }
        catch (HttpClientResponseException ex) {
            log.error(JsonOutput.toJson([method: "notifier", status: "HttpClientResponseException", message: ex.message]))
            return false
        }

    }

    /**
     * Envoyer les messages à l'API_COMM
     *
     * @param comMessageJsonObject
     * @return notificationResponseDTO
     */
    NotificationResponseDTO callApiComm(String comMessageJsonObject) throws SpringbatchException, ResourceAccessException, SocketTimeoutException {

        log.debug(JsonOutput.toJson([method: "callApiComm", comMessageJsonObject: comMessageJsonObject]))

        def resp = apicomClient.sendSmsMail(apiManagerService.apiComBearerToken(), comMessageJsonObject)
        if (resp.code() != 200) {
            throw new SpringbatchException(JsonOutput.toJson([method: "callApiComm", code: resp.code(), message: resp.body().toString()]))
        }

        // La réponse doit être de type json
        JSONObject jsonResponse = resp.body()
        if (!(jsonResponse instanceof JSONObject)) {
            throw new SpringbatchException("Type de réponse incorrecte, status=${resp.status().name()} , reponse=${jsonResponse.json} ")
        }

        NotificationResponseDTO notificationResponseDTO = new NotificationResponseDTO()
        notificationResponseDTO.code = jsonResponse.code
        notificationResponseDTO.message = jsonResponse.message
        notificationResponseDTO.emailUid = jsonResponse.emailUid
        notificationResponseDTO.smsUid = jsonResponse.smsUid

        log.debug(JsonOutput.toJson([method  : "callApiComm",
                                     code    : notificationResponseDTO.code,
                                     message : notificationResponseDTO.message,
                                     emailUid: notificationResponseDTO.message,
                                     smsUid  : notificationResponseDTO.smsUid]))

        return notificationResponseDTO

    }

    /**
     * Préparer les messages à envoyer, puis les sauvegarder pour les envoyer plus tard via un job.
     * @param objet
     * @param message
     * @return boolean (true=success, false=echec)
     */
    boolean sendBatchEmailToAdmin(String objet, String message) {
        final String methodName = 'sendBatchEmailToAdmin'

        boolean saveStatus

        String adminEmail = runtimeConfigService.getAdminEmail()
        if (adminEmail == null || "".equals(adminEmail)) {
            log.error(JsonOutput.toJson([method: "notificationBatchStart", message: "Aucun admin email trouvé pour les nottificatioon batch"]))
        }

        String[] emailList = adminEmail.split(";")

        if (message == null) {
            throw new IllegalArgumentException("message is null")
        }


        String typeMessage = Constantes.MESSAGE_MAIL
        Map messageToSend = createGenericMessageBatch(typeMessage, objet, message, emailList)
        saveStatus = saveMessageToSend(typeMessage, messageToSend)

        return saveStatus
    }

    /**
     * Sauvegarder les messages à envoyer plus tard par job
     * @param typeMessage
     * @param comMessageMap
     * @return boolean (true=success, false=echec)
     */
    boolean saveMessageToSend(String typeMessage,Map comMessageMap) throws SpringbatchException {
        final String methodName = 'saveMessageToSend'

        log.debug(JsonOutput.toJson([method: methodName, typeMessage: typeMessage]))

        //Boolean toSend = paramGenService.getParamValue(ConstantesParam.Param.FLAG_ALERT.code)
        Boolean toSend = true // TODO : A modifier ...


        ComMessage comMessage = new ComMessage()
        comMessage.typeMessage = typeMessage
        comMessage.dateDepot = new Date()
        comMessage.toSend = toSend
        comMessage.messageContains = SerializationUtils.serialize(JsonOutput.toJson(comMessageMap))
        if (!comMessage.save(flush: true)) {
            throw new SpringbatchException("Error save comMessage ${comMessage.errors.allErrors.join('\\n')}")
        }
        return true
    }


    /**
     *  Préparation du message à envoyer sous forme de Map
     *
     * @param message
     * @param typeMessage
     * @param emailList
     */
    Map createGenericMessageBatch(String typeMessage, String objet,  String message, String[] emailList) throws SpringbatchException {
        final String methodName = 'createGenericMessageBatch'

        def etablissemetCode  = runtimeConfigService.getApiComEstablishmentCode()

        if (etablissemetCode == null) {
            throw new SpringbatchException("etablissemetCode is null ")
        }

        if (typeMessage == null) {
            throw new IllegalArgumentException("typeMessage is null")
        }

        //String senderEmail = paramGenService.getParamValue(ConstantesParam.Param.COMM_SENDER_EMAIL.code)
        String senderEmail  = runtimeConfigService.getApiComSenderMail()

        def messageMap = [etabCode    : etablissemetCode,
                          templateCode: null,
                          paramMessage: null,
                          comEmail    : [
                                  fromEmailAddress: senderEmail,
                                  fromName        : etablissemetCode,
                                  toList          : emailList,
                                  ccList          : [],
                                  bccList         : [],
                                  subject         : objet,
                                  message         : message,
                                  attachmentList  : []
                          ],
                          comSms      : null,
                          typeMessage : typeMessage,
                          source      : Constantes.MESSAGE_SOURCE
        ]

        log.debug(JsonOutput.toJson([method: methodName, messageMap: messageMap]))

        return messageMap
    }

    def sendAlerteSecurite(EvenementSecuriteDTO evenementSecuriteDTO) {
        String dateSysteme = (new Date())?.format("dd/MM/yyyy HH:mm")
        String mailObjet
        String contenuMail

        if (runtimeConfigService.getSendSecurityAlert() != "true") {
            return
        }

        switch (evenementSecuriteDTO.typeEvenement) {

            case ConstantesSecurite.Event.UPDATE_REQUEST_MAP.code:
                contenuMail = "" +
                        "<div>Bonjour,</div>" +
                        "<div>&nbsp;</div>" +
                        "<div>Les habilitations ont été mis à jour pour les URls ci-dessous. le ${dateSysteme}</div>"

                if (evenementSecuriteDTO.newConfigList.size() > 0) {
                    contenuMail += "<div>&nbsp;</div>" +
                            "<div><b><u>Nouvelles attributions</u></b> :</div>" +
                            "<div>&nbsp;</div>"
                    evenementSecuriteDTO.newConfigList.each {
                        contenuMail += "<div>url: <b>${it.url}</b> , roles : [${it.roles}] </div>"
                    }
                }
                if (evenementSecuriteDTO.updatedConfigList.size() > 0) {
                    contenuMail += "<div>&nbsp;</div>" +
                            "<div><b><u>Attributions mises à jour</u></b> :</div>" +
                            "<div>&nbsp;</div>"
                    evenementSecuriteDTO.updatedConfigList.each {
                        contenuMail += "<div>url: <b>${it.url}</b> roles : [${it.roles}] </div>"
                    }
                }

                contenuMail += "<div>&nbsp;</div>" +
                        "<div>Date Comptable: <b>${evenementSecuriteDTO.dateTraitement}</b></div>" +
                        "<div>Date Systeme: <b>${dateSysteme}</b></div>" +
                        "<div>&nbsp;</div>" +
                        "<div>Mise à jour effectuée par <b>${evenementSecuriteDTO.usernameModif}</b> à partir de ${evenementSecuriteDTO.ipAddress} </div>" +
                        "<div>&nbsp;</div>" +
                        "<div>Cordialement.</div>" +
                        "<div>&nbsp;</div>"

                mailObjet = "Mise à jour des habilitations pour le rôle ${evenementSecuriteDTO.updatedRole} "
                break

            case ConstantesSecurite.Event.ADD_USER_BUREAU.code:
                contenuMail = "" +
                        "<div>Bonjour,</div>" +
                        "<div>&nbsp;</div>" +
                        "<div>Ajout de bureaux à l'utilisateur <b>${evenementSecuriteDTO.username}</b> </div>" +
                        "<div>&nbsp;</div>" +
                        "<div><b><u>Liste des bureaux</u></b> :</div>" +
                        "<div>&nbsp;</div>"
                evenementSecuriteDTO.newUserBureauList.each {
                    contenuMail += "<div>Bureau : <b>${it.codeBureau}-${it.libelleBureau}</b></div>"
                }
                contenuMail += "<div>&nbsp;</div>" +
                        "<div>Date Comptable: <b>${evenementSecuriteDTO.dateTraitement}</b></div>" +
                        "<div>Date Systeme: <b>${dateSysteme}</b></div>" +
                        "<div>&nbsp;</div>" +
                        "<div>Mise à jour effectuée par <b>${evenementSecuriteDTO.usernameModif}</b> à partir de ${evenementSecuriteDTO.ipAddress} </div>" +
                        "<div>&nbsp;</div>" +
                        "<div>Cordialement.</div>" +
                        "<div>&nbsp;</div>"

                mailObjet = "Ajout de bureaux à l'utilisateur  ${evenementSecuriteDTO.username}  "
                break

            case ConstantesSecurite.Event.DELETE_USER_BUREAU.code:
                contenuMail = "" +
                        "<div>Bonjour,</div>" +
                        "<div>&nbsp;</div>" +
                        "<div>Retrait de bureau à l'utilisateur <b>${evenementSecuriteDTO.username}</b> </div>" +
                        "<div>&nbsp;</div>"
                contenuMail += "<div>Bureau : <b>${evenementSecuriteDTO.deletedUserBureau.codeBureau}-${evenementSecuriteDTO.deletedUserBureau.libelleBureau}</b></div>"
                contenuMail += "<div>&nbsp;</div>" +
                        "<div>Date Comptable: <b>${evenementSecuriteDTO.dateTraitement}</b></div>" +
                        "<div>Date Systeme: <b>${dateSysteme}</b></div>" +
                        "<div>&nbsp;</div>" +
                        "<div>Mise à jour effectuée par <b>${evenementSecuriteDTO.usernameModif}</b> à partir de ${evenementSecuriteDTO.ipAddress} </div>" +
                        "<div>&nbsp;</div>" +
                        "<div>Cordialement.</div>" +
                        "<div>&nbsp;</div>"

                mailObjet = "Retrait de bureau à l'utilisateur ${evenementSecuriteDTO.username}  "
                break


            case ConstantesSecurite.Event.ADD_USER_CLIENT.code:
                contenuMail = "" +
                        "<div>Bonjour,</div>" +
                        "<div>&nbsp;</div>" +
                        "<div>Ajout de code client à l'utilisateur <b>${evenementSecuriteDTO.username}</b> </div>" +
                        "<div>&nbsp;</div>"
                contenuMail += "<div>Client : <b>${evenementSecuriteDTO.userClient}</b></div>"
                contenuMail += "<div>&nbsp;</div>" +
                        "<div>Date Comptable: <b>${evenementSecuriteDTO.dateTraitement}</b></div>" +
                        "<div>Date Systeme: <b>${dateSysteme}</b></div>" +
                        "<div>&nbsp;</div>" +
                        "<div>Mise à jour effectuée par <b>${evenementSecuriteDTO.usernameModif}</b> à partir de ${evenementSecuriteDTO.ipAddress} </div>" +
                        "<div>&nbsp;</div>" +
                        "<div>Cordialement.</div>" +
                        "<div>&nbsp;</div>"

                mailObjet = "Ajout de code client à l'utilisateur  ${evenementSecuriteDTO.username}  "
                break

            case ConstantesSecurite.Event.DELETE_USER_CLIENT.code:
                contenuMail = "" +
                        "<div>Bonjour,</div>" +
                        "<div>&nbsp;</div>" +
                        "<div>Retrait de code client à l'utilisateur <b>${evenementSecuriteDTO.username}</b> </div>" +
                        "<div>&nbsp;</div>"
                contenuMail += "<div>Client : <b>${evenementSecuriteDTO.userClient}</b></div>"
                contenuMail += "<div>&nbsp;</div>" +
                        "<div>Date Comptable: <b>${evenementSecuriteDTO.dateTraitement}</b></div>" +
                        "<div>Date Systeme: <b>${dateSysteme}</b></div>" +
                        "<div>&nbsp;</div>" +
                        "<div>Mise à jour effectuée par <b>${evenementSecuriteDTO.usernameModif}</b> à partir de ${evenementSecuriteDTO.ipAddress} </div>" +
                        "<div>&nbsp;</div>" +
                        "<div>Cordialement.</div>" +
                        "<div>&nbsp;</div>"

                mailObjet = "Retrait de code client à l'utilisateur ${evenementSecuriteDTO.username}  "
                break

            case ConstantesSecurite.Event.UPDATE_USER_FROM_KEYCLOAK.code:
                contenuMail = "" +
                        "<div>Bonjour,</div>" +
                        "<div>&nbsp;</div>" +
                        "<div>Mise à jour de l'utilisateur <b>${evenementSecuriteDTO.username}</b> à partir de Keycloak</div>" +
                        "<div>&nbsp;</div>" +
                        "<div><b><u>Attributs</u></b> :</div>" +
                        "<div>&nbsp;</div>"
                evenementSecuriteDTO.userUpdatedFields.each {
                    contenuMail += "<div>${it.key} : <b>${it.value}</b></div>"
                }
                contenuMail += "<div>&nbsp;</div>" +
                        "<div>Date Comptable: <b>${evenementSecuriteDTO.dateTraitement}</b></div>" +
                        "<div>Date Systeme: <b>${dateSysteme}</b></div>" +
                        "<div>&nbsp;</div>" +
                        "<div>Cordialement.</div>" +
                        "<div>&nbsp;</div>"

                mailObjet = "Mise à jour de l'utilisateur ${evenementSecuriteDTO.username} à partir de Keycloak "
                break

            case ConstantesSecurite.Event.NEW_USER_FROM_KEYCLOAK.code:
                contenuMail = "" +
                        "<div>Bonjour,</div>" +
                        "<div>&nbsp;</div>" +
                        "<div>Nouvel utilisateur <b>${evenementSecuriteDTO.username}</b> à partir de Keycloak</div>" +
                        "<div>&nbsp;</div>" +
                        "<div><b><u>Attributs</u></b> :</div>"
                evenementSecuriteDTO.userUpdatedFields.each {
                    contenuMail += "<div>${it.key} : <b>${it.value}</b></div>"
                }
                contenuMail += "<div>&nbsp;</div>" +
                        "<div>Date Comptable: <b>${evenementSecuriteDTO.dateTraitement}</b></div>" +
                        "<div>Date Systeme: <b>${dateSysteme}</b></div>" +
                        "<div>&nbsp;</div>" +
                        "<div>Cordialement.</div>" +
                        "<div>&nbsp;</div>"

                mailObjet = "Nouvel utilisateur  ${evenementSecuriteDTO.username} à partir de Keycloak "
                break

            case ConstantesSecurite.Event.UPDATE_USER_ROLE_FROM_KEYCLOAK.code:
                contenuMail = "" +
                        "<div>Bonjour,</div>" +
                        "<div>&nbsp;</div>" +
                        "<div>Mise à jour des rôles de l'utilisateur <b>${evenementSecuriteDTO.username}</b> à partir de Keycloak</div>"
                if (evenementSecuriteDTO.addedRoleList.size() > 0) {
                    contenuMail += "<div>&nbsp;</div>" +
                            "<div><b><u>Nouveaux rôles</u></b> :</div>"
                    evenementSecuriteDTO.addedRoleList.each {
                        contenuMail += "<div>Rôle : <b>${it}</b></div>"
                    }
                }
                if (evenementSecuriteDTO.deletedRoleList.size() > 0) {
                    contenuMail += "<div>&nbsp;</div>" +
                            "<div><b><u>Rôles retirés</u></b> :</div>"
                    evenementSecuriteDTO.deletedRoleList.each {
                        contenuMail += "<div>Rôle : <b>${it}</b></div>"
                    }
                }
                contenuMail += "<div>&nbsp;</div>" +
                        "<div>Date Comptable: <b>${evenementSecuriteDTO.dateTraitement}</b></div>" +
                        "<div>Date Systeme: <b>${dateSysteme}</b></div>" +
                        "<div>&nbsp;</div>" +
                        "<div>Cordialement.</div>" +
                        "<div>&nbsp;</div>"

                mailObjet = "Mise à jour des rôles de l'utilisateur ${evenementSecuriteDTO.username} à partir de Keycloak "
                break

            case ConstantesSecurite.Event.ACTIVATION_SEQUENCE_BATCH.code:
                contenuMail = "" +
                        "<div>Bonjour,</div>" +
                        "<div>&nbsp;</div>" +
                        "<div>Activation séquence batch <b>${evenementSecuriteDTO.batchStep}</b> par l'utilisateur <b>${evenementSecuriteDTO.username}</b> </div>"

                contenuMail += "<div>&nbsp;</div>" +
                        "<div>Date Comptable: <b>${evenementSecuriteDTO.dateTraitement}</b></div>" +
                        "<div>Date Systeme: <b>${dateSysteme}</b></div>" +
                        "<div>&nbsp;</div>" +
                        "<div>Cordialement.</div>" +
                        "<div>&nbsp;</div>"

                mailObjet = "Activation séquence batch ${evenementSecuriteDTO.batchStep} "
                break

            case ConstantesSecurite.Event.DESACTIVATION_SEQUENCE_BATCH.code:
                contenuMail = "" +
                        "<div>Bonjour,</div>" +
                        "<div>&nbsp;</div>" +
                        "<div>Forçage status du batch à complet par l'utilisateur <b>${evenementSecuriteDTO.username}</b> </div>"

                contenuMail += "<div>&nbsp;</div>" +
                        "<div>Date Comptable: <b>${dateTraitement}</b></div>" +
                        "<div>Date Systeme: <b>${dateSysteme}</b></div>" +
                        "<div>&nbsp;</div>" +
                        "<div>Cordialement.</div>" +
                        "<div>&nbsp;</div>"

                mailObjet = "Désactivation séquence batch ${evenementSecuriteDTO.batchStep} "
                break

            case ConstantesSecurite.Event.FORCAGE_BATCH_COMPLET.code:
                contenuMail = "" +
                        "<div>Bonjour,</div>" +
                        "<div>&nbsp;</div>" +
                        "<div>Forçage status du batch à complet par l'utilisateur <b>${evenementSecuriteDTO.username}</b> </div>"

                contenuMail += "<div>&nbsp;</div>" +
                        "<div>Date Comptable: <b>${evenementSecuriteDTO.dateTraitement}</b></div>" +
                        "<div>Date Systeme: <b>${dateSysteme}</b></div>" +
                        "<div>&nbsp;</div>" +
                        "<div>Cordialement.</div>" +
                        "<div>&nbsp;</div>"

                mailObjet = "Forçage status du batch à complet  "
                break

            case ConstantesSecurite.Event.FORCAGE_NEW_INSTANCE_MEME_JOURNEE.code:
                contenuMail = "" +
                        "<div>Bonjour,</div>" +
                        "<div>&nbsp;</div>" +
                        "<div>Forçage lancement d'une nouvelle instance dans la même journée par l'utilisateur <b>${evenementSecuriteDTO.username}</b> </div>"

                contenuMail += "<div>&nbsp;</div>" +
                        "<div>Date Comptable: <b>${evenementSecuriteDTO.dateTraitement}</b></div>" +
                        "<div>Date Systeme: <b>${dateSysteme}</b></div>" +
                        "<div>&nbsp;</div>" +
                        "<div>Cordialement.</div>" +
                        "<div>&nbsp;</div>"

                mailObjet = "Forçage lancement d'une nouvelle instance dans la même journée  "
                break

            case ConstantesSecurite.Event.FORCAGE_NEW_INSTANCE_DERIERE_SEQUENCE.code:
                contenuMail = "" +
                        "<div>Bonjour,</div>" +
                        "<div>&nbsp;</div>" +
                        "<div>Forçage lancement d'une nouvelle instance en commençant par la dernière séquence en cours par l'utilisateur <b>${evenementSecuriteDTO.username}</b> </div>"

                contenuMail += "<div>&nbsp;</div>" +
                        "<div>Date Comptable: <b>${evenementSecuriteDTO.dateTraitement}</b></div>" +
                        "<div>Date Systeme: <b>${dateSysteme}</b></div>" +
                        "<div>&nbsp;</div>" +
                        "<div>Cordialement.</div>" +
                        "<div>&nbsp;</div>"

                mailObjet = "Forçage lancement d'une nouvelle instance en commençant par la dernière séquence en cours  "
                break

            default:
                return

        }

        sendBatchEmailToAdmin(mailObjet, contenuMail)
    }
}
