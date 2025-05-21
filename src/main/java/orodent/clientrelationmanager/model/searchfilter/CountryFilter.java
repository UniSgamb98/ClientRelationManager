package orodent.clientrelationmanager.model.searchfilter;

import orodent.clientrelationmanager.model.enums.Business;

import java.util.List;

public class CountryFilter extends AbstractFilter<Business>{

    public CountryFilter(List<Business> enumList) {
        super(enumList);
    }
}
