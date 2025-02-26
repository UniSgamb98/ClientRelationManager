package orodent.clientrelationmanager.controller.database;

import orodent.clientrelationmanager.model.Client;

import java.util.List;

public interface DBManagerInterface {


    void close();
    void open();
    void insertClient(Client selectedClient);
    List<Client> getAllClient();
    /**
     *  ID, RAGIONE_SOCIALE, PERSONA_RIFERIMENTO, EMAIL_RIFERIMENTO, CELLULARE_RIFERIMENTO,
     *  TELEFONO_AZIENDALE, EMAIL_AZIENDALE, PAESE, CITTA, NOME_TITOLARE, CELLULARE_TITOLARE,
     *  EMAIL_TITOLARE, SITO_WEB, VOLTE_CONTATTATI, ULTIMA_CHIAMATA, PROSSIMA_CHIAMATA,
     *  DATA_ACQUISIZIONE, BUSINESS, OPERATORE_ASSEGNATO, INFORMATION, CATALOG, SAMPLE
     */
    <T> List<Client> queryCustomerWithSingleParameter(String field, T value);
}
