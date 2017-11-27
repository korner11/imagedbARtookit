package com.imagedb.artoolkit.controllers;

import com.imagedb.artoolkit.entity.Image;
import com.imagedb.artoolkit.entity.QImage;
import com.imagedb.artoolkit.entity.Wifi;
import com.imagedb.artoolkit.entity.requests.AddImageRequest;
import com.imagedb.artoolkit.repositories.ImageRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.io.IOUtils;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;


@RestController
@RequestMapping("/images")
public class ImageController {

    private ImageRepository imageRepository;

    @Autowired
    public ImageController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @GetMapping("/")
    public List<Image> getAllImages(){

        List<Image> images = this.imageRepository.findAll();
        return images;
    }

    @GetMapping(value = "/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage() throws IOException {
        File imageFile = new File("testImages/image.jpg");
        FileInputStream f = new FileInputStream(imageFile);
        return IOUtils.toByteArray(f);
    }

    @GetMapping("/getImageByID/{id}")
    public Image getImageByID(@PathVariable String id){
        Image image = this.imageRepository.findImageById(id);
        return image;
    }

    @GetMapping(value = "/getImageByIDimage/{id}",produces = MediaType.IMAGE_JPEG_VALUE )
    public byte[]  getImageByIDimage(@PathVariable String id){
        Image image = this.imageRepository.findImageById(id);
        return image.getImage().getData();
    }

    @PostMapping("/getImagesByBssid")
    public List<Image> getImagesByBssid(@RequestBody List<Wifi> wifilist){

        System.out.println(wifilist);
        List<Image> allImages = new ArrayList<>();

        for( Wifi wifi : wifilist) {

            String bssid = wifi.getBssid();
            QImage qImage = new QImage("image");
            BooleanExpression filterContainingBssid = qImage.wifiList.any().bssid.eq(bssid);
            List<Image> images = (List<Image>) this.imageRepository.findAll(filterContainingBssid);
            allImages.addAll(images);
        }
        return allImages;

    }

    @PostMapping("/getImagesIDsByBssid")
    public List<String> getImagesIDsByBssid(@RequestBody List<Wifi> wifilist){

        List<String> allIDs = new ArrayList<>();

        for( Wifi wifi : wifilist) {

            String bssid = wifi.getBssid();
            QImage qImage = new QImage("image");
            BooleanExpression filterContainingBssid = qImage.wifiList.any().bssid.eq(bssid);

            List<Image> images = (List<Image>) this.imageRepository.findAll(filterContainingBssid);

            for(Image image: images){
                allIDs.add(image.getId());
            }
        }
        return allIDs;
    }


    @PostMapping("/")
    public void addNewImage(@RequestBody AddImageRequest requestImage){

            Image image = new Image(requestImage.getName());
            byte[] decodedString = Base64.getDecoder().decode(requestImage.getImage());
            Binary binary = new Binary(decodedString);
            image.setImage(binary);
            image.setWifiList(requestImage.getWifiList());
            this.imageRepository.insert(image);

    }


}
