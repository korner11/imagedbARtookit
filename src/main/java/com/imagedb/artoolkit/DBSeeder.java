package com.imagedb.artoolkit;

import com.imagedb.artoolkit.entity.Image;
import com.imagedb.artoolkit.entity.Wifi;
import com.imagedb.artoolkit.repositories.ImageRepository;
import org.bson.types.Binary;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

@Component
public class DBSeeder implements CommandLineRunner{

    private ImageRepository imageRepository;


    public DBSeeder(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public void run(String... strings) throws Exception {

        try {
            File imageFile = new File("testImages/image.jpg");
            FileInputStream f = new FileInputStream(imageFile);

            byte b[] = new byte[f.available()];
            f.read(b);
            Binary data = new Binary(b);
            Image image2 = new Image("image2");
            image2.setImage(data);
            f.close();

            Image image1 = new Image("image1");
            Wifi wifi1 = new Wifi("test1","test1",1);
            Wifi wifi2 = new Wifi("test2","test2",2);
            image1.setWifiList(Arrays.asList(wifi1,wifi2));


            Image image3 = new Image("image3");
            Wifi wifi21 = new Wifi("test31","test31",1);
            Wifi wifi22 = new Wifi("test32","test32",2);
            image3.setWifiList(Arrays.asList(wifi21,wifi22));

            Image image4 = new Image("image4");
            Wifi wifi41 = new Wifi("test41","test42",1);
            Wifi wifi42 = new Wifi("test42","test2",2);
            image4.setWifiList(Arrays.asList(wifi41,wifi42));

            Image image5 = new Image("image5");
            Wifi wifi51 = new Wifi("test5","test5",1);
            Wifi wifi52 = new Wifi("test51","test51",2);
            image5.setWifiList(Arrays.asList(wifi51,wifi52));

            this.imageRepository.deleteAll();

            this.imageRepository.save(Arrays.asList(image1,image3,image4,image5));
        }catch (IOException e){
            e.printStackTrace();
        }


    }
}
