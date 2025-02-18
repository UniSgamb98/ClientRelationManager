package orodent.clientrelationmanager.view;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import orodent.clientrelationmanager.controller.LoginController;
import orodent.clientrelationmanager.controller.StatusToolTipController;
import orodent.clientrelationmanager.controller.database.ConnectionManager;
import orodent.clientrelationmanager.model.App;

import java.sql.*;

public class MainView extends BorderPane {
    private final StatusToolTipController statusToolTipController;
    private HBox hBox;
    private TextField searchField;
    private LoginView loginView;
    private LoginController loginController;

    public MainView(App app) {
        this.statusToolTipController = new StatusToolTipController();
        this.setBottom(statusToolTipController.getView());

        this.setLeft(new HotBarView());

        searchField = new TextField();
        Button connectButton = new Button("Connect");
        Button disconnectButton = new Button("Disconnect");
        Button testButton = new Button("Test");


        connectButton.setOnAction(event -> {
            app.getDbManager().open();
        });
        disconnectButton.setOnAction(event -> {
            app.getDbManager().close();
        });

        testButton.setOnAction(event -> app.getDbManager().test(searchField.getText()));

        hBox = new HBox();
        this.setCenter(hBox);
        hBox.getChildren().addAll(connectButton, disconnectButton, searchField, testButton);

        loginController = new LoginController(app);
        loginView = loginController.getView();
        this.setRight(loginView);
    }
}
