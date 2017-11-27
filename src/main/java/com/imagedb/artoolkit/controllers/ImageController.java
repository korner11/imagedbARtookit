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

import javax.servlet.http.HttpServletRequest;
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
    public Image getImageByID(HttpServletRequest request, @PathVariable String id){
        Image image = this.imageRepository.findImageById(id);
        System.out.println(getURL(request));
        return image;
    }

    @GetMapping(value = "/getImage/{id}",produces = MediaType.IMAGE_JPEG_VALUE )
    public byte[] getImage(@PathVariable String id){
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

    public static String getURL(HttpServletRequest req) {

        String scheme = req.getScheme();             // http
        String serverName = req.getServerName();     // hostname.com
        int serverPort = req.getServerPort();        // 80
        String contextPath = req.getContextPath();   // /mywebapp
        String servletPath = req.getServletPath();   // /servlet/MyServlet
        String pathInfo = req.getPathInfo();         // /a/b;c=123
        String queryString = req.getQueryString();          // d=789

        // Reconstruct original requesting URL
        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);

        if (serverPort != 80 && serverPort != 443) {
            url.append(":").append(serverPort);
        }

        url.append(contextPath).append(servletPath);

        if (pathInfo != null) {
            url.append(pathInfo);
        }
        if (queryString != null) {
            url.append("?").append(queryString);
        }
        return url.toString();
    }

}
