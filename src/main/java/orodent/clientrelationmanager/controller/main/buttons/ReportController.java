package orodent.clientrelationmanager.controller.main.buttons;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.controller.main.MainController;
import orodent.clientrelationmanager.view.report.ReportView;

public class ReportController implements EventHandler<ActionEvent> {
    DBManagerInterface dbInterface;
    MainController mainController;
    ReportView reportView;

    public  ReportController() {
        mainController = new MainController();
        dbInterface = mainController.getApp().getDbManager();
    }
    @Override
    public void handle(ActionEvent event) {
        reportView = new ReportView(dbInterface, mainController);
        mainController.showView(reportView);
    }
}
