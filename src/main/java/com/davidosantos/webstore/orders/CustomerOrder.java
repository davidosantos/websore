/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.orders;

import com.davidosantos.webstore.customers.Customer;
import com.davidosantos.webstore.customers.CustomerAddress;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Id;
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
    private CustomerAddress selectedCustomerAddress;
    private BigDecimal totalQuantity;
    private BigDecimal totalFreight;
    private BigDecimal totalDiscount;
    private BigDecimal totalDiscountPercent;
    private BigDecimal totalLiquid;
    private BigDecimal totalAmount;
    private BigDecimal supplierTotalAmount;
    private BigDecimal totalProfit;
    private BigDecimal totalProfitPercent;
    private Date createdDate;
    private Date updatedDate;
    private CustomerOrderStatus lastCustomerOrderStatus;
    private List<CustomerOrderStatusItem> customerOrderStatusItems;
    private List<CustomerOrderProductItem> customerOrderItems;
    private List<CustomerOrderPaymentItem> customerOrderPaymentItems;
    private List<CustomerOrderUpdateHistory> customerOrderUpdateHistorys;

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

    public BigDecimal getTotalFreight() {
        return totalFreight;
    }

    public void setTotalFreight(BigDecimal totalFreight) {
        this.totalFreight = totalFreight;
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

    public BigDecimal getSupplierTotalAmount() {
        return supplierTotalAmount;
    }

    public void setSupplierTotalAmount(BigDecimal supplierTotalAmount) {
        this.supplierTotalAmount = supplierTotalAmount;
    }

    public BigDecimal getTotalLiquid() {
        return totalLiquid;
    }

    public void setTotalLiquid(BigDecimal totalLiquid) {
        this.totalLiquid = totalLiquid;
    }

}
