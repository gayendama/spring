package sn.sensoft.springbatch.dto


import groovy.transform.CompileStatic
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder

@CompileStatic
class DateArreteDTO {
    String id
    String mutuelleId
    String codeMutuelle
    String libelleMutuelle
    Date dateDebut
    Date dateArrete
    Boolean arreteEnCours
    Date dateClotureArrete
    String typeArrete

    MessageSource messageSource

    DateArreteDTO() {
        // Constructeur par défaut nécessaire pour la désérialisation
    }

    void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource
    }

    def getDateArreteDisplay() {
        dateArrete?.format("dd-MM-yyyy")
    }


    def getMutuelleDisplay() {
        if (this.mutuelleId == null || this.mutuelleId.equals("")) {
            String mutuelleLabel = messageSource.getMessage("mutuelle.label", [] as Object[], 'Mutuelle', LocaleContextHolder.locale)
            String allMutuelleLabel = messageSource.getMessage("all.mutuelle.label", [] as Object[], 'Toutes les', LocaleContextHolder.locale)
            return "${allMutuelleLabel} ${mutuelleLabel}"
        }
        else {
            return "${this.codeMutuelle} ${this.libelleMutuelle}"
        }
    }

}
