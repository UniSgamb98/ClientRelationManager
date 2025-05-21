package orodent.clientrelationmanager.view;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import orodent.clientrelationmanager.controller.main.StatusToolTipController;
import orodent.clientrelationmanager.controller.main.buttons.ClientInfoController;
import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.model.App;
import orodent.clientrelationmanager.model.Client;
import orodent.clientrelationmanager.model.enums.ClientField;
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

            //Qua faccio tutti i check di correttezza di inserimento del cliente
            StatusToolTipController toolTipController = new StatusToolTipController();
            if (!App.configs.get("paesi").contains(newClient.get(ClientField.PAESE))) {
                toolTipController.update("Paese inserito non corretto");
            } else if (newClient.get(ClientField.RAGIONE_SOCIALE).equals("")) {
                toolTipController.update("Ragione sociale non inserita");
            } else if (db.getAllRagioniSociali().contains(newClient.get(ClientField.RAGIONE_SOCIALE))) {
                toolTipController.update(newClient.get(ClientField.RAGIONE_SOCIALE) + "è già presente nel database");
            } else {
                db.insertClient(newClient);
                toolTipController.update("Aggiunto cliente " + newClient);
            }
        });
        this.getChildren().addAll(clientInfoView, addClientButton);
    }
}
