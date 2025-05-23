package orodent.clientrelationmanager.view.mainview;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import orodent.clientrelationmanager.model.Annotation;
import orodent.clientrelationmanager.controller.main.MainController;

import java.util.Objects;

public class AnnotationEditorStage extends Stage {

    private final Annotation annotation;
    private boolean saved = false;

    // Campi di input
    private final DatePicker callDatePicker;
    private final ComboBox<String> operatorComboBox;
    private final TextArea contentArea;
    private final DatePicker nextCallDatePicker;

    // Nuovi campi per i booleani
    private final CheckBox informationCheckBox;
    private final CheckBox catalogCheckBox;
    private final CheckBox sampleCheckBox;

    public AnnotationEditorStage(Annotation annotation, String stageTitle) {
        this.annotation = annotation;
        initModality(Modality.APPLICATION_MODAL);
        setTitle(stageTitle);

        // Inizializza i campi con i dati attuali dell'annotazione
        callDatePicker = new DatePicker(annotation.getCallDate());

        operatorComboBox = new ComboBox<>();
        operatorComboBox.getItems().addAll(new MainController().getApp().getConfigs().get("OPERATORE_ASSEGNATO"));
        operatorComboBox.setValue(annotation.getMadeBy());

        contentArea = new TextArea(annotation.getContent());
        contentArea.setPromptText("Inserisci il contenuto della chiamata...");
        contentArea.setWrapText(true);

        nextCallDatePicker = new DatePicker();
        nextCallDatePicker.setPromptText("(Obbligatoria)");
        if (annotation.getNextCallDate() != null) {
            nextCallDatePicker.setValue(annotation.getNextCallDate());
        }

        // Inizializza le checkbox per INFORMATION, CATALOG e SAMPLE
        informationCheckBox = new CheckBox("Information");
        catalogCheckBox = new CheckBox("Catalog");
        sampleCheckBox = new CheckBox("Sample");
        informationCheckBox.setSelected(annotation.getInformation());
        catalogCheckBox.setSelected(annotation.getCatalog());
        sampleCheckBox.setSelected(annotation.getSample());

        // Gruppo di checkbox in orizzontale
        HBox booleansBox = new HBox(10, informationCheckBox, catalogCheckBox, sampleCheckBox);
        booleansBox.setPadding(new Insets(5, 0, 5, 0));

        // Pulsanti
        HBox buttonBox = getButtonBox();
        buttonBox.getStyleClass().add("annotation-editor-buttons");

        // Layout del form: aggiungiamo anche i checkbox prima dei pulsanti
        VBox layout = new VBox(10,
                new Label("Data Chiamata:"), callDatePicker,
                new Label("Operatore:"), operatorComboBox,
                new Label("Contenuto:"), contentArea,
                new Label("Prossima Chiamata:"), nextCallDatePicker,
                booleansBox,
                buttonBox
        );
        layout.setPadding(new Insets(15));
        layout.getStyleClass().add("annotation-editor-layout");

        Scene scene = new Scene(layout, 400, 450);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/annotation-editor.css")).toExternalForm());
        setScene(scene);
        contentArea.requestFocus();
    }

    private HBox getButtonBox() {
        Button saveButton = new Button("Salva");
        Button cancelButton = new Button("Annulla");

        saveButton.setOnAction(e -> {
            if (contentArea.getText() == null || nextCallDatePicker.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText(null);
                alert.setContentText("Devi scrivere un contenuto e scegliere una data di richiamo.");
                alert.showAndWait();
                return;
            }

            // Aggiorna l'annotazione con i valori modificati
            annotation.setCallDate(callDatePicker.getValue());
            annotation.setMadeBy(operatorComboBox.getValue());
            annotation.setContent(contentArea.getText());
            annotation.setNextCallDate(nextCallDatePicker.getValue());

            // Aggiorna anche i campi booleani
            annotation.setInformation(informationCheckBox.isSelected());
            annotation.setCatalog(catalogCheckBox.isSelected());
            annotation.setSample(sampleCheckBox.isSelected());

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
