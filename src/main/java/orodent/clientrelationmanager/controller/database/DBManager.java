package orodent.clientrelationmanager.controller.database;

import orodent.clientrelationmanager.controller.main.StatusToolTipController;
import orodent.clientrelationmanager.model.Client;

import java.sql.*;

public class DBManager implements DBManagerInterface{
    StatusToolTipController statusToolTipController;
    ConnectionManager connectionManager;

    public DBManager(){
        statusToolTipController = new StatusToolTipController();
        connectionManager = new ConnectionManager();
    }

    public void close(){
        connectionManager.close();
    }

    public void open() {
        connectionManager = new ConnectionManager();
    }

    /**
     * Executes the query with the server side, managing changes of host.
     * The bug occurs when a pc has hosted an embedded connection in the past and if changes from Client to Embedded again happens seamlessly
     *
     * @param query Query to share with database server
     * @return the ResultSet of the given PreparedStatement
     * @throws NoServerFoundException TODO to distinguish when query has failed for a change of host calling for another attempt BUT A BUG MAKES THE TRANSITION SEAMLESS AND LIGHTNING FAST  and the failure for a empty ResultSet
     */
    private ResultSet execute(PreparedStatement query) throws NoServerFoundException{
        ResultSet resultSet = null;
        try {
            resultSet = query.executeQuery();
            resultSet.next();
        } catch (SQLException sqle) {
            //host has stopped hosting
            if (sqle.getSQLState().equals("08006")) {
                statusToolTipController.redLight();
                open();
                throw new NoServerFoundException();
            }
            else {  //to be found what causes this
                System.out.println(sqle.getSQLState());
                printSQLException(sqle);
            }
        }   //Something caused a disconnection. Could be collapsed with catch clause above
        catch (NullPointerException e) {
            statusToolTipController.redLight();
            open();
            throw new NoServerFoundException();
        }
        return resultSet;
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

    public void insertClient(Client selectedClient) {

    }
}
