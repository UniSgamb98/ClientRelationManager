package orodent.clientrelationmanager.controller.main.buttons.searchclient;

import orodent.clientrelationmanager.view.searchclient.DisplayableClient;
import orodent.clientrelationmanager.view.searchclient.SearchClientView;
import orodent.clientrelationmanager.view.searchclient.SearchResultView;

public class ClientSelectionController {
    private final SearchClientView searchClientView;
    private final SearchResultView searchResultView;

    public ClientSelectionController(SearchClientView searchClientView, SearchResultView searchResultView) {
        this.searchClientView = searchClientView;
        this.searchResultView = searchResultView;
        initialize();
    }

    private void initialize() {
        // Gestione del doppio click sulla ListView
        searchResultView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                DisplayableClient selected = searchResultView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    // Mostra la view di dettaglio tramite il metodo della SearchClientView
                    searchClientView.showClientDetail(selected.getClient());
                }
            }
        });
    }
}
