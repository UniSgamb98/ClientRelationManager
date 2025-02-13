package orodent.clientrelationmanager.controller.database;

import java.sql.*;

public class DBManager{
    private final Connection connection;


    public DBManager(Connection connection){
        this.connection = connection;
    }

    /**
     * Insert an entry user for the table USER --->FOR DEMO
     * @param userId UId for the entry
     */
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
    }



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
}
