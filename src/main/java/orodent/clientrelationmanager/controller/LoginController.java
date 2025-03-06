package orodent.clientrelationmanager.controller;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import orodent.clientrelationmanager.controller.database.ConnectionManager;
import orodent.clientrelationmanager.controller.main.MainController;
import orodent.clientrelationmanager.model.App;
import orodent.clientrelationmanager.model.enums.Operator;
import orodent.clientrelationmanager.view.LoginView;
import orodent.clientrelationmanager.view.TemporaryView;

import java.util.logging.Logger;
import java.util.logging.StreamHandler;

public class LoginController {
    private final LoginView view;
    private final Logger logger;
    private final ChoiceBox<Operator> operatorChoiceBox;

    public LoginController(App app, MainController mainController) {
        this.logger = Logger.getLogger(ConnectionManager.class.getName());
        logger.addHandler(new StreamHandler());

        // Creazione dei controlli UI
        Label operatorLabel = new Label("Operator: ");
        this.operatorChoiceBox = new ChoiceBox<>();
        operatorChoiceBox.getItems().addAll(Operator.values());

        Button loginButton = new Button("LogIn");

        // Aggiunta comportamento al pulsante
        loginButton.setOnAction(event -> {
            Operator selectedOperator = operatorChoiceBox.getValue();
            if (selectedOperator != null) {
                logger.info(selectedOperator + " has logged in");
                app.setOperator(selectedOperator);

                // Dopo il login, mostra la HotBar sul lato sinistro
                mainController.showHotBar();

                // Esempio: Sostituisci la LoginView con una TemporaryView nel centro attualmente null
                mainController.showView(new TemporaryView(selectedOperator));
            }
        });


        // Inizializza la view passando i controlli già creati
        this.view = new LoginView(operatorLabel, operatorChoiceBox, loginButton);
    }

    public LoginView getView() {
        return view;
    }
}


