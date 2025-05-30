package orodent.clientrelationmanager.view.customerservice;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import orodent.clientrelationmanager.controller.main.MainController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FlowPhoto extends BorderPane {
    private final ArrayList<PhotoItem> photoItems;

    public FlowPhoto(String discCode){
        MainController mainController = new MainController();
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
                PhotoItem photoItem = new PhotoItem(selectedFile);
                photoItem.removeMe.addListener((obs, oldVal, newVal) -> {
                    flowPane.getChildren().remove(photoItem);
                    photoItems.remove(photoItem);
                });
                photoItems.add(photoItem);
                flowPane.getChildren().add(photoItem);
            } else {
                System.err.println("Nessun file selezionato.");
            }
        });

        Label discLabel = new Label(discCode);
        setAlignment(discLabel, Pos.CENTER);
        this.setTop(discLabel);

        getStyleClass().add("flow-photo");
        discLabel.getStyleClass().add("flowphoto-label");
        flowPane.getStyleClass().add("flowphoto-container");
        allegaFoto.getStyleClass().add("flowphoto-button");
        this.setBottom(allegaFoto);
        this.setCenter(flowPane);
        setAlignment(allegaFoto, Pos.CENTER);
    }

    public List<File> getImageFiles(){
        ArrayList<File> ret = new ArrayList<>();
        for (PhotoItem i : photoItems){
            ret.add(i.getImageFile());
        }
        return ret;
    }
}
