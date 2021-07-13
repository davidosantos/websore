/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author david
 */
@Service
public class SupplierService {

    @Autowired
    SupplierRepository supplierRepository;

    Page getAllSuppliers(Pageable paging) {
        return supplierRepository.findAll(paging);
    }

    Page getByDefaultFilter(String name, Pageable paging) {
        return supplierRepository.findByNameContainingIgnoreCase(name, paging);
    }

    public Supplier getById(String id) {
        return supplierRepository.findById(id).get();
    }

    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

}
