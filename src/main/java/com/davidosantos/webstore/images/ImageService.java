/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.images;

import java.io.IOException;
import java.util.Base64;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author david
 */
@Service
public class ImageService {

    @Autowired
    ImageRepository imageRepository;

    public Image uploadImage(String title, MultipartFile file) throws IOException {
        Image image = new Image();
        image.setTitle(title);
        image.setImageData(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        return imageRepository.insert(image);

    }

    public Image downloadImage(String id) {
        System.out.println("Downloading Image: " + id);
        return imageRepository.findById(id).orElse(new Image());
    }

    public String downloadImageData(String id) {
        System.out.println("Downloading Image data: " + id);
        if (id == null) {
            return new String();
        }
        Image image = imageRepository.findById(id).orElse(new Image());
        if (image.getImageData() != null && image.getImageData().length() > 0) {

            return new String(Base64.getEncoder().encodeToString(image.getImageData().getData()));
        } else {
            return new String();
        }
    }

}
