package orodent.clientrelationmanager.view.searchclient;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.controller.main.buttons.ClientInfoController;
import orodent.clientrelationmanager.model.Annotation;
import orodent.clientrelationmanager.model.App;
import orodent.clientrelationmanager.model.Client;
import orodent.clientrelationmanager.model.enums.ClientField;
import orodent.clientrelationmanager.view.clientinfo.ClientInfoView;
import orodent.clientrelationmanager.view.mainview.AnnotationEditorStage;

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
        clientAnnotationView.setOnUpdate(this::update);

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
        client = clientInfoController.getClient();
        client.set(ClientField.PVU, clientAnnotationView.getPvuText());
        dbManagerInterface.saveClientChanges(client);
    }

    private void showNewAnnotationStage() {
        // Supponiamo di avere un oggetto 'annotation' da modificare.
        AnnotationEditorStage editor = new AnnotationEditorStage(new Annotation(LocalDate.now(), null, null, null), "Registra Nuova Chiamata");
        editor.setDefaultOperator(App.getWorkingOperator());
        editor.showAndWait();

        if (editor.isSaved()) {
            Annotation updatedAnnotation = editor.getAnnotation();
            // Procedi con l'aggiornamento sul database o altre operazioni.
            dbManagerInterface.saveAnnotation(updatedAnnotation, client.getUuid().toString());
            dbManagerInterface.saveClientAfterAnnotationChange(updatedAnnotation, client.getUuid().toString());
            client = dbManagerInterface.getClientWhere(client.getUuid().toString()).getFirst();
            update();
        }
    }

    private void update(){
        client = dbManagerInterface.getClientWhere(client.getUuid().toString()).getFirst();
        updateClientInfoView();
        updateClientAnnotationView();
    }

    private void updateClientInfoView() {
        clientInfoView = new ClientInfoView(client);
        this.setCenter(new VBox(clientInfoView, makeCallButton));
    }

    private void updateClientAnnotationView(){
        clientAnnotationView = new ClientAnnotationView(client, dbManagerInterface);
        clientAnnotationView.setOnUpdate(this::update);
        this.setRight(clientAnnotationView);
    }
}
