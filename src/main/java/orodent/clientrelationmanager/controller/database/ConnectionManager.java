package orodent.clientrelationmanager.controller.database;
import orodent.clientrelationmanager.controller.main.StatusToolTipController;

import java.net.InetAddress;
import java.sql.*;
import java.util.Properties;

public class ConnectionManager extends Thread {
    private final String DBNAME = "NSSimpleDB";
    protected final int NETWORKSERVER_PORT = 1527;
    private final String DB_USER = "me";
    private final String DB_PSW = "pw";

    private final StatusToolTipController status;
    private Connection connection;
    private ConnectionType connectionType;

    public ConnectionManager() {
        status = new StatusToolTipController();
        // Avvia il thread che tenterà subito di aprire una connessione.
        start();
    }

    @Override
    public void run() {
        open();
    }

    /**
     *  Cerca l'indirizzo ip del pc che sta facendo da host per il server
     *  @return null se non è stato trovato un pc host
     */
    public String findHostIp() {
        String ret = null;
        try {
        IpAddressesBean ipsToScan = new IpAddressesBean();
        ipsToScan.initialize();

        // Avvia thread per testare ogni IP
        for (int i = 0; i < ipsToScan.getIpListSize(); i++) {
            new ConnectionTesterThread(i).start();
        }

        // Attendi che tutti i thread abbiano terminato (tempo fisso di 1,5 secondi)

            Thread.sleep(1500);


        // Verifica se almeno un IP è raggiungibile
        for (int i = 0; i < ipsToScan.getIpListSize(); i++) {
            if (ipsToScan.isIpReachable(i)) {
                ret = ipsToScan.getIpAddresses(i);
                break;
            }
        }
        } catch (InterruptedException e) {
            status.update("Interrupted: " + e.getMessage());
        }
        return ret;
    }
    /**
     * Tenta di aprire una connessione. Se viene trovato un server in esecuzione su uno degli IP specificati,
     * utilizza la modalità DRIVER (client) altrimenti avvia un server embedded.
     */
    protected void open() {
        try {
            status.update("Searching for running Network Server");

            String validIP = findHostIp();
            if (validIP == null) {
                throw new NoServerFoundException();
            }

            status.update("Found running Network Server at " + validIP);

            // Prepara le proprietà e carica il driver client
            Properties props = new Properties();
            props.setProperty("user", DB_USER);
            props.setProperty("password", DB_PSW);

            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String url = "jdbc:derby://" + validIP + ":" + NETWORKSERVER_PORT + "/" + DBNAME;
            connection = DriverManager.getConnection(url, props);
            connectionType = ConnectionType.DRIVER;
            status.update("Connected as client via DriverManager.");
            status.greenLight();
        } catch (NoServerFoundException nsfe) {
            status.update("No Network Server found, hosting embedded server.");
            try {
                startNetworkServer();
            } catch (Exception e) {
                status.update("Failed to start Network Server: " + e.getMessage());
                System.exit(1);
            }
            try {
                connection = getEmbeddedConnection();
                connectionType = ConnectionType.EMBEDDED;
                status.update("Connected via embedded connection.");
                status.greenLight();
            } catch (SQLException sqle) {
                if ("XJ040".equals(sqle.getSQLState().trim())) {
                    status.update("Embedded connection conflict. Server already running, retrying connection...");
                    open(); // Riprova ad aprire la connessione
                } else {
                    status.update("Error obtaining embedded connection: " + sqle.getMessage());
                }
            }
        } catch (ClassNotFoundException e) {
            status.update("Driver class not found: " + e.getMessage());
        } catch (SQLException e) {
            status.update("SQL error: " + e.getMessage());
        }
    }

    /**
     * Chiude la connessione in base al tipo utilizzato.
     */
    protected void close() {
        if (connection == null)
            return;
        switch (connectionType) {
            case EMBEDDED -> {
                try {
                    DriverManager.getConnection("jdbc:derby:;shutdown=true");
                } catch (SQLException e) {
                    if ("XJ015".equals(e.getSQLState().trim())) {
                        connection = null;
                        connectionType = ConnectionType.NONE;
                        status.update("Disconnesso.");
                        status.redLight();
                    } else {
                        status.update("Error shutting down Derby: " + e.getMessage());
                    }
                }
            }
            case DRIVER -> {
                try {
                    connection.commit();
                    connection.close();
                    connection = null;
                    connectionType = ConnectionType.NONE;
                    status.update("Disconnected from database.");
                    status.redLight();
                } catch (SQLException e) {
                    status.update("Error closing connection: " + e.getMessage());
                }
            }
            case NONE -> {
                status.update("Already disconnected.");
                status.redLight();
            }
        }
    }

    /**
     * Ottiene una connessione embedded.
     * @return la connessione embedded
     * @throws SQLException se si verifica un errore
     */
    private Connection getEmbeddedConnection() throws SQLException {
        String url = "jdbc:derby:" + DBNAME + ";create=true;user=" + DB_USER + ";password=" + DB_PSW;// I:\CliZr\Tommaso\
        return DriverManager.getConnection(url);
    }

    /**
     * Avvia il Network Server impostando le proprietà di sistema e attende che il server sia disponibile.
     *
     * @throws Exception se non riesce ad avviare il server.
     */
    private void startNetworkServer() throws Exception {
        startWithProperty();
        waitForStart();
    }

    /**
     * Imposta le proprietà per avviare il Network Server e inizializza il driver embedded.
     *
     * @throws Exception se non riesce a inizializzare il driver.
     */
    private void startWithProperty() throws Exception {
        System.setProperty("derby.drda.startNetworkServer", "true");
        String ip = InetAddress.getLocalHost().getHostAddress();
        System.setProperty("derby.drda.host", ip);
        // Boot dell'EmbeddedDriver
        Class<?> clazz = Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        clazz.getConstructor().newInstance();
    }

    /**
     * Attende fino a 6 secondi che il server risponda al ping.
     *
     * @throws Exception se il server non risponde dopo il tempo atteso.
     */
    private void waitForStart() throws Exception {
        org.apache.derby.drda.NetworkServerControl server = new org.apache.derby.drda.NetworkServerControl();
        for (int i = 0; i < 6; i++) {
            try {
                Thread.sleep(1000);
                server.ping();
                return;
            } catch (Exception e) {
                if (i == 5) {
                    throw e;
                }
            }
        }
    }

    /**
     * Restituisce la connessione, riaprendola se necessario.
     */
    protected Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                status.update("Riconnesione in corso...");
                status.redLight();
                open();
            }
        } catch (SQLException e) {
            status.update("Error checking connection status: " + e.getMessage());
        }
        return connection;
    }
}
