package orodent.clientrelationmanager.controller.main.buttons;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import orodent.clientrelationmanager.controller.main.MainController;
import orodent.clientrelationmanager.view.NewClientView;

public class NewClientController implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
        NewClientView newClientView = new NewClientView();
        newClientView.setMaxWidth(300);
        newClientView.setAlignment(Pos.CENTER_RIGHT);
        new MainController().showView(newClientView);
    }
}
