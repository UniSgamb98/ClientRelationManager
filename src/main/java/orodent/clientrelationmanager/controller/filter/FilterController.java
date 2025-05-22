package orodent.clientrelationmanager.controller.filter;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import orodent.clientrelationmanager.model.searchfilter.Filter;
import orodent.clientrelationmanager.view.searchclient.FiltersView;

public class FilterController implements EventHandler<MouseEvent> {
    private FiltersView filtersView;
    private final Filter filterModel;
    private final FilterGroupController filterGroupController;

    public FilterController(Filter filter, FilterGroupController filterGroupController) {
        this.filterGroupController = filterGroupController;
        this.filterModel = filter;
    }

    public void setFiltersView(FiltersView filtersView) {
        this.filtersView = filtersView;
    }

    public Filter getFilterModel() {
        return filterModel;
    }

    //Click sul cerchio per rendere il filtro inclusivo o esclusivo il filtro
    @Override
    public void handle(MouseEvent event) {
        boolean inclusive = !filterModel.isInclusive();
        filterModel.setInclusive(inclusive);
        filtersView.setInclusive(inclusive);
        filterGroupController.updateList();
    }

    public void removeMe() {
        filterGroupController.remove(filtersView, this);
    }

    public void updateList(String filterName, String value) {
        filterModel.setSql(filterName + " = '" + value + "'");
        filterGroupController.updateList();
    }

    public String getSql(){
        return filterModel.getSql();
    }

    public boolean isInclusive(){
        return filterModel.isInclusive();
    }

    public boolean isActive(){
        return filterModel.isActive();
    }

    public void setActive(boolean active){
        filterModel.setActive(active);
    }
}
