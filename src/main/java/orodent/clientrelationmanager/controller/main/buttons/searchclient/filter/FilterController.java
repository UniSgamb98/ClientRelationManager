package orodent.clientrelationmanager.controller.main.buttons.searchclient.filter;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import orodent.clientrelationmanager.model.enums.Business;
import orodent.clientrelationmanager.model.searchfilter.AbstractFilter;
import orodent.clientrelationmanager.view.searchclient.FiltersView;

public class FilterController implements EventHandler<MouseEvent> {
    private FiltersView filtersView;
    private final AbstractFilter<? extends Enum<?>> filterModel;
    private final FilterGroupController filterGroupController;

    public FilterController(AbstractFilter<? extends Enum<?>> abstractFilter, FilterGroupController filterGroupController) {
        this.filterGroupController = filterGroupController;
        this.filterModel = abstractFilter;
    }

    public void setFiltersView(FiltersView filtersView) {
        this.filtersView = filtersView;
    }

    public AbstractFilter<? extends Enum<?>> getFilterModel() {
        return filterModel;
    }

    //Click sul cerchio per attivare o disattivare il filtro
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

    public void updateList(Enum<?> value) {
        switch (value) {
         /*   case Operator ignored -> {
                filterModel.setSql("OPERATORE_ASSEGNATO = '" + value + "'");
                filterGroupController.updateList();
            }

            case Country ignored -> {
                filterModel.setSql("PAESE = '" + value + "'");
                filterGroupController.updateList();
            }*/

            case Business ignored -> {
                filterModel.setSql("BUSINESS = '" + value + "'");
                filterGroupController.updateList();
            }

            default -> {}
        }
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
