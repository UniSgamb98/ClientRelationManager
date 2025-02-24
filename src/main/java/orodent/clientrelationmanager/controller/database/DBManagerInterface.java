package orodent.clientrelationmanager.controller.database;

import orodent.clientrelationmanager.model.Client;

public interface DBManagerInterface {


    public void close();
    public void open();
    void insertClient(Client selectedClient);
}
