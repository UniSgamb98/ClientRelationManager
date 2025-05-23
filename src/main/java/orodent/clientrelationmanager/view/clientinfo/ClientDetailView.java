package orodent.clientrelationmanager.view.clientinfo;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import orodent.clientrelationmanager.controller.ClientFormatter;
import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.controller.main.MainController;
import orodent.clientrelationmanager.controller.main.buttons.ClientInfoController;
import orodent.clientrelationmanager.model.Annotation;
import orodent.clientrelationmanager.model.Client;
import orodent.clientrelationmanager.model.enums.ClientField;
import orodent.clientrelationmanager.view.mainview.AnnotationEditorStage;
import orodent.clientrelationmanager.view.searchclient.ClientAnnotationView;
import orodent.clientrelationmanager.view.searchclient.SearchClientView;

import java.time.LocalDate;
import java.util.Objects;

public class ClientDetailView extends BorderPane {
    private final MainController mainController;
    private Client client;
    DBManagerInterface dbManagerInterface;
    ClientInfoView clientInfoView;
    ClientInfoController clientInfoController;
    Button makeCallButton;
    ClientAnnotationView clientAnnotationView;


    public ClientDetailView(Client client) {
        mainController = new MainController();
        dbManagerInterface = mainController.getApp().getDbManager();
        this.client = client;

        // Header: titolo centrato
        Label titleLabel = new Label("DETTAGLI CLIENTE");
        titleLabel.getStyleClass().add("detail-title");
        HBox header = new HBox(titleLabel);
        header.getStyleClass().add("header");

        // Right: ClientAnnotationView con le informazioni sulle chiamate
        clientAnnotationView = new ClientAnnotationView(client, dbManagerInterface);

        // Left: ClientInfoView con le informazioni del cliente
        clientInfoView = new ClientInfoView(client);
        clientInfoView.getStyleClass().add("client-info-view");
        clientInfoController = new ClientInfoController(client, clientInfoView);
        clientInfoView.setOnKeyPressed(clientInfoController);   //Ctrl + H to show developer info

        // Footer: pulsanti "Indietro" e "Salva modifiche" allineati a destra. Make call allineato a sinistra
        Button backButton = new Button("INDIETRO");
        backButton.setOnAction(e -> onBack());
        Button saveButton = new Button("SALVA MODIFICHE");
        saveButton.setOnAction(e -> onSave());
        makeCallButton = new Button("Registra Chiamata");
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

    /**
     * Salva su database il client che si vede a schermo, dopo aver fatto le verifiche di formattazione
     */
    private void onSave(){
        // Raccolgo le informazioni lasciate dall'utente
        client = clientInfoController.getClient();
        client.set(ClientField.PVU, clientAnnotationView.getPvuText());

        // Se è tutto formattato giusto salvo sul database
        ClientFormatter formatter = new ClientFormatter(client);
        if (formatter.isCorrectlyFormatted(false))   dbManagerInterface.saveClientChanges(client);
    }

    /**
     * Mostra una nuova istanza di SearchClientView ma mostra la lista di clienti visualizzata prima che venisse aperto il dettaglio del cliente
     */
    private void onBack(){
        SearchClientView searchClientView = new SearchClientView();
        searchClientView.setResultList(mainController.getListFromFilteredSearch());
        mainController.showView(searchClientView);
    }

    private void showNewAnnotationStage() {
        client = clientInfoController.getClient();
        client.set(ClientField.PVU, clientAnnotationView.getPvuText());
        // Supponiamo di avere un oggetto 'annotation' da modificare.
        AnnotationEditorStage editor = new AnnotationEditorStage(new Annotation(LocalDate.now(), mainController.getApp().getWorkingOperator(), null, null), "Registra Nuova Chiamata");
        editor.showAndWait();

        if (editor.isSaved()) {
            Annotation updatedAnnotation = editor.getAnnotation();
            // Procedi con l'aggiornamento sul database o altre operazioni.
            dbManagerInterface.saveAnnotation(updatedAnnotation, client.getUuid().toString());

            //Aggiorno il client visualizzato (ancora da salvare in DB) con le nuove info della registrazione chiamata
            client.set(ClientField.ULTIMA_CHIAMATA, updatedAnnotation.getCallDate());
            client.set(ClientField.VOLTE_CONTATTATI, (Integer) client.get(ClientField.VOLTE_CONTATTATI) + 1);
            client.set(ClientField.PROSSIMA_CHIAMATA, updatedAnnotation.getNextCallDate());

            //Refresh della pagina
            mainController.showClientPage(client);
        }
    }
}
