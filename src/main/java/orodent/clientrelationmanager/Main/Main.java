package orodent.clientrelationmanager.Main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import orodent.clientrelationmanager.controller.main.MainController;

import java.io.*;
import java.util.*;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Debugging
        File file = new File("outStream.txt");
        PrintStream stream = new PrintStream(file);
        System.setOut(stream);

        MainController mainController = new MainController();
        Map<String, List<String>> config = mainController.getApp().getConfigs();

        //Lettura file configurazione
        String percorsoFile = "configs/config.txt"; // Cambia percorso
        String sezioneCorrente = null;

        // Set delle sezioni richieste
        Set<String> sezioniRichieste = new HashSet<>(Set.of("operatori femmine", "filtri")); // Aggiungi altre sezioni se vuoi

        try (BufferedReader reader = new BufferedReader(new FileReader(percorsoFile))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                linea = linea.strip();

                if (linea.startsWith("#")) {
                    sezioneCorrente = linea.substring(1);
                    config.putIfAbsent(sezioneCorrente, new ArrayList<>());
                } else if (linea.startsWith("-") && sezioneCorrente != null) {
                    String valore = linea.substring(1).strip();
                    config.get(sezioneCorrente).add(valore);
                }
            }
        } catch (IOException e) {
            System.err.println("Errore nella lettura del file: " + e.getMessage());
            return;
        }

        // Aggiungo alle sezioni richieste le sezioni elencate nella sezione filtri
        sezioniRichieste.addAll(config.get("filtri"));

        // Controllo che tutte le sezioni richieste siano presenti
        List<String> mancanti = new ArrayList<>();
        for (String richiesta : sezioniRichieste) {
            if (!config.containsKey(richiesta)) {
                mancanti.add(richiesta);
            }
        }

        if (!mancanti.isEmpty()) {
            System.err.println("Sezioni mancanti nel file di configurazione: " + mancanti);
        }



        Scene scene = new Scene(mainController.getMainView(), 350, 250);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/clientdetailview.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/hotbar.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/annotation.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/clientinfoview.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/filterview.css")).toExternalForm());

        mainController.setPrimaryStage(primaryStage);
        mainController.showLoginView();
        primaryStage.setTitle("Orodent");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}