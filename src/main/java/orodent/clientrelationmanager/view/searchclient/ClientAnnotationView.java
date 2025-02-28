package orodent.clientrelationmanager.view.searchclient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.model.Annotation;
import orodent.clientrelationmanager.model.Client;
import orodent.clientrelationmanager.model.enums.ClientField;
import orodent.clientrelationmanager.view.mainview.AnnotationEditorStage;

import java.util.List;

public class ClientAnnotationView extends VBox {
    List<Annotation> annotations;
    TextArea textArea;
    ObservableList<AnnotationPane> annotationItems;
    Runnable onUpdate;

    public ClientAnnotationView(Client client, DBManagerInterface dbManager) {
        Label titleLabel = new Label("ANNOTAZIONI");
        titleLabel.getStyleClass().add("annotation-title");

        Label pvuLabel = new Label("PVU Aziendali");
        pvuLabel.getStyleClass().add("annotation-label");

        textArea = new TextArea(client.getField(ClientField.PVU, String.class));
        textArea.setWrapText(true);
        textArea.setMinHeight(100);
        textArea.setMaxHeight(150);
        textArea.getStyleClass().add("annotation-pvu");

        Label callLabel = new Label("Registrazione Chiamate");
        callLabel.getStyleClass().add("annotation-label");
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
                        dbManager.saveClientAfterAnnotationChange(updatedAnnotation, client.getUuid().toString());
                        annotations = dbManager.getAnnotationsForClient(client);
                        annotationItems = FXCollections.observableArrayList();

                        for (Annotation annotation : annotations) {
                            annotationItems.add(new AnnotationPane(annotation));
                        }
                        annotationArea.setItems(annotationItems);

                        onUpdate.run();
                    }
                }
            }
        });

        this.getChildren().addAll(titleLabel, pvuLabel, textArea, callLabel, annotationArea);
        this.getStyleClass().add("annotation-view");
    }

    public void setOnUpdate(Runnable onUpdate) {
        this.onUpdate = onUpdate;
    }

    public String getPvuText() {
        return textArea.getText();
    }
}
