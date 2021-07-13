/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author david
 */
@Service
public class SupplierOrderService {

    @Autowired
    SupplierOrderRepository supplierOrderRepository;

    public SupplierOrder createOrder(SupplierOrder supplierOrder) {

        return supplierOrderRepository.save(supplierOrder);
    }

}
