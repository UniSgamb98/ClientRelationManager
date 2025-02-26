package orodent.clientrelationmanager.model.searchfilter;

import orodent.clientrelationmanager.model.enums.Business;

import java.util.List;

public class BusinessFilter extends AbstractFilter<Business>{

    public BusinessFilter(List<Business> enumList) {
        super(enumList);
    }
}
