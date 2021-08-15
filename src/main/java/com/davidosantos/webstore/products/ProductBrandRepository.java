package com.davidosantos.webstore.products;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductBrandRepository extends MongoRepository<ProductBrand,String>{
 
    public List<ProductBrand> findByIsActive(boolean isActive);
}