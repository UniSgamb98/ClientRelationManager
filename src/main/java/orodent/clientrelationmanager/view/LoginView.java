package orodent.clientrelationmanager.view;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import orodent.clientrelationmanager.model.enums.Operator;

public class LoginView extends GridPane {

    public LoginView(Button loginButton, ChoiceBox<Operator> operatorChoiceBox) {
        Label operatorLabel = new Label("Operator: ");
        loginButton.setText("LogIn");

        this.add(operatorLabel, 0, 0);
        this.add(operatorChoiceBox, 1, 0);
        this.add(loginButton, 2, 1);
    }
}
