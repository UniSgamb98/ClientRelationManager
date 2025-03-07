package orodent.clientrelationmanager.controller.main;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.util.Duration;
import orodent.clientrelationmanager.controller.LoginController;
import orodent.clientrelationmanager.controller.main.buttons.ReportController;
import orodent.clientrelationmanager.controller.main.buttons.ShowCallsController;
import orodent.clientrelationmanager.controller.main.buttons.newclient.NewClientController;
import orodent.clientrelationmanager.controller.main.buttons.searchclient.SearchClientController;
import orodent.clientrelationmanager.model.App;
import orodent.clientrelationmanager.model.Client;
import orodent.clientrelationmanager.view.mainview.HotBarView;
import orodent.clientrelationmanager.view.mainview.MainView;
import orodent.clientrelationmanager.view.searchclient.SearchClientView;

public class MainController {
    private final MainView mainView;
    private final App app;

    private final NewClientController newClientController;
    private final SearchClientController searchClientController;
    private final ReportController reportController;
    private final ShowCallsController showCallsController;



    public MainController(App app, MainView mainView) {
        newClientController = new NewClientController(app.getDbManager(), this);
        searchClientController = new SearchClientController(app.getDbManager(), this);
        reportController = new ReportController(app.getDbManager(), this);
        showCallsController = new ShowCallsController(app.getDbManager(), this);
        this.app = app;
        this.mainView = mainView;
    }

    /**
     * Imposta la vista centrale della MainView.
     * @param view La nuova vista da mostrare al centro.
     */
    private void setCenterView(Node view) {
        mainView.setCenter(view);
        resizeWindow(1200,800);
    }

    /**
     * Imposta la vista Centrale della MainView per mostrare i dettagli di un client
     * @param client Il cliente da mostrare all'utente
     */
    public void showClientPage(Client client) {
        SearchClientView searchClientView = new SearchClientView(this, app.getDbManager());
        searchClientView.showClientDetail(client);
        showView(searchClientView);
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
        HotBarView hotBarView = new HotBarView();
        hotBarView.setAddClientEvent(newClientController);
        hotBarView.setSearchClientEvent(searchClientController);
        hotBarView.setReportEvent(reportController);
        hotBarView.setCallsEvent(showCallsController);
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
