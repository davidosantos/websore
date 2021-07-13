/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore;

import com.davidosantos.webstore.customers.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author david
 */
@Controller
@RequestMapping("/backoffice")
public class BackOfficeController {


    @Autowired
    CustomerService customerService;
    

    @RequestMapping
    public String backofficePage(Model model) {
        model.addAttribute("attribute", "value");
        return "backoffice/backoffice";
    }
    
    

    @ExceptionHandler({Exception.class})
    public String databaseError() {
        return "error-view-name";
    }

}
