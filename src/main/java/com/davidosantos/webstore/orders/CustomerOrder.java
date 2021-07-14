/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.orders;

import com.davidosantos.webstore.customers.Customer;
import com.davidosantos.webstore.customers.CustomerAddress;
import com.davidosantos.webstore.supplier.Supplier;
import com.davidosantos.webstore.supplier.SupplierOrder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author david
 */
@Document
public class CustomerOrder {

    @Id
    private String id;
    private String code;
    private Customer customer;
    private String notes;
    private CustomerAddress selectedCustomerAddress;
    private BigDecimal totalQuantity;
    private BigDecimal totalEstimatedFreight;
    private BigDecimal totalPoweredFreight;
    private BigDecimal totalDiscount;
    private BigDecimal supplierPoweredDiscount;
    private BigDecimal totalDiscountPercent;
    private BigDecimal totalLiquid;
    private BigDecimal totalAmount;
    private BigDecimal supplierTotalEstimatedAmount;
    private BigDecimal supplierTotalPoweredAmount;
    private BigDecimal totalProfit;
    private BigDecimal totalProfitPercent;
    private Date createdDate;
    private Date updatedDate;
    private CustomerOrderStatus lastCustomerOrderStatus;
    private List<CustomerOrderStatusItem> customerOrderStatusItems;
    private List<CustomerOrderProductItem> customerOrderItems;
    private List<CustomerOrderPaymentItem> customerOrderPaymentItems;
    private List<CustomerOrderUpdateHistory> customerOrderUpdateHistorys;
    @DBRef
    private List<Supplier> suppliers;
    @DBRef
    private List<SupplierOrder> supplierOrders;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CustomerAddress getSelectedCustomerAddress() {
        return selectedCustomerAddress;
    }

    public void setSelectedCustomerAddress(CustomerAddress selectedCustomerAddress) {
        this.selectedCustomerAddress = selectedCustomerAddress;
    }

    public List<CustomerOrderProductItem> getCustomerOrderItems() {
        if (customerOrderItems == null) {
            customerOrderItems = new ArrayList<CustomerOrderProductItem>();
        }
        return customerOrderItems;
    }

    public void setCustomerOrderItems(List<CustomerOrderProductItem> customerOrderItems) {
        this.customerOrderItems = customerOrderItems;
    }
    
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public CustomerOrderStatus getLastCustomerOrderStatus() {
        return lastCustomerOrderStatus;
    }

    public void setLastCustomerOrderStatus(CustomerOrderStatus lastCustomerOrderStatus) {
        this.lastCustomerOrderStatus = lastCustomerOrderStatus;
    }

    public List<CustomerOrderStatusItem> getCustomerOrderStatusItems() {
        return customerOrderStatusItems;
    }

    public void setCustomerOrderStatusItems(List<CustomerOrderStatusItem> customerOrderStatusItems) {
        this.customerOrderStatusItems = customerOrderStatusItems;
    }

    public List<CustomerOrderPaymentItem> getCustomerOrderPaymentItems() {
        return customerOrderPaymentItems;
    }

    public void setCustomerOrderPaymentItems(List<CustomerOrderPaymentItem> customerOrderPaymentItems) {
        this.customerOrderPaymentItems = customerOrderPaymentItems;
    }

    public List<CustomerOrderUpdateHistory> getCustomerOrderUpdateHistorys() {
        if (customerOrderUpdateHistorys == null) {
            customerOrderUpdateHistorys = new ArrayList<CustomerOrderUpdateHistory>();
        }
        return customerOrderUpdateHistorys;
    }

    public void setCustomerOrderUpdateHistorys(List<CustomerOrderUpdateHistory> customerOrderUpdateHistorys) {
        this.customerOrderUpdateHistorys = customerOrderUpdateHistorys;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalEstimatedFreight() {
        return totalEstimatedFreight;
    }

    public void setTotalEstimatedFreight(BigDecimal totalFreight) {
        this.totalEstimatedFreight = totalFreight;
    }

    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalDiscountPercent() {
        return totalDiscountPercent;
    }

    public void setTotalDiscountPercent(BigDecimal totalDiscountPercent) {
        this.totalDiscountPercent = totalDiscountPercent;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

    public BigDecimal getTotalProfitPercent() {
        return totalProfitPercent;
    }

    public void setTotalProfitPercent(BigDecimal totalProfitPercent) {
        this.totalProfitPercent = totalProfitPercent;
    }

    public BigDecimal getSupplierTotalEstimatedAmount() {
        return supplierTotalEstimatedAmount;
    }

    public void setSupplierTotalEstimatedAmount(BigDecimal supplierTotalAmount) {
        this.supplierTotalEstimatedAmount = supplierTotalAmount;
    }

    public BigDecimal getTotalLiquid() {
        return totalLiquid;
    }

    public void setTotalLiquid(BigDecimal totalLiquid) {
        this.totalLiquid = totalLiquid;
    }

    public List<Supplier> getSuppliers() {
        if(suppliers == null){
            suppliers = new ArrayList<Supplier>();
        }
        return suppliers;
    }

    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public List<SupplierOrder> getSupplierOrders() {
        if(supplierOrders == null){
            supplierOrders = new ArrayList<SupplierOrder>();
        }
        return supplierOrders;
    }

    public void setSupplierOrders(List<SupplierOrder> supplierOrders) {
        this.supplierOrders = supplierOrders;
    }

    public BigDecimal getTotalPoweredFreight() {
        return totalPoweredFreight;
    }

    public void setTotalPoweredFreight(BigDecimal totalPoweredFreight) {
        this.totalPoweredFreight = totalPoweredFreight;
    }

    public BigDecimal getSupplierTotalPoweredAmount() {
        return supplierTotalPoweredAmount;
    }

    public void setSupplierTotalPoweredAmount(BigDecimal supplierTotalPoweredAmount) {
        this.supplierTotalPoweredAmount = supplierTotalPoweredAmount;
    }

    public BigDecimal getSupplierPoweredDiscount() {
        return supplierPoweredDiscount;
    }

    public void setSupplierPoweredDiscount(BigDecimal supplierPoweredDiscount) {
        this.supplierPoweredDiscount = supplierPoweredDiscount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


}
