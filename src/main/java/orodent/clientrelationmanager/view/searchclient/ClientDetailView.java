package orodent.clientrelationmanager.view.searchclient;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.controller.main.buttons.ClientInfoController;
import orodent.clientrelationmanager.model.Annotation;
import orodent.clientrelationmanager.model.Client;
import orodent.clientrelationmanager.model.enums.ClientField;
import orodent.clientrelationmanager.model.enums.Operator;
import orodent.clientrelationmanager.view.clientinfo.ClientInfoView;

import java.time.LocalDate;

public class ClientDetailView extends BorderPane {
    DBManagerInterface dbManagerInterface;
    Client client;
    ClientInfoView clientInfoView;
    ClientInfoController clientInfoController;
    Button makeCallButton;
    ClientAnnotationView clientAnnotationView;


    public ClientDetailView(Client client, DBManagerInterface dbManagerInterface, Runnable onBack) {
        this.dbManagerInterface = dbManagerInterface;
        this.client = client;


        // Header: titolo centrato
        Label titleLabel = new Label("DETTAGLI CLIENTE");
        titleLabel.getStyleClass().add("detail-title");
        HBox header = new HBox(titleLabel);
        header.getStyleClass().add("header");

        // Right: ClientAnnotationView con le informazioni sulle chiamate
        clientAnnotationView = new ClientAnnotationView(client, dbManagerInterface);

        // Center: ClientInfoView con le informazioni del cliente
        clientInfoView = new ClientInfoView(client);
        clientInfoView.getStyleClass().add("client-info-view");
        makeCallButton = new Button("Registra Chiamata");
        makeCallButton.getStyleClass().add("make-call-button");
        makeCallButton.setOnAction(event -> showNewAnnotationStage());
        clientInfoController = new ClientInfoController(client, clientInfoView);
        clientInfoView.setOnKeyPressed(clientInfoController);   //Ctrl + H to show developer info
        VBox vBox = new VBox(clientInfoView, makeCallButton);

        // Footer: pulsanti "Indietro" e "Salva modifiche" allineati a destra
        Button backButton = new Button("INDIETRO");
        backButton.setOnAction(e -> onBack.run());
        Button saveButton = new Button("SALVA MODIFICHE");
        saveButton.setOnAction(e -> onSave());
        HBox footer = new HBox(10, backButton, saveButton);
        footer.getStyleClass().add("footer");

        // Configurazione del layout BorderPane
        this.setTop(header);
        this.setCenter(vBox);
        this.setBottom(footer);
        this.setRight(clientAnnotationView);
        this.getStyleClass().add("client-detail-view");
    }

    private void onSave(){
        clientInfoController.getClientChangesFromView();
        client = clientInfoController.getClient();
        client.set(ClientField.PVU, clientAnnotationView.getPvuText());
        dbManagerInterface.saveClientChanges(client);
    }

    private void showNewAnnotationStage() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Registra Nuova Chiamata");

        // Campi input per la nuova annotazione
        DatePicker callDatePicker = new DatePicker(LocalDate.now());
        ComboBox<Operator> operatorComboBox = new ComboBox<>();
        operatorComboBox.getItems().addAll(Operator.values());
        operatorComboBox.setPromptText("Seleziona operatore");  //TODO da mettere in default il workingOperator di App

        TextArea contentArea = new TextArea();
        contentArea.setPromptText("Inserisci il contenuto della chiamata...");

        DatePicker nextCallDatePicker = new DatePicker();
        nextCallDatePicker.setPromptText("(Opzionale)");

        // Pulsanti di conferma e annullamento
        Button saveButton = new Button("Salva");
        Button cancelButton = new Button("Annulla");

        saveButton.setOnAction(event -> {
            if (operatorComboBox.getValue() == null || contentArea.getText().trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText(null);
                alert.setContentText("Devi selezionare un operatore e scrivere un contenuto.");
                alert.show();
                return;
            }

            // Creazione nuova annotazione
            Annotation newAnnotation = new Annotation(
                    callDatePicker.getValue(),
                    operatorComboBox.getValue(),
                    contentArea.getText(),
                    nextCallDatePicker.getValue()
            );

            // Salvataggio dell'annotazione.
            dbManagerInterface.saveAnnotation(newAnnotation, client.getUuid().toString());
            dbManagerInterface.saveClientAfterAnnotationChange(newAnnotation, client.getUuid().toString());
            client = dbManagerInterface.getClient(client.getUuid());
            // ⚠️ Chiamata al metodo che aggiorna la ClientInfoView
            updateClientInfoView();
            stage.close();
        });

        cancelButton.setOnAction(event -> stage.close());

        // Layout
        VBox layout = new VBox(10,
                new Label("Data Chiamata:"), callDatePicker,
                new Label("Operatore:"), operatorComboBox,
                new Label("Contenuto:"), contentArea,
                new Label("Prossima Chiamata:"), nextCallDatePicker,
                new HBox(10, saveButton, cancelButton)
        );

        layout.setPadding(new Insets(15));

        // Creazione della scena e apertura della finestra
        Scene scene = new Scene(layout, 400, 350);
        stage.setScene(scene);
        stage.showAndWait();

    }

    private void updateClientInfoView() {
        this.setCenter(null);  // Rimuove la vecchia ClientInfoView
        this.setRight(null);
        clientInfoView = new ClientInfoView(client); // Crea una nuova ClientInfoView aggiornata
        clientAnnotationView = new ClientAnnotationView(client, dbManagerInterface);
        this.setCenter(new VBox(clientInfoView, makeCallButton)); // Aggiunge la nuova ClientInfoView
        this.setRight(clientAnnotationView);
    }
}
