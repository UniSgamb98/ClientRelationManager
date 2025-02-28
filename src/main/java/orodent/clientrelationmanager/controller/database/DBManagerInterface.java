package orodent.clientrelationmanager.controller.database;

import orodent.clientrelationmanager.model.Annotation;
import orodent.clientrelationmanager.model.Client;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
    void saveClientChanges(Client client);
    List<Annotation> getAnnotationsForClient(Client client);
    void saveAnnotation(Annotation annotation, String clientID);
    void saveClientAfterAnnotationChange(Annotation annotation, String clientID);
    void updateAnnotation(Annotation annotation, String clientID);
    boolean isAlive();
    Client getClient(UUID uuid);

    List<Client> getClientsByNextCall(LocalDate value);
}
