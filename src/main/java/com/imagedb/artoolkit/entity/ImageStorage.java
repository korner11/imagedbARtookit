package com.imagedb.artoolkit.entity;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "ImageStorage")
public class ImageStorage {

    @Id
    private String id;
    private String name;
    private String image;

    List<Wifi> wifiList;

    public ImageStorage() {
    }

    public ImageStorage(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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


}
