package orodent.clientrelationmanager.controller.main.buttons.searchclient.filter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import orodent.clientrelationmanager.model.searchfilter.FilterInterface;
import orodent.clientrelationmanager.view.searchclient.FiltersView;

import java.util.ArrayList;

public class FilterController implements EventHandler<MouseEvent> {
    private FiltersView filtersView;
    private final FilterInterface filterInterface;
    private final FilterGroupController filterGroupController;

    public FilterController(FilterInterface filterInterface, FilterGroupController filterGroupController) {
        this.filterGroupController = filterGroupController;
        this.filterInterface = filterInterface;
    }

    public void setFiltersView(FiltersView filtersView) {
        this.filtersView = filtersView;
    }

    public ArrayList<String> getFilterItems() {
        return new ArrayList<>(filterInterface.getFilterItems());
    }

    @Override
    public void handle(MouseEvent event) {
        boolean isActive = !filterInterface.isActive();
        filterInterface.setActive(isActive);
        filtersView.setActive(isActive);
    }

    public void removeMe() {
        filterGroupController.remove(filtersView, this);
    }
}
