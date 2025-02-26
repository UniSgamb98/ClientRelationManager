package orodent.clientrelationmanager.model.searchfilter;

import orodent.clientrelationmanager.model.Client;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFilter<E extends Enum<E>> {
    boolean active;
    List<Client> resultList;
    protected List<E> enumList;


    public AbstractFilter(List<E> enumList) {
        this.enumList = enumList;
        active = true;
        resultList = new ArrayList<>();
    }

    public List<E> getEnumList() {
        return enumList;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean isActive) {
        active = isActive;
    }

    public void setResultList(List<Client> resultList){
        this.resultList = new ArrayList<>(resultList);
    }

    public List<Client> getResultList(){
        return resultList;
    }
}
