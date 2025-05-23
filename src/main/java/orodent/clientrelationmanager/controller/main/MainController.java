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
import orodent.clientrelationmanager.model.Client;
import orodent.clientrelationmanager.view.mainview.HotBarView;
import orodent.clientrelationmanager.view.mainview.MainView;
import orodent.clientrelationmanager.view.clientinfo.ClientDetailView;

import java.util.List;

public class MainController {
    private static final MainView mainView = new MainView();
    private static final App app = new App();

    /**
     * Imposta la vista centrale della MainView.
     * @param view La nuova vista da mostrare al centro.
     */
    private void setCenterView(Node view) {
        mainView.setCenter(view);
        resizeWindow(1135,800);
    }

    /**
     * Imposta la vista Centrale della MainView per mostrare i dettagli di un client
     * @param client Il cliente da mostrare all'utente
     */
    public void showClientPage(Client client) {
        ClientDetailView clientDetailView = new ClientDetailView(client);
        showView(clientDetailView);
    }

    //Si dovrei sistemare in modo che showClientPage accetti degli UUID ma questo significa che la SearchResultView va cambiata e forse anche DisplayableClient
    public void showClientPage(String clientUUID) {
        ClientDetailView clientDetailView = new ClientDetailView(getApp().getDbManager().getClientWhere("ID = '" + clientUUID + "'").getFirst());
        showView(clientDetailView);
    }

    /**
     * Ridimensiona la finestra principale in modo fluido.
     * @param targetWidth La nuova larghezza.
     * @param targetHeight La nuova altezza.
     */
    public void resizeWindow(double targetWidth, double targetHeight) {
        Stage stage = app.getPrimaryStage();

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
                new KeyFrame(Duration.millis(1),
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
        mainView.setLeft(hotBarView);
        hotBarView.animateEntrance();
    }

    /**
     * Metodo helper per mostrare la LoginView.
     */
    public void showLoginView() {
        // Crea il controller della LoginView e ottieni la view
        LoginController loginController = new LoginController(app);
        showView(loginController.getView());
    }

    /**
     * Metodo helper per mostrare, per esempio, una TemporaryView (o qualsiasi altra vista).
     * @param newView La vista da mostrare al centro.
     */
    public void showView(Node newView) {
        setCenterView(newView);
    }

    public MainView getMainView(){
        return mainView;
    }

    public void setPrimaryStage(Stage primaryStage) {
        app.setPrimaryStage(primaryStage);
    }

    public App getApp(){
        return app;
    }

    /**
     * Questo metodo serve a FilterSectionController per salvare la lista che sta mostrando in SearchResultView
     */
    public void saveListFromFilteredSearch(List<Client> list) {
        app.setListFromFilteredSearch(list);
    }

    /**
     * Questo metodo serve alla ClientDetails quando viene premuto il pulsante Indietro per recuperare i clienti che erano visualizzati
     */
    public List<Client> getListFromFilteredSearch(){
        return app.getListFromFilteredSearch();
    }

    // Altri metodi possono essere aggiunti per coordinare operazioni globali,
    // come aggiornamenti dello status, comunicazioni tra i vari subController, ecc.
}
