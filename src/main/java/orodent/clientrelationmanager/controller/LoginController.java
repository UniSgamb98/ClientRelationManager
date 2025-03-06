package orodent.clientrelationmanager.controller;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import orodent.clientrelationmanager.controller.database.ConnectionManager;
import orodent.clientrelationmanager.model.App;
import orodent.clientrelationmanager.model.enums.Operator;
import orodent.clientrelationmanager.view.LoginView;
import orodent.clientrelationmanager.view.mainview.HotBarView;
import orodent.clientrelationmanager.view.mainview.MainView;

import java.util.logging.Logger;
import java.util.logging.StreamHandler;

public class LoginController {
    private final LoginView view;
    private final Logger logger;
    private final ChoiceBox<Operator> operatorChoiceBox;

    public LoginController(App app, MainView mainView) {
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

                // Rimuove la LoginView dal centro
                mainView.setCenter(null);

                // Crea la HotBarView e la posiziona a sinistra
                HotBarView hotBarView = new HotBarView(mainView, app.getDbManager());
                mainView.setLeft(hotBarView);

                // Esegue l'animazione per far scorrere la hotbar
                hotBarView.animateEntrance();
            }
        });


        // Inizializza la view passando i controlli già creati
        this.view = new LoginView(operatorLabel, operatorChoiceBox, loginButton);
    }

    public LoginView getView() {
        return view;
    }
}


