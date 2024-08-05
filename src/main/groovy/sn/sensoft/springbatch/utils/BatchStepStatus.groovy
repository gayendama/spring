package sn.sensoft.springbatch.utils

public enum BatchStepStatus {
    DESACTIVER("OFF", "Désactivé"),
    ACTIVER("ON", "Activé"),

    private BatchStepStatus( String id, String libelle) {
        this.id = id
        this.libelle = libelle
    }

    final String id
    final String libelle

    static BatchStepStatus findById(String id) {
        values().find { it.id == id }
    }

    String getId() {
        return id
    }

    String getLibelle() {
        return libelle
    }

    @Override
    String toString() {
        return this.libelle
    }
}
