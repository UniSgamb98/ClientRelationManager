package orodent.clientrelationmanager.Main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import orodent.clientrelationmanager.model.App;
import orodent.clientrelationmanager.view.mainview.MainView;

import java.io.*;
import java.util.*;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Debugging
        File file = new File("outStream.txt");
        PrintStream stream = new PrintStream(file);
        System.setOut(stream);

        App app = new App();
        app.setPrimaryStage(primaryStage);

        //Lettura file configurazione
        String percorsoFile = "configs/config.txt"; // Cambia percorso
        App.configs = new HashMap<>();
        String sezioneCorrente = null;

        // Set delle sezioni richieste
        Set<String> sezioniRichieste = Set.of("operatori", "operatori femmine", "paesi", "filtri"); // Aggiungi altre sezioni se vuoi

        try (BufferedReader reader = new BufferedReader(new FileReader(percorsoFile))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                linea = linea.strip();

                if (linea.startsWith("#")) {
                    sezioneCorrente = linea.substring(1).toLowerCase();
                    App.configs.putIfAbsent(sezioneCorrente, new ArrayList<>());
                } else if (linea.startsWith("-") && sezioneCorrente != null) {
                    String valore = linea.substring(1).strip();
                    App.configs.get(sezioneCorrente).add(valore);
                }
            }
        } catch (IOException e) {
            System.err.println("Errore nella lettura del file: " + e.getMessage());
            return;
        }

        // Controllo che tutte le sezioni richieste siano presenti
        List<String> mancanti = new ArrayList<>();
        for (String richiesta : sezioniRichieste) {
            if (!App.configs.containsKey(richiesta)) {
                mancanti.add(richiesta);
            }
        }

        if (!mancanti.isEmpty()) {
            System.err.println("Sezioni mancanti nel file di configurazione: " + mancanti);
        }

        // Stampa solo le configurazioni richieste (le altre vengono ignorate)
        for (String sezione : sezioniRichieste) {
            List<String> valori = App.configs.getOrDefault(sezione, Collections.emptyList());
            System.out.println("[" + sezione + "] -> " + valori);
        }

        MainView mainView = new MainView(app);
        Scene scene = new Scene(mainView, 350, 250);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/clientdetailview.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/hotbar.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/annotation.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/clientinfoview.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/filterview.css")).toExternalForm());

        primaryStage.setTitle("Orodent");
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}