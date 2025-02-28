package orodent.clientrelationmanager.view.searchclient;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import orodent.clientrelationmanager.model.Annotation;

public class AnnotationPane extends VBox {
    Annotation annotation;

    public AnnotationPane(Annotation annotation) {
        this.annotation = annotation;
        this.setSpacing(10);
        this.setPadding(new Insets(10));
        this.getStyleClass().add("annotation-pane");

        // Etichetta per l'operatore e la data della chiamata
        Label operatorLabel = new Label(annotation.getMadeBy().name() + " - " + annotation.getCallDate());
        operatorLabel.getStyleClass().add("operator-label");

        // Testo per il contenuto dell'annotazione (con un limite di lunghezza)
        Text contentText = new Text(annotation.getContent());
        contentText.getStyleClass().add("content-text");
        TextFlow contentFlow = new TextFlow(contentText);
        contentFlow.setMaxWidth(300); // Per evitare che il testo sia troppo largo

        // Etichetta per la prossima chiamata (se esiste)
        Label nextCallLabel = new Label(annotation.getNextCallDate() != null
                ? "Prossima chiamata: " + annotation.getNextCallDate()
                : "Nessuna prossima chiamata");
        nextCallLabel.getStyleClass().add("next-call-label");

        // Layout principale
        this.getChildren().addAll(operatorLabel, contentFlow, nextCallLabel);
        this.setSpacing(5);
    }

    public Annotation getAnnotation() {
        return annotation;
    }

}
