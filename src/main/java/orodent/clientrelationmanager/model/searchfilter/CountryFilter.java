package orodent.clientrelationmanager.model.searchfilter;

import orodent.clientrelationmanager.model.enums.Business;
import orodent.clientrelationmanager.model.enums.Country;

import java.util.ArrayList;
import java.util.List;

public class CountryFilter extends AbstractFilter{

    public CountryFilter() {
        super();
    }

    @Override
    public List<String> getFilterItems() {
        List<String> list = new ArrayList<>();
        for (Country country : Country.values()) {
            list.add(country.toString());
        }
        return list;
    }
}
