package orodent.clientrelationmanager.controller.database;

import orodent.clientrelationmanager.controller.main.StatusToolTipController;
import orodent.clientrelationmanager.model.Client;
import orodent.clientrelationmanager.model.enums.Business;
import orodent.clientrelationmanager.model.enums.ClientField;
import orodent.clientrelationmanager.model.enums.Operator;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
     * TODO to distinguish when query has failed for a change of host calling for another attempt BUT A BUG MAKES THE TRANSITION SEAMLESS AND LIGHTNING FAST  and the failure for a empty ResultSet
     */
    private ResultSet execute(PreparedStatement query) /*throws NoServerFoundException*/{
        ResultSet resultSet = null;
        try {
            resultSet = query.executeQuery();
            resultSet.next();
        } catch (SQLException sqle) {
            //host has stopped hosting
            if (sqle.getSQLState().equals("08006")) {
                statusToolTipController.redLight();
                open();
               // throw new NoServerFoundException();
            }
            else {  //to be found what causes this
                if (sqle.getSQLState().equals("X0Y78")) {
                    try {
                        query.execute();        //the preparedStatement was no a query but an insert and to be found
                    } catch (SQLException e) {
                        System.out.println(sqle.getSQLState());
                        printSQLException(sqle);
                    }
                }
            }
        }   //Something caused a disconnection. Could be collapsed with catch clause above
        catch (NullPointerException e) {
            statusToolTipController.redLight();
            open();
            //throw new NoServerFoundException();
        }
        return resultSet;
    }

    //TODO javadoc
    public void test(String search) {
        {
            String sql = "SELECT PAESE FROM CUSTOMERS WHERE RAGIONE_SOCIALE = ?";

            try (PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql)) {
                statement.setString(1, search);

                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        statusToolTipController.update(rs.getString("PAESE"));
                    }
                }
            } catch (SQLException sqle) {
                //host has stopped hosting
                if (sqle.getSQLState().equals("08006")) {
                    statusToolTipController.redLight();
                    open();
                } else {
                    System.out.println(sqle.getSQLState());
                    printSQLException(sqle);
                }
            } catch (NullPointerException e) {
                statusToolTipController.redLight();
                open();
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

    public void insertClient(Client client) {
        String sql = "INSERT INTO CUSTOMERS (ID, RAGIONE_SOCIALE, PERSONA_RIFERIMENTO, EMAIL_RIFERIMENTO, CELLULARE_RIFERIMENTO, TELEFONO_AZIENDALE, EMAIL_AZIENDALE, PAESE, CITTA, NOME_TITOLARE, CELLULARE_TITOLARE, EMAIL_TITOLARE, SITO_WEB, VOLTE_CONTATTATI, ULTIMA_CHIAMATA, PROSSIMA_CHIAMATA, DATA_ACQUISIZIONE, BUSINESS, OPERATORE_ASSEGNATO, INFORMATION, CATALOG, SAMPLE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql))
        {
            statement.setString(1, client.getUuid().toString());
            statement.setString(2, (String) client.get(ClientField.RAGIONE_SOCIALE));
            statement.setString(3, (String) client.get(ClientField.PERSONA_RIFERIMENTO));
            statement.setString(4, (String) client.get(ClientField.EMAIL_RIFERIMENTO));
            statement.setString(5, (String) client.get(ClientField.CELLULARE_RIFERIMENTO));
            statement.setString(6, (String) client.get(ClientField.TELEFONO_AZIENDALE));
            statement.setString(7, (String) client.get(ClientField.EMAIL_AZIENDALE));
            statement.setString(8, (String) client.get(ClientField.PAESE));
            statement.setString(9, (String) client.get(ClientField.CITTA));
            statement.setString(10, (String) client.get(ClientField.NOME_TITOLARE));
            statement.setString(11, (String) client.get(ClientField.CELLULARE_TITOLARE));
            statement.setString(12, (String) client.get(ClientField.EMAIL_TITOLARE));
            statement.setString(13, (String) client.get(ClientField.SITO_WEB));
            statement.setInt(14, client.getField(ClientField.VOLTE_CONTATTATI, Integer.class));

            //Gestione delle date per evitare NULLPOINTEREXCEPTION
            setNullableDate(statement, 15, (LocalDate) client.get(ClientField.ULTIMA_CHIAMATA));
            setNullableDate(statement, 16, (LocalDate) client.get(ClientField.PROSSIMA_CHIAMATA));
            setNullableDate(statement, 17, (LocalDate) client.get(ClientField.DATA_ACQUISIZIONE));

            statement.setString(18, client.getField(ClientField.BUSINESS, Business.class)+"");
            statement.setString(19, client.getField(ClientField.OPERATORE_ASSEGNATO, Operator.class)+"");
            statement.setBoolean(20, client.getField(ClientField.INFORMATION, Boolean.class));
            statement.setBoolean(21, client.getField(ClientField.CATALOG, Boolean.class));
            statement.setBoolean(22, client.getField(ClientField.SAMPLE, Boolean.class));

            statement.executeUpdate();
        } catch (SQLException e){
            printSQLException(e);
        }
    }

    public List<Client> getAllClient(){
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM CUSTOMERS";
        try (PreparedStatement stmt = connectionManager.getConnection().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                clients.add(getClientFromResultSet(rs));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
            return clients;
    }

    /**
     * ID, RAGIONE_SOCIALE, PERSONA_RIFERIMENTO, EMAIL_RIFERIMENTO, CELLULARE_RIFERIMENTO,
     * TELEFONO_AZIENDALE, EMAIL_AZIENDALE, PAESE, CITTA, NOME_TITOLARE, CELLULARE_TITOLARE,
     * EMAIL_TITOLARE, SITO_WEB, VOLTE_CONTATTATI, ULTIMA_CHIAMATA, PROSSIMA_CHIAMATA,
     * DATA_ACQUISIZIONE, BUSINESS, OPERATORE_ASSEGNATO, INFORMATION, CATALOG, SAMPLE
     */
    @Override
    public <T> List<Client> queryCustomerWithSingleParameter(String field, T value) {
        ArrayList<Client> result = new ArrayList<>();
        String sql = "SELECT * FROM CUSTOMERS WHERE " + field + " = ?";
        try (PreparedStatement stmt = connectionManager.getConnection().prepareStatement(sql)) {

            //Controllo di Tipo
            switch (value) {
                case null -> stmt.setNull(1, Types.NULL); // Gestione del valore null
                case String s -> stmt.setString(1, s);
                case Integer i -> stmt.setInt(1, i);
                case Long l -> stmt.setLong(1, l);
                case LocalDate localDate -> stmt.setDate(1, Date.valueOf(localDate));
                case Boolean b -> stmt.setBoolean(1, b);
                case Enum<?> anEnum -> stmt.setString(1, anEnum.name());
                default -> throw new IllegalArgumentException("Tipo di parametro non supportato: " + value.getClass());
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(getClientFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    private void setNullableDate(PreparedStatement preparedStatement, int index, LocalDate date) throws SQLException {
        if (date != null) {
            preparedStatement.setDate(index, java.sql.Date.valueOf(date));
        } else {
            preparedStatement.setNull(index, java.sql.Types.DATE);
        }
    }

    private Client getClientFromResultSet(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setUuid(UUID.fromString(rs.getString("ID")));
        client.set(ClientField.RAGIONE_SOCIALE, rs.getString("RAGIONE_SOCIALE"));
        client.set(ClientField.PERSONA_RIFERIMENTO, rs.getString("PERSONA_RIFERIMENTO"));
        client.set(ClientField.EMAIL_RIFERIMENTO, rs.getString("EMAIL_RIFERIMENTO"));
        client.set(ClientField.CELLULARE_RIFERIMENTO, rs.getString("CELLULARE_RIFERIMENTO"));
        client.set(ClientField.TELEFONO_AZIENDALE, rs.getString("TELEFONO_AZIENDALE"));
        client.set(ClientField.EMAIL_AZIENDALE, rs.getString("EMAIL_AZIENDALE"));
        client.set(ClientField.PAESE, rs.getString("PAESE"));
        client.set(ClientField.CITTA, rs.getString("CITTA"));
        client.set(ClientField.NOME_TITOLARE, rs.getString("NOME_TITOLARE"));
        client.set(ClientField.CELLULARE_TITOLARE, rs.getString("CELLULARE_TITOLARE"));
        client.set(ClientField.EMAIL_TITOLARE, rs.getString("EMAIL_TITOLARE"));
        client.set(ClientField.SITO_WEB, rs.getString("SITO_WEB"));
        client.set(ClientField.VOLTE_CONTATTATI, rs.getInt("VOLTE_CONTATTATI"));

        // Gestione delle date (evita NullPointerException)
        client.set(ClientField.ULTIMA_CHIAMATA, rs.getDate("ULTIMA_CHIAMATA") != null ? rs.getDate("ULTIMA_CHIAMATA").toLocalDate() : null);
        client.set(ClientField.PROSSIMA_CHIAMATA, rs.getDate("PROSSIMA_CHIAMATA") != null ? rs.getDate("PROSSIMA_CHIAMATA").toLocalDate() : null);
        client.set(ClientField.DATA_ACQUISIZIONE, rs.getDate("DATA_ACQUISIZIONE") != null ? rs.getDate("DATA_ACQUISIZIONE").toLocalDate() : null);

        // Business e Operatore possono essere null, quindi gestiamo il caso
        client.set(ClientField.BUSINESS, rs.getString("BUSINESS") != null ? Business.valueOf(rs.getString("BUSINESS")) : null);
        client.set(ClientField.OPERATORE_ASSEGNATO, rs.getString("OPERATORE_ASSEGNATO") != null ? Operator.valueOf(rs.getString("OPERATORE_ASSEGNATO")) : null);

        client.set(ClientField.INFORMATION, rs.getBoolean("INFORMATION"));
        client.set(ClientField.CATALOG, rs.getBoolean("CATALOG"));
        client.set(ClientField.SAMPLE, rs.getBoolean("SAMPLE"));

        return client;
    }
}
