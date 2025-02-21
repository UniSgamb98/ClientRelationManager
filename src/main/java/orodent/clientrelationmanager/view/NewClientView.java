package orodent.clientrelationmanager.view;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import orodent.clientrelationmanager.controller.ClientInfoController;
import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.model.Client;
import orodent.clientrelationmanager.view.clientinfo.ClientInfoView;

public class NewClientView extends VBox {
    ClientInfoView clientInfoView;

    public NewClientView(DBManagerInterface db, Client client) {
        clientInfoView = new ClientInfoView(client);
        ClientInfoController clientInfoController = new ClientInfoController(client, clientInfoView);
        clientInfoView.setOnKeyPressed(clientInfoController);   //Ctrl + H to show developer info
        Button addClientButton = new Button("Add Client");
        addClientButton.setOnAction(e -> db.insertClient(clientInfoController.getClient()));
        this.getChildren().addAll(clientInfoView, addClientButton);
    }
}
