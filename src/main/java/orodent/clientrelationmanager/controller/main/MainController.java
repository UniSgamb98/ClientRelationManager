package orodent.clientrelationmanager.controller.main;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.util.Duration;
import orodent.clientrelationmanager.controller.LoginController;
import orodent.clientrelationmanager.model.App;
import orodent.clientrelationmanager.view.mainview.HotBarView;
import orodent.clientrelationmanager.view.mainview.MainView;

public class MainController {
    private final MainView mainView;
    private final App app;

    public MainController(App app, MainView mainView) {
        this.app = app;
        this.mainView = mainView;
    }

    /**
     * Imposta la vista centrale della MainView.
     * @param view La nuova vista da mostrare al centro.
     */
    public void setCenterView(Node view) {
        mainView.setCenter(view);
        resizeWindow(1200,800);
    }

    /**
     * Ridimensiona la finestra principale in modo fluido.
     * @param targetWidth La nuova larghezza.
     * @param targetHeight La nuova altezza.
     */
    public void resizeWindow(double targetWidth, double targetHeight) {
        Stage stage = App.getPrimaryStage();

        // Raccogli le dimensioni e posizione iniziali
        double initialWidth = stage.getWidth();
        double initialHeight = stage.getHeight();
        double initialX = stage.getX();
        double initialY = stage.getY();

        // Calcola la differenza e le nuove posizioni per mantenere il centro fisso
        double deltaWidth = targetWidth - initialWidth;
        double deltaHeight = targetHeight - initialHeight;
        double targetX = initialX - deltaWidth / 2;
        double targetY = initialY - deltaHeight / 2;

        // Crea proprietà personalizzate per larghezza, altezza, x e y
        SimpleDoubleProperty widthProp = new SimpleDoubleProperty(initialWidth);
        SimpleDoubleProperty heightProp = new SimpleDoubleProperty(initialHeight);
        SimpleDoubleProperty xProp = new SimpleDoubleProperty(initialX);
        SimpleDoubleProperty yProp = new SimpleDoubleProperty(initialY);

        // Aggiungi listener per aggiornare lo Stage quando le proprietà cambiano
        widthProp.addListener((obs, oldVal, newVal) -> stage.setWidth(newVal.doubleValue()));
        heightProp.addListener((obs, oldVal, newVal) -> stage.setHeight(newVal.doubleValue()));
        xProp.addListener((obs, oldVal, newVal) -> stage.setX(newVal.doubleValue()));
        yProp.addListener((obs, oldVal, newVal) -> stage.setY(newVal.doubleValue()));

        // Crea una Timeline che anima le quattro proprietà
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(1000),
                        new KeyValue(widthProp, targetWidth),
                        new KeyValue(heightProp, targetHeight),
                        new KeyValue(xProp, targetX),
                        new KeyValue(yProp, targetY)
                )
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    /**
     * Mostra la HotBar sul lato sinistro e ne avvia l'animazione.
     */
    public void showHotBar() {
        HotBarView hotBarView = new HotBarView(this, app.getDbManager());
        mainView.setLeft(hotBarView);
        hotBarView.animateEntrance();
    }

    /**
     * Metodo helper per mostrare la LoginView.
     */
    public void showLoginView() {
        // Crea il controller della LoginView e ottieni la view
        LoginController loginController = new LoginController(app, this);
        setCenterView(loginController.getView());
    }

    /**
     * Metodo helper per mostrare, per esempio, una TemporaryView (o qualsiasi altra vista).
     * @param newView La vista da mostrare al centro.
     */
    public void showView(Node newView) {
        setCenterView(newView);
    }

    // Altri metodi possono essere aggiunti per coordinare operazioni globali,
    // come aggiornamenti dello status, comunicazioni tra i vari subController, ecc.
}
