package orodent.clientrelationmanager.model;

import javafx.scene.control.TextField;
import orodent.clientrelationmanager.model.enums.Operator;
import orodent.clientrelationmanager.model.enums.TipoCliente;

import java.time.LocalDate;
import java.util.UUID;

public class Client {
    private String ragioneSociale;
    private String personaRiferimento;
    private String emailRiferimento;
    private String cellulareRiferimento;
    private String telefonoAziendale;
    private String emailAziendale;
    private String paese;
    private String citta;
    private String nomeTitolare;
    private String cellulareTitolare;
    private String emailTitolare;
    private String sitoWeb;
    private int volteContattati;
    private LocalDate ultimaChiamata;
    private LocalDate prossimaChiamata;
    private LocalDate dataAcquisizione;
    private TipoCliente tipoCliente;
    private Operator operatoreAssegnato;
    private boolean information;
    private boolean catalog;
    private boolean sample;
    private UUID uuid;
    //TODO note

    public Client() {
        uuid = UUID.randomUUID();
    }

    public Client(Operator operatoreAssegnato){
        uuid = UUID.randomUUID();
        this.operatoreAssegnato = operatoreAssegnato;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    public void setRagioneSociale(String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }
    public void setPersonaRiferimento(String personaRiferimento) {
        this.personaRiferimento = personaRiferimento;
    }
    public void setEmailRiferimento(String emailRiferimento) {
        this.emailRiferimento = emailRiferimento;
    }
    public void setCellulareRiferimento(String cellulareRiferimento) {
        this.cellulareRiferimento = cellulareRiferimento;
    }
    public void setTelefonoAziendale(String telefonoAziendale) {
        this.telefonoAziendale = telefonoAziendale;
    }
    public void setEmailAziendale(String emailAziendale) {
        this.emailAziendale = emailAziendale;
    }
    public void setPaese(String paese) {
        this.paese = paese;
    }
    public void setCitta(String citta) {
        this.citta = citta;
    }
    public void setNomeTitolare(String nomeTitolare) {
        this.nomeTitolare = nomeTitolare;
    }
    public void setCellulareTitolare(String cellulareTitolare) {
        this.cellulareTitolare = cellulareTitolare;
    }
    public void setEmailTitolare(String emailTitolare) {
        this.emailTitolare = emailTitolare;
    }
    public void setSitoWeb(String sitoWeb) {
        this.sitoWeb = sitoWeb;
    }
    public void setVolteContattati(int volteContattati) {
        this.volteContattati = volteContattati;
    }
    public void setUltimaChiamata(LocalDate ultimaChiamata) {
        this.ultimaChiamata = ultimaChiamata;
    }
    public void setProssimaChiamata(LocalDate prossimaChiamata) {
        this.prossimaChiamata = prossimaChiamata;
    }
    public void setDataAcquisizione(LocalDate dataAcquisizione) {
        this.dataAcquisizione = dataAcquisizione;
    }
    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }
    public void setOperatoreAssegnato(Operator operatoreAssegnato) {
        this.operatoreAssegnato = operatoreAssegnato;
    }
    public void setInformation(boolean information) {
        this.information = information;
    }
    public void setCatalog(boolean catalog) {
        this.catalog = catalog;
    }
    public void setSample(boolean sample) {
        this.sample = sample;
    }
    public UUID getUuid() {
        return uuid;
    }
    public String getRagioneSociale() {
        return ragioneSociale;
    }
    public String getPersonaRiferimento() {
        return personaRiferimento;
    }
    public String getEmailRiferimento() {
        return emailRiferimento;
    }
    public String getCellulareRiferimento() {
        return cellulareRiferimento;
    }
    public String getTelefonoAziendale() {
        return telefonoAziendale;
    }
    public String getEmailAziendale() {
        return emailAziendale;
    }
    public String getPaese() {
        return paese;
    }
    public String getCitta() {
        return citta;
    }
    public String getNomeTitolare() {
        return nomeTitolare;
    }
    public String getCellulareTitolare() {
        return cellulareTitolare;
    }
    public String getEmailTitolare() {
        return emailTitolare;
    }
    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }
    public Operator getOperatoreAssegnato() {
        return operatoreAssegnato;
    }
    public LocalDate getUltimaChiamata() {
        return ultimaChiamata;
    }
    public LocalDate getProssimaChiamata() {
        return prossimaChiamata;
    }
    public LocalDate getDataAcquisizione() {
        return dataAcquisizione;
    }
    public int getVolteContattati(){
        return volteContattati;
    }
    public String getSitoWeb() {
        return sitoWeb;
    }
    public Boolean getInformation() {
        return information;
    }
    public Boolean getCatalog() {
        return catalog;
    }
    public Boolean getSample() {
        return sample;
    }

    @Override
    public String toString(){
        return "[" + ragioneSociale + "|"  + paese + "|" + citta;
    }
}