package com.imagedb.artoolkit.controllers;

import com.imagedb.artoolkit.entity.Image;
import com.imagedb.artoolkit.entity.ImageStorage;
import com.imagedb.artoolkit.entity.QImage;
import com.imagedb.artoolkit.entity.Wifi;
import com.imagedb.artoolkit.entity.requests.AddImageRequest;
import com.imagedb.artoolkit.repositories.ImageRepository;
import com.imagedb.artoolkit.repositories.ImageStorageRepository;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.io.IOUtils;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@RestController
@RequestMapping("/imageStorage")
public class ImageStorageController {

    private ImageStorageRepository imageStorageRepository;

    @Autowired
    public ImageStorageController(ImageStorageRepository imageStorageRepository) {
        this.imageStorageRepository = imageStorageRepository;
    }


    @GetMapping(value = "/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@PathVariable String imageId) throws IOException {
        //File imageFile = new File("testImages/image.jpg");
        //FileInputStream f = new FileInputStream(imageFile);
        MongoClient mongoClient = new MongoClient("localhost"); // connect to mongodb
        DB db = mongoClient.getDB("dynamsoft");


        GridFS gfsPhoto = new GridFS(db, "twain");
        GridFSDBFile imageForOutput = gfsPhoto.findOne(imageId);
        InputStream f = imageForOutput.getInputStream();

        return IOUtils.toByteArray(f);
    }


    @PostMapping("/")
    public void addNewImage(@RequestBody AddImageRequest requestImage){

        ImageStorage image = new ImageStorage(requestImage.getName());
        byte[] decodedString = Base64.getDecoder().decode(requestImage.getImage());
        InputStream input = new ByteArrayInputStream(decodedString);

        MongoClient mongoClient = new MongoClient("localhost"); // connect to mongodb
        DB db = mongoClient.getDB("dynamsoft");                 // get database

        GridFS fs = new GridFS(db, "ImageStorage");                    // GridFS for storing images

        GridFSInputFile file = fs.createFile(input, requestImage.getName());

        file.setContentType("image");
        file.save();
        image.setImage("http://localhost:8080/imageStorage/" + requestImage.getName());
        /*
        Binary binary = new Binary(decodedString);
        image.setImage(binary);*/
        image.setWifiList(requestImage.getWifiList());
        this.imageStorageRepository.insert(image);

    }

}
