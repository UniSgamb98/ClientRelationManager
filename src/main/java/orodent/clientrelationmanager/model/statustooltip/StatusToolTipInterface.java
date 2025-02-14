package orodent.clientrelationmanager.model.statustooltip;

import javafx.scene.paint.Color;

public interface StatusToolTipInterface {
    void update (String statusChange);
    void switchColor (Color color);
    boolean isConnected();
}
