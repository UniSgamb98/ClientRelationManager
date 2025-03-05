package orodent.clientrelationmanager.controller.main;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import orodent.clientrelationmanager.controller.database.ConnectionManager;
import orodent.clientrelationmanager.view.mainview.StatusToolTipView;

import java.util.logging.Logger;
import java.util.logging.StreamHandler;

public class StatusToolTipController {
    public static final StatusToolTipView statusToolTipView = new StatusToolTipView();
    private final Logger logger;

    public StatusToolTipController() {
        logger = Logger.getLogger(ConnectionManager.class.getName());
        logger.addHandler(new StreamHandler());
    }

    public StatusToolTipView getView() {
        return statusToolTipView;
    }

    public void update(String msg){
        Platform.runLater(() -> {
            statusToolTipView.updateStatusLabel(msg);
            statusToolTipView.startFlashingEffect();
        });
        logger.info(msg);
    }

    public void greenLight(){
        Platform.runLater(() -> statusToolTipView.switchColor(Color.GREEN));
    }

    public void redLight(){
        Platform.runLater(() -> statusToolTipView.switchColor(Color.RED));

    }
}
