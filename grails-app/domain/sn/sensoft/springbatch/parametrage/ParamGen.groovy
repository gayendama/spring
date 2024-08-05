package sn.sensoft.springbatch.parametrage

import sn.sensoft.springbatch.exception.SpringbatchException
import sn.sensoft.springbatch.utils.Constantes

import javax.persistence.Version
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.ParseException

// Modif AichaV4. Amélioration de la gestion des paramètres généraux afin d'éviter d'utiliser la table parammetres_divers poour plus de visibilité
class ParamGen  {

    String id = UUID.randomUUID().toString()
    @Version
    Long version

    String code
    String libelle
    String dataType
    String valeur
    String context
    Boolean isValeurEncrypte = false

    static constraints = {
        code nullable: false, unique: true
        dataType blank: false, nullable: false, inList: [Constantes.DATATYPE_BOOLEAN, Constantes.DATATYPE_DATE, Constantes.DATATYPE_INTEGER, Constantes.DATATYPE_STRING, Constantes.DATATYPE_NUMBER, Constantes.DATATYPE_OPTION]
    }

    static mapping = {
        id generator: 'assigned'

    }

    String toString() {
        return "${code}"
    }

    String userCreate
    String userUpdate
    Date dateCreated
    Date lastUpdated
 
    /**
     *
     * Pour vérifier et mettre à jour la valeur du paramètre selon son type
     *
     */
    void checkAndSetParamValue() {
        if ((this.valeur == null)) {
            throw new SpringbatchException("Paramètre ${this.code} a une valeur nulle!")
        }
        BigDecimal bdVal
        Integer intVal
        Date dateVal
        Boolean boolVal
        this.valeur = this.valeur.trim()
        switch (this.dataType) {
            case Constantes.DATATYPE_NUMBER:
                try {
                    this.valeur = this.valeur.replace(",", ".")
                    bdVal = new BigDecimal(this.valeur)
                }
                catch (NumberFormatException ex) {
                    throw new SpringbatchException("La valeur ${this.valeur} du paramètre ${this.code} de type nombre est invalide!")
                }
                bdVal = bdVal.setScale(10, BigDecimal.ROUND_HALF_DOWN);
                DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US) // Pour forcer le séparateur décimal à '.'
                this.valeur = new DecimalFormat("#0.##########", dfs).format(bdVal)
                break
            case Constantes.DATATYPE_INTEGER:
                try {
                    intVal = new Integer(this.valeur)
                }
                catch (NumberFormatException ex) {
                    throw new SpringbatchException("La valeur ${this.valeur} du paramètre ${this.code} de type entier est invalide!")
                }
                this.valeur = intVal.toString()
                break
            case Constantes.DATATYPE_DATE:
                try {
                    dateVal = Date.parse("dd/MM/yyyy", this.valeur)
                }
                catch (ParseException ex) {
                    try {
                        dateVal = Date.parse("yyyy-MM-dd", this.valeur)
                    }
                    catch (ParseException ex2) {
                        throw new SpringbatchException("La valeur ${this.valeur} du paramètre ${this.code} de type date est invalide!, les formas acceptés 'dd/MM/yyyy', 'yyyy-MM-dd' ")
                    }
                }
                this.valeur = dateVal.format("yyyy-MM-dd")

                break
            case Constantes.DATATYPE_BOOLEAN:
                boolVal = new Boolean(this.valeur)
                this.valeur = boolVal.toString()
                break
            case Constantes.DATATYPE_STRING:
                // RAS
                break
            default:
                // RAS
                break
        }

    }

    def logInfos() {
        return [
                code    : code,
                libelle : libelle,
                dataType: dataType,
                valeur  : valeur,
                context : context
        ]
    }

}
