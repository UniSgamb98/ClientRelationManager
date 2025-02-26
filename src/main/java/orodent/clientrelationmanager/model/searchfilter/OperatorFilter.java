package orodent.clientrelationmanager.model.searchfilter;

import orodent.clientrelationmanager.model.enums.Operator;

import java.util.ArrayList;
import java.util.List;

public class OperatorFilter extends AbstractFilter<Operator>{

    public OperatorFilter(List<Operator> enumList) {
        super(enumList);
    }

}
