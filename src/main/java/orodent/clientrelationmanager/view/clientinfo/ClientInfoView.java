package orodent.clientrelationmanager.view.clientinfo;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import orodent.clientrelationmanager.model.Client;
import orodent.clientrelationmanager.model.enums.Operator;
import orodent.clientrelationmanager.model.enums.TipoCliente;

import java.time.LocalDate;
import java.util.UUID;

public class ClientInfoView extends VBox {
    private final TextField ragioneSociale;
    private final TextField personaRiferimento;
    private final TextField emailRiferimento;
    private final TextField cellulareRiferimento;
    private final TextField telefonoAziendale;
    private final TextField emailAziendale;
    private final TextField paese;
    private final TextField citta;
    private final TextField nomeTitolare;
    private final TextField cellulareTitolare;
    private final TextField emailTitolare;
    private final TextField volteContattati;
    private final DatePicker ultimaChiamata;
    private final DatePicker prossimaChiamata;
    private final TextField sitoWeb;
    private final ChoiceBox<TipoCliente> tipoCliente;

    private final DatePicker dataAcquisizione;
    private final ChoiceBox<Operator> operatoreAssegnato;
    private final CheckBox information;
    private final CheckBox catalog;
    private final CheckBox sample;
    private final TextField uuid;
    InformationGroup developerInfos;

    public ClientInfoView() {
        ragioneSociale = new TextField();
        personaRiferimento = new TextField();
        emailRiferimento = new TextField();
        cellulareRiferimento = new TextField();
        telefonoAziendale = new TextField();
        emailAziendale = new TextField();
        paese = new TextField();
        citta = new TextField();
        nomeTitolare = new TextField();
        cellulareTitolare = new TextField();
        emailTitolare = new TextField();
        volteContattati = new TextField();
        ultimaChiamata = new DatePicker();
        prossimaChiamata = new DatePicker();
        sitoWeb = new TextField();
        tipoCliente = new ChoiceBox<>();
        tipoCliente.getItems().addAll(TipoCliente.values());
        operatoreAssegnato = new ChoiceBox<>();
        operatoreAssegnato.getItems().addAll(Operator.values());
        information = new CheckBox();
        catalog = new CheckBox();
        sample = new CheckBox();
        uuid = new TextField();
        dataAcquisizione = new DatePicker();

        ragioneSociale.setPromptText("Ragione Sociale");
        personaRiferimento.setPromptText("Persona Riferimento");
        emailRiferimento.setPromptText("Email Riferimento");
        cellulareRiferimento.setPromptText("Cellulare Riferimento");
        telefonoAziendale.setPromptText("Telefono Aziendale");
        emailAziendale.setPromptText("Email Aziendale");
        paese.setPromptText("Paese");
        citta.setPromptText("Citta");
        nomeTitolare.setPromptText("Nome Titolare");
        cellulareTitolare.setPromptText("Cellulare Titolare");
        emailTitolare.setPromptText("Email Titolare");
        volteContattati.setMaxWidth(35);
        ultimaChiamata.setPromptText("Ultima Chiamata");
        prossimaChiamata.setPromptText("Prossima Chiamata");
        sitoWeb.setPromptText("Sito Web");
        information.setText("Informazioni");
        catalog.setText("Catalogo");
        sample.setText("Campione");
        uuid.setPromptText("uuid");
        dataAcquisizione.setPromptText("Data Acquisizione");

        ultimaChiamata.setMinWidth(20);
        prossimaChiamata.setMinWidth(20);


        InformationGroup aziendaInfos = new InformationGroup("Azienda");
        aziendaInfos.add(ragioneSociale, 0, 0, 2, 1);
        aziendaInfos.add(paese, 0, 1);
        aziendaInfos.add(citta, 1, 1);
        aziendaInfos.add(telefonoAziendale, 0, 2);
        aziendaInfos.add(emailAziendale, 1, 2);
        aziendaInfos.add(sitoWeb, 0, 3, 2, 1);
        this.getChildren().add(aziendaInfos);

        InformationGroup personaInfos = new InformationGroup("Referente");

        personaInfos.add(personaRiferimento, 0, 0, 2, 1);
        personaInfos.add(emailRiferimento, 0,1);
        personaInfos.add(cellulareRiferimento, 1, 1);
        this.getChildren().add(personaInfos);

        InformationGroup titolareInfos = new InformationGroup("Titolare");
        titolareInfos.add(nomeTitolare, 0, 0, 2, 1);
        titolareInfos.add(cellulareTitolare, 0, 1);
        titolareInfos.add(emailTitolare, 1, 1);
        this.getChildren().add(titolareInfos);

        InformationGroup contattatiInfos = new InformationGroup("");
        contattatiInfos.add(ultimaChiamata, 0, 0);
        contattatiInfos.add(new Label(" Ultima Chiamata"), 1, 0);
        contattatiInfos.add(prossimaChiamata, 0, 1);
        contattatiInfos.add(new Label(" Prossima Chiamata"), 1, 1);
        HBox littleBox = new HBox(tipoCliente, new Label("   Contattati "), volteContattati, new Label(" volte."));
        littleBox.setAlignment(Pos.CENTER_LEFT);
        contattatiInfos.add(littleBox, 0,2, 2,1);
        this.getChildren().add(contattatiInfos);

        developerInfos = new InformationGroup("Developer");
        developerInfos.add(information, 1, 0);
        developerInfos.add(catalog, 1, 1);
        developerInfos.add(sample, 1, 2);
        HBox tinyBox = new HBox(operatoreAssegnato, new Label("Operatore"));
        tinyBox.setAlignment(Pos.CENTER_LEFT);
        developerInfos.add(tinyBox, 0, 2);
        developerInfos.add(uuid, 0, 3, 2, 1);
        developerInfos.add(new Label("Data Acquisizione"), 0, 0);
        developerInfos.add(dataAcquisizione, 0, 1);
        this.getChildren().add(developerInfos);
    }

