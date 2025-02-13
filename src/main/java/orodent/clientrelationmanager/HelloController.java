package orodent.clientrelationmanager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import orodent.clientrelationmanager.controller.database.ConnectionManager;
import orodent.clientrelationmanager.view.StatusLabel;

import java.sql.*;

public class HelloController {
    Connection conn;
     @FXML
     private TextField searchField;
     @FXML
     private Label resultField;
     @FXML
     public Label status;



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
    protected void onHelloButtonClick() {
        conn = ConnectionManager.getConnection(new StatusLabel());
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

    public void updateStatus(String msg) {
        status.setText(msg);
    }


}