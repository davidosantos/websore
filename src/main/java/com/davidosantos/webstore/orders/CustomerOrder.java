/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.orders;

import com.davidosantos.webstore.customers.Customer;
import com.davidosantos.webstore.customers.CustomerAddress;
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
    private double totalQuantity;
    private double totalFreight;
    private double totalDiscount;
    private double totalAmount;
    private Date createdDate;
    private Date updatedDate;
    private CustomerOrderStatus lastCustomerOrderStatus;
    private List<CustomerOrderStatusItem> customerOrderStatusItems;
    private List<CustomerOrderProductItem> customerOrderItems;
    private List<CustomerOrderPaymentItem> customerOrderPaymentItems;

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
        if(customerOrderItems == null){
            customerOrderItems = new ArrayList<CustomerOrderProductItem>();
        }
        return customerOrderItems;
    }

    public void setCustomerOrderItems(List<CustomerOrderProductItem> customerOrderItems) {
        this.customerOrderItems = customerOrderItems;
    }

    public double getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(double totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
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

    public double getTotalFreight() {
        return totalFreight;
    }

    public void setTotalFreight(double totalFreight) {
        this.totalFreight = totalFreight;
    }
    

}
