/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.products;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author david
 */
@Document
public class Product {

    @Id
    private String id;
    private String code;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal supplierPrice;
    private BigDecimal discount;
    private BigDecimal freight;
    private int availableQuantity;
    private int totalSupplierQuantity;
    private int lockedQuantity;
    private boolean isActive;
    private boolean displayInHome;
     @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date registeredDate;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date expireDate;
    private Date lastUpdateDate;
    private List<String> imagesId;
    private String providerName;
    private String providerUrl;
    private ProductCategory productCategory;
    private List<ProductVariant> productVariants;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public int getTotalSupplierQuantity() {
        return totalSupplierQuantity;
    }

    public void setTotalSupplierQuantity(int totalSupplierQuantity) {
        this.totalSupplierQuantity = totalSupplierQuantity;
    }

    public int getLockedQuantity() {
        return lockedQuantity;
    }

    public void setLockedQuantity(int lockedQuantity) {
        this.lockedQuantity = lockedQuantity;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isDisplayInHome() {
        return displayInHome;
    }

    public void setDisplayInHome(boolean displayInHome) {
        this.displayInHome = displayInHome;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public List<String> getImagesId() {
        if(imagesId == null){
            imagesId = new ArrayList<String>();
        }
        return imagesId;
    }

    public void setImagesId(List<String> imagesId) {
        this.imagesId = imagesId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderUrl() {
        return providerUrl;
    }

    public void setProviderUrl(String providerUrl) {
        this.providerUrl = providerUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public List<ProductVariant> getProductVariants() {
        if(productVariants == null){
            productVariants = new ArrayList<ProductVariant>();
        }
        return productVariants;
    }

    public void setProductVariants(List<ProductVariant> productVariants) {
        this.productVariants = productVariants;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price.setScale(2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getSupplierPrice() {
        return supplierPrice;
    }

    public void setSupplierPrice(BigDecimal supplierPrice) {
        this.supplierPrice = supplierPrice.setScale(2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount.setScale(2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight.setScale(2, RoundingMode.HALF_EVEN);
    }

}
