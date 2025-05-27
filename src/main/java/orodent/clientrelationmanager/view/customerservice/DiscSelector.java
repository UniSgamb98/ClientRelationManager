package orodent.clientrelationmanager.view.customerservice;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import orodent.clientrelationmanager.model.Disc;

import java.util.ArrayList;
import java.util.List;

//TODO NUOVO DA GPT
public class DiscSelector extends VBox {
    public final ObservableList<DiscView> discs;
    public DiscSelector() {
        discs = FXCollections.observableArrayList();
        setSpacing(5);
        setPadding(new Insets(10));
        aggiungiRiga();
    }

    // Aggiunge una riga solo quando non ci sono altri disc incompleti e solo quando ne viene completato uno
    private void aggiungiRiga() {
        DiscView nuovaRiga = new DiscView(this::rimuoviRiga);
        nuovaRiga.completed.addListener((obs, oldVal, newVal) -> {
            boolean needNewDisc = true;
            for (DiscView i : discs){
                if (!i.completed.get())  needNewDisc = false;
            }
            if (newVal && needNewDisc) aggiungiRiga();
        });
        discs.add(nuovaRiga);
        getChildren().add(nuovaRiga);
    }

    private void rimuoviRiga(DiscView riga) {
        int nonCompleted = 0;
        for (DiscView i : discs){
            if (!i.completed.get()) nonCompleted++;
        }
        if (nonCompleted > 1 || riga.completed.get()) {
            getChildren().remove(riga);
            discs.remove(riga);
        }
    }

    public List<Disc> getModelliCompilati() {
        List<Disc> lista = new ArrayList<>();
        for (DiscView i : discs) {
            if (i.completed.get())
                lista.add(i.toModello());
        }
        return lista;
    }
}
