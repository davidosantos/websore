/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.orders;

import com.davidosantos.webstore.products.Product;
import com.davidosantos.webstore.products.ProductVariant;
import java.math.BigDecimal;
import org.springframework.data.annotation.Id;

/**
 *
 * @author david
 */
public class CustomerOrderProductItem {
    @Id
    private String id;
    private Product product;
    private ProductVariant selectedproductVariant;
    private String status;
    private BigDecimal quantity;
    private BigDecimal unitDiscount;
    private BigDecimal totalDiscount;
    private BigDecimal discountPercent;
    private BigDecimal unitFreight;
    private BigDecimal totalFreight;
    private BigDecimal liquidTotal;
    private BigDecimal total;
    private BigDecimal supplierTotal;
    private BigDecimal profit;
    private BigDecimal profitPercent;

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

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public void incrementQuantityBy(BigDecimal quantity) {
        this.quantity = this.quantity.add(quantity);
    }
    public BigDecimal getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(BigDecimal discountPercent) {
        this.discountPercent = discountPercent;
    }

    public BigDecimal getLiquidTotal() {
        return liquidTotal;
    }

    public void setLiquidTotal(BigDecimal liquidTotal) {
        this.liquidTotal = liquidTotal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getProfitPercent() {
        return profitPercent;
    }

    public void setProfitPercent(BigDecimal profitPercent) {
        this.profitPercent = profitPercent;
    }

    public BigDecimal getUnitDiscount() {
        return unitDiscount;
    }

    public void setUnitDiscount(BigDecimal unitDiscount) {
        this.unitDiscount = unitDiscount;
    }

    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public BigDecimal getUnitFreight() {
        return unitFreight;
    }

    public void setUnitFreight(BigDecimal unitFreight) {
        this.unitFreight = unitFreight;
    }

    public BigDecimal getTotalFreight() {
        return totalFreight;
    }

    public void setTotalFreight(BigDecimal totalFreight) {
        this.totalFreight = totalFreight;
    }

    public BigDecimal getSupplierTotal() {
        return supplierTotal;
    }

    public void setSupplierTotal(BigDecimal supplierTotal) {
        this.supplierTotal = supplierTotal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
