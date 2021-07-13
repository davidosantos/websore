/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.supplier;

import com.davidosantos.webstore.orders.CustomerOrder;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author david
 */
@Document
public class SupplierOrder {

    @Id
    private String id;
    @DBRef
    private Supplier supplier;
    @DBRef
    private CustomerOrder customerOrder;
    private String code;
    private String notes;
    private List<SupplierOrderItem> supplierOrderItems;
    private BigDecimal totalAmount;
    private BigDecimal totalFreight;
    private BigDecimal totalDiscount;
    private BigDecimal totalQuantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalFreight() {
        return totalFreight;
    }

    public void setTotalFreight(BigDecimal totalfreight) {
        this.totalFreight = totalfreight;
    }

    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public List<SupplierOrderItem> getSupplierOrderItems() {
        return supplierOrderItems;
    }

    public void setSupplierOrderItems(List<SupplierOrderItem> supplierOrderItems) {
        this.supplierOrderItems = supplierOrderItems;
    }
}
