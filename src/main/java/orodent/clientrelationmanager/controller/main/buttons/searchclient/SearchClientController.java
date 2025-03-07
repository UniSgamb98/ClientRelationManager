package orodent.clientrelationmanager.controller.main.buttons.searchclient;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.controller.main.MainController;
import orodent.clientrelationmanager.view.searchclient.SearchClientView;

public class SearchClientController implements EventHandler<ActionEvent> {
    DBManagerInterface db;
    MainController mainController;
    SearchClientView searchClientView;

    public  SearchClientController(DBManagerInterface db, MainController mainController) {
        this.db = db;
        this.mainController = mainController;
    }

    @Override
    public void handle(ActionEvent event) {
        searchClientView = new SearchClientView(mainController, db);
        mainController.showView(searchClientView);
    }
}
