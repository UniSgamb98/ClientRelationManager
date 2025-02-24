package orodent.clientrelationmanager.view.mainview;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.controller.main.buttons.newclient.NewClientController;
import orodent.clientrelationmanager.controller.main.buttons.searchclient.SearchClientController;

public class HotBarView extends VBox {
    private final Button addButton;
    private final Button searchButton;
    private final Button reportButton;
    private final Button callsButton;

    public HotBarView(MainView mainView, DBManagerInterface db) {
        addButton = new Button("aggiungi");
        addButton.setOnAction(event -> expand());
        searchButton = new Button("cerca");
        reportButton = new Button("report");
        callsButton = new Button("chiamate");
        getChildren().addAll(addButton, searchButton, reportButton, callsButton);

        addButton.setPrefWidth(70);
        searchButton.setPrefWidth(70);
        reportButton.setPrefWidth(70);
        callsButton.setPrefWidth(70);
        addButton.setPrefHeight(70);
        searchButton.setPrefHeight(70);
        reportButton.setPrefHeight(70);
        callsButton.setPrefHeight(70);

        addButton.setOnAction(new NewClientController(db, mainView));
        searchButton.setOnAction(new SearchClientController(db, mainView));
      //  reportButton.setOnAction(new ReportController(app, mainView));
        //callsButton.setOnAction(new ShowCallsController(app, mainView));

        this.setSpacing(10);
        this.setAlignment(Pos.TOP_CENTER);
    }

    public void expand(){
        addButton.setMaxHeight(0);
        searchButton.setMaxHeight(0);
        reportButton.setMaxHeight(0);
        callsButton.setMaxHeight(0);
        this.setMaxHeight(0);
    }
}
