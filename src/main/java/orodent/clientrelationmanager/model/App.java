package orodent.clientrelationmanager.model;

import orodent.clientrelationmanager.controller.database.DBManager;
import orodent.clientrelationmanager.model.enums.Operator;

public class App{
    private Operator operator;
    private final DBManager dbManager;

    public App(){
        dbManager = new DBManager();
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
    public Operator getOperator() {
        return operator;
    }
    public DBManager getDbManager() {
        return dbManager;
    }
}
