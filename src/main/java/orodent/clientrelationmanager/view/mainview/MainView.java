package orodent.clientrelationmanager.view.mainview;

import javafx.scene.layout.BorderPane;
import orodent.clientrelationmanager.controller.main.MainController;
import orodent.clientrelationmanager.controller.main.StatusToolTipController;
import orodent.clientrelationmanager.model.App;

public class MainView extends BorderPane {
    private final MainController mainController;

    public MainView(App app) {
        // Inizializza il controller passando questa MainView e l'app
        mainController = new MainController(app, this);

        // Imposta, ad esempio, lo StatusToolTip in basso
        this.setBottom(StatusToolTipController.statusToolTipView);

        // Visualizza la LoginView al centro
        mainController.showLoginView();
    }

    public MainController getMainController() {
        return mainController;
    }
}



