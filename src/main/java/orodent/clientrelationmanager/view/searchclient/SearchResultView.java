package orodent.clientrelationmanager.view.searchclient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.model.Client;

import java.util.List;

public class SearchResultView extends ListView<DisplayableClient> {
    private final ObservableList<DisplayableClient> displayableClients;

    public SearchResultView(DBManagerInterface dbManagerInterface) {
        displayableClients = FXCollections.observableArrayList();
        this.setItems(displayableClients);
        for(Client i : dbManagerInterface.getAllClient()) {
            DisplayableClient displayableClient = new DisplayableClient(i);
            displayableClients.add(displayableClient);
        }
    }

    public void setClients(List<Client> Clients) {
        this.displayableClients.clear();
        for(Client i : Clients) {
            this.displayableClients.add(new DisplayableClient(i));
        }
    }
}
