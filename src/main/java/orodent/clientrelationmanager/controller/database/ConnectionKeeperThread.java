package orodent.clientrelationmanager.controller.database;

import org.apache.derby.drda.NetworkServerControl;
import orodent.clientrelationmanager.model.App;

import java.net.InetAddress;

public class ConnectionKeeperThread extends Thread {            //TODO non mi piacciono tutti questi warning è meglio fare un sistema che se fallisce una query recupera una connessione in background e riprova ad eseguirla
    NetworkServerControl server;
    String ip;
    App app;
    public ConnectionKeeperThread(App app, String ip) {
        this.ip = ip;
        this.app = app;
    }

    @Override
    public void run() {
        try {
            server = new NetworkServerControl(InetAddress.getByName(ip), 1527);
            while (true) {
                server.ping();
                sleep(5000);
            }
        } catch (Exception e) {
            app.setConnection(new ConnectionManager(app).getConnection());
        }
    }
}
