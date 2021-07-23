package com.davidosantos.webstore.kart;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface KartRepository extends MongoRepository <Kart,String> {

    List<Kart> findBySessionIdAndStatus(String sessionId,String status);

    int countBySessionIdAndStatus(String sessionId,String status);
    
}
