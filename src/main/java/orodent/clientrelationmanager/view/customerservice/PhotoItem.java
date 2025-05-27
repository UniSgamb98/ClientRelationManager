package orodent.clientrelationmanager.view.customerservice;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;

public class PhotoItem extends VBox {
    public SimpleBooleanProperty removeMe;

    public PhotoItem(File imageFile){
        removeMe = new SimpleBooleanProperty(false);
        Label removeLabel = new Label("Rimuovi immagine");
        Button removeButton = new Button("❌");
        HBox bottom = new HBox(10, removeLabel, removeButton);
        ImageView imageView = new ImageView();
        Image image = new Image(imageFile.toURI().toString());
        imageView.setImage(image);
        imageView.setFitWidth(400); // dimensione massima
        imageView.setPreserveRatio(true); // mantiene le proporzioni

        removeButton.setOnAction(e -> removeMe.set(true));

        setSpacing(5);
        getChildren().addAll(imageView, bottom);
    }
}
