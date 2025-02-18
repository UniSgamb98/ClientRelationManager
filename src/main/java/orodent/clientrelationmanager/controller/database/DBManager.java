package orodent.clientrelationmanager.controller.database;

import orodent.clientrelationmanager.controller.StatusToolTipController;

import java.sql.*;

public class DBManager{
    StatusToolTipController statusToolTipController;
    ConnectionManager connectionManager;

    public DBManager(){
        statusToolTipController = new StatusToolTipController();
        connectionManager = new ConnectionManager();
    }

    /*
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

    public void close(){
        connectionManager.close();
    }

    public void open() {
        connectionManager = new ConnectionManager();
    }

    //TODO javadoc
    public void test(String search) {
        {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                stmt = connectionManager.getConnection().prepareStatement(
                        "SELECT * FROM PRODUCTS WHERE CODE = ?");
                stmt.setString(1, search);
                rs = stmt.executeQuery();
                rs.next();
                statusToolTipController.update(rs.getString(2));
            }
            catch(SQLException sqle)
            {
                //host has stopped hosting
                if (sqle.getSQLState().equals("08006")) {
                    statusToolTipController.redLight();
                    open();
                }
                else {
                    System.out.println(sqle.getSQLState());
                    printSQLException(sqle);
                }
            }   //Something caused a disconnection. Could be collapsed with catch clause above
            catch (NullPointerException e) {
                statusToolTipController.redLight();
                open();
            }
            finally
            {
                try {
                    if (rs != null && !rs.isClosed())
                        rs.close();
                    if (stmt != null && !stmt.isClosed())
                        stmt.close();
                } catch (SQLException ignored) {}
                System.out.println("done");
            }
        }
    }

    /**
     * Prints details of an SQLException chain to <code>System.err</code>.
     * Details included are SQL State, Error code, Exception message.
     *
     * @param e the SQLException from which to print details.
     */
    private static void printSQLException(SQLException e) {
        // Unwraps the entire exception chain to unveil the real cause of the
        // Exception.
        while (e != null)
        {
            System.err.println("\n----- SQLException -----");
            System.err.println("  SQL State:  " + e.getSQLState());
            System.err.println("  Error Code: " + e.getErrorCode());
            System.err.println("  Message:    " + e.getMessage());
            e = e.getNextException();
        }
    }
}
