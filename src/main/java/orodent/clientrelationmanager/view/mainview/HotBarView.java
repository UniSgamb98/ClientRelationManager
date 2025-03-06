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
    public HotBarView(MainController mainController, DBManagerInterface db) {
        // Creazione bottoni
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
        addButton.setOnAction(new NewClientController(db, mainController));
        searchButton.setOnAction(new SearchClientController(db, mainController));
        reportButton.setOnAction(new ReportController(db, mainController));
        callsButton.setOnAction(new ShowCallsController(db, mainController));

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
}
