package orodent.clientrelationmanager.database;

import org.apache.derby.drda.NetworkServerControl;
import orodent.clientrelationmanager.utils.IOManager;

import java.net.InetAddress;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

public abstract class ConnectionManager {
    private static final String DBNAME="NSSimpleDB";
    private static final int NETWORKSERVER_PORT=1527;
    private static final String DB_USER="me";
    private static final String DB_PSW="pw";
    private static final Logger logger = Logger.getLogger(ConnectionManager.class.getName());

    public static Connection getConnection() {
        Connection conn = null;
        try {
            String ip = checkForServer();
            System.out.println("Found running Network Server");
            logger.info("Found running Network Server");

            // The user and password properties are a must, required by JCC
            Properties properties = new java.util.Properties();
            properties.setProperty("user",DB_USER);
            properties.setProperty("password",DB_PSW);

            // Load ClientDriver and get database connection via DriverManager api
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String DERBY_CLIENT_URL= "jdbc:derby://"+ ip +":"+ NETWORKSERVER_PORT+"/"+DBNAME;
            conn = DriverManager.getConnection(DERBY_CLIENT_URL,properties);
            System.out.println("Got a client connection via the DriverManager.");
            logger.info("Got a client connection via the DriverManager.");
        } catch (NoServerFoundException e) {
            System.out.println("Network Server not found, creating one!");
            logger.info("Network Server not found, creating one!");
            try {
                startNetworkServer();
            } catch (Exception e1) {
                System.out.println("Failed to start NetworkServer: " + e1);
                System.exit(1);
            }
            try {
                // Since the Network Server was started in this JVM, this JVM can get an embedded connection
                // to the same database that the Network Server is accessing to serve clients from other JVMs.
                // The embedded connection will be faster than going across the network
                conn = getEmbeddedConnection();
                System.out.println("Got an embedded connection.");
                logger.info("Got an embedded connection.");
            } catch (Exception sqle) {
                System.out.println("Failure making connection: " + sqle);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException: " + e);
        } catch (SQLException e) {
            System.out.println("SQLException: " + e);
        }
        return conn;
    }

    /*
     *
     * @param ip
     * @throws NoServerFoundException
     *
    private static void pingServer(String ip) throws NoServerFoundException{
        try {
            org.apache.derby.drda.NetworkServerControl server = new NetworkServerControl(InetAddress.getByName(ip), NETWORKSERVER_PORT);
            server.ping();
        } catch (Exception e) {
            throw new NoServerFoundException();
        }
    }
*/

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
     * Pings the database server to see if one is running
     * @throws NoServerFoundException if no server database was found
     */
    private static String checkForServer() throws NoServerFoundException {
        String ip;
        try {
            ip = (String) IOManager.read("IPAddress");
            org.apache.derby.drda.NetworkServerControl server = new NetworkServerControl(InetAddress.getByName(ip), NETWORKSERVER_PORT);
            System.out.println("Searching for running Network Server");
            logger.info("Searching for running Network Server");
            server.ping();
        } catch (Exception e) {
            throw new NoServerFoundException();
        }
        return ip;
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
     * derby.drda.keepAlive,
     * derby.drda.logConnections,
     * derby.drda.maxThreads,
     * derby.drda.minThreads,
     * derby.drda.portNumber,
     * derby.drda.startNetworkServer,
     * derby.drda.timeslice,
     * derby.drda.traceAll,
     * derby.drda.traceDirectory,
     * derby.drda.host,
     * @throws Exception Don't know when it could throw
     */
    private static void startWithProperty() throws Exception {
        System.out.println("Starting Network Server");
        logger.info("Starting Network Server");
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
        System.out.println("Testing if Network Server is up and running!");
        logger.info("Testing if Network Server is up and running!");
        for (int i = 0; i < 6; i++) {
            try {
                Thread.sleep(1000);
                server.ping();
            } catch (Exception e) {
                System.out.println("Try #" + i + " " + e);
                if (i == 5) {
                    System.out.println("Giving up trying to connect to Network Server!");
                    throw e;
                }
            }
        }
        System.out.println("Derby Network Server now running");
        logger.info("Derby Network Server now running");
    }
}
