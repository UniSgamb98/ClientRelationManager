package orodent.clientrelationmanager.model.searchfilter;

import orodent.clientrelationmanager.model.enums.Country;

import java.util.List;

public class CountryFilter extends AbstractFilter<Country>{

    public CountryFilter(List<Country> enumList) {
        super(enumList);
    }
}
