package orodent.clientrelationmanager.model;

import javafx.stage.Stage;
import orodent.clientrelationmanager.controller.database.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App{
    private static String operator;
    private static DBManager dbManager;
    private static Stage primaryStage;
    private static Map<String, List<String>> configs;


    public App(){
        dbManager = new DBManager();
        configs = new HashMap<>();
    }
    public void setOperator(String operator) {
        App.operator = operator;
    }

    public static Map<String, List<String>> getConfigs(){
        return configs;
    }
    public static String getWorkingOperator() {
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
}
