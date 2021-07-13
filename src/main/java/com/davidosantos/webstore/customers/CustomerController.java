/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.customers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author david
 */
@Controller
@RequestMapping("/backoffice")
public class CustomerController {

    @Autowired
    CustomerService customerService;


    @RequestMapping("/customerslist")
    public String backofficeListCustomersPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String email,
            @RequestParam(defaultValue = "") String documentNumber,
            Model model) {
//https://www.baeldung.com/spring-thymeleaf-pagination

        Pageable paging = PageRequest.of(page, size);
        Page customerPage;
        if (name.equals("") && email.equals("") && documentNumber.equals("")) {
            customerPage = customerService.getAllCustumers(paging);
        } else {
            customerPage = customerService.getByDefaultFilter(name, email, documentNumber, paging);
        }

        model.addAttribute("customerPage", customerPage);

        int totalPages = customerPage.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages - 1)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "backoffice/customerslist";
    }

    @RequestMapping("/customers")
    public String backofficeEditCustomersPage(
            @RequestParam(defaultValue = "") String id,
            @RequestParam(defaultValue = "") String addressid, Model model) {
        Customer customer;
        if (id.equals("")) {
            customer = new Customer();
        } else {
            customer = customerService.getById(id);
        }

        List<CustomerPhone> customerPhones;
        if (customer.getPhones() != null && customer.getPhones().size() > 0) {
            customerPhones = customer.getPhones();
        } else {
            customerPhones = new ArrayList<CustomerPhone>();
        }

        String addressString = "";
        List<CustomerAddress> customerAddresses;
        if (customer.getAddresses() != null && customer.getAddresses().size() > 0) {
            customerAddresses = customer.getAddresses();
            addressString = customerAddresses.get(Integer.parseInt(addressid.equals("") ? "1" : addressid) - 1).toString();
        } else {
            customerAddresses = new ArrayList<CustomerAddress>();
        }

        CustomerPhone customerPhone = new CustomerPhone();

        CustomerAddress customerAddress = new CustomerAddress();

        model.addAttribute("customer", customer);

        model.addAttribute("customerPhones", customerPhones);

        model.addAttribute("customerAddresses", customerAddresses);

        model.addAttribute("customerPhone", customerPhone);

        model.addAttribute("customerAddress", customerAddress);

        model.addAttribute("addressString", addressString);

        return "backoffice/customers";
    }

    @RequestMapping("/customers/address")
    public String backofficeEditCustomersAddressPage(@RequestParam(defaultValue = "") String customerid, @RequestParam(defaultValue = "") String addressid, RedirectAttributes redirAttrs, Model model) {
        Customer customer;
        if (customerid.equals("")) {
            redirAttrs.addFlashAttribute("errorAddress", "Primeiro Salve o Cadastro, depois inseri os endereços.");
            return "redirect:/backoffice/customers";
        } else {
            customer = customerService.getById(customerid);
        }

        CustomerAddress customerAddress;
        if (addressid.equals("")) {
            customerAddress = new CustomerAddress();
        } else {
            customerAddress = customer
                    .getAddresses().get(Integer.parseInt(addressid) - 1);
        }
        model.addAttribute("customer", customer);
        model.addAttribute("addressid", addressid);
        model.addAttribute("customerAddress", customerAddress);

        return "backoffice/address";
    }

    @RequestMapping(value = "/customers/address", method = RequestMethod.POST)
    public String backofficeSaveCustomersAddressPage(@RequestParam(defaultValue = "") String customerid, @RequestParam(defaultValue = "") String addressid, CustomerAddress customerAddress, RedirectAttributes redirAttrs, Model model) {
        Customer customer;
        if (customerid.equals("")) {
            redirAttrs.addFlashAttribute("errorAddress", "Cliente Não Encontrado");
            return "redirect:/backoffice/customers/address";
        } else {
            customer = customerService.getById(customerid);
        }

        if (addressid.equals("")) {
            customer.getAddresses().add(customerAddress);
            customerService.saveCustomer(customer);
        } else {
            customer.getAddresses().set(Integer.parseInt(addressid) - 1, customerAddress);
            customerService.saveCustomer(customer);
        }

        model.addAttribute("customer", customer);
        model.addAttribute("customerAddress", customerAddress);

        return "redirect:/backoffice/customers/address?customerid=" + customerid + "&addressid=" + customer.getAddresses().size();
    }

    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    public String backofficeSaveCustomerPage(@Validated @ModelAttribute Customer customer, BindingResult errors, Model model) {
        errors.getFieldErrors().stream().forEach((error) -> {
            System.out.println("Erro: " + error.toString());
        });

        if (customer.getId().equals("")) {
            customer.setId(null);
            customer.setRegisteredDate(new Date());
        }

        customer = customerService.saveCustomer(customer);
        model.addAttribute("product", customer);
        return "redirect:/backoffice/customers?id=" + customer.getId();
    }


    @RequestMapping(value = "/customers/phone", method = RequestMethod.POST)
    public String backofficeSaveProductsPhonePage(@Validated @ModelAttribute CustomerPhone customerPhone, String id, RedirectAttributes redirAttrs, BindingResult errors, Model model) {
        errors.getFieldErrors().stream().forEach((error) -> {
            System.out.println("Erro: " + error.toString());
        });

        if (id != null && id.equals("")) {
            redirAttrs.addFlashAttribute("error", "Primeiro Salve o Cadastro, depois inseri os telefones");
            return "redirect:/backoffice/customers";
        } else {
            Customer cust = customerService.getById(id);
            cust.getPhones().add(customerPhone);
            customerService.saveCustomer(cust);
            return "redirect:/backoffice/customers?id=" + cust.getId();
        }

    }


    @RequestMapping("/customerselect")
    public String selectCustomer(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String email,
            @RequestParam(defaultValue = "") String documentNumber,
            String redirectTo,
            @RequestParam(defaultValue = "GET") String method,
            Model model) {
//https://www.baeldung.com/spring-thymeleaf-pagination

        Pageable paging = PageRequest.of(page, size);
        Page customerPage;
        if (name.equals("") && email.equals("") && documentNumber.equals("")) {
            customerPage = customerService.getAllCustumers(paging);
        } else {
            customerPage = customerService.getByDefaultFilter(name, email, documentNumber, paging);
        }

        model.addAttribute("method", method);
        model.addAttribute("redirectTo", redirectTo);
        model.addAttribute("customerPage", customerPage);

        int totalPages = customerPage.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages - 1)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "backoffice/customerselect";

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
