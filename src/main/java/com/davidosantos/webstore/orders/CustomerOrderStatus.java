/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.orders;

/**
 *
 * @author david
 */
public enum CustomerOrderStatus {
    Created,
    Cancelled,
    PaymentWait,
    Paid,
    SupplierOrderCreated,
    SupplierPartiallyOrderCreated,
    Delivered

}
