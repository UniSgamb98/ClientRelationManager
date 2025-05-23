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
    private final App app;

    public ClientFormatter(Client clientToFormat){
        client = clientToFormat;
        toolTipController = new StatusToolTipController();
        app = new MainController().getApp();
        dbManager = app.getDbManager();
    }

    public boolean isCorrectlyFormatted(boolean isNewClient) {
        boolean ret = true;

        if (!app.getConfigs().get("PAESE").contains(client.get(ClientField.PAESE))) {
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
