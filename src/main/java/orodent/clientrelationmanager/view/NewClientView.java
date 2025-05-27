package orodent.clientrelationmanager.view;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import orodent.clientrelationmanager.controller.ClientFormatter;
import orodent.clientrelationmanager.controller.main.MainController;
import orodent.clientrelationmanager.controller.main.StatusToolTipController;
import orodent.clientrelationmanager.controller.main.buttons.ClientInfoController;
import orodent.clientrelationmanager.model.Client;
import orodent.clientrelationmanager.view.clientinfo.ClientInfoView;

public class NewClientView extends VBox {
    ClientInfoView clientInfoView;

    public NewClientView() {
        Client client = new Client();
        clientInfoView = new ClientInfoView(client);
        ClientInfoController clientInfoController = new ClientInfoController(client, clientInfoView);
        clientInfoView.setOnKeyPressed(clientInfoController);   //Ctrl + H to show developer info
        Button addClientButton = new Button("Add Client");
        this.getChildren().addAll(clientInfoView, addClientButton);

        addClientButton.setOnAction(e -> {
            Client newClient = clientInfoController.getClient();

            //Qua faccio tutti i check di correttezza di inserimento del cliente
            StatusToolTipController toolTipController = new StatusToolTipController();

            ClientFormatter clientFormatter = new ClientFormatter(newClient);
            if (clientFormatter.isCorrectlyFormatted(true)) {
                new MainController().getApp().getDbManager().insertClient(newClient);
                toolTipController.update("Aggiunto cliente " + newClient);
            }
        });
    }
}
