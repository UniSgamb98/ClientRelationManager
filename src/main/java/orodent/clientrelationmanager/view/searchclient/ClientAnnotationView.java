package orodent.clientrelationmanager.view.searchclient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.controller.main.MainController;
import orodent.clientrelationmanager.model.Annotation;
import orodent.clientrelationmanager.model.Client;
import orodent.clientrelationmanager.model.enums.ClientField;
import orodent.clientrelationmanager.view.mainview.AnnotationEditorStage;

import java.util.List;

public class ClientAnnotationView extends VBox {
    List<Annotation> annotations;
    TextArea textArea;
    ObservableList<AnnotationPane> annotationItems;

    public ClientAnnotationView(Client client, DBManagerInterface dbManager) {
        Label pvuLabel = new Label("PVU Aziendali");
        textArea = new TextArea(client.getField(ClientField.PVU, String.class));
        textArea.setWrapText(true);
        textArea.setMinHeight(100);
        //textArea.getStyleClass().add("annotation-pvu");

        Label callLabel = new Label("Registrazione Chiamate");
        ListView<AnnotationPane> annotationArea = new ListView<>();
        annotationArea.getStyleClass().add("annotation-list");
        annotationArea.setMinHeight(200);


        // Recupero annotazioni dal database
        annotations = dbManager.getAnnotationsForClient(client);
        annotationItems = FXCollections.observableArrayList();

        for (Annotation annotation : annotations) {
            annotationItems.add(new AnnotationPane(annotation));
        }
        annotationArea.setItems(annotationItems);

        // Aggiungo il listener per il doppio click sulla ListView
        annotationArea.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                AnnotationPane selectedPane = annotationArea.getSelectionModel().getSelectedItem();
                if (selectedPane != null) {
                    AnnotationEditorStage editor = new AnnotationEditorStage(selectedPane.getAnnotation(), "Modifica Annotazione");
                    editor.showAndWait();

                    if (editor.isSaved()) {
                        Annotation updatedAnnotation = editor.getAnnotation();
                        // Procedi con l'aggiornamento sul database o altre operazioni.
                        dbManager.updateAnnotation(updatedAnnotation, client.getUuid().toString());

                        if (updatedAnnotation.getInformation()) client.set(ClientField.INFORMATION, true);
                        if (updatedAnnotation.getCatalog()) client.set(ClientField.CATALOG, true);
                        if (updatedAnnotation.getSample()) client.set(ClientField.SAMPLE, true);

                        new MainController().showClientPage(client);
                    }
                }
            }
        });

        VBox pvuBox = new VBox(pvuLabel, textArea);
        VBox annotationBox = new VBox(callLabel, annotationArea);
        SplitPane splitPane = new SplitPane(pvuBox, annotationBox);
        splitPane.setOrientation(Orientation.VERTICAL);
        splitPane.setDividerPositions(0.4);
        VBox.setVgrow(splitPane, Priority.ALWAYS);
        VBox.setVgrow(textArea, Priority.ALWAYS);
        VBox.setVgrow(annotationArea, Priority.ALWAYS);

        this.getChildren().addAll(splitPane);
        this.getStyleClass().add("client-info-view");
        pvuBox.getStyleClass().add("information-group");
        annotationBox.getStyleClass().add("information-group");
        this.getStyleClass().add("annotation-view");
    }

    public String getPvuText() {
        return textArea.getText();
    }
}
