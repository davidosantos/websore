/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.customers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 *
 * @author david
 */
@Component
public class CustomerAddressRevolver implements HandlerMethodArgumentResolver {

    @Autowired
    CustomerService customerService;

    @Override
    public boolean supportsParameter(MethodParameter mp) {
        return mp.getParameterAnnotation(CustomerAddressAnnotation.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter mp, ModelAndViewContainer mavc, NativeWebRequest nwr, WebDataBinderFactory wdbf) throws Exception {
        System.out.println("Inside Resolverrr!!!" + nwr.getParameter("selectedCustomerAddress"));
        System.out.println("Inside Resolverrr!!!" + nwr.getParameter("customerid"));
//https://www.fatalerrors.org/a/handlermethodargumentresolver-is-used-to-obtain-the-current-login-user-uniformly.html
        //Customer customer = customerService.getById(nwr.getParameter("customerid"));

        return (CustomerAddress) nwr.getAttribute("selectedCustomerAddress", RequestAttributes.SCOPE_REQUEST);
        //return customer.getAddresses().stream().filter(predicate -> predicate.toString().equals(nwr.getParameter("selectedCustomerAddress"))).findFirst().get();
    }

}
