/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.products;

import java.util.List;

import com.davidosantos.webstore.images.ImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    ProductBrandRepository productBrandRepository;

    @Autowired
    ImageService imageService;

    public List<Product> getHomeProducts() {

        return productRepository.findAll();
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public ProductCategory saveProductCategory(ProductCategory productCategory) {
        productCategory.setIsActive(true);
        return productCategoryRepository.save(productCategory);
    }

    public List<ProductBrand> getProductBrands() {
        return this.productBrandRepository.findByIsActive(true);
    }

    public ProductBrand saveProductBrand(ProductBrand productBrand) {
        productBrand.setIsActive(true);
        return productBrandRepository.save(productBrand);
    }

    public ProductBrand getProductBrandById(String id) {
        
        return productBrandRepository.findById(id).get();
    }

    public ProductCategory getProductCategoryById(String id) {
        
        return productCategoryRepository.findById(id).get();
    }


    public List<ProductCategory> getProductCategories() {
        return this.productCategoryRepository.findByIsActive(true);
    }

    public Page getAllProducts(Pageable paging) {
        return productRepository.findAll(paging);
    }

    public Page getByDefaultFilter(String code, String name, String providerName, Pageable paging) {
        return productRepository
                .findByCodeContainingIgnoreCaseAndNameContainingIgnoreCaseAndProviderNameContainingIgnoreCase(code,
                        name, providerName, paging);
    }

    public Product getById(String productid) {
        return productRepository.findById(productid).get();
    }

    public Product saveImage(String productid, String imageId) {
        Product product = productRepository.findById(productid).get();

        product.getImagesId().add(imageId);

        return productRepository.save(product);
    }

    public Product removeImage(String productid, int imageIndex) {
        Product product = productRepository.findById(productid).get();

        imageService.delete(product.getImagesId().get(imageIndex));

        product.getImagesId().remove(product.getImagesId().get(imageIndex));

        return productRepository.save(product);
    }

    public void deleteProductBrand(ProductBrand productBrand) {
        productBrand.setIsActive(false);
        productBrandRepository.save(productBrand);
    }

    public void deleteProductCategory(ProductCategory productCategory) {
        productCategory.setIsActive(false);
        productCategoryRepository.save(productCategory);
    }

}
