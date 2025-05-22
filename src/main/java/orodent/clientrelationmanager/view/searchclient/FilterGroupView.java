package orodent.clientrelationmanager.view.searchclient;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import orodent.clientrelationmanager.controller.filter.FilterGroupController;

public class FilterGroupView extends StackPane {
    FlowPane flowPane;
    Button addButton;
    public FilterGroupView(String text, FilterGroupController filterGroupController) {
        flowPane = new FlowPane();
        addButton = new Button("Aggiungi filtro");
        addButton.setOnAction(e -> filterGroupController.add());

        Label label = new Label(text);
        Separator separator = new Separator(Orientation.HORIZONTAL);
        separator.setPadding(new Insets(10, 0, 0, 0));
        setAlignment(separator, Pos.TOP_LEFT);
        setAlignment(label, Pos.TOP_LEFT);
        flowPane.setPadding(new Insets(20, 0, 0, 0));
        flowPane.setHgap(5);
        flowPane.setVgap(2);
        flowPane.getChildren().add(addButton);
        this.getChildren().addAll(flowPane, separator, label);
    }

    public void add(FiltersView node) {
      flowPane.getChildren().add(flowPane.getChildren().size()-1, node);
    }

    public void remove(FiltersView node) {
        flowPane.getChildren().remove(node);
    }
}
