/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.orders;

import com.davidosantos.webstore.customers.Customer;
import com.davidosantos.webstore.customers.CustomerService;
import com.davidosantos.webstore.products.ProductService;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author david
 */
@Controller
@RequestMapping("/backoffice/customerorder")
public class CustomerOrderController {

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerOrderService customerOrderService;

    @Autowired
    ProductService productService;

    @RequestMapping("customerorderlist")
    public String page(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "7") int size,
            @RequestParam(defaultValue = "") String code,
            @RequestParam(defaultValue = "") String email,
            @RequestParam(defaultValue = "") String documentNumber,
            @RequestParam(defaultValue = "") String status,
            Model model) {
//https://www.baeldung.com/spring-thymeleaf-pagination

        Pageable paging = PageRequest.of(page, size);
        Page orderPage;
        if (code.equals("") && email.equals("") && documentNumber.equals("") && status.equals("")) {
            orderPage = customerOrderService.getAllOrders(paging);
        } else {
            orderPage = customerOrderService.getByDefaultFilter(code, email, documentNumber, status, paging);
        }

        model.addAttribute("orderPage", orderPage);

        int totalPages = orderPage.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages - 1)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "/backoffice/customerorderlist";
    }

    @RequestMapping("order")
    public String OrderEditPage(
            @RequestParam(defaultValue = "") String id,
            @RequestParam(defaultValue = "") String customerid,
            Model model) {

        Customer customer = null;
        CustomerOrder customerOrder = null;;
        if (!customerid.equals("")) {
            customer = customerService.getById(customerid);
            customerOrder = new CustomerOrder();
            customerOrder.setCustomer(customer);
        } else if (!id.equals("")) {
            customerOrder = customerOrderService.getById(id);
            customer = customerOrder.getCustomer();
        } else {
            customer = new Customer();
            customerOrder = new CustomerOrder();
        }

        model.addAttribute("id", id);
        model.addAttribute("customerid", customerid);
        model.addAttribute("customer", customer);
        model.addAttribute("customerOrder", customerOrder);

        return "/backoffice/customerorder";
    }

    @RequestMapping(value = "createOrder", method = RequestMethod.POST)
    public String OrderSave(
            String customerAddress,
            String customerid,
            CustomerOrder customerOrder,
            Model model) {
        Customer customer = customerService.getById(customerid);
        customerOrder.setCustomer(customer);
        customerOrder.setSelectedCustomerAddress(customer.getAddresses().stream().filter(address -> address.toString().equals(customerAddress.toString())).findFirst().get());

        System.out.println("customerAddress " + customerAddress);
        return "redirect:/backoffice/customerorder/order?id=" + customerOrderService.createOrder(customerOrder, "Backoffice").getId();

    }

    @RequestMapping(value = "productadd", method = RequestMethod.POST)
    public String OrderAddProduct(
            String id,
            String productid,
            Model model) {

        customerOrderService.addProduct(id,productid);

        return "redirect:/backoffice/customerorder/order?id=" + id;

    }

}
