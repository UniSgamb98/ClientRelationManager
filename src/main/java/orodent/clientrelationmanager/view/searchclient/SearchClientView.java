package orodent.clientrelationmanager.view.searchclient;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.controller.main.buttons.searchclient.ClientSelectionController;
import orodent.clientrelationmanager.controller.main.buttons.searchclient.filter.FilterGroupController;
import orodent.clientrelationmanager.controller.main.buttons.searchclient.filter.FilterSectionController;
import orodent.clientrelationmanager.model.Client;
import orodent.clientrelationmanager.model.enums.Filter;

public class SearchClientView extends BorderPane {
    private final VBox filters;
    private final SearchResultView searchResultView;
    private final DBManagerInterface dbManager;

    public SearchClientView(DBManagerInterface dbManagerInterface) {
        dbManager = dbManagerInterface;
        searchResultView = new SearchResultView(dbManagerInterface);
        FilterSectionController filterSectionController = new FilterSectionController(searchResultView);

        FilterGroupController operatorGroupController = new FilterGroupController(Filter.OPERATOR, dbManagerInterface, filterSectionController);
        FilterGroupController countryGroupController = new FilterGroupController(Filter.COUNTRY, dbManagerInterface, filterSectionController);
        FilterGroupController businessGroupController = new FilterGroupController(Filter.BUSINESS, dbManagerInterface, filterSectionController);

        filterSectionController.add(operatorGroupController);
        filterSectionController.add(countryGroupController);
        filterSectionController.add(businessGroupController);

        FilterGroupView operator = new FilterGroupView("Operatori", operatorGroupController);
        FilterGroupView country = new FilterGroupView("Paesi", countryGroupController);
        FilterGroupView business = new FilterGroupView("Tipo Cliente", businessGroupController);

        operatorGroupController.setGroupView(operator);
        countryGroupController.setGroupView(country);
        businessGroupController.setGroupView(business);

        filters = new VBox(operator, country, business);
        filters.setMaxWidth(150);
        this.setLeft(filters);
        this.setCenter(searchResultView);

        new ClientSelectionController(this, searchResultView);
    }

    public void showClientDetail(Client client) {
        this.setLeft(null); // Nascondi i filtri
        // Crea la view di dettaglio e passale una callback per tornare indietro
        ClientDetailView detailView = new ClientDetailView(client, dbManager, this::restoreSearchView);
        this.setCenter(detailView);
    }

    // Metodo per ripristinare la view originale per il pulsante indietro
    public void restoreSearchView() {
        this.setLeft(filters);
        this.setCenter(searchResultView);
    }
}
