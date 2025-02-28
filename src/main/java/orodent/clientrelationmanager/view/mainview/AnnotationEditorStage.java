package orodent.clientrelationmanager.view.mainview;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import orodent.clientrelationmanager.model.Annotation;
import orodent.clientrelationmanager.model.enums.Operator;

import java.util.Objects;

public class AnnotationEditorStage extends Stage {

    private final Annotation annotation;
    private boolean saved = false;

    // Campi di input
    private final DatePicker callDatePicker;
    private final ComboBox<Operator> operatorComboBox;
    private final TextArea contentArea;
    private final DatePicker nextCallDatePicker;

    public AnnotationEditorStage(Annotation annotation, String stageTitle) {
        this.annotation = annotation;
        initModality(Modality.APPLICATION_MODAL);
        setTitle(stageTitle);

        // Inizializza i campi con i dati attuali dell'annotazione
        callDatePicker = new DatePicker(annotation.getCallDate());

        operatorComboBox = new ComboBox<>();
        operatorComboBox.getItems().addAll(Operator.values());
        operatorComboBox.setValue(annotation.getMadeBy());

        contentArea = new TextArea(annotation.getContent());
        contentArea.setPromptText("Inserisci il contenuto della chiamata...");
        contentArea.setWrapText(true);

        nextCallDatePicker = new DatePicker();
        nextCallDatePicker.setPromptText("(Opzionale)");
        if (annotation.getNextCallDate() != null) {
            nextCallDatePicker.setValue(annotation.getNextCallDate());
        }

        // Pulsanti
        HBox buttonBox = getButtonBox();
        buttonBox.getStyleClass().add("annotation-editor-buttons");

        // Layout del form
        VBox layout = new VBox(10,
                new Label("Data Chiamata:"), callDatePicker,
                new Label("Operatore:"), operatorComboBox,
                new Label("Contenuto:"), contentArea,
                new Label("Prossima Chiamata:"), nextCallDatePicker,
                buttonBox
        );
        layout.setPadding(new Insets(15));
        layout.getStyleClass().add("annotation-editor-layout");

        Scene scene = new Scene(layout, 400, 400);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/annotation-editor.css")).toExternalForm());
        setScene(scene);
    }

    private HBox getButtonBox() {
        Button saveButton = new Button("Salva");
        Button cancelButton = new Button("Annulla");

        saveButton.setOnAction(e -> {
            if (operatorComboBox.getValue() == null || contentArea.getText().trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText(null);
                alert.setContentText("Devi selezionare un operatore e scrivere un contenuto.");
                alert.show();
                return;
            }

            // Aggiorna l'annotazione con i valori modificati
            annotation.setCallDate(callDatePicker.getValue());
            annotation.setMadeBy(operatorComboBox.getValue());
            annotation.setContent(contentArea.getText());
            annotation.setNextCallDate(nextCallDatePicker.getValue());
            saved = true;
            close();
        });

        cancelButton.setOnAction(e -> close());

        return new HBox(10, saveButton, cancelButton);
    }

    /**
     * Indica se l'utente ha salvato le modifiche.
     */
    public boolean isSaved() {
        return saved;
    }

    /**
     * Restituisce l'annotazione aggiornata.
     */
    public Annotation getAnnotation() {
        return annotation;
    }
}
