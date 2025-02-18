package orodent.clientrelationmanager.controller.database;

import orodent.clientrelationmanager.controller.StatusToolTipController;
import orodent.clientrelationmanager.model.App;

import java.sql.*;

public class DBManager{
    StatusToolTipController statusToolTipController;


    public DBManager(){
        statusToolTipController = new StatusToolTipController();
    }

    /**
     * Insert an entry user for the table USER --->FOR DEMO
     * @param userId UId for the entry

    public void insertUser(long userId){
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO USERSESSIONS (ID) VALUES (?)"
            );
            statement.setLong(1, userId);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }*/



    /**
     * Prints details of an SQLException chain to <code>System.err</code>.
     * Details included are SQL State, Error code, Exception message.
     *
     * @param e the SQLException from which to print details.
     */
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
/*
    public void test() {
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
                statusToolTipController.update(rs.getString(2));
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

    }*/
}
