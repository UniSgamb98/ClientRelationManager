package orodent.clientrelationmanager.controller.database;

import org.apache.derby.drda.NetworkServerControl;

import java.net.InetAddress;

import static orodent.clientrelationmanager.controller.database.ConnectionManager.NETWORKSERVER_PORT;

public class ConnectionTesterThread extends Thread{
    private final int ipToTest;
    private final IpAddressesBean ipsBean;

    public ConnectionTesterThread(int ipToTest) throws NoServerFoundException {
        this.ipToTest = ipToTest;
        ipsBean = new IpAddressesBean();
    }

    @Override
    public void run() {
        try {
            NetworkServerControl s1 = new NetworkServerControl(InetAddress.getByName(ipsBean.getIpAddresses(ipToTest)), NETWORKSERVER_PORT);
            s1.ping();
            ipsBean.setIpIsReachable(ipToTest);
        } catch (Exception ignored) {}
    }
}
