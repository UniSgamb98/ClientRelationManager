package orodent.clientrelationmanager.model;

import javafx.stage.Stage;
import orodent.clientrelationmanager.controller.database.DBManager;
import orodent.clientrelationmanager.model.enums.Operator;

import java.util.List;
import java.util.Map;

public class App{
    private static Operator operator;
    private static DBManager dbManager;
    private static Stage primaryStage;
    public static Map<String, List<String>> configs;


    public App(){
        dbManager = new DBManager();
    }
    public void setOperator(Operator operator) {
        App.operator = operator;
    }
    public static Operator getWorkingOperator() {
        return operator;
    }
    public DBManager getDbManager() {
        return dbManager;
    }
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    public void setPrimaryStage(Stage primaryStage) {
        App.primaryStage = primaryStage;
    }
    public static DBManager getDBManager() {
        return dbManager;
    }
}
