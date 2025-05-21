package orodent.clientrelationmanager.controller;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import orodent.clientrelationmanager.controller.database.ConnectionManager;
import orodent.clientrelationmanager.controller.main.MainController;
import orodent.clientrelationmanager.model.App;
import orodent.clientrelationmanager.view.LoginView;
import orodent.clientrelationmanager.view.TemporaryView;

import java.util.List;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

public class LoginController {
    private final LoginView view;
    private final Logger logger;
    private final ChoiceBox<String> operatorChoiceBox;

    public LoginController(App app, MainController mainController) {
        this.logger = Logger.getLogger(ConnectionManager.class.getName());
        logger.addHandler(new StreamHandler());

        // Creazione dei controlli UI
        Label operatorLabel = new Label("Operator: ");
        this.operatorChoiceBox = new ChoiceBox<>();
        operatorChoiceBox.getItems().addAll(App.configs.get("operatori"));

        Button loginButton = new Button("LogIn");

        loginButton.setOnAction(event -> {
            String selectedOperator = operatorChoiceBox.getValue();

            // Aggiungo i Operatori che non sono nel file config ma sono già presenti nel Database in quanto non voglio vedere in login gli utenti non presenti in config.txt ma nel resto dell'applicazioni mi servono
            List<String> operatorInDatabase = App.getDBManager().getAllOperators();
            for (String i : operatorInDatabase){
                if (!App.configs.get("operatori").contains(i))  App.configs.get("operatori").add(i);
            }

            if (selectedOperator != null) {
                logger.info(selectedOperator + " has logged in");
                app.setOperator(selectedOperator);

                // Dopo il login, mostra la HotBar sul lato sinistro
                mainController.showHotBar();

                // Esempio: Sostituisci la LoginView con una TemporaryView nel centro
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
