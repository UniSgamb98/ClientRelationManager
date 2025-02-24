package orodent.clientrelationmanager.view.searchclient;

import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import orodent.clientrelationmanager.controller.main.buttons.searchclient.filter.FilterController;

public class FiltersView extends HBox {
    ChoiceBox<String> options;
    Polyline checkMark;
    Polyline cross;
    Circle background;
    StackPane activeOrInactivePanel;
    Rectangle rectangle;
    public FiltersView(FilterController filterController) {
        checkMark = new Polyline();
        cross = new Polyline();
        checkMark.getPoints().addAll(
                8.0, 0.0,
                3.0, 7.0,
                0.0, 4.0);
        checkMark.setStroke(Color.GREEN);
        cross.getPoints().addAll(
                0.0, 0.0,
                8.0, 8.0,
                4.0, 4.0,
                8.0, 0.0,
                0.0, 8.0
        );
        cross.setStroke(Color.RED);

        background = new Circle(10);
        background.setFill(Color.BEIGE);
        background.setStrokeWidth(1);
        background.setStroke(Color.BLACK);
        activeOrInactivePanel = new StackPane(background, checkMark);
        activeOrInactivePanel.setOnMouseClicked(filterController);
        activeOrInactivePanel.setMaxHeight(10);

        StackPane sp = new StackPane();
        rectangle = new Rectangle(10,20, Color.DARKRED);
        rectangle.setArcHeight(5);
        rectangle.setArcWidth(5);
        Polyline x = new Polyline(
                4.5, 4.5,
                0.0, 0.0,
                9.0, 9.0,
                4.5, 4.5,
                9.0, 0.0,
                0.0, 9.0,
                4.5, 4.5
        );
        x.setStrokeWidth(2);
        x.setStroke(Color.RED);
        sp.getChildren().addAll(rectangle, x);
        sp.setOnMouseClicked(event -> filterController.removeMe());

        options = new ChoiceBox<>();
        options.getItems().addAll(filterController.getFilterItems());
        this.getChildren().addAll(activeOrInactivePanel, options, sp);
    }

    public void setActive(boolean active) {
        if (active) {
            activeOrInactivePanel.getChildren().clear();
            activeOrInactivePanel.getChildren().addAll(background, checkMark);
        } else {
            activeOrInactivePanel.getChildren().clear();
            activeOrInactivePanel.getChildren().addAll(background, cross);
        }
    }

    public String getSelectedOption() {
        return options.getValue();
    }
}
