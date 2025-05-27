package orodent.clientrelationmanager.view.customerservice;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.controller.main.MainController;
import orodent.clientrelationmanager.controller.popup.AutoCompletePopup;
import orodent.clientrelationmanager.model.Disc;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class DiscView extends HBox {
    private final TextField tipologia;
    private final TextField misura;
    private final TextField lotto;
    private final TextField diametro;
    private final TextField colore;
    private final TextField problema;
    private final Label indicator;
    public SimpleBooleanProperty completed;
    public SimpleBooleanProperty existsInDatabase;
    private final List<Disc> discsInDatabase;

    public DiscView(Consumer<DiscView> onRemove) {
        super(5);
        tipologia = new TextField();
        misura = new TextField();
        lotto = new TextField();
        diametro = new TextField();
        colore = new TextField();
        problema = new TextField();
        indicator = new Label();
        tipologia.setPromptText("Tipologia");
        misura.setPromptText("Misura");
        lotto.setPromptText("Lotto");
        diametro.setPromptText("Diametro");
        colore.setPromptText("Colore");
        problema.setPromptText("Problema");
        completed = new SimpleBooleanProperty(false);
        existsInDatabase = new SimpleBooleanProperty(false);
        DBManagerInterface dbManagerInterface = new MainController().getApp().getDbManager();
        discsInDatabase = dbManagerInterface.getDiscWhere("");    //TODO Attenzione qua all'inserimento di nuovi disc che potrebbe essere fatto un insert quando serve un update perchè viene inserito lo stesso disco nella stessa pagina

        // Autocomplete Popup
        Map<String, List<String>> config = new MainController().getApp().getConfigs();
        ObservableList<String> diametri = FXCollections.observableArrayList();
        ObservableList<String> misure   = FXCollections.observableArrayList();
        ObservableList<String> colori   = FXCollections.observableArrayList();
        ObservableList<String> tipologie= FXCollections.observableArrayList();
        ObservableList<String> problemi = FXCollections.observableArrayList();
        ObservableList<String> lotti = FXCollections.observableArrayList();

        diametri.addAll(config.get("DIAMETRI"));
        misure.addAll(config.get("MISURE"));
        colori.addAll(config.get("COLORI"));
        tipologie.addAll(config.get("TIPOLOGIE"));
        problemi.addAll(dbManagerInterface.getAllValuesFromDiscColumn("PROBLEMA"));
        lotti.addAll(dbManagerInterface.getAllValuesFromDiscColumn("LOTTO"));

        new AutoCompletePopup(diametro, diametri);
        new AutoCompletePopup(misura, misure);
        new AutoCompletePopup(colore, colori);
        new AutoCompletePopup(tipologia, tipologie);
        new AutoCompletePopup(problema, problemi);
        new AutoCompletePopup(lotto, lotti);

        // Rimuovi da DiscSelector
        Button removeButton = new Button("❌");
        removeButton.setOnAction(e -> onRemove.accept(this));

        getChildren().addAll(diametro, misura, tipologia, colore, lotto, problema, indicator, removeButton);

        tipologia.textProperty().addListener((obs, oldVal, newVal) -> isComplete());
        misura.textProperty().addListener((obs, oldVal, newVal) -> isComplete());
        lotto.textProperty().addListener((obs, oldVal, newVal) -> isComplete());
        diametro.textProperty().addListener((obs, oldVal, newVal) -> isComplete());
        colore.textProperty().addListener((obs, oldVal, newVal) -> isComplete());
        problema.textProperty().addListener((obs, oldVal, newVal) -> isComplete());

        getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/discview.css")).toExternalForm());
    }

    // Cambia l'icona dell'indicatore usato per segnalare all'utente se verrà creato un nuovo disco oppure c'è già in database
    private void changeIndicator(){
        if (completed.get()){
            if (discsInDatabase.contains(toModello()))  {
                indicator.setText("✅");
                existsInDatabase.set(true);
            }
            else    {
                indicator.setText("new");
                existsInDatabase.set(false);
            }
        } else {
            indicator.setText("");
            existsInDatabase.set(false);
        }
    }

    private void isComplete() {
        completed.set(!tipologia.getText().isEmpty()
                && !misura.getText().isEmpty()
                && !lotto.getText().isEmpty()
                && !diametro.getText().isEmpty()
                && !colore.getText().isEmpty()
                && !problema.getText().isEmpty());
        changeIndicator();
    }

    public Disc toModello() {
        return new Disc(
                tipologia.getText(),
                misura.getText(),
                lotto.getText(),
                diametro.getText(),
                colore.getText(),
                problema.getText()
        );
    }
}
