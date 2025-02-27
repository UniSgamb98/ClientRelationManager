package orodent.clientrelationmanager.view;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import orodent.clientrelationmanager.controller.main.StatusToolTipController;
import orodent.clientrelationmanager.controller.main.buttons.ClientInfoController;
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
        addClientButton.setOnAction(e -> {          //TODO Gestire la SQLE di quando si cerca di aggiungere due volte lo stesso cliente
            Client newClient = clientInfoController.getClient();
            db.insertClient(newClient);
            new StatusToolTipController().update("Aggiunto cliente " + newClient);
        });
        this.getChildren().addAll(clientInfoView, addClientButton);
    }
}
