package com.imagedb.artoolkit.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


public class Wifi {

    String ssid;
    String bssid;
    double strenght;

    public Wifi() {
    }

    public Wifi(String ssid, String bssid, double strenght) {
        this.ssid = ssid;
        this.bssid = bssid;
        this.strenght = strenght;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public double getStrenght() {
        return strenght;
    }

    public void setStrenght(double strenght) {
        this.strenght = strenght;
    }
}
