package orodent.clientrelationmanager.controller.main.buttons.searchclient;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.controller.main.MainController;
import orodent.clientrelationmanager.view.searchclient.SearchClientView;

public class SearchClientController implements EventHandler<ActionEvent> {
    DBManagerInterface dbInterface;
    MainController mainController;
    SearchClientView searchClientView;

    public  SearchClientController() {
        mainController = new MainController();
        dbInterface = mainController.getApp().getDbManager();
    }

    @Override
    public void handle(ActionEvent event) {
        searchClientView = new SearchClientView(mainController, dbInterface);
        mainController.showView(searchClientView);
    }
}
