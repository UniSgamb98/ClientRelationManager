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
        addButton = new Button("➕ Aggiungi");
        searchButton = new Button("🔍 Cerca");
        reportButton = new Button("📊 Report");
        callsButton = new Button("📞 Chiamate");

        // Aggiunta dei bottoni alla barra
        getChildren().addAll(addButton, searchButton, reportButton, callsButton);

        // Stile dei bottoni
        for (Button button : new Button[]{addButton, searchButton, reportButton, callsButton}) {
            button.getStyleClass().add("hotbar-button");
        }

        // Eventi
        addButton.setOnAction(new NewClientController(db, mainView));
        searchButton.setOnAction(new SearchClientController(db, mainView));
        // reportButton.setOnAction(new ReportController(app, mainView));
        // callsButton.setOnAction(new ShowCallsController(app, mainView));

        // Stile della HotBar
        this.getStyleClass().add("hotbar");
        this.setSpacing(15);
        this.setAlignment(Pos.CENTER);
    }
}
