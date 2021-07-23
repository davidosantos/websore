package com.davidosantos.webstore.kart;

import java.util.ArrayList;
import java.util.List;

import com.davidosantos.webstore.products.Product;
import com.davidosantos.webstore.products.ProductVariant;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class KartItem {
    int quantity;
    String status;
    @DBRef
    Product product;
    List<ProductVariant> productVariants;
    
    public List<ProductVariant> getProductVariants() {
        if(productVariants == null) {
            productVariants = new ArrayList<ProductVariant>();
        }
        return productVariants;
    }
    public void setProductVariants(List<ProductVariant> variants) {
        this.productVariants = variants;
    }
    
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    
}
