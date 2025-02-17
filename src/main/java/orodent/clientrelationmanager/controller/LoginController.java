package orodent.clientrelationmanager.controller;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import orodent.clientrelationmanager.controller.database.ConnectionManager;
import orodent.clientrelationmanager.model.App;
import orodent.clientrelationmanager.model.enums.Operator;
import orodent.clientrelationmanager.view.LoginView;

import java.util.logging.Logger;
import java.util.logging.StreamHandler;

public class LoginController {
    private final LoginView view;
    private final Logger logger;
    private Button loginButton;
    private ChoiceBox<Operator> operatorChoiceBox;

    public LoginController(App app) {
        logger = Logger.getLogger(ConnectionManager.class.getName());
        logger.addHandler(new StreamHandler());

        operatorChoiceBox = new ChoiceBox<>();
        operatorChoiceBox.getItems().addAll(Operator.values());

        loginButton = new Button();
        loginButton.setOnAction(event -> {
            logger.info(operatorChoiceBox.getValue() + " has logged in");
            app.setOperator(operatorChoiceBox.getValue());
        });

        view = new LoginView(loginButton, operatorChoiceBox);
    }

    public LoginView getView() {
        return view;
    }
}
