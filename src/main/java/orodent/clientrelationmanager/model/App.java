package orodent.clientrelationmanager.model;

import javafx.stage.Stage;
import orodent.clientrelationmanager.controller.database.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App{
    private String operator;
    private DBManager dbManager;
    private Stage primaryStage;
    private Map<String, List<String>> configs;
    private List<Client> list;


    public App(){
        configs = new HashMap<>();
    }

    public void start() {
        this.dbManager = new DBManager();
    }
    public void setConfigs(Map<String, List<String>> configs) {
        this.configs = configs;
    }
    public void setWorkingOperator(String operator) {
        this.operator = operator;
    }
    public Map<String, List<String>> getConfigs(){
        return configs;
    }
    public String getWorkingOperator() {
        return operator;
    }
    public DBManager getDbManager() {
        return dbManager;
    }
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    public void setListFromFilteredSearch(List<Client> list) {
        this.list = list;
    }
    public List<Client> getListFromFilteredSearch() {
        return list;
    }
}
