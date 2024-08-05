package sn.sensoft.springbatch

/**
 * @author alioune-mac
 */
public enum TypeEntite {
    USER("USER", "Utilisateur", "user"),
    BATCH("BATCH", "Batch", "batch")

    String code
    String name
    String controller

    TypeEntite(String code, String name, String controller) {
        this.code = code
        this.name = name
        this.controller = controller
    }

    static TypeEntite getDefault() {
        return BATCH
    }

    @Override
    String toString() {
        return code
    }

}

