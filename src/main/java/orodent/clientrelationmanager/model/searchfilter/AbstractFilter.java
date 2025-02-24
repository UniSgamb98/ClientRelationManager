package orodent.clientrelationmanager.model.searchfilter;

public abstract class AbstractFilter implements FilterInterface {
    boolean active;

    public AbstractFilter() {
        active = true;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean isActive) {
        active = isActive;
    }
}
