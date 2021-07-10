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
public class CustomerOrderUpdateHistory {

    public CustomerOrderUpdateHistory(String UpdatedBy, String descrition) {
        this.UpdatedBy = UpdatedBy;
        this.descrition = descrition;
        this.updated = new Date();
    }

    

    private Date updated;
    private String UpdatedBy;
    private String descrition;

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(String UpdatedBy) {
        this.UpdatedBy = UpdatedBy;
    }

    public String getDescrition() {
        return descrition;
    }

    public void setDescrition(String descrition) {
        this.descrition = descrition;
    }

    

}
