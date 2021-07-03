/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.customers;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author david
 */
@Document
public class CustomerAddress implements Cloneable {

    @Id
    private String id;
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String coutry;
    private String zipCode;
    private String type;
    private String reference;
    private boolean isActive;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCoutry() {
        return coutry;
    }

    public void setCoutry(String coutry) {
        this.coutry = coutry;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(street);
        sb.append(", ").append(number);
        if (complement != null && !complement.equals("")) {
            sb.append(", ").append(complement);
        }
        sb.append(", ").append(neighborhood);
        sb.append(", ").append(city);
        sb.append(", ").append(coutry);
        sb.append(", ").append(zipCode);
        sb.append(", ").append(type);
        if (reference != null && !reference.equals("")) {
            sb.append(", ").append(reference);
        }
        return sb.toString();
    }

}
