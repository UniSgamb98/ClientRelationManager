package orodent.clientrelationmanager.view.searchclient;

import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.controller.main.MainController;
import orodent.clientrelationmanager.controller.filter.FilterGroupController;
import orodent.clientrelationmanager.controller.filter.FilterSectionController;
import orodent.clientrelationmanager.model.App;
import orodent.clientrelationmanager.model.Client;

import java.util.List;

public class SearchClientView extends BorderPane {
    private final SearchResultView searchResultView;
    private final TextField searchBar;
    //Sezione filtri

    public SearchClientView() {
        App app = new MainController().getApp();
        DBManagerInterface dbInterface = app.getDbManager();

        //ListView con il clienti della ricerca
        searchResultView = new SearchResultView();
        searchResultView.setClients(dbInterface.getClientWhere(""));
        this.setCenter(searchResultView);

        //Filtri con i loro Controllers
        FilterSectionController filterSectionController = new FilterSectionController(searchResultView, dbInterface);

        // Sezione Filtri
        VBox filters = new VBox();
        filters.setMaxWidth(150);
        this.setLeft(filters);
        for (String i : app.getConfigs().get("filtri")){
            FilterGroupController filterGroupController = new FilterGroupController(i, filterSectionController, dbInterface);
            filterSectionController.add(filterGroupController);
            FilterGroupView filterGroupView = new FilterGroupView(i, filterGroupController);
            filterGroupController.setGroupView(filterGroupView);
            filters.getChildren().add(filterGroupView);
        }

        //SearchBar
        searchBar = new TextField();
        searchBar.setPromptText("Search");
        searchBar.setOnAction(e -> {
            if (filterSectionController.isVoidOfFilters()){
                searchResultView.setClients(dbInterface.searchClient(searchBar.getText()));
            } else {
                //TODO filtra ulteriormente la lista;
            }
        });
        this.setTop(searchBar);
    }

    public void setResultList(List<Client> clientList){
        if (clientList != null) searchResultView.setClients(clientList);
    }
}
