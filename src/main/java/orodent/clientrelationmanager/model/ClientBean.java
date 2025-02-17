package orodent.clientrelationmanager.model;

import orodent.clientrelationmanager.model.enums.InteressamentoStatus;
import orodent.clientrelationmanager.model.enums.Operator;
import orodent.clientrelationmanager.model.enums.TipoCliente;

import java.time.LocalDate;
import java.util.UUID;

public class ClientBean {
    private String ragioneSociale;
    private String personaRiferimento;
    private String emailReferente;
    private String telefono;
    private String telefono2;
    private String cellulare;
    private String paese;
    private String citta;
    private InteressamentoStatus interessamento;
    private TipoCliente tipoCliente;
    private String titolare;
    private String emailGenereica;
    private int volteContattati;
    private LocalDate ultimaChiamata;
    private LocalDate prossimaChiamata;
    private String sitoWeb;
    private UUID id;
    private UUID noteId;
    private Operator operator;
    private double coinvolgimento;
    private int checkpoint;
    private LocalDate acquisizione;

    public ClientBean(){
        noteId = UUID.randomUUID();
    }

    public String getTelefono() {
        return telefono;
    }
    public String getTelefono2() {
        return telefono2;
    }
    public String getCellulare() {
        return cellulare;
    }
    public int getCheckpoint() {
        return checkpoint;
    }
    public LocalDate getAcquisizione() {
        return acquisizione;
    }
    public Operator getOperator() {
        return operator;
    }
    public String getPersonaRiferimento() {
        return personaRiferimento;
    }
    public String getCitta() {
        return citta;
    }
    public String getPaese() {
        return paese;
    }
    public String getEmailReferente() {
        return emailReferente;
    }
    public LocalDate getProssimaChiamata() {
        return prossimaChiamata;
    }
    public LocalDate getUltimaChiamata() {
        return ultimaChiamata;
    }
    public int getVolteContattati() {
        return volteContattati;
    }
    public InteressamentoStatus getInteressamento() {
        return interessamento;
    }
    public String getRagioneSociale() {
        return ragioneSociale;
    }
    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }
    public String getEmailGenereica() {
        return emailGenereica;
    }
    public String getSitoWeb() {
        return sitoWeb;
    }
    public UUID getId() {
        return id;
    }
    public String getTitolare() {
        return titolare;
    }
    public UUID getNoteId() {
        return noteId;
    }
    public double getCoinvolgimento() {
        return coinvolgimento;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }
    public void setNoteId(UUID noteId) {
        this.noteId = noteId;
    }
    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }
    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }
    public void setCheckpoint(int checkpoint) {
        this.checkpoint = checkpoint;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public void setPersonaRiferimento(String personaRiferimento) {
        this.personaRiferimento = personaRiferimento;
    }
    public void setPaese(String paese) {
        this.paese = paese;
    }
    public void setEmailReferente(String emailReferente) {
        this.emailReferente = emailReferente;
    }
    public void setCitta(String citta) {
        this.citta = citta;
    }
    public void setInteressamento(InteressamentoStatus interessamento) {
        this.interessamento = interessamento;
    }
    public void setAcquisizione(LocalDate acquisizione) {
        this.acquisizione = acquisizione;
    }
    public void setProssimaChiamata(LocalDate prossimaChiamata) {
        this.prossimaChiamata = prossimaChiamata;
    }
    public void setRagioneSociale(String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }
    public void setOperator(Operator operator) {
        this.operator = operator;
    }
    public void setUltimaChiamata(LocalDate ultimaChiamata) {
        this.ultimaChiamata = ultimaChiamata;
    }
    public void incrementVolteContattati() {
        volteContattati++;
    }
    public void setEmailGenereica(String emailGenereica) {
        this.emailGenereica = emailGenereica;
    }
    public void setSitoWeb(String sitoWeb) {
        this.sitoWeb = sitoWeb;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public void setTitolare(String titolare) {
        this.titolare = titolare;
    }
    public void setVolteContattati(int volteContattati) {
        this.volteContattati = volteContattati;
    }
    public void setCoinvolgimento(double coinvolgimento) {
        this.coinvolgimento = coinvolgimento;
    }

    @Override
    public String toString(){
        return "[" + ragioneSociale + "|" + personaRiferimento + "|" + paese + "|" + citta + "|" + tipoCliente + "|" + interessamento + "|" + telefono + "|" + emailReferente + "]";
    }
}