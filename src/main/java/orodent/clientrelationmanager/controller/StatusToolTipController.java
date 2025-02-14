package orodent.clientrelationmanager.controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import orodent.clientrelationmanager.model.database.ConnectionManager;
import orodent.clientrelationmanager.view.StatusToolTipView;

import java.util.logging.Logger;
import java.util.logging.StreamHandler;

public class StatusToolTipController implements EventHandler<MouseEvent> {
    private static StatusToolTipView statusToolTipView;
    private static final Logger logger = Logger.getLogger(ConnectionManager.class.getName());

    public StatusToolTipController() {
        statusToolTipView = new StatusToolTipView();
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
