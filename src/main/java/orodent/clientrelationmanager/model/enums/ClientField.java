package orodent.clientrelationmanager.model.enums;

import java.time.LocalDate;

public enum ClientField {
    RAGIONE_SOCIALE(String.class),
    PERSONA_RIFERIMENTO(String.class),
    EMAIL_RIFERIMENTO(String.class),
    CELLULARE_RIFERIMENTO(String.class),
    TELEFONO_AZIENDALE(String.class),
    EMAIL_AZIENDALE(String.class),
    PAESE(String.class),
    CITTA(String.class),
    NOME_TITOLARE(String.class),
    CELLULARE_TITOLARE(String.class),
    EMAIL_TITOLARE(String.class),
    SITO_WEB(String.class),
    VOLTE_CONTATTATI(Integer.class),
    ULTIMA_CHIAMATA(LocalDate.class),
    PROSSIMA_CHIAMATA(LocalDate.class),
    DATA_ACQUISIZIONE(LocalDate.class),
    BUSINESS(String.class),
    OPERATORE_ASSEGNATO(String.class),
    INFORMATION(Boolean.class),
    CATALOG(Boolean.class),
    SAMPLE(Boolean.class),
    PVU(String.class);

    private final Class<?> type;

    ClientField(Class<?> type) {
        this.type = type;
    }

    public Class<?> getType() {
        return type;
    }
}
