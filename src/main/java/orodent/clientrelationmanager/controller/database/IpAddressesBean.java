package orodent.clientrelationmanager.controller.database;

import orodent.clientrelationmanager.controller.main.MainController;

import java.util.ArrayList;
import java.util.List;

public class  IpAddressesBean {
    private final List<String> ipAddresses;
    private static ArrayList<Boolean> ipIsReachable;

    public IpAddressesBean(){
        ipAddresses = new MainController().getApp().getConfigs().get("indirizzi ip");
        ipIsReachable = new ArrayList<>();
        for (String ignored : ipAddresses){
            ipIsReachable.add(false);
        }
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

