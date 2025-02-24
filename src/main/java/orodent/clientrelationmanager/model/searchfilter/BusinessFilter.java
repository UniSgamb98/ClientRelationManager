package orodent.clientrelationmanager.model.searchfilter;

import orodent.clientrelationmanager.model.enums.Business;

import java.util.ArrayList;
import java.util.List;

public class BusinessFilter extends AbstractFilter{

    public BusinessFilter() {
        super();
    }

    @Override
    public List<String> getFilterItems() {
        List<String> list = new ArrayList<>();
        for (Business business : Business.values()) {
            list.add(business.toString());
        }
        return list;
    }
}
