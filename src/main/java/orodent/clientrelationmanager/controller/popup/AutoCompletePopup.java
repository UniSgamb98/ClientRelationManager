package orodent.clientrelationmanager.controller.popup;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Window;

public class AutoCompletePopup {
    private final TextField textField;
    private final Popup popup;
    private final ListView<String> listView;
    private final FilteredList<String> filteredList;

    public AutoCompletePopup(TextField textField, ObservableList<String> items) {
        this.textField = textField;
        this.popup = new Popup();
        this.filteredList = new FilteredList<>(items, p -> true);
        this.listView = new ListView<>(filteredList);
        listView.setMaxHeight(150); // Altezza massima

        popup.getContent().add(listView);

        setupListeners();
    }

    private void setupListeners() {
        // Mostra il popup quando si scrive
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                popup.hide();
            } else {
                filteredList.setPredicate(item -> item.toLowerCase().startsWith(newValue.toLowerCase()));
                if (!filteredList.isEmpty()) {
                    showPopup();
                } else {
                    popup.hide();
                }
            }
        });

        // Selezione con il mouse
        listView.setOnMouseClicked(event -> selectItem());

        // Navigazione con tastiera
        textField.setOnKeyPressed(event -> {
            if (popup.isShowing()) {
                if (event.getCode() == KeyCode.DOWN) {
                    listView.requestFocus();
                    listView.getSelectionModel().selectFirst();
                } else if (event.getCode() == KeyCode.ESCAPE) {
                    popup.hide();
                }
            }
        });

        listView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                selectItem();
            }
        });

        // Chiude il popup quando si clicca fuori
        textField.getScene().addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (!textField.getBoundsInParent().contains(event.getX(), event.getY()) &&
                    !listView.getBoundsInParent().contains(event.getX(), event.getY())) {
                popup.hide();
            }
        });
    }

    private void showPopup() {
        Window window = textField.getScene().getWindow();
        popup.show(window, textField.localToScreen(0, textField.getHeight()).getX(),
                textField.localToScreen(0, textField.getHeight()).getY());
    }

    private void selectItem() {
        textField.setText(listView.getSelectionModel().getSelectedItem());
        popup.hide();
    }
}
