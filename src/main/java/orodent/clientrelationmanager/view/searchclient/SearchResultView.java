package orodent.clientrelationmanager.view.searchclient;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
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
    }

    public void setClients(List<Client> clients) {
        this.clients.clear();
        this.clients.addAll(clients); // Questo scatenerà il listener automaticamente
    }

    public void showEmptyList() {
        this.clients.clear();
        clients.add(new EmptyClient());
    }
}
