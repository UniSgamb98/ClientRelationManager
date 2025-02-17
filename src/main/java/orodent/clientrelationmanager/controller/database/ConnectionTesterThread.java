package orodent.clientrelationmanager.controller.database;

import org.apache.derby.drda.NetworkServerControl;

import java.net.InetAddress;

public class ConnectionTesterThread extends Thread{
    private final int ipToTest;
    private final IpAddressesBean ipsBean;

    public ConnectionTesterThread(int ipToTest) {
        this.ipToTest = ipToTest;
        ipsBean = new IpAddressesBean();
    }

    @Override
    public void run() {
        try {
            NetworkServerControl s1 = new NetworkServerControl(InetAddress.getByName(ipsBean.getIpAddresses(ipToTest)), 1527);
            s1.ping();
            ipsBean.setIpIsReachable(ipToTest);
        } catch (Exception ignored) {}
    }
}
