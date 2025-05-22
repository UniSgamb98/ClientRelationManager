package orodent.clientrelationmanager.view.mainview;

import javafx.scene.layout.BorderPane;
import orodent.clientrelationmanager.controller.main.StatusToolTipController;

public class MainView extends BorderPane {

    public MainView() {
        // L'applicazione all'accensione necessita solo dello StatusToolTip, ma comunque questa è la BorderPane più esterna dell'applicazione
        this.setBottom(StatusToolTipController.getStatusToolTipView());
    }
}



