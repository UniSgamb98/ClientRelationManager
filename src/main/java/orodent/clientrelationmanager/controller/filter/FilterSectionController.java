package orodent.clientrelationmanager.controller.filter;

import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.controller.main.MainController;
import orodent.clientrelationmanager.model.Client;
import orodent.clientrelationmanager.view.searchclient.SearchResultView;

import java.util.ArrayList;
import java.util.List;

public class FilterSectionController {
    ArrayList<FilterGroupController> filterGroups;
    List<Client> list;
    List<Client> excludedList;
    SearchResultView searchResultView;
    DBManagerInterface dbManagerInterface;

    public FilterSectionController(SearchResultView searchResultView, DBManagerInterface dbManagerInterface) {
        filterGroups = new ArrayList<>();
        list = new ArrayList<>();
        excludedList = new ArrayList<>();
        this.searchResultView = searchResultView;
        this.dbManagerInterface = dbManagerInterface;
    }

    public void updateList() {
        StringBuilder sql = new StringBuilder();
        boolean notFirstTimeLooping = false;
        for (FilterGroupController i : filterGroups){
            if (i.isSomeFilterActive()) {
                if (notFirstTimeLooping) sql.append(" AND ");
                sql.append(i.getSql());
                notFirstTimeLooping = true;
            }
        }
        list = dbManagerInterface.getClientWhere(sql.toString());
        new MainController().saveListFromFilteredSearch(list);

        //aggiorno la view
        if (list.isEmpty() && someFilterIsActive()){
            searchResultView.showEmptyList();
        } else if (list.isEmpty() && !someFilterIsActive()) {
            searchResultView.setClients(dbManagerInterface.getClientWhere(""));
        } else {
            searchResultView.setClients(list);
        }
    }

    private boolean someFilterIsActive(){
        for (FilterGroupController i : filterGroups){
            if (i.isSomeFilterActive()) return true;
        }
        return false;
    }

    public List<Client> getList(){
        return list;
    }

    public boolean isVoidOfFilters() {
        return true;
    }

    public void add(FilterGroupController operatorGroupController) {
        filterGroups.add(operatorGroupController);
    }
}
