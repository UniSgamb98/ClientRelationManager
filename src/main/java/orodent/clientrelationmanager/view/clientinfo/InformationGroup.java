package orodent.clientrelationmanager.view.clientinfo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class InformationGroup extends StackPane {
    private final GridPane gridPane;

    public InformationGroup(String text) {
        gridPane = new GridPane();
        Label label = new Label(text);
        setAlignment(label, Pos.TOP_LEFT);
        gridPane.getColumnConstraints().addFirst(new ColumnConstraints(130, 130, Double.MAX_VALUE));
        gridPane.setPadding(new Insets(20, 0, 0, 0));
        gridPane.setHgap(5);
        gridPane.setVgap(2);
        this.getChildren().addAll(gridPane, label);
    }

    public void add(Node node, int col, int row, int colSpan, int rowSpan) {
        gridPane.add(node, col, row, colSpan, rowSpan);
        if (col == 1 && gridPane.getColumnConstraints().isEmpty()) {
            gridPane.getColumnConstraints().add(col, new ColumnConstraints(160, 230, Double.MAX_VALUE));
        }
    }
    public void add(Node node, int col, int row) {
        gridPane.add(node, col, row);
        if (col == 1 && gridPane.getColumnConstraints().isEmpty()) {
            gridPane.getColumnConstraints().add(col, new ColumnConstraints(160, 230, Double.MAX_VALUE));
        }
    }
}
