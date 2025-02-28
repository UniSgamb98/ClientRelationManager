package orodent.clientrelationmanager.controller.database;

import java.util.ArrayList;
import java.util.Arrays;

public class  IpAddressesBean {
    private final ArrayList<String> ipAddresses = new ArrayList<>(Arrays.asList("192.168.1.138", "192.168.1.136"));
    private static ArrayList<Boolean> ipIsReachable = new ArrayList<>(Arrays.asList(false, false));

    public void initialize(){
        ipIsReachable = new ArrayList<>(Arrays.asList(false, false));
    }

    public void setIpIsReachable(int ipPosition) {
        ipIsReachable.set(ipPosition, true);
    }

    public boolean isIpReachable(int ipPosition) {
        return ipIsReachable.get(ipPosition);
    }

    public String getIpAddresses(int ipPosition) {
        return ipAddresses.get(ipPosition);
    }

    public int getIpListSize(){
        return ipAddresses.size();
    }
}

