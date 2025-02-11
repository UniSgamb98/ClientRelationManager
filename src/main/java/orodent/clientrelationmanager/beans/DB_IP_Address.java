package orodent.clientrelationmanager.beans;

import java.io.Serializable;

public class DB_IP_Address implements Serializable {
    private String ip_address;

    public String getIp_address() {
        return ip_address;
    }
    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }
}
