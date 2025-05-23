package orodent.clientrelationmanager.view;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import orodent.clientrelationmanager.controller.main.MainController;

public class TemporaryView extends StackPane {
    public TemporaryView(String operator) {
        boolean isFemale = new MainController().getApp().getConfigs().get("operatori femmine").contains(operator);
        Label label = new Label((isFemale ? "Benvenuta" : "Benvenuto") + " " + operator);
        this.getChildren().add(label);
    }
}
