package com.davidosantos.webstore.kart;

import com.davidosantos.webstore.customers.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KartService {

    @Autowired
    KartRepository kartRepository;
    
    public Kart createKart(String sessionId){

        Kart kart = new Kart();

        kart.setSessionId(sessionId);

        return kartRepository.save(kart);
    }

    public Kart createKart(String sessionId, Customer customer){

        Kart kart = new Kart();

        kart.setSessionId(sessionId);
        kart.setCustomer(customer);

        return kartRepository.save(kart);
    }

    public Kart save(Kart kart){
        return kartRepository.save(kart);
    }

    public Kart getKart(String sessionId){

        return kartRepository.findBySessionIdAndStatus(sessionId,"active").stream().findFirst().get();
    }

    public int countKart(String sessionId){
        return kartRepository.countBySessionIdAndStatus(sessionId, "active");
    }

}
