package orodent.clientrelationmanager.model.searchfilter;

import java.util.List;

public interface FilterInterface {
    boolean isActive();
    List<String> getFilterItems();
    void setActive(boolean isActive);
}
