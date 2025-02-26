package orodent.clientrelationmanager.controller.main.buttons.searchclient.filter;

import orodent.clientrelationmanager.model.Client;
import orodent.clientrelationmanager.view.searchclient.SearchResultView;

import java.util.ArrayList;

public class FilterSectionController {
    ArrayList<FilterGroupController> filterGroups;
    ArrayList<Client> list;
    ArrayList<Client> excludedList;
    SearchResultView searchResultView;

    public FilterSectionController(SearchResultView searchResultView) {
        filterGroups = new ArrayList<>();
        list = new ArrayList<>();
        excludedList = new ArrayList<>();
        this.searchResultView = searchResultView;
    }

    public void updateList() {
        list.clear();
        for (FilterGroupController filterGroup : filterGroups) {
            list.addAll(filterGroup.getList());
        }
        for (FilterGroupController filterGroup : filterGroups) {
            list.removeAll(filterGroup.getExcludedClientList());
        }

        searchResultView.setClients(list);
    }

    public void add(FilterGroupController operatorGroupController) {
        filterGroups.add(operatorGroupController);
    }
}
