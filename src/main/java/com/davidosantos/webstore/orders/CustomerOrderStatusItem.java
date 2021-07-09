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
    Date CreatedDate;
    String CreatedBy;

    public CustomerOrderStatus getCustomerOrderStatus() {
        return customerOrderStatus;
    }

    public void setCustomerOrderStatus(CustomerOrderStatus customerOrderStatus, String createdBy) {
        this.CreatedDate = new Date();
        this.customerOrderStatus = customerOrderStatus;
        this.CreatedBy = createdBy;
    }

}
