package orodent.clientrelationmanager.controller.main.buttons;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import orodent.clientrelationmanager.controller.main.MainController;
import orodent.clientrelationmanager.view.customerservice.CustomerServiceView;

public class CustomerServiceButton implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
        new MainController().showView(new CustomerServiceView());
    }
}
