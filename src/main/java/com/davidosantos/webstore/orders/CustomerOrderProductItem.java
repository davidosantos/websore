/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.orders;

import com.davidosantos.webstore.products.Product;
import com.davidosantos.webstore.products.ProductVariant;

/**
 *
 * @author david
 */
public class CustomerOrderProductItem {
    private Product product;
    private ProductVariant selectedproductVariant;
    private String status;
    private double quantity;
    private double discount;
    private double freight;
    private double liquidTotal;
    private double total;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductVariant getSelectedproductVariant() {
        return selectedproductVariant;
    }

    public void setSelectedproductVariant(ProductVariant selectedproductVariant) {
        this.selectedproductVariant = selectedproductVariant;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public double getLiquidTotal() {
        return liquidTotal;
    }

    public void setLiquidTotal(double liquidTotal) {
        this.liquidTotal = liquidTotal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
