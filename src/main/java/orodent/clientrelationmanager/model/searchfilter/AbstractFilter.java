package orodent.clientrelationmanager.model.searchfilter;

import java.util.List;

public abstract class AbstractFilter<E extends Enum<E>> {
    private boolean inclusive;
    private boolean active;
    private String sql;
    protected List<E> enumList;


    public AbstractFilter(List<E> enumList) {
        this.enumList = enumList;
        sql = "";
        inclusive = true;
        active = false;
    }

    public List<E> getEnumList() {
        return enumList;
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
}
