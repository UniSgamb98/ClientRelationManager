package orodent.clientrelationmanager.view.clientinfo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import orodent.clientrelationmanager.controller.popup.AutoCompletePopup;
import orodent.clientrelationmanager.controller.main.MainController;
import orodent.clientrelationmanager.model.Client;
import orodent.clientrelationmanager.model.enums.ClientField;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
    private final ChoiceBox<String> tipoCliente;

    private final DatePicker dataAcquisizione;
    private final ChoiceBox<String> operatoreAssegnato;
    private final CheckBox information;
    private final CheckBox catalog;
    private final CheckBox sample;
    private final TextField uuid;
    private final InformationGroup developerInfos;

    public ClientInfoView(Client client) {
        Map<String, List<String>> config = new MainController().getApp().getConfigs();
        ragioneSociale = new TextField((String) client.get(ClientField.RAGIONE_SOCIALE));
        personaRiferimento = new TextField((String) client.get(ClientField.PERSONA_RIFERIMENTO));
        emailRiferimento = new TextField((String) client.get(ClientField.EMAIL_RIFERIMENTO));
        cellulareRiferimento = new TextField((String) client.get(ClientField.CELLULARE_RIFERIMENTO));
        telefonoAziendale = new TextField((String) client.get(ClientField.TELEFONO_AZIENDALE));
        emailAziendale = new TextField((String) client.get(ClientField.EMAIL_AZIENDALE));
        paese = new TextField((String) client.get(ClientField.PAESE));
        citta = new TextField((String) client.get(ClientField.CITTA));
        nomeTitolare = new TextField((String) client.get(ClientField.NOME_TITOLARE));
        cellulareTitolare = new TextField((String) client.get(ClientField.CELLULARE_TITOLARE));
        emailTitolare = new TextField((String) client.get(ClientField.EMAIL_TITOLARE));
        volteContattati = new TextField(client.getField(ClientField.VOLTE_CONTATTATI, Integer.class)+"");
        ultimaChiamata = new DatePicker(client.getField(ClientField.ULTIMA_CHIAMATA, java.time.LocalDate.class));
        prossimaChiamata = new DatePicker(client.getField(ClientField.PROSSIMA_CHIAMATA, java.time.LocalDate.class));
        sitoWeb = new TextField((String) client.get(ClientField.SITO_WEB));
        tipoCliente = new ChoiceBox<>();
        tipoCliente.getItems().addAll(config.get("BUSINESS"));
        tipoCliente.setValue(client.getField(ClientField.BUSINESS, String.class));
        operatoreAssegnato = new ChoiceBox<>();
        operatoreAssegnato.getItems().addAll(config.get("OPERATORE_ASSEGNATO"));
        operatoreAssegnato.setValue(client.getField(ClientField.OPERATORE_ASSEGNATO, String.class));
        information = new CheckBox();
        information.setSelected(client.getField(ClientField.INFORMATION, Boolean.class));
        catalog = new CheckBox();
        catalog.setSelected(client.getField(ClientField.CATALOG, Boolean.class));
        sample = new CheckBox();
        sample.setSelected(client.getField(ClientField.SAMPLE, Boolean.class));
        uuid = new TextField(client.getUuid().toString());
        dataAcquisizione = new DatePicker(client.getField(ClientField.DATA_ACQUISIZIONE, java.time.LocalDate.class));

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
        personaInfos.add(cellulareRiferimento, 0, 1);
        personaInfos.add(emailRiferimento, 1,1);
        this.getChildren().add(personaInfos);

        InformationGroup titolareInfos = new InformationGroup("Titolare");
        titolareInfos.add(nomeTitolare, 0, 0, 2, 1);
        titolareInfos.add(cellulareTitolare, 0, 1);
        titolareInfos.add(emailTitolare, 1, 1);
        this.getChildren().add(titolareInfos);

        InformationGroup contattatiInfos = new InformationGroup("Interazioni");
        contattatiInfos.add(ultimaChiamata, 0, 0);
        contattatiInfos.add(new Label(" Ultima Chiamata"), 1, 0);
        contattatiInfos.add(prossimaChiamata, 0, 1);
        contattatiInfos.add(new Label(" Prossima Chiamata"), 1, 1);
        contattatiInfos.add(information, 2, 0);
        contattatiInfos.add(catalog, 2, 1);
        contattatiInfos.add(sample, 2, 2);
        HBox littleBox = new HBox(tipoCliente, new Label("   Contattati "), volteContattati, new Label(" volte."));
        littleBox.setAlignment(Pos.CENTER_LEFT);
        contattatiInfos.add(littleBox, 0,2, 2,1);
        this.getChildren().add(contattatiInfos);

        developerInfos = new InformationGroup("Developer");
        developerInfos.add(new Label("Data Acquisizione"), 1, 0);
        developerInfos.add(dataAcquisizione, 0, 0);
        developerInfos.add( new Label(" Operatore Assegnato"), 1, 1);
        developerInfos.add(operatoreAssegnato, 0,1);
        developerInfos.add(uuid, 0, 2, 2, 1);

        //Popups
        ObservableList<String> countryNames = FXCollections.observableArrayList();
        countryNames.addAll(config.get("PAESE"));
        new AutoCompletePopup(paese, countryNames);


        this.getStyleClass().add("client-info-view");
        this.setMinWidth(415);
        aziendaInfos.getStyleClass().add("information-group");
        personaInfos.getStyleClass().add("information-group");
        titolareInfos.getStyleClass().add("information-group");
        contattatiInfos.getStyleClass().add("information-group");
        developerInfos.getStyleClass().add("information-group");
    }

    public void toggleDeveloperVisibility(boolean hasDeveloperPrivilege) {
        if (hasDeveloperPrivilege) {
            this.getChildren().add(developerInfos);
        } else{
            this.getChildren().remove(developerInfos);
        }
    }

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
        int ret;
        try {
              ret = Integer.parseInt(volteContattati.getText());
        } catch (NumberFormatException e) {
            ret = 0;
        }
        return ret;
    }
    public LocalDate getUltimaChiamata() {
        return ultimaChiamata.getValue();
    }
    public LocalDate getProssimaChiamata() {
        return prossimaChiamata.getValue();
    }
    public String getTipoCliente() {
        return tipoCliente.getValue();
    }
    public LocalDate getDataAcquisizione() {
        return dataAcquisizione.getValue();
    }
    public String getOperatore() {
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
