package com.davidosantos.webstore.security;

import com.davidosantos.webstore.customers.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class WebStoreUserDatailsService implements UserDetailsService {

    @Autowired
    CustomerService customerService;

    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {

        if(customerService.countByEmail(string) < 1){
            throw new UsernameNotFoundException("Email " + string + "não cadastrado.");
        }

        return customerService.getByEmail(string);
    }
    
}
