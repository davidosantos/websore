/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.products;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author david
 */
public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByIsActiveAndDisplayInHome(boolean isActive, boolean displayInHome);

    Page<Product> findByCodeContainingIgnoreCaseAndNameContainingIgnoreCaseAndProviderNameContainingIgnoreCase(String code,String name,String providerName, Pageable pgbl);

}
