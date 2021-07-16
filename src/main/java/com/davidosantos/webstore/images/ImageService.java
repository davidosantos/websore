/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.images;

import java.io.IOException;
import org.bson.internal.Base64;
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
        image.setImageData(Base64.encode(file.getBytes()));
        return imageRepository.insert(image);

    }

    public Image downloadImage(String id) {
        System.out.println("Downloading Image: " + id);
        return imageRepository.findById(id).orElse(new Image());
    }

    public void delete(String id) {
        imageRepository.deleteById(id);
    }

}
