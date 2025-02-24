package orodent.clientrelationmanager.controller.main.buttons;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import orodent.clientrelationmanager.model.Client;
import orodent.clientrelationmanager.model.enums.ClientField;
import orodent.clientrelationmanager.view.clientinfo.ClientInfoView;

public class ClientInfoController implements EventHandler<KeyEvent> {
    Client client;
    ClientInfoView clientInfoView;
    final KeyCombination developerPrivilege = new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN);
    boolean developerVisibility = false;

    public ClientInfoController(Client client, ClientInfoView clientInfoView) {
        this.client = client;
        this.clientInfoView = clientInfoView;
    }

    public Client getClient() {
        getClientChangesFromView();
        return client;
    }

    public void getClientChangesFromView() {
        client.setUuid(clientInfoView.getUuid());
        client.set(ClientField.RAGIONE_SOCIALE, clientInfoView.getRagioneSociale());
        client.set(ClientField.PERSONA_RIFERIMENTO, clientInfoView.getPersonaRiferimento());
        client.set(ClientField.EMAIL_RIFERIMENTO, clientInfoView.getEmailRiferimento());
        client.set(ClientField.CELLULARE_RIFERIMENTO, clientInfoView.getCellulareRiferimento());
        client.set(ClientField.TELEFONO_AZIENDALE, clientInfoView.getTelefonoAziendale());
        client.set(ClientField.EMAIL_AZIENDALE, clientInfoView.getEmailAziendale());
        client.set(ClientField.PAESE, clientInfoView.getPaese());
        client.set(ClientField.CITTA, clientInfoView.getCitta());
        client.set(ClientField.NOME_TITOLARE, clientInfoView.getNomeTitolare());
        client.set(ClientField.CELLULARE_TITOLARE, clientInfoView.getCellulareTitolare());
        client.set(ClientField.EMAIL_TITOLARE, clientInfoView.getEmailTitolare());
        client.set(ClientField.SITO_WEB, clientInfoView.getSitoWeb());
        client.set(ClientField.VOLTE_CONTATTATI, clientInfoView.getVolteContattati());
        client.set(ClientField.ULTIMA_CHIAMATA, clientInfoView.getUltimaChiamata());
        client.set(ClientField.PROSSIMA_CHIAMATA, clientInfoView.getProssimaChiamata());
        client.set(ClientField.DATA_ACQUISIZIONE, clientInfoView.getDataAcquisizione());
        client.set(ClientField.BUSINESS, clientInfoView.getTipoCliente());
        client.set(ClientField.OPERATORE_ASSEGNATO, clientInfoView.getOperatore());
        client.set(ClientField.INFORMATION, clientInfoView.hasInformation());
        client.set(ClientField.CATALOG, clientInfoView.hasCatalog());
        client.set(ClientField.SAMPLE, clientInfoView.hasSample());
    }

    @Override
    public void handle(KeyEvent event) {
        if (developerPrivilege.match(event)) {
            developerVisibility = !developerVisibility;
            clientInfoView.toggleDeveloperVisibility(developerVisibility);
        }
    }
}
