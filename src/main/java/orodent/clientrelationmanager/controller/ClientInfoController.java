package orodent.clientrelationmanager.controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import orodent.clientrelationmanager.model.Client;
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
        client.setRagioneSociale(clientInfoView.getRagioneSociale());
        client.setPersonaRiferimento(clientInfoView.getPersonaRiferimento());
        client.setEmailRiferimento(clientInfoView.getEmailRiferimento());
        client.setCellulareRiferimento(clientInfoView.getCellulareRiferimento());
        client.setTelefonoAziendale(clientInfoView.getTelefonoAziendale());
        client.setEmailAziendale(clientInfoView.getEmailAziendale());
        client.setPaese(clientInfoView.getPaese());
        client.setCitta(clientInfoView.getCitta());
        client.setNomeTitolare(clientInfoView.getNomeTitolare());
        client.setCellulareTitolare(clientInfoView.getCellulareTitolare());
        client.setEmailTitolare(clientInfoView.getEmailTitolare());
        client.setSitoWeb(clientInfoView.getSitoWeb());
        client.setVolteContattati(clientInfoView.getVolteContattati());
        client.setUltimaChiamata(clientInfoView.getUltimaChiamata());
        client.setProssimaChiamata(clientInfoView.getProssimaChiamata());
        client.setDataAcquisizione(clientInfoView.getDataAcquisizione());
        client.setTipoCliente(clientInfoView.getTipoCliente());
        client.setOperatoreAssegnato(clientInfoView.getOperatore());
        client.setInformation(clientInfoView.hasInformation());
        client.setCatalog(clientInfoView.hasCatalog());
        client.setSample(clientInfoView.hasSample());
    }

    @Override
    public void handle(KeyEvent event) {
        if (developerPrivilege.match(event)) {
            developerVisibility = !developerVisibility;
            clientInfoView.toggleDeveloperVisibility(developerVisibility);
        }
    }
}
