package orodent.clientrelationmanager.model;

import orodent.clientrelationmanager.model.enums.Operator;

import java.time.LocalDate;
import java.util.UUID;

public class Annotation {
    private final UUID uuid;
    private LocalDate callDate;
    private Operator madeBy;
    private String content;
    private LocalDate nextCallDate;
    private boolean information;
    private boolean catalog;
    private boolean sample;


    public Annotation(UUID uuid ,LocalDate callDate, Operator madeBy, String content, LocalDate nextCallDate) {
        this.uuid = uuid;
        this.callDate = callDate;
        this.madeBy = madeBy;
        this.content = content;
        this.nextCallDate = nextCallDate;
    }

    public Annotation(LocalDate callDate, Operator madeBy, String content, LocalDate nextCallDate) {
        this.uuid = UUID.randomUUID();
        this.callDate = callDate;
        this.madeBy = madeBy;
        this.content = content;
        this.nextCallDate = nextCallDate;
    }

    public LocalDate getCallDate() {
        return callDate;
    }
    public void setCallDate(LocalDate callDate) {
        this.callDate = callDate;
    }
    public Operator getMadeBy() {
        return madeBy;
    }
    public void setMadeBy(Operator madeBy) {
        this.madeBy = madeBy;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public LocalDate getNextCallDate() {
        return nextCallDate;
    }
    public void setNextCallDate(LocalDate nextCallDate) {
        this.nextCallDate = nextCallDate;
    }
    public UUID getUuid() {
        return uuid;
    }
    public boolean getInformation() {
        return information;
    }
    public void setInformation(boolean information) {
        this.information = information;
    }
    public boolean getCatalog() {
        return catalog;
    }
    public void setCatalog(boolean catalog) {
        this.catalog = catalog;
    }
    public boolean getSample() {
        return sample;
    }
    public void setSample(boolean sample) {
        this.sample = sample;
    }


    @Override
    public String toString() {
        return "Annotation{" +
                "callDate=" + callDate +
                ", madeBy=" + madeBy +
                ", content='" + content + '\'' +
                ", nextCallDate=" + nextCallDate +
                '}';
    }
}
