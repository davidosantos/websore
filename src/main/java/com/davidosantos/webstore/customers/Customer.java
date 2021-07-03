/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.customers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author david
 */
@Document
public class Customer {
    @Id
    private String id;
    private String name;
    private String email;
    private String encryptedPassword;
    private String documentNumber;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date registeredDate;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date activeChangeDate;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date blockChangeDate;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date emailConfirmedChangeDate;
    private String sex;
    private List<CustomerPhone> phones;
    private List<CustomerAddress> addresses;
    private boolean isBlocked;
    private boolean isActive;
    private boolean isEmailConformed;
    private boolean offerOpted;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public Date getActiveChangeDate() {
        return activeChangeDate;
    }

    public void setActiveChangeDate(Date activeChangeDate) {
        this.activeChangeDate = activeChangeDate;
    }

    public Date getBlockChangeDate() {
        return blockChangeDate;
    }

    public void setBlockChangeDate(Date blockChangeDate) {
        this.blockChangeDate = blockChangeDate;
    }

    public Date getEmailConfirmedChangeDate() {
        return emailConfirmedChangeDate;
    }

    public void setEmailConfirmedChangeDate(Date emailConfirmedChangeDate) {
        this.emailConfirmedChangeDate = emailConfirmedChangeDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List<CustomerPhone> getPhones() {
        if(this.phones == null){
            phones = new ArrayList<CustomerPhone>();
        }
        return phones;
    }

    public void setPhones(List<CustomerPhone> phones) {
        this.phones = phones;
    }

    public List<CustomerAddress> getAddresses() {
        if(this.addresses == null){
            this.addresses = new ArrayList<CustomerAddress>();
        }
        return addresses;
    }

    public void setAddresses(List<CustomerAddress> addresses) {
        this.addresses = addresses;
    }

    public boolean isIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isIsEmailConformed() {
        return isEmailConformed;
    }

    public void setIsEmailConformed(boolean isEmailConformed) {
        this.isEmailConformed = isEmailConformed;
    }

    public boolean isOfferOpted() {
        return offerOpted;
    }

    public void setOfferOpted(boolean offerOpted) {
        this.offerOpted = offerOpted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
}
