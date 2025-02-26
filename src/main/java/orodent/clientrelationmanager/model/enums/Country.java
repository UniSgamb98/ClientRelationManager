package orodent.clientrelationmanager.model.enums;

public enum Country {
    ALBANIA("Albania"),
    ANDORRA("Andorra"),
    ARMENIA("Armenia"),
    AUSTRIA("Austria"),
    AZERBAIGIAN("Azerbaigian"),
    BELGIO("Belgio"),
    BIELORUSSIA("Bielorussia"),
    BOSNIA_ED_ERZEGOVINA("Bosnia ed Erzegovina"),
    BULGARIA("Bulgaria"),
    CIPRO("Cipro"),
    CROAZIA("Croazia"),
    DANIMARCA("Danimarca"),
    ESTONIA("Estonia"),
    FINLANDIA("Finlandia"),
    FRANCIA("Francia"),
    GEORGIA("Georgia"),
    GERMANIA("Germania"),
    GRECIA("Grecia"),
    IRLANDA("Irlanda"),
    ISLANDA("Islanda"),
    ITALIA("Italia"),
    KOSOVO("Kosovo"),
    LETTONIA("Lettonia"),
    LIECHTENSTEIN("Liechtenstein"),
    LITUANIA("Lituania"),
    LUSSEMBURGO("Lussemburgo"),
    MALTA("Malta"),
    MOLDAVIA("Moldavia"),
    MONACO("Monaco"),
    MONTENEGRO("Montenegro"),
    NORVEGIA("Norvegia"),
    PAESI_BASSI("Paesi Bassi"),
    POLONIA("Polonia"),
    PORTOGALLO("Portogallo"),
    REGNO_UNITO("Regno Unito"),
    REPUBBLICA_CECA("Repubblica Ceca"),
    ROMANIA("Romania"),
    RUSSIA("Russia"),
    SAN_MARINO("San Marino"),
    SERBIA("Serbia"),
    SLOVACCHIA("Slovacchia"),
    SLOVENIA("Slovenia"),
    SPAGNA("Spagna"),
    SVEZIA("Svezia"),
    SVIZZERA("Svizzera"),
    TURCHIA("Turchia"),
    UCRAINA("Ucraina"),
    UNGHERIA("Ungheria"),
    VATICANO("Vaticano");

    private final String displayName;

    Country(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Country fromString(String text) {
        for (Country c : Country.values()) {
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
