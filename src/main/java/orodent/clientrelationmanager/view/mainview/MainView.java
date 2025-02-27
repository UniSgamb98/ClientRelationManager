package orodent.clientrelationmanager.view.mainview;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import orodent.clientrelationmanager.controller.main.StatusToolTipController;
import orodent.clientrelationmanager.model.App;

public class MainView extends BorderPane {

    public MainView(App app) {
        this.setBottom(StatusToolTipController.statusToolTipView);

        HotBarView hotBarView = new HotBarView(this, app.getDbManager());
        hotBarView.setAlignment(Pos.CENTER_LEFT);
        this.setLeft(hotBarView);
    }
}
