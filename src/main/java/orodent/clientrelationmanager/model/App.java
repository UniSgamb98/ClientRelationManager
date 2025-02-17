package orodent.clientrelationmanager.model;

import orodent.clientrelationmanager.controller.database.ConnectionType;
import orodent.clientrelationmanager.model.enums.Operator;

import java.sql.Connection;

public class App{
    private Operator operator;
    private Connection connection;
    private ConnectionType connectionType;

    public void setConnectionType(ConnectionType connectionType) {
        this.connectionType = connectionType;
    }
    public ConnectionType getConnectionType() {
        return connectionType;
    }
    public void setOperator(Operator operator) {
        this.operator = operator;
    }
    public Operator getOperator() {
        return operator;
    }
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    public Connection getConnection() {
        return connection;
    }
}
