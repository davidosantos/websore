/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.orders;

import java.util.Date;

/**
 *
 * @author david
 */
public class CustomerOrderStatusItem {

    private CustomerOrderStatus customerOrderStatus;
    private Date createdDate;
    private String createdBy;

    public CustomerOrderStatus getCustomerOrderStatus() {
        return customerOrderStatus;
    }

    public void setCustomerOrderStatus(CustomerOrderStatus customerOrderStatus, String createdBy) {
        this.createdDate = new Date();
        this.customerOrderStatus = customerOrderStatus;
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

}
