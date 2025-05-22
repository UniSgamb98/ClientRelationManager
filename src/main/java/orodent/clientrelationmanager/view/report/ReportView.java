package orodent.clientrelationmanager.view.report;

import javafx.scene.layout.BorderPane;
import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.controller.main.MainController;

public class ReportView extends BorderPane {
    private final DBManagerInterface dbManager;

    public ReportView(DBManagerInterface db, MainController mainController){
        dbManager = db;
    }
}
