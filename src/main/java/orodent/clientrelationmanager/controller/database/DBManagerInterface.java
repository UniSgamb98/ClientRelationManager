package orodent.clientrelationmanager.controller.database;

import orodent.clientrelationmanager.model.Annotation;
import orodent.clientrelationmanager.model.Client;

import java.util.List;

public interface DBManagerInterface {


    void close();
    void open();
    void insertClient(Client selectedClient);

    void saveClientChanges(Client client);
    List<Annotation> getAnnotationsForClient(Client client);
    void saveAnnotation(Annotation annotation, String clientID);
    void saveClientAfterAnnotationChange(Annotation annotation, String clientID);
    void updateAnnotation(Annotation annotation, String clientID);
    boolean isAlive();

    /**
     * Interroga il database, la tabella CUSTOMERS in base al where passato come parametro
     * Se il parametro è una stringa vuota allora restituisce tutti i Customers
     * @param whereSQL la logica di selezione dei dati
     * @return a list of Client
    */
    List<Client> getClientWhere(String whereSQL);

    /**
     * Restituisce una List di client i quali la string s è un sotto stringa di Ragione sociale oppure Persona di riferimento
     * TODO Estendere a tutti i parametri questa possibilità di ricerca
     * @param s sotto stringa
     * @return list
     */
    List<Client> searchClient(String s);

    /**
     * Restituisce una lista di tutti i valori distinti salvati in database
     * @param tableColumn la colonna del database
     * @return List
     */
    List<String> getAllValuesFromCustomerColumn(String tableColumn);
}
