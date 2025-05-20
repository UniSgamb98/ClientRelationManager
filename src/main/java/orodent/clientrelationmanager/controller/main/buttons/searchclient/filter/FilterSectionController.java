package orodent.clientrelationmanager.controller.main.buttons.searchclient.filter;

import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.model.Client;
import orodent.clientrelationmanager.view.searchclient.SearchResultView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FilterSectionController {
    ArrayList<FilterGroupController> filterGroups;
    List<Client> list;
    List<Client> excludedList;
    SearchResultView searchResultView;
    DBManagerInterface dbManagerInterface;
    int N_GRUPPI_DI_FILTRI = 3;

    public FilterSectionController(SearchResultView searchResultView, DBManagerInterface dbManagerInterface) {
        filterGroups = new ArrayList<>();
        list = new ArrayList<>();
        excludedList = new ArrayList<>();
        this.searchResultView = searchResultView;
        this.dbManagerInterface = dbManagerInterface;
    }

    public void updateList() {
        Set<Client> common = new HashSet<>();
        int i = 0;
        while (common.isEmpty() && i < N_GRUPPI_DI_FILTRI) {
            common.addAll(filterGroups.get(i).getList());
            i++;
        }
        ArrayList<Client> excluded = new ArrayList<>();

        //Ottengo l'intersezione tra le liste dei gruppi di filtri
        for (i = 1; i < filterGroups.size(); i++) {
            if (!filterGroups.get(i).getList().isEmpty()){
                common.retainAll(filterGroups.get(i).getList());
            }
        }

        //recupera tutti i clienti da escludere
        for (FilterGroupController filterGroup : filterGroups) {
            excluded.addAll(filterGroup.getExcludedClientList());
        }

        //rimuovo i duplicati
        excludedList = excluded.stream().distinct().collect(Collectors.toList());

        //rimuovo dalla list i clienti presenti nella lista dei clienti da escludere
        list = new ArrayList<>(common);
        list.removeAll(excludedList);

        //aggiorno la view
        if (list.isEmpty() && someFilterIsActive()){
            searchResultView.showEmptyList();
        } else if (list.isEmpty() && !someFilterIsActive()) {
            searchResultView.setClients(dbManagerInterface.getAllClient());
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
