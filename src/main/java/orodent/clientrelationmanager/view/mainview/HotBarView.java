package orodent.clientrelationmanager.view.mainview;

import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import orodent.clientrelationmanager.controller.main.buttons.PrintConfigController;
import orodent.clientrelationmanager.controller.main.buttons.ReportController;
import orodent.clientrelationmanager.controller.main.buttons.ShowCallsController;
import orodent.clientrelationmanager.controller.main.buttons.newclient.NewClientController;
import orodent.clientrelationmanager.controller.main.buttons.searchclient.SearchClientController;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class HotBarView extends VBox {
    private final Button addButton;
    private final Button searchButton;
    private final Button reportButton;
    private final Button callsButton;
    private final Button printConfigButton;

    public HotBarView() {
        // Creazione bottoni
        addButton = new Button("➕ Aggiungi");
        searchButton = new Button("🔍 Cerca");
        reportButton = new Button("📊 Report");
        callsButton = new Button("📞 Chiamate");
        printConfigButton = new Button("Stampa Config");

        // Aggiunta dei bottoni alla barra
        getChildren().addAll(addButton, searchButton, reportButton, callsButton, printConfigButton);

        // Stile dei bottoni
        for (Button button : new Button[]{addButton, searchButton, reportButton, callsButton, printConfigButton}) {
            button.getStyleClass().add("hotbar-button");
        }

        // Applica lo stile CSS e impostazioni iniziali
        this.getStyleClass().add("hotbar");
        this.setSpacing(15);
        this.setAlignment(Pos.CENTER);
        // La HotBar parte fuori schermo (inizialmente)
        this.setTranslateX(300);
        this.setOpacity(0);
    }

    /**
     * Anima l'entrata della HotBar, facendola scorrere da destra a sinistra.
     */
    public void animateEntrance() {
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(500), this);
        slideIn.setFromX(-300);
        slideIn.setToX(0);
        slideIn.setInterpolator(javafx.animation.Interpolator.EASE_OUT);
        slideIn.play();
        this.setOpacity(1);
        slideIn.setOnFinished(e -> this.getStyleClass().add("hotbar-visible"));
    }

    public void setAddClientEvent(NewClientController newClientController) {
        addButton.setOnAction(newClientController);
    }

    public void setSearchClientEvent(SearchClientController searchClientController) {
        searchButton.setOnAction(searchClientController);
    }

    public void setReportEvent(ReportController reportController) {
        reportButton.setOnAction(reportController);
    }

    public void setCallsEvent(ShowCallsController callsController) {
        callsButton.setOnAction(callsController);
    }

    /**
     * Scrive un fac-simile del file di configurazione
     */
    public void setPrintConfigEvent(PrintConfigController printConfigController) {
        printConfigButton.setOnAction(printConfigController);
    }
}
