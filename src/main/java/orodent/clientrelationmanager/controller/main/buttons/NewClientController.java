package orodent.clientrelationmanager.controller.main.buttons;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.controller.main.MainController;
import orodent.clientrelationmanager.model.Client;
import orodent.clientrelationmanager.view.NewClientView;

public class NewClientController implements EventHandler<ActionEvent> {
    private final MainController mainController;
    private final DBManagerInterface dbInterface;

    public NewClientController() {
       mainController = new MainController();
       dbInterface = mainController.getApp().getDbManager();
    }
    @Override
    public void handle(ActionEvent event) {
        NewClientView newClientView = new NewClientView(dbInterface, new Client());
        newClientView.setMaxWidth(300);
        newClientView.setAlignment(Pos.CENTER_RIGHT);
        mainController.showView(newClientView);
    }
}
