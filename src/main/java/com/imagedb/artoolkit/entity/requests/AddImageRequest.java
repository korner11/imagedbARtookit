package com.imagedb.artoolkit.entity.requests;

import com.imagedb.artoolkit.entity.Wifi;

import java.util.List;

/**
 * Created by Michal on 30.10.2017.
 */
public class AddImageRequest {
    private String name;
    private String image;
    private String text;
    private List<Wifi> wifiList;

    public AddImageRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public List<Wifi> getWifiList() {
        return wifiList;
    }

    public void setWifiList(List<Wifi> wifiList) {
        this.wifiList = wifiList;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