    public void toggleDeveloperVisibility() {
        developerInfos.setVisible(!developerInfos.isVisible());
    }

    public void displayClientInfo(Client client){
        ragioneSociale.setText(client.getRagioneSociale());
        personaRiferimento.setText(client.getPersonaRiferimento());
        emailRiferimento.setText(client.getEmailRiferimento());
        cellulareRiferimento.setText(client.getCellulareTitolare());
        telefonoAziendale.setText(client.getTelefonoAziendale());
        emailAziendale.setText(client.getEmailAziendale());
        paese.setText(client.getPaese());
        citta.setText(client.getCitta());
        nomeTitolare.setText(client.getNomeTitolare());
        cellulareTitolare.setText(client.getCellulareTitolare());
        emailTitolare.setText(client.getEmailTitolare());
        volteContattati.setText(client.getVolteContattati()+"");
        ultimaChiamata.setValue(client.getUltimaChiamata());
        prossimaChiamata.setValue(client.getProssimaChiamata());
        sitoWeb.setText(client.getSitoWeb());
        tipoCliente.setValue(client.getTipoCliente());
        dataAcquisizione.setValue(client.getDataAcquisizione());
        operatoreAssegnato.setValue(client.getOperatoreAssegnato());
        uuid.setText(client.getUuid().toString());
        information.setSelected(client.getInformation());
        catalog.setSelected(client.getCatalog());
        sample.setSelected(client.getSample());
    }
    /*
    public void setRagioneSociale(String text) {
        ragioneSociale.setText(text);
    }
    public void setPersonaRiferimento(String text) {
        personaRiferimento.setText(text);
    }
    public void setEmailRiferimento(String text) {
        emailRiferimento.setText(text);
    }
    public void setCellulareRiferimento(String text) {
        cellulareRiferimento.setText(text);
    }
    public void setTelefonoAziendale(String text) {
        telefonoAziendale.setText(text);
    }
    public void setEmailAziendale(String text) {
        emailAziendale.setText(text);
    }
    public void setPaese(String text) {
        paese.setText(text);
    }
    public void setCitta(String text) {
        citta.setText(text);
    }
    public void setNomeTitolare(String text) {
        nomeTitolare.setText(text);
    }
    public void setCellulareTitolare(String text) {
        cellulareTitolare.setText(text);
    }
    public void setEmailTitolare(String text) {
        emailTitolare.setText(text);
    }
    public void setVolteContattati(int number) {
        volteContattati.setText(number+"");
    }
    public void setUltimaChiamata(LocalDate date) {
        ultimaChiamata.setValue(date);
    }
    public void setProssimaChiamata(LocalDate date) {
        prossimaChiamata.setValue(date);
    }
    public void setSitoWeb(String text) {
        sitoWeb.setText(text);
    }
    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente.setValue(tipoCliente);
    }
    public void setDataAcquisizione(LocalDate data) {
        dataAcquisizione.setValue(data);
    }
    public void setOperatore(Operator operatore) {
        operatoreAssegnato.setValue(operatore);
    }*/
    public UUID getUuid() {
        return UUID.fromString(uuid.getText());
    }
    public String getRagioneSociale() {
        return ragioneSociale.getText();
    }
    public String getPersonaRiferimento() {
        return personaRiferimento.getText();
    }
    public String getEmailRiferimento() {
        return emailRiferimento.getText();
    }
    public String getCellulareRiferimento() {
        return cellulareRiferimento.getText();
    }
    public String getTelefonoAziendale() {
        return telefonoAziendale.getText();
    }
    public String getEmailAziendale() {
        return emailAziendale.getText();
    }
    public String getPaese() {
        return paese.getText();
    }
    public String getCitta() {
        return citta.getText();
    }
    public String getNomeTitolare() {
        return nomeTitolare.getText();
    }
    public String getCellulareTitolare() {
        return cellulareTitolare.getText();
    }
    public String getEmailTitolare() {
        return emailTitolare.getText();
    }
    public int getVolteContattati() {
        return Integer.parseInt(volteContattati.getText());
    }
    public LocalDate getUltimaChiamata() {
        return ultimaChiamata.getValue();
    }
    public LocalDate getProssimaChiamata() {
        return prossimaChiamata.getValue();
    }
    public TipoCliente getTipoCliente() {
        return tipoCliente.getValue();
    }
    public LocalDate getDataAcquisizione() {
        return dataAcquisizione.getValue();
    }
    public Operator getOperatore() {
        return operatoreAssegnato.getValue();
    }
    public String getSitoWeb(){
        return sitoWeb.getText();
    }
    public boolean hasInformation(){
        return information.isSelected();
    }
    public boolean hasCatalog(){
        return catalog.isSelected();
    }
    public boolean hasSample(){
        return sample.isSelected();
    }
}
