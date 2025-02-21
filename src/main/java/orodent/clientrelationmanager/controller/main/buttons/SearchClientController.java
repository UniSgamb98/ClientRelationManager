package orodent.clientrelationmanager.controller.main.buttons;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.view.mainview.MainView;
import orodent.clientrelationmanager.view.searchclient.SearchClientView;

public class SearchClientController implements EventHandler<ActionEvent> {
    DBManagerInterface db;
    MainView mainView;
    SearchClientView searchClientView;

    public  SearchClientController(DBManagerInterface db, MainView mainView) {
        this.db = db;
        this.mainView = mainView;
    }

    @Override
    public void handle(ActionEvent event) {
        searchClientView = new SearchClientView();
        mainView.setCenter(searchClientView);
    }
}
