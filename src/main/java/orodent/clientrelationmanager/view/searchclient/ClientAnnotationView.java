package orodent.clientrelationmanager.view.searchclient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.model.Annotation;
import orodent.clientrelationmanager.model.Client;
import orodent.clientrelationmanager.model.enums.ClientField;

import java.util.List;

public class ClientAnnotationView extends VBox {
    List<Annotation> annotations;
    TextArea textArea;

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
        ObservableList<AnnotationPane> annotationItems = FXCollections.observableArrayList();

        for (Annotation annotation : annotations) {
            annotationItems.add(new AnnotationPane(annotation));
        }

        annotationArea.setItems(annotationItems);

        this.getChildren().addAll(titleLabel, pvuLabel, textArea, callLabel, annotationArea);
        this.getStyleClass().add("annotation-view");
    }

    public String getPvuText() {
        return textArea.getText();
    }
}
