package orodent.clientrelationmanager.model;

import orodent.clientrelationmanager.controller.main.MainController;
import orodent.clientrelationmanager.model.enums.ClientField;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Client {

    private UUID uuid;
    private final Map<ClientField, Object> fields = new EnumMap<>(ClientField.class);
    //TODO note

    public Client() {
        uuid = UUID.randomUUID();
        set(ClientField.CATALOG, false);
        set(ClientField.INFORMATION, false);
        set(ClientField.SAMPLE, false);
        set(ClientField.VOLTE_CONTATTATI,0);
        set(ClientField.BUSINESS, "Sconosciuto");
        set(ClientField.OPERATORE_ASSEGNATO, new MainController().getApp().getWorkingOperator());
        set(ClientField.RAGIONE_SOCIALE, "");
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    public void set(ClientField field, Object value) {
        if (value != null && !field.getType().isInstance(value)) {
            throw new IllegalArgumentException("Tipo errato per " + field + ": atteso " + field.getType().getSimpleName());
        }
        fields.put(field, value);
    }
    public UUID getUuid() {
        return uuid;
    }
    public Object get(ClientField field) {
        return fields.get(field);
    }

    @SuppressWarnings("unchecked")
    public <T> T getField(ClientField field, Class<T> type) {
        Object value = fields.get(field);
        return type.isInstance(value) ? (T) value : null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Client client = (Client) obj;
        return Objects.equals(uuid, client.uuid) && Objects.equals(fields, client.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fields);
    }

    @Override
    public String toString() {
        return (String) fields.get(ClientField.RAGIONE_SOCIALE);
    }
}