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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (isActive ? 1231 : 1237);
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((productVariantValues == null) ? 0 : productVariantValues.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProductVariant other = (ProductVariant) obj;
        if (isActive != other.isActive)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (productVariantValues == null) {
            if (other.productVariantValues != null)
                return false;
        } else if (!productVariantValues.equals(other.productVariantValues))
            return false;
        return true;
    }

    

}
