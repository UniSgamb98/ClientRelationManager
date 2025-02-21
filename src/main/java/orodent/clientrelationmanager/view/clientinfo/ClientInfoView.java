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
    private final InformationGroup developerInfos;

    public ClientInfoView(Client client) {
        ragioneSociale = new TextField(client.getRagioneSociale());
        personaRiferimento = new TextField(client.getPersonaRiferimento());
        emailRiferimento = new TextField(client.getEmailRiferimento());
        cellulareRiferimento = new TextField(client.getCellulareRiferimento());
        telefonoAziendale = new TextField(client.getTelefonoAziendale());
        emailAziendale = new TextField(client.getEmailAziendale());
        paese = new TextField(client.getPaese());
        citta = new TextField(client.getCitta());
        nomeTitolare = new TextField(client.getNomeTitolare());
        cellulareTitolare = new TextField(client.getCellulareTitolare());
        emailTitolare = new TextField(client.getEmailTitolare());
        volteContattati = new TextField(client.getVolteContattati()+"");
        ultimaChiamata = new DatePicker(client.getUltimaChiamata());
        prossimaChiamata = new DatePicker(client.getProssimaChiamata());
        sitoWeb = new TextField(client.getSitoWeb());
        tipoCliente = new ChoiceBox<>();
        tipoCliente.getItems().addAll(TipoCliente.values());
        tipoCliente.setValue(client.getTipoCliente());
        operatoreAssegnato = new ChoiceBox<>();
        operatoreAssegnato.getItems().addAll(Operator.values());
        operatoreAssegnato.setValue(client.getOperatoreAssegnato());
        information = new CheckBox();
        information.setSelected(client.getInformation());
        catalog = new CheckBox();
        catalog.setSelected(client.getCatalog());
        sample = new CheckBox();
        sample.setSelected(client.getSample());
        uuid = new TextField(client.getUuid()+"");
        dataAcquisizione = new DatePicker(client.getDataAcquisizione());

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
