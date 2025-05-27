package orodent.clientrelationmanager.view.customerservice;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.controller.main.MainController;

import java.io.File;
import java.util.ArrayList;

public class FlowPhoto extends BorderPane {
    private final ArrayList<PhotoItem> photoItems;

    public FlowPhoto(int praticaNumber, int discoId, String discCode){
        MainController mainController = new MainController();
        DBManagerInterface dbManagerInterface = mainController.getApp().getDbManager();
        photoItems = new ArrayList<>();
        FlowPane flowPane = new FlowPane();

        //TODO Qua manca la parte che inizializza tutte le foto di un reclamo già esistente quindi con tutte le sue foto lavora con DB
        //dbManagerInterface. getPhoto(pratica NUmber, disco id);
        flowPane.getChildren().addAll(photoItems);

        Button allegaFoto = new Button("Allega foto");
        allegaFoto.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();

            // Imposta un filtro per mostrare solo immagini
            FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter(
                    "Immagini (*.png, *.jpg, *.jpeg, *.gif)", "*.png", "*.jpg", "*.jpeg", "*.gif"
            );
            fileChooser.getExtensionFilters().add(imageFilter);

            // Mostra la finestra di dialogo
            File selectedFile = fileChooser.showOpenDialog(mainController.getApp().getPrimaryStage());

            if (selectedFile != null) {
                dbManagerInterface.savePhoto(praticaNumber, discoId, selectedFile);
                PhotoItem photoItem = new PhotoItem(selectedFile);
                photoItem.removeMe.addListener((obs, oldVal, newVal) -> {
                    flowPane.getChildren().remove(photoItem);
                    photoItems.remove(photoItem);
                    //TODO Qua dovrei aggiungere un DeletePhoto Per il DB
                });
                photoItems.add(photoItem);
                flowPane.getChildren().add(photoItem);
            } else {
                System.err.println("Nessun file selezionato.");
            }
        });

        this.setBottom(allegaFoto);
        this.setCenter(flowPane);
        setAlignment(allegaFoto, Pos.CENTER);
    }
}
