package orodent.clientrelationmanager.view.mainview;

import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.controller.main.buttons.ReportController;
import orodent.clientrelationmanager.controller.main.buttons.ShowCallsController;
import orodent.clientrelationmanager.controller.main.buttons.newclient.NewClientController;
import orodent.clientrelationmanager.controller.main.buttons.searchclient.SearchClientController;

public class HotBarView extends VBox {
    public HotBarView(MainView mainView, DBManagerInterface db) {
        Button addButton = new Button("➕ Aggiungi");
        Button searchButton = new Button("🔍 Cerca");
        Button reportButton = new Button("📊 Report");
        Button callsButton = new Button("📞 Chiamate");

        // Aggiunta dei bottoni alla barra
        getChildren().addAll(addButton, searchButton, reportButton, callsButton);

        // Stile dei bottoni
        for (Button button : new Button[]{addButton, searchButton, reportButton, callsButton}) {
            button.getStyleClass().add("hotbar-button");
        }

        // Eventi
        addButton.setOnAction(new NewClientController(db, mainView));
        searchButton.setOnAction(new SearchClientController(db, mainView));
        reportButton.setOnAction(new ReportController());
        callsButton.setOnAction(new ShowCallsController(db));

        // Stile della HotBar
        this.getStyleClass().add("hotbar");
        this.setSpacing(15);
        this.setAlignment(Pos.CENTER);

        PauseTransition delay = new PauseTransition(Duration.millis(50));
        delay.setOnFinished(e -> this.getStyleClass().add("hotbar-visible"));
        delay.play();
    }

    public void animateEntrance() {
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(500), this);
        slideIn.setFromX(300); // Inizia fuori schermo
        slideIn.setToX(0);     // Scorre a sinistra
        slideIn.setInterpolator(javafx.animation.Interpolator.EASE_OUT);
        slideIn.play();
        slideIn.setOnFinished(e -> this.getStyleClass().add("hotbar-visible"));
    }
}
