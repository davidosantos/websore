package com.davidosantos.webstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@SpringBootApplication
@Configuration
public class WebstoreApplication extends WebSecurityConfigurerAdapter{

    public static void main(String[] args) {
        SpringApplication.run(WebstoreApplication.class, args);
    }
//
//    @Override
//    public void addArgumentResolvers(
//      List<HandlerMethodArgumentResolver> argumentResolvers) {
//        argumentResolvers.add(new CustomerAddressRevolver());
//    }

@Override
protected void configure(HttpSecurity http) throws Exception {
    http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
}

}
