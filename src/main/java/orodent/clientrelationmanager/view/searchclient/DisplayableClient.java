package orodent.clientrelationmanager.view.searchclient;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import orodent.clientrelationmanager.model.Client;
import orodent.clientrelationmanager.model.enums.ClientField;

public class DisplayableClient extends HBox {
    public DisplayableClient(Client client) {
        this.getChildren().add(new Label(client.getField(ClientField.RAGIONE_SOCIALE, String.class)));
    }
}
