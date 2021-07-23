/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.web;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.davidosantos.webstore.carousel.Carousel;
import com.davidosantos.webstore.carousel.CarouselService;
import com.davidosantos.webstore.images.ImageService;
import com.davidosantos.webstore.kart.Kart;
import com.davidosantos.webstore.kart.KartItem;
import com.davidosantos.webstore.kart.KartService;
import com.davidosantos.webstore.products.Product;
import com.davidosantos.webstore.products.ProductCategory;
import com.davidosantos.webstore.products.ProductCategoryRepository;
import com.davidosantos.webstore.products.ProductRepository;
import com.davidosantos.webstore.products.ProductService;

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
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

    @Autowired
    KartService kartService;

    public boolean addKart(String JSESSIONID, Model model) {
        Kart kart;
        if (kartService.countKart(JSESSIONID) > 0) {
            kart = kartService.getKart(JSESSIONID);
        } else {
            kart = new Kart();
        }
        model.addAttribute("kart", kart);
        return true;
    }

    @RequestMapping("/")
    public String homePage(@CookieValue(value = "JSESSIONID") String JSESSIONID, Model model) {
        List<Product> products = productRepository.findByIsActiveAndDisplayInHome(true, true);
        List<ProductCategory> productCategories = productCategoryRepository.findByIsActive(true);
        List<Carousel> carouselItems = carouselService.getDefault();

        addKart(JSESSIONID, model);

        model.addAttribute("products", products);
        model.addAttribute("productCategories", productCategories);
        model.addAttribute("imageService", imageService);
        model.addAttribute("carouselItems", carouselItems);

        return "index";
    }

    @RequestMapping("/produtos")
    public String productPage(@CookieValue(value = "JSESSIONID") String JSESSIONID,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String categoryId, Model model) {
        List<ProductCategory> productCategories = productCategoryRepository.findByIsActive(true);

        addKart(JSESSIONID, model);

        model.addAttribute("productCategories", productCategories);

        Pageable paging = PageRequest.of(page, size);
        Page products;

        if (categoryId.equals("")) {
            products = productRepository.findByIsActive(true, paging);
        } else {
            products = productRepository.findByProductCategoryIdAndIsActive(categoryId, true, paging);
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
    public String productDetailsPage(@CookieValue(value = "JSESSIONID") String JSESSIONID,
            @RequestParam(defaultValue = "") String id, Model model) {
        List<ProductCategory> productCategories = productCategoryRepository.findByIsActive(true);

        addKart(JSESSIONID, model);

        model.addAttribute("productCategories", productCategories);

        Product product;
        if (id.equals("")) {
            product = productRepository.findAll().stream().findFirst().get();
        } else {
            product = productRepository.findById(id).get();
        }

        Pageable paging = PageRequest.of(0, 3);
        Page products = productRepository.findByProductCategoryIdAndIsActive(product.getProductCategory().getId(), true,
                paging);

        model.addAttribute("product", product);
        model.addAttribute("products", products);

        return "product-details";
    }

    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public String createOrder(@CookieValue(value = "JSESSIONID") String JSESSIONID, @RequestParam String action,
            Product product, int quantity) {

        System.out.println("Action: " + action);
        System.out.println("JSESSIONID: " + JSESSIONID);
        System.out.println("product: " + product);
        if (kartService.countKart(JSESSIONID) > 0) {
            Kart kart = kartService.getKart(JSESSIONID);
            kart.setLastUpdateDate(new Date());
            KartItem kartItem = new KartItem();
            kartItem.setProduct(productRepository.findById(product.getId()).get());
            kartItem.setStatus("active");
            kartItem.setQuantity(quantity);

            if (kart.getItems().stream().filter(filter -> filter.getProduct().getId().equals(product.getId()))
                    .count() <= 0) {
                kart.getItems().add(kartItem);
            } else {

                KartItem kartItemFromDB = kart.getItems().stream()
                        .filter(filter -> filter.getProduct().getId().equals(product.getId())).findFirst().get();

                kartItemFromDB.setQuantity(kartItemFromDB.getQuantity() + quantity);
                kartItemFromDB.setProductVariants(product.getProductVariants());
            }

            kartService.save(kart);
        } else {
            Kart kart = new Kart();
            kart.setSessionId(JSESSIONID);
            kart.setCreatedDate(new Date());
            kart.setLastUpdateDate(new Date());
            kart.setStatus("active");
            KartItem kartItem = new KartItem();
            kartItem.setProduct(product);
            kartItem.setStatus("active");
            kartItem.setQuantity(quantity);
            kart.getItems().add(kartItem);
            kartService.save(kart);
        }

        return "redirect:/produtos";
    }

    @ExceptionHandler({ Exception.class })
    public String databaseError() {
        return "error-view-name";
    }

    @GetMapping("/images/downloadDecoded/{id}")
    public ResponseEntity<byte[]> getImageDecoded(@PathVariable String id) {
        HttpHeaders headers = new HttpHeaders();

        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        return new ResponseEntity<>(Base64.getDecoder().decode(imageService.downloadImage(id).getImageData()), headers,
                HttpStatus.OK);
    }

}
