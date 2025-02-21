package orodent.clientrelationmanager.view.mainview;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import orodent.clientrelationmanager.controller.main.StatusToolTipController;
import orodent.clientrelationmanager.model.App;

public class MainView extends BorderPane {

    public MainView(App app) {
        this.setBottom(StatusToolTipController.statusToolTipView);

        HotBarView hotBarView = new HotBarView(this, app.getDbManager());
        hotBarView.setAlignment(Pos.CENTER_LEFT);
        this.setLeft(hotBarView);

        TextField searchField = new TextField();
        Button connectButton = new Button("Connect");
        Button disconnectButton = new Button("Disconnect");
        Button testButton = new Button("Test");

        connectButton.setOnAction(event -> app.getDbManager().open());
        disconnectButton.setOnAction(event -> app.getDbManager().close());
        testButton.setOnAction(event -> app.getDbManager().test(searchField.getText()));

        VBox vBox = new VBox();
        this.setRight(vBox);
        vBox.getChildren().addAll(connectButton, disconnectButton, searchField, testButton);
    }
}
