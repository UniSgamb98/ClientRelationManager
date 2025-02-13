package orodent.clientrelationmanager.controller.database;

import org.apache.derby.drda.NetworkServerControl;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;

import static orodent.clientrelationmanager.controller.database.ConnectionManager.NETWORKSERVER_PORT;

public class ConnectionHelperThread extends Thread{
    private String operativeServerIP;
    private NetworkServerControl server;
    ArrayList<String> ips = new ArrayList<>(Arrays.asList("192.168.1.136", "192.168.1.138"));


    public ConnectionHelperThread() throws NoServerFoundException {
        findOperativeServerIP();
    }
    @Override
    public void run() {
        if (operativeServerIP != null) {    //keep checking if server is up
            try {
                server.ping();
                sleep(5000);
            } catch (Exception e) {
                operativeServerIP = null;
            }
        } else {
            try {
                findOperativeServerIP();
            } catch (NoServerFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void findOperativeServerIP() throws NoServerFoundException {
        int triesPerIP = 1; //change this to cycle more time on every ip
        int tries = 0;
        while(operativeServerIP == null && tries < ips.size() * triesPerIP) {
            try {
                NetworkServerControl s1 = new NetworkServerControl(InetAddress.getByName(ips.get(tries % ips.size())), NETWORKSERVER_PORT);
                s1.ping();
                operativeServerIP = ips.get(tries % ips.size());
                server = s1;
            } catch (Exception e) {
                tries++;
            }
        }
        if (operativeServerIP == null) {
            throw new NoServerFoundException();
        }
    }

    public String getOperativeServerIP() {
        return operativeServerIP;
    }

    public NetworkServerControl getServer() {
        return server;
    }
}
