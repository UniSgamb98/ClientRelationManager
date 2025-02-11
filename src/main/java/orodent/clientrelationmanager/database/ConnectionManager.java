package orodent.clientrelationmanager.database;

import org.apache.derby.drda.NetworkServerControl;
import orodent.clientrelationmanager.HelloController;
import orodent.clientrelationmanager.utils.IOManager;


import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.Properties;

public abstract class ConnectionManager {
    private static final String DBNAME="NSSimpleDB";
    private static final int NETWORKSERVER_PORT=1527;
    private static final String DB_USER="me";
    private static final String DB_PSW="pw";



    public static Connection getConnection(String ip, HelloController controller) {
        Connection conn = null;
        try {
            checkForServer(ip);
            System.out.println("Found running Network Server");
            controller.log("Found running Network Server");

            //Load DB2 Driver for JDBC class
          //  Class.forName("com.ibm.db2.jcc.DB2Driver");

            Properties properties = new java.util.Properties();
            // The user and password properties are a must, required by JCC
            properties.setProperty("user",DB_USER);
            properties.setProperty("password",DB_PSW);

            // Get database connection  via DriverManager api
            String DERBY_CLIENT_URL= "jdbc:derby://"+ ip +":"+ NETWORKSERVER_PORT+"/"+DBNAME;
            conn = DriverManager.getConnection(DERBY_CLIENT_URL,properties);
            System.out.println("Got a client connection via the DriverManager.");
            controller.log("Got a client connection via the DriverManager.");
        } catch (Exception e) {
            System.out.println("Network Server not found, creating one!");
            controller.log("Network Server not found, creating one!");
            try {
                startNetworkServer();
            } catch (Exception e1) {
                System.out.println("Failed to start NetworkServer: " + e1);
                controller.log("Failed to start NetworkServer: " + e1);
                System.exit(1);
            }
            try {
                conn = getEmbeddedConnection();
                // get an embedded connection
                // Since the Network Server was started in this JVM, this JVM can get an embedded
                // connection to the same database that the Network Server
                // is accessing to serve clients from other JVMs.
                // The embedded connection will be faster than going across the network
                System.out.println("Got an embedded connection.");
                controller.log("Got an embedded connection.");
            } catch (Exception sqle) {
                System.out.println("Failure making connection: " + sqle);
                log(""+ sqle);
                controller.log("Failure making connection: " + sqle);
            }
        }
        return conn;
    }

    public static void pingServer(String ip, HelloController controller) {

        try {
            checkForServer(ip);
            System.out.println("Server found");
            controller.log("Server found");
        } catch (Exception e) {
            System.out.println("server not found");
            controller.log("Server not found");
        }
    }

    /**
     * Database must be running in this JVM
     *
     * @return Embedded Connection to the database.
     * @throws Exception if couldn't do so
     */
    private static Connection getEmbeddedConnection() throws Exception
    {
        return DriverManager.getConnection("jdbc:derby:"+ ConnectionManager.DBNAME +";create=true;user=me;password=pw");
    }

    /**
     * Pings the database server to see if one is running
     * @throws Exception if no server database was found
     */
    private static void checkForServer(String ip) throws Exception {
        org.apache.derby.drda.NetworkServerControl server = new NetworkServerControl(InetAddress.getByName(ip), NETWORKSERVER_PORT);
        System.out.println("Searching for running Network Server");
        log("Searching for running Network Server");
        server.ping();
    }

    /**
     * starts a server database
     * @throws Exception if couldn't start one.
     */
    private static void startNetworkServer() throws Exception {
        // Start the Network Server using the property
        // and then wait for the server to start by testing a connection
        startWithProperty();
        waitForStart();


        //IOManager.write();
    }

    /**
     * Here you can set the proprieties of the server.
     * derby.drda.keepAlive
     * derby.drda.logConnections
     * derby.drda.maxThreads
     * derby.drda.minThreads
     * derby.drda.portNumber
     * derby.drda.startNetworkServer
     * derby.drda.timeslice
     * derby.drda.traceAll
     * derby.drda.traceDirectory
     * derby.drda.host
     * @throws Exception Don't know when it could throw
     */
    private static void startWithProperty() throws Exception {
        System.out.println("Starting Network Server");
        log("Starting Network Server");
        System.setProperty("derby.drda.startNetworkServer", "true");
        System.setProperty("derby.drda.host", InetAddress.getLocalHost().getHostAddress());

        // Booting Derby
        Class<?> clazz = Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        clazz.getConstructor().newInstance();
    }

    /**
     * Ping the database server. If no servers ping back in 10 seconds will throw an exception
     * @throws Exception If no server was found
     */
    private static void waitForStart() throws Exception {
        // Server instance for testing connection
        org.apache.derby.drda.NetworkServerControl server;

        // Use NetworkServerControl.ping() to wait for the
        // Network Server to come up.  We could have used
        // NetworkServerControl to start the server, but the property is
        // easier.
        server = new NetworkServerControl();

        System.out.println("Testing if Network Server is up and running!");
        log("Testing if Network Server is up and running!");
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
                server.ping();
            } catch (Exception e) {
                System.out.println("Try #" + i + " " + e);
                log("Try #" + i + " " + e);
                if (i == 9) {
                    System.out.println("Giving up trying to connect to Network Server!");
                    log("Giving up trying to connect to Network Server!");
                    throw e;
                }
            }
        }
        System.out.println("Derby Network Server now running");
        log("Derby Network Server now running");
        log(getIPAddress());
    }

    public static String getIPAddress () throws UnknownHostException {
        InetAddress localhost = InetAddress.getLocalHost();
        return localhost.getHostAddress().trim();
    }

    public static void log(String msg) {
        try{
            FileWriter fw = new FileWriter("log.txt", true);
            fw.write(msg);
            fw.write(System.lineSeparator());
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
