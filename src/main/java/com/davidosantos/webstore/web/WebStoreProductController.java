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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author david
 */
@Controller

public class WebStoreProductController {

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

    @RequestMapping("/produtos")
    public String productPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String categoryId, Model model) {
        List<ProductCategory> productCategories = productCategoryRepository.findByIsActive(true);

        model.addAttribute("productCategories", productCategories);

        Pageable paging = PageRequest.of(page, size);
        Page products;

        if (categoryId.equals("")) {
            products = productRepository.findByIsActive(true,paging);
        } else {
            products = productRepository.findByProductCategoryIdAndIsActive(categoryId,true,paging);
        }

        model.addAttribute("products", products);
        model.addAttribute("categoryId", categoryId);

        int totalPages = products.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages - 1).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "products";
    }

    @RequestMapping("/detalhes")
    public String productDetailsPage(@RequestParam(defaultValue = "") String id, Model model) {
        List<ProductCategory> productCategories = productCategoryRepository.findByIsActive(true);

        model.addAttribute("productCategories", productCategories);
        model.addAttribute("imageService", imageService);

        Product product;
        if (id.equals("")) {
            product = productRepository.findAll().stream().findFirst().get();
        } else {
            product = productRepository.findById(id).get();
        }

        Pageable paging = PageRequest.of(0, 3);
        Page products = productRepository.findByProductCategoryIdAndIsActive(product.getProductCategory().getId(),true,paging);

        model.addAttribute("product", product);
        model.addAttribute("products", products);

        return "product-details";
    }

    @ExceptionHandler({ Exception.class })
    public String databaseError() {
        return "error-view-name";
    }

}
