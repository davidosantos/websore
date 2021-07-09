/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.orders;

import com.davidosantos.webstore.products.Product;
import com.davidosantos.webstore.products.ProductService;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import org.apache.commons.codec.binary.Base32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author david
 */
@Service
public class CustomerOrderService {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private ProductService productService;

    private String GenerateCode() {

// Create a secure random generator (it's thread-safe)
        SecureRandom sr = new SecureRandom();

// Allocate an array for 8 bytes
        byte[] random = new byte[6];

// Generate the random bytes
        sr.nextBytes(random);

// Create the encoded ID, strip any padding
        return new Base32().encodeToString(random).replace("=", "");
    }

    public CustomerOrder createOrder(CustomerOrder customerOrder, String createdBy) {
        CustomerOrderStatus customerOrderStatus = CustomerOrderStatus.Created;
        customerOrder.setCreatedDate(new Date());
        customerOrder.setLastCustomerOrderStatus(customerOrderStatus);
        ArrayList<CustomerOrderStatusItem> statusListItem = new ArrayList<CustomerOrderStatusItem>();
        CustomerOrderStatusItem customerOrderStatusItem = new CustomerOrderStatusItem();
        customerOrderStatusItem.setCustomerOrderStatus(customerOrderStatus, createdBy);
        statusListItem.add(customerOrderStatusItem);
        customerOrder.setCustomerOrderStatusItems(statusListItem);
        customerOrder.setCode(GenerateCode());
        customerOrder.setId(null);
        return customerOrderRepository.save(customerOrder);
    }

    private void changeOrderStatus(CustomerOrderStatus customerOrderStatus) {

    }

    public CustomerOrder getById(String id) {
        return customerOrderRepository.findById(id).get();
    }

    Page getAllOrders(Pageable paging) {
        return customerOrderRepository.findAll(paging);
    }

    Page getByDefaultFilter(String code, String email, String documentNumber, String status, Pageable paging) {
        return customerOrderRepository.findByCodeContainingIgnoreCaseAndCustomerEmailContainingIgnoreCaseAndCustomerDocumentNumberContainingIgnoreCaseAndLastCustomerOrderStatus(code, email, documentNumber, status,paging);
    }

    void addProduct(String id, String productid) {
        CustomerOrder customerOrder = customerOrderRepository.findById(id).get();
        Product product = productService.getById(productid);
        CustomerOrderProductItem customerOrderProductItem = new CustomerOrderProductItem();
        customerOrderProductItem.setProduct(product);
        customerOrder.getCustomerOrderItems().add(customerOrderProductItem);
        customerOrderRepository.save(customerOrder);
    }

}
