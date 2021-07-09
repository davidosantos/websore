package com.davidosantos.webstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@Configuration
public class WebstoreApplication implements WebMvcConfigurer{

    public static void main(String[] args) {
        SpringApplication.run(WebstoreApplication.class, args);
    }
//
//    @Override
//    public void addArgumentResolvers(
//      List<HandlerMethodArgumentResolver> argumentResolvers) {
//        argumentResolvers.add(new CustomerAddressRevolver());
//    }

}
