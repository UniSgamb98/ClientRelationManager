package orodent.clientrelationmanager.view.mainview;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import orodent.clientrelationmanager.controller.LoginController;
import orodent.clientrelationmanager.controller.main.StatusToolTipController;
import orodent.clientrelationmanager.model.App;

public class MainView extends BorderPane {
    private final LoginController loginController;

    public MainView(App app) {
        this.setBottom(StatusToolTipController.statusToolTipView);

        // Inizializza il controller, che crea i controlli UI
        this.loginController = new LoginController(app, this);

        // Ottiene la view dal controller e la imposta al centro
        this.setCenter(loginController.getView());
    }
}


