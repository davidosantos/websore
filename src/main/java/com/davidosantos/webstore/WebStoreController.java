/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore;

import com.davidosantos.webstore.images.Image;
import com.davidosantos.webstore.images.ImageService;
import com.davidosantos.webstore.products.Product;
import com.davidosantos.webstore.products.ProductRepository;
import com.davidosantos.webstore.products.ProductService;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author david
 */
@Controller
public class WebStoreController {

    @Autowired
    ImageService imageService;
    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;

    @RequestMapping("/")
    public String homePage(Model model) {
        List<Product> products =  productRepository.findByIsActiveAndDisplayInHome(true,true);
        model.addAttribute("products", products);
        model.addAttribute("imageService", imageService);
        return "index";
    }

    

    @ExceptionHandler({Exception.class})
    public String databaseError() {
        return "error-view-name";
    }

    @PostMapping("/images/upload")
    public ResponseEntity<Image> uploadImage(@RequestParam("title") String title, @RequestParam("image") MultipartFile image) throws IOException {
        return ResponseEntity.ok().body(imageService.uploadImage(title, image));
    }

    @GetMapping("/images/download/{id}")
    public ResponseEntity<Image> downloadImage(@PathVariable String id) {

        return ResponseEntity.ok().body(imageService.downloadImage(id));

    }

}
