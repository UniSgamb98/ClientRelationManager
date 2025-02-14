package orodent.clientrelationmanager.view;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import orodent.clientrelationmanager.model.database.ConnectionManager;

import java.sql.*;

public class MainView extends BorderPane {
    private StatusLabel statusLabel;
    private HBox hBox;
    private Connection connection;
    private TextField searchField;

    public MainView() {
        this.statusLabel = new StatusLabel();
        this.setBottom(statusLabel);

        searchField = new TextField();
        Button connectButton = new Button("Connect");
        Button disconnectButton = new Button("Disconnect");
        Button testButton = new Button("Test");


        connectButton.setOnAction(event -> {
            ConnectionManager connectionManager = new ConnectionManager();
            connection = connectionManager.getConnection(statusLabel);
        });
        disconnectButton.setOnAction(event -> {
            try {
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        testButton.setOnAction(event -> {
            try {
                test(connection);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        hBox = new HBox();
        this.setCenter(hBox);
        hBox.getChildren().addAll(connectButton, disconnectButton, searchField, testButton);
    }

    public void test(Connection conn) throws Exception
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String search = searchField.getText();
            // To test our connection, we will try to do a select from the system catalog tables
            stmt = conn.prepareStatement(
                    "SELECT * FROM PRODUCTS WHERE CODE = ?");
            stmt.setString(1, search);
            rs = stmt.executeQuery();
            rs.next();
            System.out.println("number of rows in sys.systables = " + rs.getString(2));
            statusLabel.setText(rs.getString(2));
        }
        catch(SQLException sqle)
        {
            System.out.println("SQLException when querying on the database connection; "+ sqle);
            throw sqle;
        }
        finally
        {
            System.out.println("done");
            if(rs != null)
                rs.close();
            if(stmt != null)
                stmt.close();
        }

    }
}
