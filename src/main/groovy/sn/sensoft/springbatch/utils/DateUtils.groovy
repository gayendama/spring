package sn.sensoft.springbatch.utils

import sn.sensoft.springbatch.date.DateTraitement
import sn.sensoft.springbatch.exception.SpringbatchException

import java.text.SimpleDateFormat

class DateUtils {

    static def jourAnnee(Date date1) {
        GregorianCalendar gcDate1 = new GregorianCalendar()
        gcDate1.setTime(date1)

        def res = gcDate1.get(Calendar.DAY_OF_YEAR)

        return res
    }

    static def annee(Date date1) {
        GregorianCalendar gcDate1 = new GregorianCalendar()
        gcDate1.setTime(date1)

        def res = gcDate1.get(Calendar.YEAR)

        return res.toString()
    }

    static def getYear(Date date1) {
        GregorianCalendar gcDate1 = new GregorianCalendar()
        gcDate1.setTime(date1)

        def res = gcDate1.get(Calendar.YEAR)

        return res.toString().toInteger()
    }


    static def diffDateMonth(Date Date1, Date Date2) {

        //Calcul de nombre de jours 
        GregorianCalendar gcDate1 = new GregorianCalendar()
        gcDate1.setTime(Date1)
        GregorianCalendar gcDate2 = new GregorianCalendar()
        gcDate2.setTime(Date2)

        long aTime = gcDate1.getTimeInMillis()
        long bTime = gcDate2.getTimeInMillis()
        long dif = bTime - aTime

        // On passe la différence en Calendar :
        GregorianCalendar difference = new GregorianCalendar()
        difference.setTimeInMillis(dif)

        // Le timestamp commençant le 1 janvier 1970, on enlève 1970 à l'année obtenue
        int year = difference.get(Calendar.YEAR) - 1970
        int month = difference.get(Calendar.MONTH)
        int day = difference.get(Calendar.DAY_OF_MONTH)

        //Verifier si on es dans une année bisextille
        //def bisextil = difference.isLeapYear(year) 

        int mois = year * 12 + month

        //Nbre total de jours 
        Integer diff = Math.abs(Date1 - Date2)
        Integer days = diff % (1000 * 60 * 60 * 24)

        if (days == 366) {
            mois = 12
        }

        return [year, mois, day, days]
    }

    static Date stringToDate(String dateStr, String format = 'dd/MM/yyyy') {
        Date dtTmp = new SimpleDateFormat(format).parse(dateStr)

        return dtTmp
    }


    static Date dateTraitementBatch(String codeDate) throws SpringbatchException {
        if (codeDate == null) {
            throw new IllegalArgumentException("paramètrre codeDate is null")
        }
        String cdate
        switch (codeDate) {
            case Constantes.CODE_DATE_JOUR:   // Juste pour la compatibilité
                cdate = Constantes.DATE_TRAITEMENT_JOUR
                break
            case Constantes.CODE_DATE_VEILLE:  // Juste pour la compatibilité
                cdate = Constantes.DATE_TRAITEMENT_VEILLE
                break
            case Constantes.CODE_DATE_LENDEMAIN:  // Juste pour la compatibilité
                cdate = Constantes.DATE_TRAITEMENT_LENDEMAIN
                break
            case Constantes.DATE_TRAITEMENT_JOUR:
                cdate = Constantes.DATE_TRAITEMENT_JOUR
                break
            case Constantes.DATE_TRAITEMENT_VEILLE:
                cdate = Constantes.DATE_TRAITEMENT_VEILLE
                break
            case Constantes.DATE_TRAITEMENT_LENDEMAIN:
                cdate = Constantes.DATE_TRAITEMENT_LENDEMAIN
                break
            default:
                throw new IllegalArgumentException("Valeur paramètrre codeDate invalide, valeurs permises ${Constantes.DATE_TRAITEMENT_JOUR}, ${Constantes.DATE_TRAITEMENT_VEILLE}, ${Constantes.DATE_TRAITEMENT_LENDEMAIN}")
        }

        DateTraitement dateTraitementInstance = DateTraitement.findByCodeDate(cdate)
        if (dateTraitementInstance == null) {
            throw new SpringbatchException("Date traitement ${cdate} non définie dans la table DateTraitement")
        }
        if (dateTraitementInstance.dateTraitement == null) {
            throw new SpringbatchException("Date traitement ${cdate} non positionnée dans la table DateTraitement")
        }
        return dateTraitementInstance.dateTraitement

    }

    static Date dateTraitement(String codeDate) {
        if (codeDate == null) {
            throw new IllegalArgumentException("paramètrre codeDate is null")
        }
        String cdate
        switch (codeDate) {
            case Constantes.CODE_DATE_JOUR:   // Juste pour la compatibilité
                cdate = Constantes.DATE_TRAITEMENT_JOUR
                break
            case Constantes.CODE_DATE_VEILLE:  // Juste pour la compatibilité
                cdate = Constantes.DATE_TRAITEMENT_VEILLE
                break
            case Constantes.CODE_DATE_LENDEMAIN:  // Juste pour la compatibilité
                cdate = Constantes.DATE_TRAITEMENT_LENDEMAIN
                break
            case Constantes.DATE_TRAITEMENT_JOUR:
                cdate = Constantes.DATE_TRAITEMENT_JOUR
                break
            case Constantes.DATE_TRAITEMENT_VEILLE:
                cdate = Constantes.DATE_TRAITEMENT_VEILLE
                break
            case Constantes.DATE_TRAITEMENT_LENDEMAIN:
                cdate = Constantes.DATE_TRAITEMENT_LENDEMAIN
                break
            default:
                throw new IllegalArgumentException("Valeur paramètrre codeDate invalide, valeurs permises ${Constantes.DATE_TRAITEMENT_JOUR}, ${Constantes.DATE_TRAITEMENT_VEILLE}, ${Constantes.DATE_TRAITEMENT_LENDEMAIN}")
        }

        DateTraitement dateTraitementInstance = DateTraitement.findByCodeDate(cdate)
        if (dateTraitementInstance == null) {
            throw new SpringbatchException("Date traitement ${cdate} non définie dans la table DateTraitement")
        }
        return dateTraitementInstance.dateTraitement
    }

