package orodent.clientrelationmanager.view.searchclient;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import orodent.clientrelationmanager.controller.main.buttons.searchclient.filter.FilterController;

import java.util.List;

public class FiltersView extends HBox {
    private final ChoiceBox<Enum<?>> options;
    private final Polyline checkMark;
    private final Polyline cross;
    private final Circle background;
    private final StackPane activeOrInactivePanel;
    private final Rectangle rectangle;

    public FiltersView(FilterController filterController) {
        getStyleClass().add("filters-view"); // Usa il CSS per lo stile
        setSpacing(10);
        setAlignment(Pos.CENTER_LEFT);

        // Check mark ✔
        checkMark = new Polyline(8.0, 0.0, 3.0, 7.0, 0.0, 4.0);
        checkMark.getStyleClass().add("check-mark");

        // Cross ❌
        cross = new Polyline(0.0, 0.0, 8.0, 8.0, 4.0, 4.0, 8.0, 0.0, 0.0, 8.0);
        cross.getStyleClass().add("cross");

        // Background Circle
        background = new Circle(10);
        background.getStyleClass().add("background-circle");

        // Active/Inactive Panel
        activeOrInactivePanel = new StackPane(background, checkMark);
        activeOrInactivePanel.setOnMouseClicked(filterController);
        activeOrInactivePanel.setMinSize(20, 20);
        activeOrInactivePanel.getStyleClass().add("toggle-panel");

        // Remove Button (Red Cross)
        StackPane removeButton = new StackPane();
        rectangle = new Rectangle(15, 15);
        rectangle.getStyleClass().add("remove-button");

        Polyline x = new Polyline(4.5, 4.5, 0.0, 0.0, 9.0, 9.0, 4.5, 4.5, 9.0, 0.0, 0.0, 9.0, 4.5, 4.5);
        x.getStyleClass().add("remove-icon");

        removeButton.getChildren().addAll(rectangle, x);
        removeButton.setOnMouseClicked(event -> filterController.removeMe());
        removeButton.getStyleClass().add("remove-button-container");

        // ChoiceBox
        options = new ChoiceBox<>();
        List<? extends Enum<?>> enumList = filterController.getAbstractFilter().getEnumList();
        options.getItems().addAll(enumList);
        options.setOnAction(event -> filterController.updateList(options.getValue()));
        options.getStyleClass().add("filter-options");

        this.getChildren().addAll(activeOrInactivePanel, options, removeButton);
    }

    public void setActive(boolean active) {
        activeOrInactivePanel.getChildren().clear();
        activeOrInactivePanel.getChildren().addAll(background, active ? checkMark : cross);

        // Effetto di transizione per migliorare l'UX
        FadeTransition fade = new FadeTransition(Duration.millis(200), activeOrInactivePanel);
        fade.setFromValue(0.5);
        fade.setToValue(1.0);
        fade.play();
    }
}
