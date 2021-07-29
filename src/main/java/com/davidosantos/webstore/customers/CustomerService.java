/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.customers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author david
 */
@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public Page getAllCustumers(Pageable pgbl) {

        return customerRepository.findAll(pgbl);
    }

    public Page getByDefaultFilter(String name, String email, String documentNumber, Pageable pgbl) {

        return customerRepository.findByNameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndDocumentNumberContainingIgnoreCase(name, email, documentNumber, pgbl);
    }

    public Customer getById(String id) {
        return customerRepository.findById(id).get();
    }

    public Customer getByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }


    public int countByEmail(String email) {
        return customerRepository.countByEmail(email);
    }

}
