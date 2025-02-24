package orodent.clientrelationmanager.view.searchclient;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import orodent.clientrelationmanager.controller.main.buttons.searchclient.filter.FilterGroupController;
import orodent.clientrelationmanager.model.enums.Filter;

public class SearchClientView extends BorderPane {

    public SearchClientView() {
        FilterGroupController operatorGroupController = new FilterGroupController(Filter.OPERATOR);
        FilterGroupController countryGroupController = new FilterGroupController(Filter.COUNTRY);
        FilterGroupController businessGroupController = new FilterGroupController(Filter.BUSINESS);

        FilterGroupView operator = new FilterGroupView("Operatori", operatorGroupController);
        FilterGroupView country = new FilterGroupView("Paesi", countryGroupController);
        FilterGroupView business = new FilterGroupView("Tipo Cliente", businessGroupController);

        operatorGroupController.setGroupView(operator);
        countryGroupController.setGroupView(country);
        businessGroupController.setGroupView(business);

        operatorGroupController.add();
        countryGroupController.add();
        businessGroupController.add();

        VBox filters = new VBox(operator, country, business);
        this.setLeft(filters);

        SearchResultView searchResultView = new SearchResultView();
        this.setCenter(searchResultView);
    }
}
