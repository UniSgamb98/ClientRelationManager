package orodent.clientrelationmanager.view.clientinfo;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class InformationGroup extends StackPane {
    private final GridPane gridPane;

    public InformationGroup(String text) {
        gridPane = new GridPane();
        Label label = new Label(text);
        Separator separator = new Separator(Orientation.HORIZONTAL);
        separator.setPadding(new Insets(10, 0, 0, 0));
        setAlignment(separator, Pos.TOP_LEFT);
        setAlignment(label, Pos.TOP_LEFT);
        gridPane.setPadding(new Insets(20, 0, 0, 0));
        gridPane.setHgap(5);
        gridPane.setVgap(2);
        this.getChildren().addAll(gridPane, separator, label);
    }

    public void add(Node node, int col, int row, int colSpan, int rowSpan) {
        gridPane.add(node, col, row, colSpan, rowSpan);
    }
    public void add(Node node, int col, int row) {
        gridPane.add(node, col, row);
    }
}
