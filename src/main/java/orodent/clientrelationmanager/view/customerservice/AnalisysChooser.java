package orodent.clientrelationmanager.view.customerservice;

import javafx.geometry.Pos;
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

    public AnalisysChooser(){
        mainController = new MainController();
        Label title = new Label("Analisi Tecnica");
        fileLabel = new Label();
        Button allegaAnalisi = new Button("Allega analisi.docx");
        Button removeButton = new Button("❌");
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

        HBox label = new HBox(20, fileLabel, removeButton);
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(title, label, allegaAnalisi);
    }

    public File getSelectedFile() {
        return selectedFile;
    }
}
