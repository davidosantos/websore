/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.orders;

import com.davidosantos.webstore.products.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;

/**
 *
 * @author david
 */
public class CustomerOrderProductItem {

    @Id
    private String id;
    private Product product;
    private List<CustomerOrderItemVariant> customerOrderItemVariants;
    private String status;
    private BigDecimal quantity;
    private BigDecimal supplierQuantityBought;
    private BigDecimal unitDiscount;
    private BigDecimal totalDiscount;
    private BigDecimal discountPercent;
    private BigDecimal unitFreight;
    private BigDecimal supplierEstimatedFreight;
    private BigDecimal supplierEstimatedTotal;
    private BigDecimal liquidTotal;
    private BigDecimal total;
    private BigDecimal supplierPoweredPrice;
    private BigDecimal supplierPoweredFreight;
    private BigDecimal supplierPoweredDiscount;
    private BigDecimal supplierPoweredTotal;

    private BigDecimal profit;
    private BigDecimal profitPercent;
    
    public BigDecimal getSupplierPoweredDiscount() {
        return supplierPoweredDiscount;
    }

    public void setSupplierPoweredDiscount(BigDecimal supplierPoweredDiscount) {
        this.supplierPoweredDiscount = supplierPoweredDiscount;
    }
    public BigDecimal getSupplierPoweredTotal() {
        return supplierPoweredTotal;
    }

    public void setSupplierPoweredTotal(BigDecimal supplierPoweredTotal) {
        this.supplierPoweredTotal = supplierPoweredTotal;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<CustomerOrderItemVariant> getCustomerOrderItemVariants() {
        if (customerOrderItemVariants == null) {
            customerOrderItemVariants = new ArrayList<CustomerOrderItemVariant>();
        }
        return customerOrderItemVariants;
    }

    public void setCustomerOrderItemVariants(List<CustomerOrderItemVariant> customerOrderItemVariants) {
        this.customerOrderItemVariants = customerOrderItemVariants;
    }

    public BigDecimal getSupplierEstimatedTotal() {
        return supplierEstimatedTotal;
    }

    public void setSupplierEstimatedTotal(BigDecimal supplierEstimatedTotal) {
        this.supplierEstimatedTotal = supplierEstimatedTotal;
    }
    
    public BigDecimal getSupplierEstimatedFreight() {
        return supplierEstimatedFreight;
    }

    public void setSupplierEstimatedFreight(BigDecimal supplierEstimatedFreight) {
        this.supplierEstimatedFreight = supplierEstimatedFreight;
    }

    public BigDecimal getSupplierPoweredPrice() {
        return supplierPoweredPrice;
    }

    public void setSupplierPoweredPrice(BigDecimal supplierPoweredPrice) {
        this.supplierPoweredPrice = supplierPoweredPrice;
    }

    public BigDecimal getSupplierPoweredFreight() {
        return supplierPoweredFreight;
    }

    public void setSupplierPoweredFreight(BigDecimal supplierPoweredFreight) {
        this.supplierPoweredFreight = supplierPoweredFreight;
    }

    public BigDecimal getSupplierQuantityBought() {
        return supplierQuantityBought;
    }

    public void setSupplierQuantityBought(BigDecimal supplierQuantityBought) {
        this.supplierQuantityBought = supplierQuantityBought;
    }

}
