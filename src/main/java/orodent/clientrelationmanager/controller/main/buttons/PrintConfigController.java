package orodent.clientrelationmanager.controller.main.buttons;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PrintConfigController implements EventHandler<ActionEvent> {


    @Override
    public void handle(ActionEvent event) {
        String percorso = "config_sample.txt";
        Map<String, List<String>> configurazione = Map.of(
                "operatori", List.of("Victoria", "Teresa", "Gaetano", "Tommaso", "Hugo", "Santolo"),
                "operatori femmine", List.of("Victoria"),
                "filtri", List.of("OPERATORE_ASSEGNATO", "PAESE", "BUSINESS"),
                "paesi", List.of("Albania","Andorra","Armenia","Austria","Azerbaigian","Belgio","Bielorussia","Bosnia ed Erzegovina","Bulgaria","Cipro","Croazia","Danimarca","Estonia","Finlandia","Francia","Georgia","Germania","Grecia","Irlanda","Islanda","Italia","Kosovo","Lettonia","Liechtenstein","Lituania","Lussemburgo","Malta","Moldavia","Monaco","Montenegro","Norvegia","Paesi Bassi","Polonia","Portogallo","Regno Unito","Repubblica Ceca","Romania","Russia","San Marino","Serbia","Slovacchia","Slovenia","Spagna","Svezia","Svizzera","Turchia","Ucraina","Ungheria","Vaticano","Vietnam"),
                "businesses", List.of("Rivenditore", "Laboratorio", "Centro Fresaggio", "Sconosciuto")
        );
        // Commenti esplicativi sopra ogni sezione
        Map<String, String> commenti = Map.of(
                "OPERATORE_ASSEGNATO", "Elenco degli operatori abilitati nel sistema.",
                "operatori femmine", "Serve per dare il benvenuto correttamente",
                "filtri", "Filtri disponibili per la ricerca dei clienti nel database. ATTENZIONE: Ogni voce presente in filtro DEVE avere la sua #sezione e questa deve essere nominato come la colonna del database. Filtri possibili: ID, RAGIONE_SOCIALE, PERSONA_RIFERIMENTO, EMAIL_RIFERIMENTO, CELLULARE_RIFERIMENTO, TELEFONO_AZIENDALE, EMAIL_AZIENDALE, PAESE, CITTA, NOME_TITOLARE, CELLULARE_TITOLARE, EMAIL_TITOLARE, SITO_WEB, VOLTE_CONTATTATI, ULTIMA_CHIAMATA, PROSSIMA_CHIAMATA, DATA_ACQUISIZIONE, BUSINESS, OPERATORE_ASSEGNATO, INFORMATION, CATALOG, SAMPLE, PVU",
                "PAESE", "Paesi disponibili",
                "BUSINESS", "Lista della tipologia dei clienti. ATTENZIONE: aggiungere un valore con nome \"Sconosciuto\""
        );

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(percorso))) {
            for (Map.Entry<String, List<String>> entry : configurazione.entrySet()) {
                String sezione = entry.getKey();
                List<String> valori = entry.getValue();

                // Scrivi il commento se esiste
                if (commenti.containsKey(sezione)) {
                    writer.write("// " + commenti.get(sezione));
                    writer.newLine();
                }

                // Scrivi il titolo della sezione
                writer.write("#" + sezione);
                writer.newLine();

                // Scrivi i valori
                for (String valore : valori) {
                    writer.write("- " + valore);
                    writer.newLine();
                }

                writer.newLine(); // Riga vuota tra le sezioni
            }

            System.out.println("File di configurazione fac-simile scritto con successo in: " + percorso);
        } catch (IOException e) {
            System.err.println("Errore durante la scrittura del file: " + e.getMessage());
        }
    }
}
