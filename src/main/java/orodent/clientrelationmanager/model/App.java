package orodent.clientrelationmanager.model;

import orodent.clientrelationmanager.controller.database.DBManager;
import orodent.clientrelationmanager.model.enums.Operator;

public class App{
    private Operator operator;
    private DBManager dbManager;

    public App(){
        dbManager = new DBManager();
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
    public Operator getOperator() {
        return operator;
    }
    public void setDbManager(DBManager dbManager) {
        this.dbManager = dbManager;
    }
    public DBManager getDbManager() {
        return dbManager;
    }
}
