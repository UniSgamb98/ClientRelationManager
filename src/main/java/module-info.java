module orodent.clientrelationmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.derby.server;
    requires org.apache.derby.tools;


    opens orodent.clientrelationmanager to javafx.fxml;
    exports orodent.clientrelationmanager.Main;
    opens orodent.clientrelationmanager.Main to javafx.fxml;
}