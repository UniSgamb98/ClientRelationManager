package orodent.clientrelationmanager.model.enums;

public enum Business {
    RIVENDITORE("Rivenditore"),
    LABORATORIO("Laboratorio"),
    DISTRIBUTORE("Distributore"),
    DATO_IGNOTO("Dato Ignoto"),
    CENTRO_FRESAGGIO("Centro di Fresaggio");

    private final String displayName;

    Business(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Business fromString(String text) {
        if (text.equals("CENTROFRESAGGIO")){
            return Business.CENTRO_FRESAGGIO;
        } else if (!text.equals("BLANK")&& !text.equals("null")) {
            for (Business c : Business.values()) {
                if (c.displayName.equalsIgnoreCase(text) || c.name().equalsIgnoreCase(text.replace(" ", "_"))) {
                    return c;
                }
            }
        } else {
            return null;
        }
        throw new IllegalArgumentException("Nessun Business corrispondente a: " + text);
    }


    @Override
    public String toString() {
        return displayName;
    }
}
