package orodent.clientrelationmanager.controller.database;

import orodent.clientrelationmanager.model.Client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DBManagerInterface {


    public void close();
    public void open();
    public void test(String search);
    void insertClient(Client selectedClient);
}
