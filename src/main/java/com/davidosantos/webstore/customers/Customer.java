/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.customers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author david
 */
@Document
public class Customer implements UserDetails {
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
    private boolean isTermsAccepted;
    private boolean isActive;
    private boolean isEmailConformed;
    private boolean offerOpted;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public boolean isTermsAccepted() {
        return isTermsAccepted;
    }

    public void setTermsAccepted(boolean isTermsAccepted) {
        this.isTermsAccepted = isTermsAccepted;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isEmailConformed() {
        return isEmailConformed;
    }

    public void setEmailConformed(boolean isEmailConformed) {
        this.isEmailConformed = isEmailConformed;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", name=" + name + ", email=" + email + ", encryptedPassword=" + encryptedPassword + ", documentNumber=" + documentNumber + ", birthDate=" + birthDate + ", registeredDate=" + registeredDate + ", activeChangeDate=" + activeChangeDate + ", blockChangeDate=" + blockChangeDate + ", emailConfirmedChangeDate=" + emailConfirmedChangeDate + ", sex=" + sex + ", phones=" + phones + ", addresses=" + addresses + ", isBlocked=" + isBlocked + ", isActive=" + isActive + ", isEmailConformed=" + isEmailConformed + ", offerOpted=" + offerOpted + '}';
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("SITE_USER_ROLE"));
    }

    @Override
    public String getPassword() {
        return encryptedPassword;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    
}
