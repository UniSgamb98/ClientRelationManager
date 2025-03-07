package orodent.clientrelationmanager.view.mainview;

import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.controller.main.MainController;
import orodent.clientrelationmanager.controller.main.buttons.ReportController;
import orodent.clientrelationmanager.controller.main.buttons.ShowCallsController;
import orodent.clientrelationmanager.controller.main.buttons.newclient.NewClientController;
import orodent.clientrelationmanager.controller.main.buttons.searchclient.SearchClientController;

public class HotBarView extends VBox {
    private final Button addButton;
    private final Button searchButton;
    private final Button reportButton;
    private final Button callsButton;
    public HotBarView() {
        // Creazione bottoni
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

        // Applica lo stile CSS e impostazioni iniziali
        this.getStyleClass().add("hotbar");
        this.setSpacing(15);
        this.setAlignment(Pos.CENTER);
        // La HotBar parte fuori schermo (inizialmente) grazie al CSS, oppure, se non usi la transizione CSS, puoi impostarlo qui:
        this.setTranslateX(300);
        this.setOpacity(0);
    }

    /**
     * Anima l'entrata della HotBar, facendola scorrere da destra a sinistra.
     */
    public void animateEntrance() {
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(500), this);
        slideIn.setFromX(-300); // parte da fuori schermo
        slideIn.setToX(0);     // arriva a posizione 0
        slideIn.setInterpolator(javafx.animation.Interpolator.EASE_OUT);
        slideIn.play();
        // Allo stesso tempo, puoi animare l'opacità se vuoi:
        this.setOpacity(1);
        // Infine, aggiungi la classe "hotbar-visible" se vuoi che il CSS possa eventualmente gestire altre modifiche in seguito:
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
}
