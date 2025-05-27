package orodent.clientrelationmanager.controller.main.buttons;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrintConfigController implements EventHandler<ActionEvent> {


    @Override
    public void handle(ActionEvent event) {
        String percorso = "config_sample.txt";
        Map<String, List<String>> configurazione = new HashMap<>(Map.of(
                "database home", List.of("I:/CliZr/Tommaso"),
                "indirizzi ip", List.of("192.168.1.138", "192.168.1.136", "192.168.1.19", "192.168.1.139"),
                "OPERATORE_ASSEGNATO", List.of("Victoria", "Teresa", "Gaetano", "Tommaso", "Hugo", "Santolo"),
                "operatori femmine", List.of("Victoria"),
                "admin", List.of("Tommaso"),
                "filtri", List.of("OPERATORE_ASSEGNATO", "PAESE", "BUSINESS"),
                "PAESE", List.of("Albania", "Andorra", "Armenia", "Austria", "Azerbaigian", "Belgio", "Bielorussia", "Bosnia ed Erzegovina", "Bulgaria", "Cipro", "Croazia", "Danimarca", "Estonia", "Finlandia", "Francia", "Georgia", "Germania", "Grecia", "Irlanda", "Islanda", "Italia", "Kosovo", "Lettonia", "Liechtenstein", "Lituania", "Lussemburgo", "Malta", "Moldavia", "Monaco", "Montenegro", "Norvegia", "Paesi Bassi", "Polonia", "Portogallo", "Regno Unito", "Repubblica Ceca", "Romania", "Russia", "San Marino", "Serbia", "Slovacchia", "Slovenia", "Spagna", "Svezia", "Svizzera", "Turchia", "Ucraina", "Ungheria", "Vaticano", "Vietnam"),
                "BUSINESS", List.of("Rivenditore", "Laboratorio", "Centro Fresaggio", "Sconosciuto"),
                "DIAMETRI", List.of("98", "95"),
                "MISURE", List.of("10", "12", "14", "16", "18", "20", "22", "25")
        ));
        configurazione.put("COLORI", List.of ("a1", "a2", "a3", "a35", "a4", "b1", "b2", "b3", "b4", "c1", "c2", "c3", "d2", "d3"));
        configurazione.put("TIPOLOGIE", List.of ("Thor", "Eos", "Venus", "Preshaded", "Bleach", "High Translucent", "White Matt", "gold"));


        // Commenti esplicativi sopra ogni sezione
        String ip = "[Sconosciuto, qualcosa è andato storto]";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ignored) {}
        Map<String, String> commenti = Map.of(
                "database home", "L'indirizzo della cartella che ospita il Database",
                "indirizzi ip", "Gli indirizzi ip delle macchine che lavorano con il CRM. Se hai creato questo file da questa macchina il tuo indirizzo è " + ip,
                "OPERATORE_ASSEGNATO", "Elenco degli operatori abilitati nel sistema.",
                "operatori femmine", "Serve per dare il benvenuto correttamente",
                "admin", "Gli admin vedono funzionalità extra. ATTENZIONE il nome deve corrispondere ad un valore presente nella sezione OPERATORE_ASSEGNATO",
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
