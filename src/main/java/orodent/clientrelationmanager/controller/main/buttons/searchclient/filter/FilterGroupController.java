package orodent.clientrelationmanager.controller.main.buttons.searchclient.filter;

import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.model.Client;
import orodent.clientrelationmanager.model.enums.Filter;
import orodent.clientrelationmanager.model.searchfilter.BusinessFilter;
import orodent.clientrelationmanager.model.searchfilter.CountryFilter;
import orodent.clientrelationmanager.model.searchfilter.OperatorFilter;
import orodent.clientrelationmanager.view.searchclient.FilterGroupView;
import orodent.clientrelationmanager.view.searchclient.FiltersView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilterGroupController {
    private FilterGroupView filterGroupView;
    private final ArrayList<FilterController> filterControllerList;
    private List<Client> list;
    private List<Client> excludedClientList;
    private final FilterSectionController filterSectionController;
    private final Filter type;
    private final DBManagerInterface dbManagerInterface;

    public FilterGroupController(Filter type, DBManagerInterface dbManagerInterface, FilterSectionController filterSectionController) {
        this.filterSectionController = filterSectionController;
        this.type = type;
        this.dbManagerInterface = dbManagerInterface;
        this.filterControllerList = new ArrayList<>();
        this.excludedClientList = new ArrayList<>();
        list = new ArrayList<>();
    }

    public void add() {
        FilterController filterController = null;
        switch (type) {
            case BUSINESS -> filterController = new FilterController(new BusinessFilter(dbManagerInterface.getAllBusiness()), this, dbManagerInterface);
            case COUNTRY ->  filterController = new FilterController(new CountryFilter(dbManagerInterface.getAllCountries()), this, dbManagerInterface);
            case OPERATOR -> filterController = new FilterController(new OperatorFilter(dbManagerInterface.getAllOperators()), this, dbManagerInterface);
        }
        FiltersView filterView = new FiltersView(filterController);
        filterController.setFiltersView(filterView);
        filterGroupView.add(filterView);
        filterControllerList.add(filterController);
    }

    public void remove(FiltersView filtersView, FilterController filterController) {
        filterGroupView.remove(filtersView);
        filterControllerList.remove(filterController);
        updateList();
    }

    public void setGroupView(FilterGroupView filterGroupView) {
        this.filterGroupView = filterGroupView;
    }

    /**
     * Restituisce aggiorna 2 liste e chiama il suo superController per aggiornarsi di conseguenza.
     * Lista add, l'unisce tutti i client dei singoli filtri
     * Lista remove, tutti i client da escludere a fine operazione
     */
    public void updateList() {
        ArrayList<Client> toAdd = new ArrayList<>();
        ArrayList<Client> toRemove = new ArrayList<>();
        for (FilterController filterController : filterControllerList) {
            if (filterController.isActive()) {  //Client da includere
                toAdd.addAll(filterController.getList());
            } else {                            //Client da escludere alla fine
                toRemove.addAll(filterController.getList());
            }
        }

        //istanzio nuovamente le due liste add e remove popolandole con oggetti client senza duplicati
        list = toAdd.stream().distinct().collect(Collectors.toList());
        excludedClientList = toRemove.stream().distinct().collect(Collectors.toList());

        //chiama il suo super controller per far aggiornare la lista
        filterSectionController.updateList();
    }

    /**
     * Ottieni la lista di client da escludere alla fine
     */
    public List<Client> getExcludedClientList() {
        return excludedClientList;
    }

    /**
     * Ottieni la lista dei client da aggiungere
     */
    public List<Client> getList() {
        return list;
    }

    public boolean isSomeFilterActive() {
        for (FilterController i : filterControllerList){
            if (i.isActive()) {
                return true;
            }
        }
        return false;
    }
}
