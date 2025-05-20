package orodent.clientrelationmanager.controller.main.buttons;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.controller.main.MainController;
import orodent.clientrelationmanager.controller.main.buttons.searchclient.ClientSelectionController;
import orodent.clientrelationmanager.model.Client;
import orodent.clientrelationmanager.view.searchclient.SearchResultView;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class ShowCallsController implements EventHandler<ActionEvent> {
    private final DBManagerInterface dbManager;
    private Stage stage;
    private final MainController mainController;

    public ShowCallsController(DBManagerInterface dbManagerInterface, MainController mainController) {
        this.dbManager = dbManagerInterface;
        this.mainController = mainController;
    }

    @Override
    public void handle(ActionEvent event) {
        if (stage != null && stage.isShowing()) {
            stage.toFront(); // Porta la finestra in primo piano
            return;
        }

        stage = new Stage();
        stage.setTitle("Chiamate Programmate");

        // DatePicker con data di default a oggi
        DatePicker datePicker = new DatePicker(LocalDate.now());

        // Label di avviso quando non ci sono clienti
        Label emptyLabel = new Label("Nessuna chiamata programmata per questa data.");
        emptyLabel.setVisible(false);

        // ListView con DisplayableClient e controller per la selezione di un cliente
        SearchResultView clientListView = new SearchResultView();
        new ClientSelectionController(mainController, clientListView);

        // Metodo per aggiornare la ListView
        Runnable updateList = () -> {
            List<Client> clients = dbManager.getClientsByNextCall(datePicker.getValue());
            if (clients.isEmpty()) {
                emptyLabel.setVisible(true);
            } else {
                emptyLabel.setVisible(false);
                clientListView.setClients(clients);
            }
        };

        // Primo aggiornamento
        updateList.run();

        // Quando cambia la data nel DatePicker, aggiorna la ListView
        datePicker.setOnAction(e -> updateList.run());

        // Bottone per chiudere la finestra
        Button closeButton = new Button("Chiudi");
        closeButton.setOnAction(e -> stage.close());

        // Layout
        VBox layout = new VBox(10, datePicker, emptyLabel, clientListView, closeButton);
        layout.setPadding(new Insets(15));

        // Scena
        Scene scene = new Scene(layout, 400, 300);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/scheduled-calls.css")).toExternalForm());
        stage.setScene(scene);

        // Quando la finestra viene chiusa, resettiamo il riferimento allo stage
        stage.setOnHidden(e -> stage = null);
        stage.show();
    }
}

