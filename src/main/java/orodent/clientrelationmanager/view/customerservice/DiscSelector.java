package orodent.clientrelationmanager.view.customerservice;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

//TODO NUOVO DA GPT
public class DiscSelector extends VBox {
    public final ObservableList<DiscView> discs;
    public DiscSelector() {
        discs = FXCollections.observableArrayList();
        setSpacing(5);
        setPadding(new Insets(10));
    }

    // Aggiunge una riga solo quando non ci sono altri disc incompleti e solo quando ne viene completato uno
    public void aggiungiRiga() {
        DiscView nuovaRiga = new DiscView();
        nuovaRiga.completed.addListener((obs, oldVal, newVal) -> {
            boolean needNewDisc = true;
            for (DiscView i : discs){
                if (!i.completed.get())  needNewDisc = false;
            }
            if (newVal && needNewDisc) {
                discs.add(nuovaRiga);
                aggiungiRiga();
            }
        });
        nuovaRiga.toBeRemoved.addListener((obs, oldVal, newVal) -> {
            int nonCompleted = 0;
            for (DiscView i : discs){
                if (!i.completed.get()) nonCompleted++;
            }
            if (nonCompleted > 1 || nuovaRiga.completed.get()) {
                getChildren().remove(nuovaRiga);
                discs.remove(nuovaRiga);
            }
        });
        getChildren().add(nuovaRiga);
    }
}
