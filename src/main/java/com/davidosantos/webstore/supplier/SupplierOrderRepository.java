/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.supplier;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author david
 */
public interface SupplierOrderRepository extends MongoRepository<SupplierOrder,String> {

}
