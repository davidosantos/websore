/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.web;

import com.davidosantos.webstore.carousel.Carousel;
import com.davidosantos.webstore.carousel.CarouselService;
import com.davidosantos.webstore.images.ImageService;
import com.davidosantos.webstore.products.Product;
import com.davidosantos.webstore.products.ProductCategory;
import com.davidosantos.webstore.products.ProductCategoryRepository;
import com.davidosantos.webstore.products.ProductRepository;
import com.davidosantos.webstore.products.ProductService;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    CarouselService carouselService;

    @RequestMapping("/")
    public String homePage(Model model) {
        List<Product> products = productRepository.findByIsActiveAndDisplayInHome(true, true);
        List<ProductCategory> productCategories = productCategoryRepository.findByIsActive(true);
        List<Carousel> carouselItems = carouselService.getDefault();

        model.addAttribute("products", products);
        model.addAttribute("productCategories", productCategories);
        model.addAttribute("imageService", imageService);
        model.addAttribute("carouselItems", carouselItems);
        return "index";
    }

   


    @ExceptionHandler({Exception.class})
    public String databaseError() {
        return "error-view-name";
    }



    @GetMapping("/images/downloadDecoded/{id}")
    public ResponseEntity<byte[]> getImageDecoded(@PathVariable String id) {
        HttpHeaders headers = new HttpHeaders();

        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        return new ResponseEntity<>(
                Base64.getDecoder()
                        .decode(
                                imageService
                                        .downloadImage(id)
                                        .getImageData()),
                headers, HttpStatus.OK);
    }

}
