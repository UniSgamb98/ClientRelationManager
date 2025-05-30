package orodent.clientrelationmanager.controller.database;

import javafx.scene.control.TextField;
import orodent.clientrelationmanager.controller.main.StatusToolTipController;
import orodent.clientrelationmanager.model.Annotation;
import orodent.clientrelationmanager.model.Client;
import orodent.clientrelationmanager.model.Disc;
import orodent.clientrelationmanager.model.enums.ClientField;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

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

            statement.setString(18, client.getField(ClientField.BUSINESS, String.class));
            statement.setString(19, client.getField(ClientField.OPERATORE_ASSEGNATO, String.class));
            statement.setBoolean(20, client.getField(ClientField.INFORMATION, Boolean.class));
            statement.setBoolean(21, client.getField(ClientField.CATALOG, Boolean.class));
            statement.setBoolean(22, client.getField(ClientField.SAMPLE, Boolean.class));

            statement.executeUpdate();
        } catch (SQLException e){
            printSQLException(e);
        }
    }

    /**
     * Interroga il database, la tabella CUSTOMERS in base al where passato come parametro
     * Se il parametro è una stringa vuota allora restituisce tutti i Customers
     * @param whereSQL la logica di selezione dei dati
     * @return a list of Client
     */
    @Override
    public List<Client> getClientWhere (String whereSQL){
        ArrayList<Client> result = new ArrayList<>();
        String sql;
        if (whereSQL.isEmpty()) sql = "SELECT * FROM CUSTOMERS";
        else sql = "SELECT * FROM CUSTOMERS WHERE " + whereSQL;

        try (Statement stmt = connectionManager.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                result.add(mapResultSetToClient(rs));
            }
        } catch (SQLException e){
            printSQLException(e);
        }
        return result;
    }

    @Override
    public List<Client> getClientWhere(String whereSQL, LocalDate date) {
        ArrayList<Client> result = new ArrayList<>();
        String sql = "SELECT * FROM CUSTOMERS WHERE " + whereSQL;

        try (PreparedStatement stmt = connectionManager.getConnection().prepareStatement(sql)) {
            stmt.setDate(1, java.sql.Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(mapResultSetToClient(rs));
            }
        } catch (SQLException e){
            printSQLException(e);
        }
        return result;
    }

    @Override
    public List<Disc> getDiscWhere (String whereSQL){
        ArrayList<Disc> result = new ArrayList<>();
        String sql;
        if (whereSQL.isEmpty()) sql = "SELECT * FROM DISCHI";
        else sql = "SELECT * FROM DISCHI WHERE " + whereSQL;

        try (Statement stmt = connectionManager.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                result.add(mapResultSetToDisc(rs));
            }
        } catch (SQLException e){
            printSQLException(e);
        }
        return result;
    }

    @Override
    public List<String> getAllValuesFromCustomerColumn(String tableColumn){
        List<String> ret = new ArrayList<>();
        String sql = "SELECT DISTINCT " + tableColumn + " FROM CUSTOMERS";
        try (Statement stmt = connectionManager.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                String ragioneSociale = rs.getString(1);
                if (ragioneSociale != null)     ret.add(ragioneSociale);
            }
            ret.sort(Comparator.naturalOrder());

        } catch (SQLException e){
            printSQLException(e);
        }
        return ret;
    }

    @Override
    public List<String> getAllValuesFromDiscColumn(String tableColumn){
        List<String> ret = new ArrayList<>();
        String sql = "SELECT DISTINCT " + tableColumn + " FROM DISCHI";
        try (Statement stmt = connectionManager.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                String ragioneSociale = rs.getString(1);
                if (ragioneSociale != null)     ret.add(ragioneSociale);
            }
            ret.sort(Comparator.naturalOrder());

        } catch (SQLException e){
            printSQLException(e);
        }
        return ret;
    }


    /**
     * Restituisce una List di client i quali la string s è un sotto stringa di Ragione sociale oppure Persona di riferimento
     * TODO Estendere a tutti i parametri IN CONFIG questa possibilità di ricerca
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

            stmt.setString(17, (String) client.get(ClientField.BUSINESS));
            stmt.setString(18, (String) client.get(ClientField.OPERATORE_ASSEGNATO));

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
                    String madeBy =rs.getString("OPERATORE"); // Enum Operator
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

            stmt.setString(4, annotation.getMadeBy());
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

            stmt.setString(5, annotation.getMadeBy());
            stmt.setString(6, annotation.getContent());
            stmt.executeUpdate();
            statusToolTipController.update("Annotazione salvata con successo.");
        } catch (SQLException e) {
            printSQLException(e);
            statusToolTipController.update("Errore nel salvataggio dell'annotazione");
        }
    }

    @Override
    public boolean isAlive() {
        return connectionManager.findHostIp() != null;
    }

    private void setNullableDate(PreparedStatement preparedStatement, int index, LocalDate date) throws SQLException {
        if (date != null) {
            preparedStatement.setDate(index, java.sql.Date.valueOf(date));
        } else {
            preparedStatement.setNull(index, java.sql.Types.DATE);
        }
    }

    private Disc mapResultSetToDisc(ResultSet rs) throws SQLException {
        Disc ret = new Disc();
        ret.setDiametro(rs.getString("DIAMETRO"));
        ret.setMisura(rs.getString("MISURA"));
        ret.setColore(rs.getString("COLORE"));
        ret.setTipologia(rs.getString("TIPOLOGIA"));
        ret.setId(rs.getInt("ID"));
        ret.setLotto(rs.getString("DIAMETRO"));
        ret.setProblema(rs.getString("PROBLEMA"));

        return ret;
    }

    /**
     * Restituisce il client che corrisponde alla posizione del cursore sul ResultSet
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
        ret.set(ClientField.BUSINESS, rs.getString("BUSINESS") != null ? rs.getString("BUSINESS") : null);
        ret.set(ClientField.OPERATORE_ASSEGNATO, rs.getString("OPERATORE_ASSEGNATO") != null ? rs.getString("OPERATORE_ASSEGNATO") : null);

        ret.set(ClientField.INFORMATION, rs.getBoolean("INFORMATION"));
        ret.set(ClientField.CATALOG, rs.getBoolean("CATALOG"));
        ret.set(ClientField.SAMPLE, rs.getBoolean("SAMPLE"));

        return ret;
    }

    public void eseguiFileSQL(String filePath) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            StringBuilder queryBuilder = new StringBuilder();
            String line;
            try (Statement stmt = connectionManager.getConnection().createStatement()) {
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("--")) continue; // ignora righe vuote o commenti
                    queryBuilder.append(line);

                    if (line.endsWith(";")) {
                        String sql = queryBuilder.toString();
                        sql = sql.substring(0, sql.length() - 1); // rimuove il punto e virgola
                        try {
                            stmt.executeUpdate(sql);
                        } catch (SQLException e) {
                            System.err.println("Errore nell'esecuzione di: " + sql);
                            printSQLException(e);
                        }
                        queryBuilder.setLength(0); // resetta il builder
                    }
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        } catch (IOException e) {
            System.err.println("Errore di IO: " + e);
        }
    }

    public void savePhoto(int assistanceId, int discoId, File fileFoto) {
        if (fileFoto == null || !fileFoto.exists()) {
            System.out.println("File non valido.");
            return;
        }
        String nome = fileFoto.getName();

        String sql = "INSERT INTO PHOTOS (ASSISTANCE_ID, DISCO_ID, NOME, IMMAGINE) VALUES (?, ?, ?, ?)";

        try (
                PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql);
                FileInputStream fis = new FileInputStream(fileFoto)
        ) {
            statement.setInt(1, assistanceId);
            statement.setInt(2, discoId);
            statement.setString(3, nome);
            statement.setBinaryStream(4, fis, (int) fileFoto.length());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Foto '" + nome + "' salvata nel database con successo.");
            } else {
                System.out.println("Errore durante l'inserimento della foto.");
            }

        } catch (SQLException e) {
            printSQLException(e);
        } catch (IOException ignored) {

        }
    }

    @Override
    public int saveDisc(Disc disc) {
        int ret = -1;
        String sql = "INSERT INTO DISCHI (DIAMETRO, MISURA, TIPOLOGIA, COLORE, LOTTO, PROBLEMA) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, disc.getDiametro());
            statement.setString(2, disc.getMisura());
            statement.setString(3, disc.getTipologia());
            statement.setString(4, disc.getColore());
            statement.setString(5, disc.getLotto());
            statement.setString(6, disc.getProblema());

            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ret = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return ret;
    }

    @Override
    public int saveAssistance(
            UUID uuid,
            int discoId,
            TextField rivNome,
            TextField rivRiferimento,
            TextField rivTelefono,
            TextField rivEmail,
            TextField labNome,
            TextField labRiferimento,
            TextField labTelefono,
            TextField labEmail,
            String descrizione,
            String sinterizzazione,
            File analisi,
            LocalDate now
    ) {
        String sql = """
        INSERT INTO ASSISTANCES (
            CUSTOMER_ID, DISCO_ID,
            RIV_NOME, RIV_RIFERIMENTO, RIV_TELEFONO, RIV_EMAIL,
            LAB_NOME, LAB_RIFERIMENTO, LAB_TELEFONO, LAB_EMAIL,
            DESCRIZIONE, SINTERIZZAZIONE, ANALISI, DATA
        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;
        int ret = -1;

        try (PreparedStatement statement = connectionManager.getConnection()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, uuid.toString());
            statement.setInt(2, discoId);

            statement.setString(3, rivNome.getText());
            statement.setString(4, rivRiferimento.getText());
            statement.setString(5, rivTelefono.getText());
            statement.setString(6, rivEmail.getText());

            statement.setString(7, labNome.getText());
            statement.setString(8, labRiferimento.getText());
            statement.setString(9, labTelefono.getText());
            statement.setString(10, labEmail.getText());

            statement.setClob(11, new StringReader(descrizione));
            statement.setClob(12, new StringReader(sinterizzazione));

            // BLOB (file di analisi)
            if (analisi != null && analisi.exists()) {
                try (InputStream blobStream = new FileInputStream(analisi)) {
                    statement.setBlob(13, blobStream);
                }
            } else {
                statement.setNull(13, Types.BLOB);
            }

            statement.setDate(14, java.sql.Date.valueOf(now));

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ret = generatedKeys.getInt(1); // Restituisce l'ID generato
                } else {
                    throw new SQLException("Inserimento fallito, nessun ID generato.");
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        } catch (IOException ignored) {}
        return ret;
    }


    @Override
    public int getNextAssistanceId () {
        int ret = -1;
        String sql = "SELECT COALESCE(MAX(ID), 0) + 1 AS NEXT_ID FROM ASSISTANCES";
        try (Statement stmt = connectionManager.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            ret = rs.getInt("NEXT_ID");

        } catch (SQLException e){
            printSQLException(e);
        }
        return ret;
    }
}
