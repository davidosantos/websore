
package com.davidosantos.webstore.kart;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.davidosantos.webstore.customers.Customer;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Kart {

    @Id
    String id;
    String status;
    String sessionId;
    Date createdDate;
    Date lastUpdateDate;
    @DBRef
    Customer customer;
    List<KartItem> items;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getSessionId() {
        return sessionId;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    public Date getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public List<KartItem> getItems() {
        if(items == null){

            items = new ArrayList<KartItem>();
        }
        return items;
    }
    public void setItems(List<KartItem> items) {
        this.items = items;
    }

    
    
}
