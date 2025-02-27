package orodent.clientrelationmanager.view.mainview;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import orodent.clientrelationmanager.model.App;

public class StatusToolTipView extends HBox {
    private final Circle light;
    private final Label label;

    public StatusToolTipView() {
        //Status Label
        label = new Label();

        //Traffic Light
        Circle border = new Circle(6, Color.BLACK);
        light = new Circle(5, Color.RED);
        StackPane trafficLight = new StackPane(border, light);
        trafficLight.setOnMouseClicked(e -> commuteDatabaseConnection());

        this.getChildren().addAll(label, trafficLight);
        this.setSpacing(3);
        this.setAlignment(Pos.BASELINE_RIGHT);
        this.setPadding(new Insets(0, 6, 0, 0));
    }

    private void commuteDatabaseConnection() {
        if(App.getDBManager().isAlive()){
            App.getDBManager().close();
        } else {
            App.getDBManager().open();
        }
    }

    public void switchColor(Color color){
        light.setFill(color);
    }

    public void updateStatusLabel(String text){
        label.setText(text);
    }
}
