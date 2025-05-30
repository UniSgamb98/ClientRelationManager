package orodent.clientrelationmanager.view.customerservice;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import orodent.clientrelationmanager.controller.main.MainController;

import java.io.File;

public class AnalisysChooser extends VBox {
    private final MainController mainController;
    private File selectedFile;
    private final Label fileLabel;

    public AnalisysChooser() {
        mainController = new MainController();
        // root container
        this.getStyleClass().add("analysis-chooser");

        // titolo
        Label title = new Label("Analisi Tecnica");
        title.getStyleClass().add("analysis-title");

        // etichetta file
        fileLabel = new Label();
        fileLabel.getStyleClass().add("analysis-file-label");

        // bottone per allegare/cambiare
        Button allegaAnalisi = new Button("Allega analisi.docx");
        allegaAnalisi.getStyleClass().add("analysis-button");

        // bottone per rimuovere
        Button removeButton = new Button("❌");
        removeButton.getStyleClass().add("analysis-remove-button");
        removeButton.setVisible(false);
        removeButton.setOnAction(e -> {
            removeButton.setVisible(false);
            fileLabel.setText("");
            selectedFile = null;
            allegaAnalisi.setText("Allega analisi.docx");
        });

        allegaAnalisi.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();

            // Imposta un filtro per le estensioni accettate
            FileChooser.ExtensionFilter fileFilter = new FileChooser.ExtensionFilter(
                    "File (*.png, *.jpg, *.jpeg, *.docx)", "*.png", "*.jpg", "*.jpeg", "*.docx"
            );
            fileChooser.getExtensionFilters().add(fileFilter);

            // Mostra la finestra di dialogo
            selectedFile = fileChooser.showOpenDialog(mainController.getApp().getPrimaryStage());

            if (selectedFile != null) {
                allegaAnalisi.setText("Cambia Allegato");
                fileLabel.setText(selectedFile.getName());
                removeButton.setVisible(true);
            } else {
                System.err.println("Nessun file selezionato.");
            }
        });

        HBox labelContainer = new HBox(fileLabel, removeButton);
        labelContainer.getStyleClass().add("analysis-label-container");

        // aggiungo tutti i nodi
        this.getChildren().addAll(title, labelContainer, allegaAnalisi);
    }

    public File getSelectedFile() {
        return selectedFile;
    }
}
