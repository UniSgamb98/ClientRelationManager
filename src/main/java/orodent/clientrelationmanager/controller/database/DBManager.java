package orodent.clientrelationmanager.controller.database;

import orodent.clientrelationmanager.controller.main.StatusToolTipController;
import orodent.clientrelationmanager.model.Annotation;
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
                clients.add(mapResultSetToClient(rs));
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
    public <T> List<Client> queryCustomerWithSingleParameter(String databaseField, T value) {
        ArrayList<Client> result = new ArrayList<>();
        String sql = "SELECT * FROM CUSTOMERS WHERE " + databaseField + " = ?";
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
                    result.add(mapResultSetToClient(rs));
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }


    /**
     * Restituisce una List di client i quali la string s è un sotto stringa di Ragione sociale oppure Persona di riferimento
     * @param s sotto stringa
     * @return list
     */
    @Override
    public List<Client> searchClient(String s) {
        List<Client> ret = new ArrayList<>();
        String sql = "SELECT * FROM CUSTOMERS WHERE RAGIONE_SOCIALE LIKE ? OR PERSONA_RIFERIMENTO LIKE ?";
        try (PreparedStatement stmt = connectionManager.getConnection().prepareStatement(sql)) {
            String pattern = "%" + s + "%";
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                ret.add(mapResultSetToClient(rs));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return ret;
    }

    @Override
    public void saveClientChanges(Client client) {
        String sql = "UPDATE CUSTOMERS SET " +
                "RAGIONE_SOCIALE = ?, PERSONA_RIFERIMENTO = ?, EMAIL_RIFERIMENTO = ?, " +
                "CELLULARE_RIFERIMENTO = ?, TELEFONO_AZIENDALE = ?, EMAIL_AZIENDALE = ?, " +
                "PAESE = ?, CITTA = ?, NOME_TITOLARE = ?, CELLULARE_TITOLARE = ?, " +
                "EMAIL_TITOLARE = ?, SITO_WEB = ?, VOLTE_CONTATTATI = ?, " +
                "ULTIMA_CHIAMATA = ?, PROSSIMA_CHIAMATA = ?, DATA_ACQUISIZIONE = ?, " +
                "BUSINESS = ?, OPERATORE_ASSEGNATO = ?, INFORMATION = ?, " +
                "CATALOG = ?, SAMPLE = ?, PVU = ? " +
                "WHERE ID = ?";

        try (PreparedStatement stmt = connectionManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, (String) client.get(ClientField.RAGIONE_SOCIALE));
            stmt.setString(2, (String) client.get(ClientField.PERSONA_RIFERIMENTO));
            stmt.setString(3, (String) client.get(ClientField.EMAIL_RIFERIMENTO));
            stmt.setString(4, (String) client.get(ClientField.CELLULARE_RIFERIMENTO));
            stmt.setString(5, (String) client.get(ClientField.TELEFONO_AZIENDALE));
            stmt.setString(6, (String) client.get(ClientField.EMAIL_AZIENDALE));
            stmt.setString(7, (String) client.get(ClientField.PAESE));
            stmt.setString(8, (String) client.get(ClientField.CITTA));
            stmt.setString(9, (String) client.get(ClientField.NOME_TITOLARE));
            stmt.setString(10, (String) client.get(ClientField.CELLULARE_TITOLARE));
            stmt.setString(11, (String) client.get(ClientField.EMAIL_TITOLARE));
            stmt.setString(12, (String) client.get(ClientField.SITO_WEB));
            stmt.setInt(13, client.getField(ClientField.VOLTE_CONTATTATI, Integer.class));

            LocalDate ultimaChiamata = client.getField(ClientField.ULTIMA_CHIAMATA, LocalDate.class);
            stmt.setDate(14, ultimaChiamata != null ? Date.valueOf(ultimaChiamata) : null);

            LocalDate prossimaChiamata = client.getField(ClientField.PROSSIMA_CHIAMATA, LocalDate.class);
            stmt.setDate(15, prossimaChiamata != null ? Date.valueOf(prossimaChiamata) : null);

            LocalDate dataAcquisizione = client.getField(ClientField.DATA_ACQUISIZIONE, LocalDate.class);
            stmt.setDate(16, dataAcquisizione != null ? Date.valueOf(dataAcquisizione) : null);

            Business business = client.getField(ClientField.BUSINESS, Business.class);
            stmt.setString(17, business != null ? business.toString() : null);

            Operator operator = client.getField(ClientField.OPERATORE_ASSEGNATO, Operator.class);
            stmt.setString(18, operator != null ? operator.toString() : null);

            stmt.setBoolean(19, client.getField(ClientField.INFORMATION, Boolean.class));
            stmt.setBoolean(20, client.getField(ClientField.CATALOG, Boolean.class));
            stmt.setBoolean(21, client.getField(ClientField.SAMPLE, Boolean.class));

            // Se PVU è un testo
            String pvu = (String) client.get(ClientField.PVU);
            stmt.setString(22, pvu != null ? pvu : "");

            stmt.setString(23, client.getUuid().toString()); // ID nella WHERE

            stmt.executeUpdate();
            statusToolTipController.update("Cambiamenti salvati!");
        } catch (SQLException e) {
            printSQLException(e);
        }
    }


    @Override
    public List<Annotation> getAnnotationsForClient(Client client) {
        String sql = "SELECT DATA_CHIAMATA, OPERATORE, CONTENUTO, PROSSIMA_CHIAMATA, ID, INFORMATION, CATALOG, SAMPLE FROM ANNOTATIONS WHERE CUSTOMER_ID = ?";
        List<Annotation> annotations = new ArrayList<>();

        try (PreparedStatement stmt = connectionManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, client.getUuid().toString());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    UUID uuid = UUID.fromString(rs.getString("ID"));
                    LocalDate callDate = rs.getDate("DATA_CHIAMATA").toLocalDate();
                    String operatorStr = rs.getString("OPERATORE");
                    Operator madeBy = Operator.fromString(operatorStr); // Enum Operator
                    String content = rs.getString("CONTENUTO");
                    LocalDate nextCallDate = rs.getDate("PROSSIMA_CHIAMATA") != null
                            ? rs.getDate("PROSSIMA_CHIAMATA").toLocalDate()
                            : null;
                    Annotation annotation = new Annotation(uuid, callDate, madeBy, content, nextCallDate);
                    annotation.setSample(rs.getBoolean("SAMPLE"));
                    annotation.setCatalog(rs.getBoolean("CATALOG"));
                    annotation.setInformation(rs.getBoolean("INFORMATION"));
                    annotations.add(annotation);
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        return annotations;
    }

    @Override
    public void updateAnnotation(Annotation annotation, String clientID) {
        String sql = "UPDATE ANNOTATIONS SET CUSTOMER_ID = ?, DATA_CHIAMATA = ?, PROSSIMA_CHIAMATA = ?, OPERATORE = ?, CONTENUTO = ?, INFORMATION = ?, CATALOG = ?, SAMPLE = ?" +
                "WHERE ID = ?";

        try (PreparedStatement stmt = connectionManager.getConnection().prepareStatement(sql)) {
            stmt.setString(9, annotation.getUuid().toString());
            stmt.setString(1, clientID);
            stmt.setDate(2, java.sql.Date.valueOf(annotation.getCallDate()));

            if (annotation.getNextCallDate() != null) {
                stmt.setDate(3, java.sql.Date.valueOf(annotation.getNextCallDate()));
            } else {
                stmt.setNull(3, java.sql.Types.DATE);
            }

            stmt.setString(4, annotation.getMadeBy().name());
            stmt.setString(5, annotation.getContent());
            stmt.setBoolean(6, annotation.getInformation());
            stmt.setBoolean(7, annotation.getCatalog());
            stmt.setBoolean(8, annotation.getSample());
            stmt.executeUpdate();
            statusToolTipController.update("Annotazione modificata.");
        } catch (SQLException e) {
            printSQLException(e);
            statusToolTipController.update("Errore nella modifica dell'annotazione");
        }
    }

    @Override
    public void saveAnnotation(Annotation annotation, String clientID) {
        String sql = "INSERT INTO ANNOTATIONS (ID, CUSTOMER_ID, DATA_CHIAMATA, PROSSIMA_CHIAMATA, OPERATORE, CONTENUTO) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connectionManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, annotation.getUuid().toString());
            stmt.setString(2, clientID);
            stmt.setDate(3, java.sql.Date.valueOf(annotation.getCallDate()));

            if (annotation.getNextCallDate() != null) {
                stmt.setDate(4, java.sql.Date.valueOf(annotation.getNextCallDate()));
            } else {
                stmt.setNull(4, java.sql.Types.DATE);
            }

            stmt.setString(5, annotation.getMadeBy().name());
            stmt.setString(6, annotation.getContent());
            stmt.executeUpdate();
            statusToolTipController.update("Annotazione salvata con successo.");
        } catch (SQLException e) {
            printSQLException(e);
            statusToolTipController.update("Errore nel salvataggio dell'annotazione");
        }
    }

    @Override
    public void saveClientAfterAnnotationChange(Annotation annotation, String clientID) {
        String sql = "UPDATE CUSTOMERS SET ULTIMA_CHIAMATA = ?, PROSSIMA_CHIAMATA = ?, VOLTE_CONTATTATI = VOLTE_CONTATTATI + 1 " +
                "WHERE ID = ?";

        try (PreparedStatement stmt = connectionManager.getConnection().prepareStatement(sql)) {
            stmt.setDate(1, java.sql.Date.valueOf(annotation.getCallDate()));

            if (annotation.getNextCallDate() != null) {
                stmt.setDate(2, java.sql.Date.valueOf(annotation.getNextCallDate()));
            }

            if (annotation.getInformation())    setInformation(clientID);
            if (annotation.getCatalog()) setCatalog(clientID);
            if (annotation.getSample()) setSample(clientID);

            stmt.setString(3, clientID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
            statusToolTipController.update("Errore nell'aggiornamento del client: " + e.getMessage());
        }
    }

    private void setInformation(String clientID) throws SQLException {
        String sql = "UPDATE CUSTOMERS SET INFORMATION = TRUE WHERE ID = ?";

        PreparedStatement stmt = connectionManager.getConnection().prepareStatement(sql);
        stmt.setString(1, clientID);

        stmt.executeUpdate();
    }
    private void setCatalog(String clientID) throws SQLException {
        String sql = "UPDATE CUSTOMERS SET CATALOG = TRUE WHERE ID = ?";

        PreparedStatement stmt = connectionManager.getConnection().prepareStatement(sql);
        stmt.setString(1, clientID);

        stmt.executeUpdate();
    }
    private void setSample(String clientID) throws SQLException {
        String sql = "UPDATE CUSTOMERS SET SAMPLE = TRUE WHERE ID = ?";

        PreparedStatement stmt = connectionManager.getConnection().prepareStatement(sql);
        stmt.setString(1, clientID);

        stmt.executeUpdate();
    }

    @Override
    public boolean isAlive() {
        return connectionManager.findHostIp() != null;
    }

    @Override
    public Client getClient(UUID uuid) {
        Client client = null;
        String sql = "SELECT * FROM CUSTOMERS WHERE ID = ?";

        try (PreparedStatement stmt = connectionManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, uuid.toString());

            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                client = mapResultSetToClient(rs);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        return client;
    }

    @Override
    public List<Client> getClientsByNextCall(LocalDate date) {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT * FROM CUSTOMERS WHERE PROSSIMA_CHIAMATA = ?";

        try (PreparedStatement statement = connectionManager.getConnection().prepareStatement(query)) {

            statement.setDate(1, Date.valueOf(date));

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    clients.add(mapResultSetToClient(resultSet));
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return clients;
    }


    private void setNullableDate(PreparedStatement preparedStatement, int index, LocalDate date) throws SQLException {
        if (date != null) {
            preparedStatement.setDate(index, java.sql.Date.valueOf(date));
        } else {
            preparedStatement.setNull(index, java.sql.Types.DATE);
        }
    }

    /**
     * Restituisce un client che corrisponde alla posizione del cursore sul ResultSet
     */
    private Client mapResultSetToClient(ResultSet rs) throws SQLException {
        Client ret = new Client();
        ret.setUuid(UUID.fromString(rs.getString("ID")));
        ret.set(ClientField.RAGIONE_SOCIALE, rs.getString("RAGIONE_SOCIALE"));
        ret.set(ClientField.PERSONA_RIFERIMENTO, rs.getString("PERSONA_RIFERIMENTO"));
        ret.set(ClientField.EMAIL_RIFERIMENTO, rs.getString("EMAIL_RIFERIMENTO"));
        ret.set(ClientField.CELLULARE_RIFERIMENTO, rs.getString("CELLULARE_RIFERIMENTO"));
        ret.set(ClientField.TELEFONO_AZIENDALE, rs.getString("TELEFONO_AZIENDALE"));
        ret.set(ClientField.EMAIL_AZIENDALE, rs.getString("EMAIL_AZIENDALE"));
        ret.set(ClientField.PAESE, rs.getString("PAESE"));
        ret.set(ClientField.CITTA, rs.getString("CITTA"));
        ret.set(ClientField.NOME_TITOLARE, rs.getString("NOME_TITOLARE"));
        ret.set(ClientField.CELLULARE_TITOLARE, rs.getString("CELLULARE_TITOLARE"));
        ret.set(ClientField.EMAIL_TITOLARE, rs.getString("EMAIL_TITOLARE"));
        ret.set(ClientField.SITO_WEB, rs.getString("SITO_WEB"));
        ret.set(ClientField.VOLTE_CONTATTATI, rs.getInt("VOLTE_CONTATTATI"));
        ret.set(ClientField.PVU, rs.getString("PVU"));

        // Gestione delle date (evita NullPointerException)
        ret.set(ClientField.ULTIMA_CHIAMATA, rs.getDate("ULTIMA_CHIAMATA") != null ? rs.getDate("ULTIMA_CHIAMATA").toLocalDate() : null);
        ret.set(ClientField.PROSSIMA_CHIAMATA, rs.getDate("PROSSIMA_CHIAMATA") != null ? rs.getDate("PROSSIMA_CHIAMATA").toLocalDate() : null);
        ret.set(ClientField.DATA_ACQUISIZIONE, rs.getDate("DATA_ACQUISIZIONE") != null ? rs.getDate("DATA_ACQUISIZIONE").toLocalDate() : null);

        // Business e Operatore possono essere null, quindi gestiamo il caso
        ret.set(ClientField.BUSINESS, rs.getString("BUSINESS") != null ? Business.fromString(rs.getString("BUSINESS")) : null);
        ret.set(ClientField.OPERATORE_ASSEGNATO, rs.getString("OPERATORE_ASSEGNATO") != null ? Operator.fromString(rs.getString("OPERATORE_ASSEGNATO")) : null);

        ret.set(ClientField.INFORMATION, rs.getBoolean("INFORMATION"));
        ret.set(ClientField.CATALOG, rs.getBoolean("CATALOG"));
        ret.set(ClientField.SAMPLE, rs.getBoolean("SAMPLE"));

        return ret;
    }
}
