package orodent.clientrelationmanager.view.mainview;

import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import orodent.clientrelationmanager.controller.main.MainController;
import orodent.clientrelationmanager.controller.main.buttons.*;
import orodent.clientrelationmanager.model.App;

public class HotBarView extends VBox {

    public HotBarView() {
        Button addButton = new Button("➕ Aggiungi");
        Button searchButton = new Button("🔍 Cerca");
        Button reportButton = new Button("📊 Report");
        Button callsButton = new Button("📞 Chiamate");
        Button customerServiceButton = new Button("Assistenza");
        Button printConfigButton = new Button("Stampa Config");
        Button printImportForDB = new Button("Stampa import.txt");

        addButton.setOnAction(new NewClientController());
        searchButton.setOnAction(new SearchClientController());
        reportButton.setOnAction(new ReportController());
        callsButton.setOnAction(new ShowCallsController());
        printConfigButton.setOnAction(new PrintConfigController());
        printImportForDB.setOnAction(new PrintImportController());
        customerServiceButton.setOnAction(new CustomerServiceButton());

        // Aggiunta dei bottoni alla barra
        getChildren().addAll(addButton, searchButton, reportButton, callsButton, customerServiceButton);
        App app = new MainController().getApp();
        if (app.getConfigs().get("admin").contains(app.getWorkingOperator()))   getChildren().addAll(printConfigButton, printImportForDB);

        // Stile dei bottoni
        for (Button button : new Button[]{addButton, searchButton, reportButton, callsButton, printConfigButton, printImportForDB, customerServiceButton}) {
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
}
