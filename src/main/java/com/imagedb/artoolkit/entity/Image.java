package com.imagedb.artoolkit.entity;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Images")
public class Image {

    @Id
    private String id;
    private String name;
    private Binary image;
    private String text;
    private List<Wifi> wifiList;

    public Image() {
    }

    public Image(String name) {
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

    public Binary getImage() {
        return image;
    }

    public void setImage(Binary image) {
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
