package orodent.clientrelationmanager.controller.database;

import org.apache.derby.drda.NetworkServerControl;

import java.net.InetAddress;
import java.sql.*;
import java.util.Properties;

public abstract class ConnectionManager {       //TODO da rendere istanziabile
    private static final String DBNAME="NSSimpleDB";
    protected static final int NETWORKSERVER_PORT=1527;
    private static final String DB_USER="me";
    private static final String DB_PSW="pw";
    private static String validIP = null;       //TODO DA ricordasi di far tornare a null quando la connessione viene persa

    public static Connection getConnection(ConnectionInterface status) {
        Connection conn = null;
        try {
            status.update("Searching for running Network Server");
            try {
                IpAddressesBean ipsToScan = new IpAddressesBean();
                //Begin to Test all available Ips in IpAddressesBean
                for (int i = 0; i < ipsToScan.getIpListSize(); i++) {
                    ConnectionTesterThread helperThread = new ConnectionTesterThread(i);
                    helperThread.start();
                }
                Thread.sleep(4000);     //TODO To run on a new thread
                //Checking the result of the test from every thread launched
                for (int i = 0; i < ipsToScan.getIpListSize(); i++) {
                    if (ipsToScan.isIpReachable(i)){
                        validIP = ipsToScan.getIpAddresses(i);
                    }
                }
            } catch (InterruptedException ignored) {}       //Thread.sleep
            if (validIP == null) {
                throw new NoServerFoundException();
            }
            status.update("Found running Network Server");

            // The user and password properties are a must, required by JCC
            Properties properties = new java.util.Properties();
            properties.setProperty("user",DB_USER);
            properties.setProperty("password",DB_PSW);

            // Load ClientDriver and get database connection via DriverManager api
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String DERBY_CLIENT_URL= "jdbc:derby://"+ validIP +":"+ NETWORKSERVER_PORT+"/"+DBNAME;
            conn = DriverManager.getConnection(DERBY_CLIENT_URL,properties);
            status.update("Got a client connection via the DriverManager.");
        } catch (NoServerFoundException e) {
            status.update("Network Server not found, creating one!");
            try {
                status.update("Starting Network Server");
                startNetworkServer();
                status.update("Derby Network Server now running");
            } catch (Exception e1) {
                status.update("Failed to start NetworkServer: " + e1);
                System.exit(1);
            }
            try {
                // Since the Network Server was started in this JVM, this JVM can get an embedded connection
                // to the same database that the Network Server is accessing to serve clients from other JVMs.
                // The embedded connection will be faster than going across the network
                conn = getEmbeddedConnection();
                status.update("Got an embedded connection.");
            } catch (Exception sqle) {
                status.update("Failure making connection: " + sqle);
            }
        } catch (ClassNotFoundException e) {
            status.update("ClassNotFoundException: " + e);
        } catch (SQLException e) {
            status.update("SQLException: " + e);
        }
        return conn;
    }

    /**
     * Database must be running in this JVM
     *
     * @return Embedded Connection to the database.
     * @throws Exception if couldn't do so
     */
    private static Connection getEmbeddedConnection() throws Exception
    {
        return DriverManager.getConnection("jdbc:derby:"+ ConnectionManager.DBNAME +";create=true;user="+DB_USER +";password="+DB_PSW);
    }

    /**
     * starts a server database
     * @throws Exception if couldn't start one.
     */
    private static void startNetworkServer() throws Exception {
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
    private static void startWithProperty() throws Exception {
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
    private static void waitForStart() throws Exception {
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
