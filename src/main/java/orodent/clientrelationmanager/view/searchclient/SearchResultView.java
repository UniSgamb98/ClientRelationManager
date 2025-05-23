package orodent.clientrelationmanager.view.searchclient;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import orodent.clientrelationmanager.controller.main.MainController;
import orodent.clientrelationmanager.model.Client;
import orodent.clientrelationmanager.model.EmptyClient;

import java.util.List;

public class SearchResultView extends ListView<DisplayableClient> {
    private final ObservableList<DisplayableClient> displayableClients;
    private final ObservableList<Client> clients;

    public SearchResultView() {
        clients = FXCollections.observableArrayList();
        this.displayableClients = FXCollections.observableArrayList();

        // Aggiungi il listener per aggiornare automaticamente la lista visualizzata
        this.clients.addListener((ListChangeListener<Client>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Client c : change.getAddedSubList()) {
                        displayableClients.add(new DisplayableClient(c));
                    }
                }
                if (change.wasRemoved()) {
                    for (Client c : change.getRemoved()) {
                        displayableClients.removeIf(d -> d.getClient().equals(c));
                    }
                }
            }
        });
        this.setItems(displayableClients);

        // Gestione del doppio click sulla ListView
        this.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                DisplayableClient selected = this.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    // Mostra la view di dettaglio tramite il metodo della SearchClientView
                    MainController mainController = new MainController();
                    mainController.showClientPage(mainController.getApp().getDbManager().getClientWhere("ID = '" + selected.getClient().getUuid() +"'").getFirst());
                }
            }
        });
    }

    // Usata per mostrare i clienti nella lista passata come parametro
    public void setClients(List<Client> clients) {
        this.clients.clear();
        this.clients.addAll(clients); // Questo scatenerà il listener automaticamente
    }

    // Viene chiamata quando i filtri hanno escluso tutti i clienti e non ce ne sono da mostrare
    public void showEmptyList() {
        this.clients.clear();
        clients.add(new EmptyClient());
    }
}
