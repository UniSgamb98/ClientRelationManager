package orodent.clientrelationmanager.view;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import orodent.clientrelationmanager.model.App;

public class TemporaryView extends StackPane {
    public TemporaryView(String operator) {
        boolean isFemale = App.configs.get("operatori femmine").contains(operator);
                //operator.equals(Operator.TERESA) || operator.equals(Operator.VICTORIA);
        Label label = new Label((isFemale ? "Benvenuta" : "Benvenuto") + " " + operator);
        this.getChildren().add(label);
    }
}
