package orodent.clientrelationmanager.view.mainview;

import javafx.scene.layout.BorderPane;
import orodent.clientrelationmanager.controller.main.MainController;
import orodent.clientrelationmanager.controller.main.StatusToolTipController;
import orodent.clientrelationmanager.model.App;

public class MainView extends BorderPane {

    public MainView(App app) {
        // Inizializza il controller passando questa MainView e l'app
        MainController mainController = new MainController(app, this);

        // Imposta, ad esempio, lo StatusToolTip in basso
        this.setBottom(StatusToolTipController.statusToolTipView);

        // Visualizza la LoginView al centro
        mainController.showLoginView();
    }
}



