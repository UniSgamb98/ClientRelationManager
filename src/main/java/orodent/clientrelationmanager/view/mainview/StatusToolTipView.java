package orodent.clientrelationmanager.view.mainview;

import javafx.animation.FadeTransition;
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
import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.controller.main.MainController;

public class StatusToolTipView extends HBox {
    private final Circle light;
    private final Label label;
    private boolean isGreen = false;
    private final FadeTransition fadeOut;

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

        // Inizializza la transizione di dissolvenza
        fadeOut = new FadeTransition(Duration.seconds(2), label);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
    }

    private void commuteDatabaseConnection() {
        if (isGreen)    updateStatusLabel("Disconnessione in corso...");
        else    updateStatusLabel("Connessione in corso...");

        // Esegui le operazioni pesanti in background
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                DBManagerInterface dbInterface = new MainController().getApp().getDbManager();
                if (dbInterface.isAlive()) {
                    dbInterface.close();
                } else {
                    dbInterface.open();
                }
                return null;
            }
        };
        new Thread(task).start();
    }

    public void startFlashingEffect() {
        //Ferma le eventuali transizioni in corso
        fadeOut.stop();
        label.setOpacity(1.0);

        //Effetto Lampeggio rosso-nero
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.2), e -> label.setTextFill(Color.RED)),
                new KeyFrame(Duration.seconds(0.4), e -> label.setTextFill(Color.BLACK))
        );
        timeline.setCycleCount(2);
        timeline.play();

        // Dopo il lampeggio, programma la dissolvenza dopo 5 secondi
        fadeOut.setDelay(Duration.seconds(5)); // Aspetta 5 secondi prima di partire
        fadeOut.play();
    }

    public void switchColor(Color color){
        light.setFill(color);
        isGreen = color.equals(Color.GREEN);
    }

    public void updateStatusLabel(String text){
        label.setText(text);
    }
}
