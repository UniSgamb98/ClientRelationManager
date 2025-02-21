package orodent.clientrelationmanager.view.searchclient;

import javafx.scene.layout.BorderPane;

public class SearchClientView extends BorderPane {
    public SearchClientView() {
        FiltersView filtersView = new FiltersView();
        SearchResultView searchResultView = new SearchResultView();

        this.setLeft(filtersView);
        this.setCenter(searchResultView);
    }
}
