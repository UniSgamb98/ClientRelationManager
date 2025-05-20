package orodent.clientrelationmanager.model;

import orodent.clientrelationmanager.model.enums.ClientField;

public class EmptyClient extends Client{

    public EmptyClient (){
        super();
        set(ClientField.RAGIONE_SOCIALE, "La ricerca non ha prodotto risultati.");
    }
}
