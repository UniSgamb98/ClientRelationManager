package orodent.clientrelationmanager.controller.main.buttons.searchclient.filter;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.model.Client;
import orodent.clientrelationmanager.model.enums.Business;
import orodent.clientrelationmanager.model.enums.Country;
import orodent.clientrelationmanager.model.enums.Operator;
import orodent.clientrelationmanager.model.searchfilter.AbstractFilter;
import orodent.clientrelationmanager.view.searchclient.FiltersView;

import java.util.List;

public class FilterController implements EventHandler<MouseEvent> {
    private FiltersView filtersView;
    private final AbstractFilter<? extends Enum<?>> abstractFilter;
    private final FilterGroupController filterGroupController;
    private final DBManagerInterface dbManager;


    public FilterController(AbstractFilter<? extends Enum<?>> abstractFilter, FilterGroupController filterGroupController, DBManagerInterface dbManagerInterface) {
        this.filterGroupController = filterGroupController;
        this.abstractFilter = abstractFilter;
        this.dbManager = dbManagerInterface;
    }

    public void setFiltersView(FiltersView filtersView) {
        this.filtersView = filtersView;
    }

    public AbstractFilter<? extends Enum<?>> getAbstractFilter() {
        return abstractFilter;
    }

    //Click sul cerchio per attivare o disattivare il filtro
    @Override
    public void handle(MouseEvent event) {
        boolean isActive = !abstractFilter.isActive();
        abstractFilter.setActive(isActive);
        filtersView.setActive(isActive);
        filterGroupController.updateList();
    }

    public void removeMe() {
        filterGroupController.remove(filtersView, this);
    }

    public void updateList(Enum<?> value) {
        switch (value) {
            case Operator operator -> {
                abstractFilter.setResultList(dbManager.queryCustomerWithSingleParameter("OPERATORE_ASSEGNATO", operator));
                filterGroupController.updateList();
            }

            case Country country -> {
                abstractFilter.setResultList(dbManager.queryCustomerWithSingleParameter("PAESE", country));
                filterGroupController.updateList();
            }

            case Business business -> {
                abstractFilter.setResultList(dbManager.queryCustomerWithSingleParameter("BUSINESS", business));
                filterGroupController.updateList();
            }

            case null, default -> {}
        }
    }

    public boolean isActive(){
        return abstractFilter.isActive();
    }

    public List<Client> getList(){
        return abstractFilter.getResultList();
    }
}
