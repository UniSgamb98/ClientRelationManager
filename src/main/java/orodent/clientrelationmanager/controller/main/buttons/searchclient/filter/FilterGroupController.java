package orodent.clientrelationmanager.controller.main.buttons.searchclient.filter;

import orodent.clientrelationmanager.model.enums.Filter;
import orodent.clientrelationmanager.model.searchfilter.BusinessFilter;
import orodent.clientrelationmanager.model.searchfilter.CountryFilter;
import orodent.clientrelationmanager.model.searchfilter.OperatorFilter;
import orodent.clientrelationmanager.view.searchclient.FilterGroupView;
import orodent.clientrelationmanager.view.searchclient.FiltersView;

import java.util.ArrayList;

public class FilterGroupController {
    FilterGroupView filterGroupView;
    ArrayList<FilterController> filterControllerList;
    Filter type;

    public FilterGroupController(Filter type) {
        this.type = type;
        this.filterControllerList = new ArrayList<>();
    }

    public void add() {
        FilterController filterController = null;
        switch (type) {
            case BUSINESS -> filterController = new FilterController(new BusinessFilter(), this);
            case COUNTRY ->  filterController = new FilterController(new CountryFilter(), this);
            case OPERATOR -> filterController = new FilterController(new OperatorFilter(), this);

        }
        FiltersView filterView = new FiltersView(filterController);
        filterController.setFiltersView(filterView);
        filterGroupView.add(filterView);
        filterControllerList.add(filterController);
    }

    public void remove(FiltersView filtersView, FilterController filterController) {
        filterGroupView.remove(filtersView);
        filterControllerList.remove(filterController);
    }

    public void setGroupView(FilterGroupView filterGroupView) {
        this.filterGroupView = filterGroupView;
    }
}
