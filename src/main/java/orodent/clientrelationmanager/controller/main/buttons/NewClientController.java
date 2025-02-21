package orodent.clientrelationmanager.controller.main.buttons;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.model.Client;
import orodent.clientrelationmanager.view.NewClientView;
import orodent.clientrelationmanager.view.mainview.MainView;

public class NewClientController implements EventHandler<ActionEvent> {
    private final MainView mainView;
    private final DBManagerInterface db;

    public NewClientController(DBManagerInterface db, MainView mainView) {
        this.mainView = mainView;
        this.db = db;
    }
    @Override
    public void handle(ActionEvent event) {
        NewClientView newClientView = new NewClientView(db, new Client());
        newClientView.setMaxWidth(300);
        newClientView.setAlignment(Pos.CENTER_RIGHT);
        mainView.setCenter(newClientView);
    }
}
