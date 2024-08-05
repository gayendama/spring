package sn.sensoft.springbatch.securite

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Version

@EqualsAndHashCode(includes = 'username')
@ToString(includes = 'username', includeNames = true, includePackage = false)
class User implements Serializable {

    String id = UUID.randomUUID().toString()
    @Version
    Long version

    static auditable = [ignore: ['version', 'lastUpdated']]

    private static final long serialVersionUID = 1

    def tools
    def securityUtils

    String username
    boolean enabled = true

    String nom
    String prenom
    String fonction
    String telephone
    String email
    String uidKeycloak

    //-----------------------------------------------------------------------
    // Autres attributs complémentaires
    //-----------------------------------------------------------------------
    String referenceExterne
    byte[] photo
    String extensionPhoto
    String photoFileName // orignal filename of the photo
    String lang = Locale.FRENCH.toString() // Langue Française par défaut
    //-----------------------------------------------------------------------

    // Pour la gestion des périodes d'inactivité. Ceci n'est pas géré par Keycloak
    Date lastLoginDate  // date dernière tentative de connexion
    String lastLoginIpAdress // Adresse IP dernière tentative de connexion
    String lastLoginBranch // Bureau sélectionnée pour la dernière tentative de connexion
    Date lastSuccesfullLoginDate // date dernière connexion réussie
    String lastSuccesfullLoginIpAdress // Adresse IP dernière connexion réussie
    String lastSuccesfullLoginBranch // Bureau dernière connexion réussie
    Date previousSuccesfullLoginDate // date précédente connexion réussie (pour affichage à l'utilisateur)
    String previousSuccesfullLoginIpAdress // adresse IP précédente connexion réussie (pour affichage à l'utilisateur)
    String previousSuccesfullLoginBranch // Bureau précédente connexion réussie (pour affichage à l'utilisateur)
    Boolean isInactive = false

    // for dual control
    Boolean firstActivation = false
    User creator
    Date creationDate
    User activator
    Date activationDate

    // Gestion des plafonds pour les transactions
    BigDecimal plafondVersementJours = BigDecimal.ZERO
    BigDecimal plafondVersementMois = BigDecimal.ZERO
    BigDecimal plafondRetraitJours = BigDecimal.ZERO
    BigDecimal plafondRetraitMois = BigDecimal.ZERO

    // Pour gérer la double validation de création d'un compte
    Boolean isValidateurCompte = false

    Boolean indQrCode = false

    String userCreate
    String userUpdate
    Date dateCreated
    Date lastUpdated

    static mapping = {
        // Config pour PostgreSQL, "user" est un mot clé réservé
        table 'utilisateur'
        id generator: 'assigned'
    }

    static constraints = {
        username nullable: false, blank: false, unique: true
        lang(nullable: true, inList: [Locale.FRENCH.toString(), Locale.ENGLISH.toString(), (new Locale("ar")).toString()])
    }


    Set<UserAttribute> getUserAttributes() {
        UserAttribute.findAllByUser(this) as Set<UserAttribute>
    }

    Set<Role> getAuthorities() {
        (UserRole.findAllByUser(this) as List<UserRole>)*.role as Set<Role>
    }

    Set<UserRole> getUserRoles() {
        UserRole.findAllByUser(this) as Set<UserRole>
    }

    String toString() {
        return "${username}"
    }




    def logInfos() {
        return [
                id                             : id,
                nom                            : nom,
                prenom                         : prenom,
                telephone                      : telephone,
                email                          : email,
                uidKeycloak                    : uidKeycloak,
                referenceExterne               : referenceExterne,
                lastLoginDate                  : lastLoginDate?.format("yyyy-MM-dd HH:mm:ss"),
                lastLoginIpAdress              : lastLoginIpAdress,
                lastLoginIpAdress              : lastLoginIpAdress,
                lastSuccesfullLoginDate        : lastSuccesfullLoginDate?.format("yyyy-MM-dd HH:mm:ss"),
                lastSuccesfullLoginIpAdress    : lastSuccesfullLoginIpAdress,
                lastSuccesfullLoginBranch      : lastSuccesfullLoginBranch,
                previousSuccesfullLoginDate    : previousSuccesfullLoginDate?.format("yyyy-MM-dd HH:mm:ss"),
                previousSuccesfullLoginIpAdress: previousSuccesfullLoginIpAdress,
                previousSuccesfullLoginBranch  : previousSuccesfullLoginBranch,
                isInactive                     : isInactive,
                creationDate                   : creationDate?.format("yyyy-MM-dd HH:mm:ss"),
                plafondVersementJours          : plafondVersementJours,
                plafondVersementMois           : plafondVersementMois,
                plafondRetraitJours            : plafondRetraitJours,
                plafondRetraitMois             : plafondRetraitMois,
                isValidateurCompte             : isValidateurCompte,
                indQrCode                      : indQrCode
        ]
    }

}
