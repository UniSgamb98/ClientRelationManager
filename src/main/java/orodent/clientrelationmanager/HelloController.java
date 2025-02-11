package orodent.clientrelationmanager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.derby.drda.NetworkServerControl;
import orodent.clientrelationmanager.database.ConnectionManager;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class HelloController {
    Connection conn;
     @FXML
     private TextField searchField;
     @FXML
     private Label resultField;
     @FXML
     private Label status;

    @FXML
    protected void onStopServer() {
        try {
            // shut down Derby Network Server
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException se) {
            //ignore se
        }
    }

    @FXML
    protected void onHelloButtonClick() throws Exception {
        conn = ConnectionManager.getConnection(searchField.getText(), this);

    }

    @FXML
    protected void onPingButtonClick() {
        try {
            ConnectionManager.pingServer(searchField.getText(), this);
        } catch (Exception e){
            System.out.println("Server not pinged");
            log("Server not pinged");
        }
    }

    @FXML
    public void onEmbeddedTest(){
        try {
            test(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            resultField.setText(rs.getString(2));


        }
        catch(SQLException sqle)
        {
            System.out.println("errore");
            System.out.println("SQLException when querying on the database connection; "+ sqle);
            log("SQLException when querying on the database connection; "+ sqle);
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

    public void log(String msg) {
        status.setText(msg);
    }

    public static void printSQLException(SQLException e)
    {
        // Unwraps the entire exception chain to unveil the real cause of the
        // Exception.
        while (e != null)
        {
            System.err.println("\n----- SQLException -----");
            System.err.println("  SQL State:  " + e.getSQLState());
            System.err.println("  Error Code: " + e.getErrorCode());
            System.err.println("  Message:    " + e.getMessage());
            // for stack traces, refer to derby.log or uncomment this:
            //e.printStackTrace(System.err);
            e = e.getNextException();
        }
    }
}