package orodent.clientrelationmanager.controller;

import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.controller.main.MainController;
import orodent.clientrelationmanager.controller.main.StatusToolTipController;
import orodent.clientrelationmanager.model.App;
import orodent.clientrelationmanager.model.Client;
import orodent.clientrelationmanager.model.enums.ClientField;

public class ClientFormatter {
    private final Client client;
    private final StatusToolTipController toolTipController;
    private final DBManagerInterface dbManager;

    public ClientFormatter(Client clientToFormat){
        client = clientToFormat;
        toolTipController = new StatusToolTipController();
        dbManager = new MainController().getApp().getDbManager();
    }

    public boolean isCorrectlyFormatted(boolean isNewClient) {
        boolean ret = true;

        if (!App.getConfigs().get("PAESE").contains(client.get(ClientField.PAESE))) {
            toolTipController.update("Paese inserito non figura come idoneo da configurazione");
            ret = false;
        }

        else if (client.get(ClientField.RAGIONE_SOCIALE).equals("")) {
            toolTipController.update("Ragione sociale non inserita");
            ret = false;
        }

        else if (isNewClient && dbManager.getAllValuesFromCustomerColumn("RAGIONE_SOCIALE").contains(client.get(ClientField.RAGIONE_SOCIALE))) {
            toolTipController.update(client.get(ClientField.RAGIONE_SOCIALE) + "è già presente nel database");
            ret = false;
        }

        return ret;
    }
}
