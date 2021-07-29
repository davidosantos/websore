package com.davidosantos.webstore.kart;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface KartRepository extends MongoRepository <Kart,String> {

    List<Kart> findByIdAndStatus(String id,String status);

    int countByIdAndStatus(String id,String status);
    
}
