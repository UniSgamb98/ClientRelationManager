package orodent.clientrelationmanager.view.searchclient;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import orodent.clientrelationmanager.controller.ClientFormatter;
import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.controller.main.MainController;
import orodent.clientrelationmanager.controller.main.buttons.ClientInfoController;
import orodent.clientrelationmanager.model.Annotation;
import orodent.clientrelationmanager.model.App;
import orodent.clientrelationmanager.model.Client;
import orodent.clientrelationmanager.model.enums.ClientField;
import orodent.clientrelationmanager.view.clientinfo.ClientInfoView;
import orodent.clientrelationmanager.view.mainview.AnnotationEditorStage;

import java.time.LocalDate;
import java.util.Objects;

public class ClientDetailView extends BorderPane {
    DBManagerInterface dbManagerInterface;
    Client client;
    ClientInfoView clientInfoView;
    ClientInfoController clientInfoController;
    Button makeCallButton;
    ClientAnnotationView clientAnnotationView;


    public ClientDetailView(Client client, Runnable onBack) {
        dbManagerInterface = new MainController().getApp().getDbManager();
        this.client = client;


        // Header: titolo centrato
        Label titleLabel = new Label("DETTAGLI CLIENTE");
        titleLabel.getStyleClass().add("detail-title");
        HBox header = new HBox(titleLabel);
        header.getStyleClass().add("header");

        // Center: ClientAnnotationView con le informazioni sulle chiamate
        clientAnnotationView = new ClientAnnotationView(client, dbManagerInterface);
        clientAnnotationView.setOnUpdate(this::update);

        // Left: ClientInfoView con le informazioni del cliente
        clientInfoView = new ClientInfoView(client);
        clientInfoView.getStyleClass().add("client-info-view");
        clientInfoController = new ClientInfoController(client, clientInfoView);
        clientInfoView.setOnKeyPressed(clientInfoController);   //Ctrl + H to show developer info

        // Footer: pulsanti "Indietro" e "Salva modifiche" allineati a destra. Make call allineato a sinistra
        Button backButton = new Button("INDIETRO");
        backButton.setOnAction(e -> onBack.run());
        Button saveButton = new Button("SALVA MODIFICHE");
        saveButton.setOnAction(e -> onSave());
        makeCallButton = new Button("Registra Chiamata");
        //makeCallButton.getStyleClass().add("make-call-button");
        makeCallButton.setOnAction(event -> showNewAnnotationStage());
        BorderPane bottom = new BorderPane();
        HBox footer = new HBox(10, backButton, saveButton);
        bottom.getStyleClass().add("footer");
        bottom.setRight(footer);
        bottom.setLeft(makeCallButton);

        // Configurazione del layout BorderPane
        this.setTop(header);
        this.setLeft(clientInfoView);
        this.setBottom(bottom);
        this.setRight(clientAnnotationView);
        this.getStyleClass().add("client-detail-view");

        //Easter egg
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/Kilroy.png")).toExternalForm());
        BackgroundSize backgroundSize = new BackgroundSize(
                100, 100, true, true, true, false
        );
        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize
        );
        StackPane center = new StackPane();
        center.setBackground(new Background(backgroundImage));
        this.setCenter(center);
    }

    private void onSave(){
        client = clientInfoController.getClient();
        client.set(ClientField.PVU, clientAnnotationView.getPvuText());
        ClientFormatter formatter = new ClientFormatter(client);
        if (formatter.isCorrectlyFormatted(false))   dbManagerInterface.saveClientChanges(client);
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
