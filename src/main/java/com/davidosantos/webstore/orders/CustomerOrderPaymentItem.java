/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.orders;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author david
 */
public class CustomerOrderPaymentItem {


    BigDecimal value;
    boolean isSuccessful;
    Date createdAt;
    String createdBy;
    String status;
    String details;

    public CustomerOrderPaymentItem (){}

    public CustomerOrderPaymentItem(BigDecimal value,boolean isSuccessful, String status, String details, String createdBy) {
        this.value = value;
        this.isSuccessful = isSuccessful;
        this.status = status;
        this.details = details;
        this.createdBy = createdBy;
        this.createdAt = new Date();
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }



}
