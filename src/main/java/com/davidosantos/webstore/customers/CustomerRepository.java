/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.customers;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author david
 */
public interface CustomerRepository extends MongoRepository<Customer,String> {

    List<Customer> findByIsActive(boolean isActive);

    Page<Customer> findByNameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndDocumentNumberContainingIgnoreCase(String name ,String email,String documentNumber, Pageable pgbl);

    Customer findByEmail(String email);

    public int countByEmail(String email);

}
