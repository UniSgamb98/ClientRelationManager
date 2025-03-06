package orodent.clientrelationmanager.view;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import orodent.clientrelationmanager.model.enums.Operator;

public class TemporaryView extends StackPane {
    public TemporaryView(Operator operator) {
        boolean isFemale = operator.equals(Operator.TERESA) || operator.equals(Operator.VICTORIA);
        Label label = new Label((isFemale ? "Benvenuta" : "Benvenuto") + " " + operator);
        this.getChildren().add(label);
    }
}
