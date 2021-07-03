/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.products;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author david
 */
public class ProductVariant {

    private String name;
    private List<ProductVariantValue> productVariantValues;
    private boolean isActive;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public List<ProductVariantValue> getProductVariantValues() {
        if(productVariantValues == null){
            productVariantValues = new ArrayList<ProductVariantValue>();
        }
        return productVariantValues;
    }

    public void setProductVariantValues(List<ProductVariantValue> productVariantValues) {
        this.productVariantValues = productVariantValues;
    }

    

}
