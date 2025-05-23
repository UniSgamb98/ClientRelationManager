package orodent.clientrelationmanager.controller.main.buttons;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import orodent.clientrelationmanager.controller.main.MainController;
import orodent.clientrelationmanager.view.searchclient.SearchClientView;

public class SearchClientController implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
        MainController mainController = new MainController();
        mainController.saveListFromFilteredSearch(null);
        new MainController().showView(new SearchClientView());
    }
}
