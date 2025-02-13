package orodent.clientrelationmanager.controller.database;

import org.apache.derby.drda.NetworkServerControl;
import orodent.clientrelationmanager.utils.IOManager;

import java.net.InetAddress;
import java.sql.*;
import java.util.Properties;

public abstract class ConnectionManager {
    private static final String DBNAME="NSSimpleDB";
    protected static final int NETWORKSERVER_PORT=1527;
    private static final String DB_USER="me";
    private static final String DB_PSW="pw";
   // private static final String[] ips = {"192.168.1.138", "192.168.1.136"};
  //  private static String validIP = null;

    public static Connection getConnection(ConnectionInterface status) {
        Connection conn = null;
        try {
            status.update("Searching for running Network Server");
            ConnectionHelperThread helperThread = new ConnectionHelperThread();
            status.update("Found running Network Server");
            String ip = helperThread.getOperativeServerIP();

            // The user and password properties are a must, required by JCC
            Properties properties = new java.util.Properties();
            properties.setProperty("user",DB_USER);
            properties.setProperty("password",DB_PSW);

            // Load ClientDriver and get database connection via DriverManager api
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String DERBY_CLIENT_URL= "jdbc:derby://"+ ip +":"+ NETWORKSERVER_PORT+"/"+DBNAME;
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

    /*
     * Pings the database server to see if one is running
     * @throws NoServerFoundException if no server database was found
     *
    private static String checkForServer() throws NoServerFoundException {

        for (String ip : ips) {
            try {
               // ip = (String) IOManager.read("IPAddress");
                org.apache.derby.drda.NetworkServerControl server = new NetworkServerControl(InetAddress.getByName(ip), NETWORKSERVER_PORT);
                server.ping();
                ret = ip;
            } catch (Exception ignored) {}
        }
        if (ret.equals("192.168.1.1")) {
            throw new NoServerFoundException();
        }
        return ret;
    }*/

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

        IOManager.write(ip, "IPAddress");
    }

    /**
     * Ping the database server. If no servers ping back in 10 seconds will throw an exception
     * @throws Exception If no server was found
     */
    private static void waitForStart() throws Exception {
        // Server instance for testing connection
        org.apache.derby.drda.NetworkServerControl server = new NetworkServerControl();          //TODO identificare chi ha risposto al ping

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
