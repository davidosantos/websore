/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.images;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * https://www.baeldung.com/spring-boot-mongodb-upload-file
 *
 * @author david
 */
@Document
public class Image {

    @Id
    private String id;
    private String title;
    private Binary imageData;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Binary getImageData() {
        return imageData;
    }

    public void setImageData(Binary imageData) {
        this.imageData = imageData;
    }

    public String getId() {
        return id;
    }

}
