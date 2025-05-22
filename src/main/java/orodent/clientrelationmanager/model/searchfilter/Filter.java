package orodent.clientrelationmanager.model.searchfilter;

import java.util.List;

public class Filter {
    private boolean inclusive;
    private boolean active;
    private String sql;
    protected List<String> filterValues;
    private final String filterName;


    public Filter(String filterName, List<String> filterValues) {
        this.filterName = filterName;
        this.filterValues = filterValues;
        sql = "";
        inclusive = true;
        active = false;
    }

    public List<String> getFilterValues() {
        return filterValues;
    }

    public boolean isInclusive() {
        return inclusive;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setInclusive(boolean isActive) {
        inclusive = isActive;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getFilterName() {
        return filterName;
    }
}
