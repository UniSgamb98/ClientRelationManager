package orodent.clientrelationmanager.controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import orodent.clientrelationmanager.controller.database.ConnectionManager;
import orodent.clientrelationmanager.view.StatusToolTipView;

import java.util.logging.Logger;
import java.util.logging.StreamHandler;

public class StatusToolTipController implements EventHandler<MouseEvent> {
    private static final StatusToolTipView statusToolTipView = new StatusToolTipView();
    private final Logger logger;

    public StatusToolTipController() {
        logger = Logger.getLogger(ConnectionManager.class.getName());
        logger.addHandler(new StreamHandler());
    }

    public StatusToolTipView getView() {
        return statusToolTipView;
    }

    public void update(String msg){
        statusToolTipView.updateStatusLabel(msg);
        logger.info(msg);
    }

    public void greenLight(){
        statusToolTipView.switchColor(Color.GREEN);
    }

    public void redLight(){
        statusToolTipView.switchColor(Color.RED);
    }

    @Override
    public void handle(MouseEvent event) {

    }
}
