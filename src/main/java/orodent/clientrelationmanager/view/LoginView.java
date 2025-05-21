package orodent.clientrelationmanager.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class LoginView extends GridPane {

    public LoginView(Label operatorLabel, ChoiceBox<String> operatorChoiceBox, Button loginButton) {
        this.add(operatorLabel, 0, 0);
        this.add(operatorChoiceBox, 1, 0);
        this.add(loginButton, 1, 1);
        this.setHgap(10);
        this.setVgap(10);
        this.setAlignment(Pos.CENTER);
        this.setTranslateX(-20);
    }
}


