package orodent.clientrelationmanager.view.mainview;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import orodent.clientrelationmanager.model.App;

public class StatusToolTipView extends HBox {
    private final Circle light;
    private final Label label;
    private boolean isGreen = false;

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
        if (isGreen)
            updateStatusLabel("Disconnessione in corso...");
        else
            updateStatusLabel("Connessione in corso...");
        // Esegui le operazioni pesanti in background
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                if (App.getDBManager().isAlive()) {
                    App.getDBManager().close();
                } else {
                    App.getDBManager().open();
                }
                return null;
            }
        };

        new Thread(task).start();
    }

    public void startFlashingEffect() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.2), e -> label.setTextFill(Color.RED)),
                new KeyFrame(Duration.seconds(0.4), e -> label.setTextFill(Color.BLACK))
        );
        timeline.setCycleCount(2);
        timeline.play();
    }

    public void switchColor(Color color){
        light.setFill(color);
        isGreen = color.equals(Color.GREEN);
    }

    public void updateStatusLabel(String text){
        label.setText(text);
    }
}
