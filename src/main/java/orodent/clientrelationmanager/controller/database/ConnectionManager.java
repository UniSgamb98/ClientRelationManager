package orodent.clientrelationmanager.controller.database;

import javafx.application.Platform;
import org.apache.derby.drda.NetworkServerControl;
import orodent.clientrelationmanager.controller.StatusToolTipController;
import orodent.clientrelationmanager.model.App;

import java.net.InetAddress;
import java.sql.*;
import java.util.Properties;

public class ConnectionManager extends Thread{
    private final String DBNAME="NSSimpleDB";
    protected final int NETWORKSERVER_PORT = 1527;
    private final String DB_USER="me";
    private final String DB_PSW="pw";
    private String validIP = null;
    private final StatusToolTipController status;
    private final App app;

    public ConnectionManager(App app){
        this.app = app;
        status = new StatusToolTipController();
    }

    @Override
    public void run(){
        app.setConnection(getConnection());
    }

    private Connection getConnection() {
        Connection conn = null;
        try {
            update("Searching for running Network Server");
            try {
                IpAddressesBean ipsToScan = new IpAddressesBean();
                //Begin to Test all available Ips in IpAddressesBean
                for (int i = 0; i < ipsToScan.getIpListSize(); i++) {
                    ConnectionTesterThread helperThread = new ConnectionTesterThread(i);
                    helperThread.start();
                }
                sleep(4000);
                //Checking the result of the test from every thread launched
                for (int i = 0; i < ipsToScan.getIpListSize(); i++) {
                    if (ipsToScan.isIpReachable(i)) {
                        validIP = ipsToScan.getIpAddresses(i);
                    }
                }
            } catch (InterruptedException ignored) {}
            if (validIP == null) {
                throw new NoServerFoundException();
            }
            update("Found running Network Server");

            // The user and password properties are a must, required by JCC
            Properties properties = new java.util.Properties();
            properties.setProperty("user",DB_USER);
            properties.setProperty("password",DB_PSW);

            // Load ClientDriver and get database connection via DriverManager api
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String DERBY_CLIENT_URL= "jdbc:derby://"+ validIP +":"+ NETWORKSERVER_PORT+"/"+DBNAME;
            conn = DriverManager.getConnection(DERBY_CLIENT_URL,properties);
            update("Got a client connection via the DriverManager.");
            app.setConnectionType(ConnectionType.DRIVER);
            status.greenLight();
        } catch (NoServerFoundException e) {
            update("Network Server not found, creating one!");
            try {
                update("Starting Network Server");
                startNetworkServer();
                update("Derby Network Server now running");
            } catch (Exception e1) {
                update("Failed to start NetworkServer: " + e1);
                System.exit(1);
            }
            try {
                // Since the Network Server was started in this JVM, this JVM can get an embedded connection
                // to the same database that the Network Server is accessing to serve clients from other JVMs.
                // The embedded connection will be faster than going across the network
                conn = getEmbeddedConnection();
                update("Got an embedded connection.");
                app.setConnectionType(ConnectionType.EMBEDDED);
                status.greenLight();
            } catch (Exception sqle) {
                update("Failure making connection: " + sqle);
            }
        } catch (ClassNotFoundException e) {
            update("ClassNotFoundException: " + e);
        } catch (SQLException e) {
            update("SQLException: " + e);
        }
        return conn;
    }

    public void endConnection(){
        if (app.getConnectionType().equals(ConnectionType.EMBEDDED)){
            try {
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
                status.redLight();
                status.update("Disconnected");
            } catch (SQLException e) {
                if (e.getSQLState().trim().equals("XJ015")){
                    status.update("Derby has been shut down");
                    status.redLight();
                } else {
                    status.update("Something went wrong during derby connection: " + e);
                }
            }
        } else if (app.getConnectionType().equals(ConnectionType.DRIVER)) {
            Connection c = app.getConnection();
            try {
                c.commit();
                c.close();
                status.redLight();
                status.update("Disconnected from database");
            } catch (SQLException e) {
                status.update("Something went wrong during derby connection: " + e);
            }
        } else {
            status.update("Already disconnected");
        }
    }

    private void update(String msg){
        Platform.runLater(()-> status.update(msg));
    }

    /**
     * Database must be running in this JVM
     *
     * @return Embedded Connection to the database.
     * @throws Exception if couldn't do so
     */
    private Connection getEmbeddedConnection() throws Exception
    {
        return DriverManager.getConnection("jdbc:derby:I:\\CliZr\\Tommaso\\"+ DBNAME +";create=true;user="+DB_USER +";password="+DB_PSW);
    }

    /**
     * starts a server database
     * @throws Exception if couldn't start one.
     */
    private void startNetworkServer() throws Exception {
        // Start the Network Server using the property and then wait for the server to start by testing a connection
        startWithProperty();
        waitForStart();
    }

    /**
     * Here you can set the proprieties of the server.
     * derby.drda.keepAlive, logConnections, maxThreads, minThreads, portNumber,
     * startNetworkServer, timeslice, traceAll, traceDirectory, host,
     * @throws Exception Don't know when it could throw
     */
    private void startWithProperty() throws Exception {
        System.setProperty("derby.drda.startNetworkServer", "true");
        String ip = InetAddress.getLocalHost().getHostAddress();
        System.setProperty("derby.drda.host", ip);

        // Booting Derby
        Class<?> clazz = Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        clazz.getConstructor().newInstance();
    }

    /**
     * Ping the database server. If no servers ping back in 5 seconds will throw an exception
     * @throws Exception If no server was found
     */
    private void waitForStart() throws Exception {
        // Server instance for testing connection on localMachine
        org.apache.derby.drda.NetworkServerControl server = new NetworkServerControl();

        // Use NetworkServerControl.ping() to wait for the network Server to come up.
        for (int i = 0; i < 6; i++) {
            try {
                Thread.sleep(1000);
                server.ping();
            } catch (Exception e) {
                if (i == 5) {
                    throw e;
                }
            }
        }
    }
}
