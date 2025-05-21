package orodent.clientrelationmanager.model.searchfilter;

import orodent.clientrelationmanager.model.enums.Business;

import java.util.List;

public class OperatorFilter extends AbstractFilter<Business>{

    public OperatorFilter(List<Business> enumList) {
        super(enumList);
    }

}
