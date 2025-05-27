package orodent.clientrelationmanager.view.customerservice;

import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import orodent.clientrelationmanager.controller.main.MainController;
import orodent.clientrelationmanager.model.Client;
import orodent.clientrelationmanager.model.Disc;
import orodent.clientrelationmanager.model.enums.ClientField;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomerServiceView extends BorderPane {
    private final ScrollPane scrollPane;
    private final Client client;
    private final ArrayList<TextField> rivenditoreInfo;
    private final ArrayList<TextField> laboratorioInfo;
    private int praticaNumber;

    public CustomerServiceView(){
        this(null);
    }

    public CustomerServiceView(Client client){
        scrollPane = new ScrollPane();
        this.client = client;
        rivenditoreInfo = new ArrayList<>();
        laboratorioInfo = new ArrayList<>();
        //TODO praticaNumber = dbInterface getNextPratica Number (il piu alto +1)
        setUpView();
    }

    private void setUpView() {
        VBox mainLayout = new VBox(10);
        scrollPane.setContent(mainLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setMinWidth(300);
        mainLayout.setPadding(new Insets(15));

        // HEADER: Data e pratica
        Label dateLabel = new Label("DATA");
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        datePicker.setPromptText("Seleziona una data");
        dateLabel.getStyleClass().add("topbar-label");
        datePicker.getStyleClass().add("topbar-date-picker");

        Label praticaLabel = new Label("PRATICA NR. " + praticaNumber);
        praticaLabel.getStyleClass().add("topbar-label");

        HBox dateBar = new HBox(10, dateLabel, datePicker);
        HBox headerBox = new HBox(50, dateBar, praticaLabel);
        headerBox.setPadding(new Insets(5, 0, 10, 0));

        // CLIENTI: Griglia
        GridPane clientGrid = new GridPane();
        clientGrid.getStyleClass().add("grid-pane");

        String[] rows = { "NOME AZIENDA", "RIFERIMENTO", "TELEFONO", "E-MAIL" };

        for (int i = 0; i < rows.length; i++) {
            Label label = new Label(rows[i]);
            label.getStyleClass().add("client-label");

            TextField leftField = new TextField();
            rivenditoreInfo.add(leftField);
            leftField.getStyleClass().add("client-cell");
            TextField rightField = new TextField();
            laboratorioInfo.add(rightField);
            rightField.getStyleClass().add("client-cell");

            clientGrid.add(label, 0, i + 1);
            clientGrid.add(leftField, 1, i + 1);
            clientGrid.add(rightField, 2, i + 1);
        }

        Label leftTitle = new Label("INFORMAZIONI CLIENTE\nRIVENDITORE");
        Label rightTitle = new Label("INFORMAZIONI CLIENTE\nLABORATORIO");
        leftTitle.getStyleClass().add("section-title");
        rightTitle.getStyleClass().add("section-title");

        clientGrid.add(new Label(""), 0, 0);
        clientGrid.add(leftTitle, 1, 0);
        clientGrid.add(rightTitle, 2, 0);

        // DESCRIZIONE PROBLEMA
        Label descLabel = new Label("Descrizione del problema:");
        descLabel.setStyle("-fx-font-style: italic;");
        TextArea descArea = new TextArea();
        descArea.setPrefRowCount(4);

        // INFO DISCO
        Label discoLabel = new Label("Indicare: tipologia di disco / colore / numero lotto");
        discoLabel.setStyle("-fx-font-style: italic;");
        DiscSelector discoArea = new DiscSelector();
        FlowPane flowPhotoBox = new FlowPane();
        //Aggiungo PhotoFlow in base ai dischi di DiscSelector
        discoArea.discs.addListener((ListChangeListener<DiscView>) change ->{
            List<Disc> discList = discoArea.getModelliCompilati();
            int p = 0;
            while(change.next()){
                if (change.wasAdded()){
                    flowPhotoBox.getChildren().add(new FlowPhoto(praticaNumber, discList.get(p).getId(), discList.get(p).toString()));
                }
                if (change.wasRemoved()){

                }
                p++;
            }
        } );

        // PROGRAMMA SINTERIZZAZIONE
        Label sinteringLabel = new Label("Indicare il programma di sinterizzazione con cui è stato sinterizzato.");
        sinteringLabel.setStyle("-fx-font-style: italic;");
        TextArea sinteringArea = new TextArea();
        sinteringArea.setPrefRowCount(2);

        // FOTO ALLEGATE
        Label note1 = new Label("Allegare foto del lavoro, posizionamento nel ");
        Text cadCam = new Text("cad/cam");
        cadCam.getStyleClass().add("highlight");
        Text note2 = new Text(", nel caso di problemi colore la foto deve essere fatta accanto alla scala vita di riferimento.");
        TextFlow noteFlow = new TextFlow(note1, cadCam, note2);
        noteFlow.getStyleClass().add("note");

        // Precompilazione
        if (client.get((ClientField.BUSINESS)) != null && client.get(ClientField.BUSINESS).equals("Rivenditore")){
            for (int i = 0; i < rivenditoreInfo.size(); i++){
                switch (i){
                    case 0 -> rivenditoreInfo.get(i).setText((String) client.get(ClientField.RAGIONE_SOCIALE));
                    case 1 -> rivenditoreInfo.get(i).setText((String) client.get(ClientField.PERSONA_RIFERIMENTO));
                    case 2 -> rivenditoreInfo.get(i).setText((String) client.get(ClientField.CELLULARE_RIFERIMENTO));
                    case 3 -> rivenditoreInfo.get(i).setText((String) client.get(ClientField.EMAIL_RIFERIMENTO));
                }
            }
        } else {
            for (int i = 0; i < laboratorioInfo.size(); i++){
                switch (i){
                    case 0 -> laboratorioInfo.get(i).setText((String) client.get(ClientField.RAGIONE_SOCIALE));
                    case 1 -> laboratorioInfo.get(i).setText((String) client.get(ClientField.PERSONA_RIFERIMENTO));
                    case 2 -> laboratorioInfo.get(i).setText((String) client.get(ClientField.CELLULARE_RIFERIMENTO));
                    case 3 -> laboratorioInfo.get(i).setText((String) client.get(ClientField.EMAIL_RIFERIMENTO));
                }
            }
        }

        //FOOTER
        Button backButton = new Button("INDIETRO");
        backButton.setOnAction(e -> onBack());
        Button saveButton = new Button("SALVA RECLAMO");
        saveButton.setOnAction(e -> onSave());

        BorderPane bottom = new BorderPane();
        bottom.setRight(saveButton);
        bottom.setLeft(backButton);
        this.setBottom(bottom);
        bottom.getStyleClass().add("footer");


        // Layout
        mainLayout.getChildren().addAll(
                headerBox,
                clientGrid,
                descLabel, descArea,
                discoLabel, discoArea,
                sinteringLabel, sinteringArea,
                noteFlow, flowPhotoBox
        );

        this.setCenter(scrollPane);
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/customer-service.css")).toExternalForm());
    }

    public void onBack(){
        new MainController().showClientPage(client);
    }

    public void onSave(){

    }
}
