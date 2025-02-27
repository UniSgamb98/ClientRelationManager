package orodent.clientrelationmanager.model.enums;

public enum Business {
    RIVENDITORE("Rivenditore"),
    LABORATORIO("Laboratorio"),
    DISTRIBUTORE("Distributore"),
    DATO_IGNOTO("Dato Ignoto");

    private final String displayName;

    Business(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Business fromString(String text) {
        for (Business c : Business.values()) {
            if (c.displayName.equalsIgnoreCase(text) || c.name().equalsIgnoreCase(text.replace(" ", "_"))) {
                return c;
            }
        }
        throw new IllegalArgumentException("Nessun paese corrispondente a: " + text);
    }


    @Override
    public String toString() {
        return displayName;
    }
}
