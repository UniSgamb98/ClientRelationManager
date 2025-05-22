package orodent.clientrelationmanager.controller.filter;

import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.model.App;
import orodent.clientrelationmanager.model.searchfilter.Filter;
import orodent.clientrelationmanager.view.searchclient.FilterGroupView;
import orodent.clientrelationmanager.view.searchclient.FiltersView;

import java.util.ArrayList;
import java.util.List;

public class FilterGroupController {
    private FilterGroupView filterGroupView;
    private final ArrayList<FilterController> filterControllerList;
    private final FilterSectionController filterSectionController;
    private final DBManagerInterface dbManagerInterface;
    private final String filterName;
    private String sql;

    public FilterGroupController(String type, FilterSectionController filterSectionController, DBManagerInterface dbManagerInterface) {
        this.filterSectionController = filterSectionController;
        filterName = type;
        sql = "";
        this.dbManagerInterface = dbManagerInterface;
        this.filterControllerList = new ArrayList<>();
    }

    public void add() {
        //preparo la lista dei valori dei filtri con i valori in config.txt e i valori già presenti in database
        List<String> filterValues = new ArrayList<>(App.getConfigs().get(filterName));
        List<String> valuesInDatabase = dbManagerInterface.getAllValuesFromCustomerColumn(filterName);
        for (String i : valuesInDatabase){
            if (!filterValues.contains(i)) filterValues.add(i);
        }
        filterValues.remove(null);

        FilterController filterController = new FilterController(new Filter(filterName, filterValues), this);
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
        StringBuilder sql = new StringBuilder("(");
        boolean notFirstTimeLooping = false;
        for (FilterController filterController : filterControllerList) {
            if (filterController.isActive()) {
                if (notFirstTimeLooping) sql.append("OR ");
                if (filterController.isInclusive()) {  //Client da includere
                    sql.append(filterController.getSql()).append(" ");
                } else {                            //Client da escludere alla fine
                    sql.append("NOT ").append(filterController.getSql()).append(" ");
                }
            }
            notFirstTimeLooping = true;
        }
        sql.append(") ");
        setSql(sql.toString());

        //chiama il suo super controller per far aggiornare la lista
        filterSectionController.updateList();
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
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
