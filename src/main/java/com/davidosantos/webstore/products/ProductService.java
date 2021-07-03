/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.products;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author david
 */
@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    public List<Product> getHomeProducts() {

        return productRepository.findAll();
    }

    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public ProductCategory saveProduct(ProductCategory productCategory){
        productCategory.setIsActive(true);
        return productCategoryRepository.save(productCategory);
    }

    public List<ProductCategory> getProductCategories(){
        return this.productCategoryRepository.findByIsActive(true);
    }

}
