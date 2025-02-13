package orodent.clientrelationmanager.controller.database;

import org.apache.derby.drda.NetworkServerControl;

import java.net.InetAddress;

import static orodent.clientrelationmanager.controller.database.ConnectionManager.NETWORKSERVER_PORT;

public class ServerFinderThread extends Thread{
    private String ip;
    private boolean isValid = false;

    public ServerFinderThread(String ipToTest) {
        ip = ipToTest;
    }
    @Override
    public void run() {
        try {
        NetworkServerControl serverControl = new NetworkServerControl(InetAddress.getByName(ip), NETWORKSERVER_PORT);
        serverControl.ping();
        isValid = true;
        } catch (Exception ignore) {
            ip = null;
        }
    }

    public boolean isValid() {
        return isValid;
    }

    public String getIp() {
        return ip;
    }
}
