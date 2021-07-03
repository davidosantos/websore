/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author david
 */
@Controller
public class CustomerController {

    @RequestMapping("/url")
    public String page(Model model) {
        model.addAttribute("attribute", "value");
        return "view.name";
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", ex);
        modelAndView.addObject("url", req.getRequestURL());
        // add other objects to the model here
        modelAndView.setViewName("error-view-name");
        return modelAndView;
    }

}
