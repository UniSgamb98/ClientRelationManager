package orodent.clientrelationmanager.view.searchclient;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SearchResultView extends VBox {
    public SearchResultView() {
        this.getChildren().add(new Label("Search Result"));
    }
}
