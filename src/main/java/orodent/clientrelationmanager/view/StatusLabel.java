package orodent.clientrelationmanager.view;

import javafx.scene.control.Label;
import orodent.clientrelationmanager.controller.database.ConnectionInterface;
import orodent.clientrelationmanager.controller.database.ConnectionManager;

import java.util.logging.Logger;
import java.util.logging.StreamHandler;

public class StatusLabel extends Label implements ConnectionInterface {                 //TODO Dividere View da Model
    private static final Logger logger = Logger.getLogger(ConnectionManager.class.getName());

    public StatusLabel() {
        logger.addHandler(new StreamHandler());
    }
    @Override
    public void update(String statusChange) {
        this.setText(statusChange);
        logger.info(statusChange);
    }
}
