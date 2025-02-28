package orodent.clientrelationmanager.model.enums;

public enum Operator {
    VICTORIA("Victoria"),
    TERESA("Teresa"),
    GAETANO("Gaetano"),
    TOMMASO("Tommaso"),
    HUGO("Hugo"),
    SANTOLO("Santolo");

    private final String displayName;

    Operator(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Operator fromString(String text) {
        if (!text.equals("BLANK")&& !text.equals("null")) {
            for (Operator c : Operator.values()) {
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