    //Rechercher la date de l'arrêté en cours
    /*
    static DateArrete dateArreteTraitement() {
        DateArrete arreteInstance = DateArrete.findByArreteEnCours(true)
        return arreteInstance
    }
    */



    static Date stringToDateDto(String dateStr, String format = 'dd/MM/yyyy') {
        if ((dateStr == null) || (dateStr.trim().equals(""))) {
            return null
        }
        Date dtTmp = new SimpleDateFormat(format).parse(dateStr)

        return dtTmp
    }

    static Calendar parseTimestamp(String timestamp) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.US)
        Date d = sdf.parse(timestamp)
        Calendar cal = Calendar.getInstance()
        cal.setTime(d)
        return cal
    }


    static def datelistInterval(Integer nbreDeJourRechercheMax, Integer jourEcheance) {
        Date dateJour
        Date dateJourEcheance = new Date()
        dateJourEcheance.setDate(jourEcheance.intValue())
        //log.debug("dateJjourEcheance >> ${dateJourEcheance}")
        def listIntervalRech = []
        //listIntervalRech.add(dateJour) 

        for (int i in 1..nbreDeJourRechercheMax.intValue()) {
            dateJour = dateJourEcheance + i
            listIntervalRech.add(dateJour)
        }
        // log.debug("listIntervalRech >> ${listIntervalRech}")
        return listIntervalRech
    }

    static def listDateFraisClient(String codePeriodicite, def dateCreationCompte, def debutPrel) {

        def tmpDate = dateCreationCompte

        //Initialiser le mois de debut si un client souscrit un mois de Mars,
        //les frais de Janv, et Fev ne lui seront pas imputé   

        def moisDeb = dateCreationCompte.format("MM").toInteger()
        def moisFin
        def dateJour = new Date()

        //Voir a partir du jour dans le mois parametre
        // si debut prélèvement pour le mois immediat
        //Ex: si un client a une date d'ouverture de compte
        //qui est superieur au 15 du mois courant ne commencer a prelever qu'a la fin du mois suivant 

        def listIntervalRech = []
        use([groovy.time.TimeCategory]) {
            if (codePeriodicite == "M") {

                moisFin = 12
                (moisDeb..moisFin).each() {
                    tmpDate = tmpDate + 1.months
                    //Tableau de date fraisClient de la même année 
                    if (tmpDate.format("yyyy") == dateJour.format("yyyy")) {
                        listIntervalRech.add(tmpDate)
                    }
                }
                log.debug("Tableau Mensuel >> " + listIntervalRech)
                log.debug("dateJour >> " + dateJour.format("dd").toInteger() + ", Date debut Prel >> ${debutPrel.toInteger()}")

                //supprimer la premiére Date de la liste si le 'jour' de la date d'ouverture est supérieure au jour deb paramétré
                if (listIntervalRech.size() > 0 && (dateJour.format("dd").toInteger() > debutPrel.toInteger())) {
                    listIntervalRech.remove(0)
                    log.debug("removed first")
                }

                log.debug("Maj Tableau Mensuel >> " + listIntervalRech)

            }
            else if (codePeriodicite == "B") {
                moisFin = 6
                (moisDeb..moisFin).each() {
                    tmpDate = tmpDate + 2.months
                    listIntervalRech.add(tmpDate)
                }
                log.debug("Tableau Bimensuel >> " + listIntervalRech)

            }
            else if (codePeriodicite == "T") {
                moisFin = 4
                (moisDeb..moisFin).each() {
                    tmpDate = tmpDate + 3.months
                    listIntervalRech.add(tmpDate)
                }
                log.debug("Tableau Trimestriel >> " + listIntervalRech)

            }
            else if (codePeriodicite == "S") {
                moisFin = 2
                (moisDeb..moisFin).each() {
                    tmpDate = tmpDate + 6.months
                    listIntervalRech.add(tmpDate)
                }
                log.debug("Tableau Semestriel >> " + listIntervalRech)
            }
            else if (codePeriodicite == "A") {
                tmpDate = tmpDate + 12.months
                listIntervalRech.add(tmpDate)
                log.debug("Tableau Semestriel >> " + listIntervalRech)

            }
            else if (codePeriodicite == "AT") {
                moisFin = 12
                (moisDeb..moisFin).each() {
                    tmpDate = tmpDate + 1.months
                    listIntervalRech.add(tmpDate)
                }
                log.debug("Tableau Autre >> " + listIntervalRech)
            }
        }
        return listIntervalRech
    }


    static def getLastDayInMonth(Date date) {
        Calendar calendar = Calendar.getInstance()
        calendar.set(date.year, date.month, date.day)
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        return maxDay
    }

}

