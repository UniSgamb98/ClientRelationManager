package orodent.clientrelationmanager.controller.main.buttons.searchclient.filter;

import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.model.enums.Filter;
import orodent.clientrelationmanager.model.searchfilter.BusinessFilter;
import orodent.clientrelationmanager.view.searchclient.FilterGroupView;
import orodent.clientrelationmanager.view.searchclient.FiltersView;

import java.util.ArrayList;

public class FilterGroupController {
    private FilterGroupView filterGroupView;
    private final ArrayList<FilterController> filterControllerList;
    private final FilterSectionController filterSectionController;
    private final String type;
    private final DBManagerInterface dbManagerInterface;
    private String sql;

    public FilterGroupController(String type, DBManagerInterface dbManagerInterface, FilterSectionController filterSectionController) {
        this.filterSectionController = filterSectionController;
        this.type = type;
        sql = "";
        this.dbManagerInterface = dbManagerInterface;
        this.filterControllerList = new ArrayList<>();
    }

    public void add() {
        FilterController filterController = null;
        switch (type) {
            case BUSINESS -> filterController = new FilterController(new BusinessFilter(dbManagerInterface.getAllBusiness()), this);
            //case COUNTRY ->  filterController = new FilterController(new CountryFilter(dbManagerInterface.getAllCountries()), this);
            //case OPERATOR -> filterController = new FilterController(new OperatorFilter(dbManagerInterface.getAllOperators()), this);
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
