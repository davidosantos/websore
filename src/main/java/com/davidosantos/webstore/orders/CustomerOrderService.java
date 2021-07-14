/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.orders;

import com.davidosantos.webstore.products.Product;
import com.davidosantos.webstore.products.ProductService;
import com.davidosantos.webstore.supplier.Supplier;
import com.davidosantos.webstore.supplier.SupplierOrder;
import com.davidosantos.webstore.supplier.SupplierOrderItem;
import com.davidosantos.webstore.supplier.SupplierOrderService;
import com.davidosantos.webstore.supplier.SupplierService;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

    int scaleTotalPrice = 4;
    int scalePrice = 2;
    int scaleQuantity = 0;

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    SupplierOrderService supplierOrderService;

    @Autowired
    SupplierService supplierService;

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
        customerOrderStatusItem.setCreatedDate(new Date());
        customerOrderStatusItem.setCreatedBy(createdBy);
        customerOrderStatusItem.setCustomerOrderStatus(customerOrderStatus, createdBy);
        statusListItem.add(customerOrderStatusItem);
        customerOrder.setCustomerOrderStatusItems(statusListItem);
        customerOrder.setCode(GenerateCode());
        customerOrder.setTotalDiscount(BigDecimal.ZERO.setScale(scaleTotalPrice, RoundingMode.HALF_UP));
        customerOrder.setTotalDiscountPercent(BigDecimal.ZERO.setScale(scaleTotalPrice, RoundingMode.HALF_UP));
        customerOrder.setTotalAmount(BigDecimal.ZERO.setScale(scaleTotalPrice, RoundingMode.HALF_UP));
        customerOrder.setTotalLiquid(BigDecimal.ZERO.setScale(scaleTotalPrice, RoundingMode.HALF_UP));
        customerOrder.setTotalProfit(BigDecimal.ZERO.setScale(scaleTotalPrice, RoundingMode.HALF_UP));
        customerOrder.setTotalProfitPercent(BigDecimal.ZERO.setScale(scaleTotalPrice, RoundingMode.HALF_UP));
        customerOrder.setTotalEstimatedFreight(BigDecimal.ZERO.setScale(scaleTotalPrice, RoundingMode.HALF_UP));
        customerOrder.setTotalQuantity(BigDecimal.ZERO.setScale(scaleQuantity, RoundingMode.HALF_UP));
        customerOrder.setId(null);
        return customerOrderRepository.save(customerOrder);
    }

    private void changeOrderStatus(CustomerOrder customerOrder, CustomerOrderStatus customerOrderStatus, String updatedBy) {

        customerOrder.setLastCustomerOrderStatus(customerOrderStatus);
        CustomerOrderStatusItem customerOrderStatusItem = new CustomerOrderStatusItem();
        customerOrderStatusItem.setCreatedBy(updatedBy);
        customerOrderStatusItem.setCreatedDate(new Date());
        customerOrderStatusItem.setCustomerOrderStatus(customerOrderStatus, updatedBy);
        customerOrder.getCustomerOrderStatusItems().add(customerOrderStatusItem);
        customerOrder.getCustomerOrderUpdateHistorys().add(new CustomerOrderUpdateHistory(updatedBy, "Alterado status do pedido para " + customerOrderStatus.name() + "."));
        customerOrderRepository.save(customerOrder);

    }

    public CustomerOrder getById(String id) {
        return customerOrderRepository.findById(id).get();
    }

    Page getAllOrders(Pageable paging) {
        return customerOrderRepository.findAll(paging);
    }

    Page getByDefaultFilter(String code, String email, String documentNumber, String status, Pageable paging) {
        return customerOrderRepository.findByCodeContainingIgnoreCaseAndCustomerEmailContainingIgnoreCaseAndCustomerDocumentNumberContainingIgnoreCaseAndLastCustomerOrderStatus(code, email, documentNumber, status, paging);
    }

    void cancelItem(String orderId, int itemIndex, String updatedBy) {

        CustomerOrder customerOrder = customerOrderRepository.findById(orderId).get();
        customerOrder.getCustomerOrderItems().get(itemIndex).setStatus("cancelled");
        customerOrder.getCustomerOrderUpdateHistorys().add(new CustomerOrderUpdateHistory(updatedBy, "Cancelado Item " + customerOrder.getCustomerOrderItems().get(itemIndex).getProduct().getCode() + " - " + customerOrder.getCustomerOrderItems().get(itemIndex).getProduct().getName()));
        recalculateOrder(customerOrder, updatedBy);
        customerOrderRepository.save(customerOrder);
    }

    void cancelOrder(String orderId, String updatedBy) {

        CustomerOrder customerOrder = customerOrderRepository.findById(orderId).get();
        customerOrder.setLastCustomerOrderStatus(CustomerOrderStatus.Cancelled);
        CustomerOrderStatusItem customerOrderStatusItem = new CustomerOrderStatusItem();
        customerOrderStatusItem.setCreatedBy(updatedBy);
        customerOrderStatusItem.setCreatedDate(new Date());
        customerOrderStatusItem.setCustomerOrderStatus(CustomerOrderStatus.Cancelled, updatedBy);
        customerOrder.getCustomerOrderStatusItems().add(customerOrderStatusItem);
        customerOrder.getCustomerOrderUpdateHistorys().add(new CustomerOrderUpdateHistory(updatedBy, "Cancelado pedido."));
        customerOrderRepository.save(customerOrder);
    }

    void addProduct(String id, String productid, String updatedBy) {

        CustomerOrder customerOrder = customerOrderRepository.findById(id).get();
        if (customerOrder.getCustomerOrderItems().stream().filter(item -> item.getStatus().equals("active") && item.getProduct().getId().equals(productid)).count() >= 1) {
            customerOrder.getCustomerOrderItems().stream().filter(item -> item.getStatus().equals("active") && item.getProduct().getId().equals(productid)).findFirst().get().incrementQuantityBy(BigDecimal.ONE);
        } else {

            Product product = productService.getById(productid);
            CustomerOrderProductItem customerOrderProductItem = new CustomerOrderProductItem();
            customerOrderProductItem.setStatus("active");
            customerOrderProductItem.setProduct(product);
            customerOrderProductItem.setQuantity(BigDecimal.ONE.setScale(scaleQuantity, RoundingMode.HALF_UP));
            customerOrderProductItem.setTotalDiscount(BigDecimal.ZERO.setScale(scalePrice, RoundingMode.HALF_UP));
            customerOrderProductItem.setUnitDiscount(BigDecimal.ZERO.setScale(scalePrice, RoundingMode.HALF_UP));
            customerOrderProductItem.setDiscountPercent(BigDecimal.ZERO.setScale(scalePrice, RoundingMode.HALF_UP));
            customerOrderProductItem.setSupplierEstimatedFreight(BigDecimal.ZERO.setScale(scalePrice, RoundingMode.HALF_UP));
            customerOrderProductItem.setSupplierEstimatedTotal(BigDecimal.ZERO.setScale(scalePrice, RoundingMode.HALF_UP));
            customerOrderProductItem.setUnitFreight(BigDecimal.ZERO.setScale(scalePrice, RoundingMode.HALF_UP));
            customerOrderProductItem.setTotal(BigDecimal.ZERO.setScale(scalePrice, RoundingMode.HALF_UP));
            customerOrderProductItem.setLiquidTotal(BigDecimal.ZERO.setScale(scalePrice, RoundingMode.HALF_UP));
            customerOrderProductItem.setProfit(BigDecimal.ZERO.setScale(scalePrice, RoundingMode.HALF_UP));
            customerOrderProductItem.setProfitPercent(BigDecimal.ZERO.setScale(scalePrice, RoundingMode.HALF_UP));
            customerOrderProductItem.setSupplierPoweredFreight(BigDecimal.ZERO.setScale(scalePrice, RoundingMode.HALF_UP));
            customerOrderProductItem.setSupplierPoweredDiscount(BigDecimal.ZERO.setScale(scalePrice, RoundingMode.HALF_UP));
            customerOrderProductItem.setSupplierPoweredPrice(BigDecimal.ZERO.setScale(scalePrice, RoundingMode.HALF_UP));
            customerOrderProductItem.setSupplierPoweredTotal(BigDecimal.ZERO.setScale(scalePrice, RoundingMode.HALF_UP));
            customerOrderProductItem.setSupplierQuantityBought(BigDecimal.ZERO.setScale(scalePrice, RoundingMode.HALF_UP));
            customerOrder.getCustomerOrderItems().add(customerOrderProductItem);
            customerOrder.getCustomerOrderUpdateHistorys().add(new CustomerOrderUpdateHistory(updatedBy, "Adicionado Item " + product.getCode() + " - " + product.getName()));
        }
        recalculateOrder(customerOrder, updatedBy);
        customerOrderRepository.save(customerOrder);
    }

    public void recalculateOrder(CustomerOrder customerOrder, String updatedBy) {

        recalculateItems(customerOrder);

        CustomerOrderStatus lastCustomerOrderStatus = customerOrder.getLastCustomerOrderStatus();
        BigDecimal lastTotalQuantity = customerOrder.getTotalQuantity();
        BigDecimal lastTotalEstimatedFreight = customerOrder.getTotalEstimatedFreight();
        BigDecimal lastTotalDiscount = customerOrder.getTotalDiscount();
        BigDecimal lastTotalDiscountPercent = customerOrder.getTotalDiscountPercent();
        BigDecimal lastTotalAmount = customerOrder.getTotalAmount();
        BigDecimal lastTotalLiquid = customerOrder.getTotalLiquid();
        BigDecimal lastSupplierTotalEstimatedAmount = customerOrder.getSupplierTotalEstimatedAmount();
        BigDecimal lastTotalProfit = customerOrder.getTotalProfit();
        BigDecimal lastTotalProfitPercent = customerOrder.getTotalProfitPercent();

        BigDecimal newTotalQuantity = new BigDecimal(customerOrder.getCustomerOrderItems().stream().filter(itemFilter -> itemFilter.getStatus().equals("active")).mapToDouble(productItem -> productItem.getQuantity().doubleValue()).sum()).setScale(scaleQuantity, RoundingMode.HALF_UP);
        BigDecimal newTotalQuantityBought = new BigDecimal(customerOrder.getCustomerOrderItems().stream().filter(itemFilter -> itemFilter.getStatus().equals("active")).mapToDouble(productItem -> productItem.getSupplierQuantityBought().doubleValue()).sum()).setScale(scaleQuantity, RoundingMode.HALF_UP);
        BigDecimal newTotalEstimatedFreight = new BigDecimal(customerOrder.getCustomerOrderItems().stream().filter(itemFilter -> itemFilter.getStatus().equals("active")).mapToDouble(productItem -> productItem.getSupplierEstimatedFreight().doubleValue()).sum()).setScale(scaleTotalPrice, RoundingMode.HALF_UP);
        BigDecimal newTotalDiscount = new BigDecimal(customerOrder.getCustomerOrderItems().stream().filter(itemFilter -> itemFilter.getStatus().equals("active")).mapToDouble(productItem -> productItem.getTotalDiscount().doubleValue()).sum()).setScale(scaleTotalPrice, RoundingMode.HALF_UP);
        BigDecimal newTotalAmount = new BigDecimal(customerOrder.getCustomerOrderItems().stream().filter(itemFilter -> itemFilter.getStatus().equals("active")).mapToDouble(productItem -> productItem.getTotal().doubleValue()).sum()).setScale(scaleTotalPrice, RoundingMode.HALF_UP);
        BigDecimal newTotalLiquid = new BigDecimal(customerOrder.getCustomerOrderItems().stream().filter(itemFilter -> itemFilter.getStatus().equals("active")).mapToDouble(productItem -> productItem.getLiquidTotal().doubleValue()).sum()).setScale(scaleTotalPrice, RoundingMode.HALF_UP);
        BigDecimal newSuppilerTotalEstimatedAmount = new BigDecimal(customerOrder.getCustomerOrderItems().stream().filter(itemFilter -> itemFilter.getStatus().equals("active")).mapToDouble(productItem -> productItem.getSupplierEstimatedTotal().doubleValue()).sum()).setScale(scaleTotalPrice, RoundingMode.HALF_UP);
        BigDecimal newTotalProfit = new BigDecimal(customerOrder.getCustomerOrderItems().stream().filter(itemFilter -> itemFilter.getStatus().equals("active")).mapToDouble(productItem -> productItem.getProfit().doubleValue()).sum()).setScale(scaleTotalPrice, RoundingMode.HALF_UP);

        BigDecimal newTotalDiscountPercent;
        if (newTotalLiquid.compareTo(BigDecimal.ZERO) > 0) {
            newTotalDiscountPercent = newTotalDiscount.divide(
                    newTotalLiquid,
                    RoundingMode.HALF_UP)
                    .multiply(new BigDecimal(100));
        } else {
            newTotalDiscountPercent = BigDecimal.ZERO.setScale(scaleTotalPrice, RoundingMode.HALF_UP);
        }

        BigDecimal newTotalProfitPercent;
        if (newSuppilerTotalEstimatedAmount.compareTo(BigDecimal.ZERO) > 0) {
            newTotalProfitPercent = newTotalProfit.divide(newSuppilerTotalEstimatedAmount, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
        } else {
            newTotalProfitPercent = BigDecimal.ZERO.setScale(scaleTotalPrice, RoundingMode.HALF_UP);
        }
        customerOrder.setTotalQuantity(newTotalQuantity);
        customerOrder.setTotalEstimatedFreight(newTotalEstimatedFreight);
        customerOrder.setTotalDiscount(newTotalDiscount);
        customerOrder.setTotalDiscountPercent(newTotalDiscountPercent);
        customerOrder.setTotalAmount(newTotalAmount);
        customerOrder.setTotalLiquid(newTotalLiquid);
        customerOrder.setSupplierTotalEstimatedAmount(newSuppilerTotalEstimatedAmount);
        customerOrder.setTotalProfit(newTotalProfit);
        customerOrder.setTotalProfitPercent(newTotalProfitPercent);

        if (newTotalQuantityBought.compareTo(BigDecimal.ZERO) > 0 && newTotalQuantityBought.compareTo(newTotalQuantity) < 0) {
            changeOrderStatus(customerOrder, CustomerOrderStatus.SupplierPartiallyOrderCreated, updatedBy);
        } else if (newTotalQuantityBought.compareTo(newTotalQuantity) == 0) {
            changeOrderStatus(customerOrder, CustomerOrderStatus.SupplierOrderCreated, updatedBy);
        } else if(newTotalQuantity.compareTo(BigDecimal.ZERO) > 0 && !customerOrder.getLastCustomerOrderStatus().equals(CustomerOrderStatus.PaymentWait)){
            changeOrderStatus(customerOrder, CustomerOrderStatus.PaymentWait, updatedBy);
        }

        customerOrder.getCustomerOrderUpdateHistorys().add(
                new CustomerOrderUpdateHistory(updatedBy,
                        "Recalculado <br />"
                        + "CustomerOrderStatus: " + lastCustomerOrderStatus + " -> " + customerOrder.getLastCustomerOrderStatus() + "<br/>"
                        + "TotalQuantity: " + lastTotalQuantity + " -> " + newTotalQuantity + "<br/>"
                        + "TotalDiscount: " + lastTotalDiscount + " -> " + newTotalDiscount + "<br/>"
                        + "TotalDiscountPercent: " + lastTotalDiscountPercent + " -> " + newTotalDiscountPercent + "<br/>"
                        + "TotalEstimatedFreight: " + lastTotalEstimatedFreight + " -> " + newTotalEstimatedFreight + "<br/>"
                        + "TotalLiquid: " + lastTotalLiquid + " -> " + newTotalLiquid + "<br/>"
                        + "TotalAmount: " + lastTotalAmount + " -> " + newTotalAmount + "<br/>"
                        + "TotalQuantityBought: " + newTotalQuantityBought + "<br/>"
                        + "SupplierTotalEstimatedAmount: " + lastSupplierTotalEstimatedAmount + " -> " + newSuppilerTotalEstimatedAmount + "<br/>"
                        + "TotalProfit: " + lastTotalProfit + " -> " + newTotalProfit + "<br/>"
                        + "TotalProfitPercent: " + lastTotalProfitPercent + " -> " + newTotalProfitPercent + "<br/>"
                ));

        customerOrderRepository.save(customerOrder);
    }

    public void recalculateItems(CustomerOrder customerOrder) {

        customerOrder.getCustomerOrderItems()
                .stream()
                .filter(predicate -> predicate.getStatus().equals("active"))
                .forEach((customerOrderItem) -> {

                    customerOrderItem.setUnitDiscount(customerOrderItem.getProduct().getDiscount());
                    customerOrderItem.setUnitFreight(customerOrderItem.getProduct().getFreight());

                    customerOrderItem.setTotalDiscount(
                            //Quantidade do item x desconto Unitario
                            customerOrderItem.getQuantity().multiply(customerOrderItem.getUnitDiscount())
                    );

                    customerOrderItem.setSupplierEstimatedFreight(
                            //Quantidade do item x frete Unitario
                            customerOrderItem.getQuantity().multiply(customerOrderItem.getUnitFreight())
                    );

                    customerOrderItem.setLiquidTotal(
                            //total do item x preço
                            customerOrderItem.getQuantity().multiply(customerOrderItem.getProduct().getPrice()));

                    customerOrderItem.setSupplierEstimatedTotal(
                            //total do item x preço forncedor
                            customerOrderItem.getQuantity().multiply(customerOrderItem.getProduct().getSupplierPrice()));

                    customerOrderItem.setTotal(
                            //total do item
                            customerOrderItem.getQuantity().multiply(customerOrderItem.getProduct().getPrice())
                                    //mais total de frete
                                    .add(customerOrderItem.getSupplierEstimatedFreight())
                                    //menos total de descontos
                                    .subtract(customerOrderItem.getTotalDiscount())
                    );

                    customerOrderItem.setDiscountPercent(
                            customerOrderItem.getTotalDiscount()
                                    //desconto dividido pelo total item
                                    .divide(customerOrderItem.getQuantity().multiply(customerOrderItem.getProduct().getPrice()), RoundingMode.HALF_UP)
                                    //multiplicado por 100
                                    .multiply(new BigDecimal(100))
                    );
                    double itemQtd = 0;
                    for (SupplierOrder supplierOrder : customerOrder.getSupplierOrders()) {
                        for (SupplierOrderItem supplierOrderItem : supplierOrder.getSupplierOrderItems()) {
                            if (supplierOrderItem.getProductId().equals(customerOrderItem.getProduct().getId())
                                    && customerOrderItem.getStatus().equals("active")) {
                                itemQtd += 1;
                            }
                        }
                    }

                    if (itemQtd > 0) {
                        //calculo das compras nos forneceres devem ser utilizado a media poderada,
                        //pois temos a possibilidade de comprar quantidades diferentes com preços diferentes
                        // mas medias ponderadas são para preço, frete, total e descontos no fornecedor.
                        //busca em todos os pedidos de fornecedor que não esteja cancelado
                        double quantitySum = 0;
                        double totalSum = 0;
                        double priceAvg = 0;
                        double priceAvgtmp = 0;
                        double freightSum = 0;
                        double discountSum = 0;
                        for (SupplierOrder supplierOrder : customerOrder.getSupplierOrders()) {
                            for (SupplierOrderItem supplierOrderItem : supplierOrder.getSupplierOrderItems()) {
                                if (supplierOrderItem.getProductId().equals(customerOrderItem.getProduct().getId())
                                        && customerOrderItem.getStatus().equals("active")) {
                                    quantitySum += supplierOrderItem.getQuantity().doubleValue();
                                    priceAvgtmp += (supplierOrderItem.getQuantity().doubleValue() * supplierOrderItem.getPrice().doubleValue());
                                    freightSum += (supplierOrderItem.getQuantity().doubleValue() * supplierOrderItem.getFreight().doubleValue());
                                    discountSum += (supplierOrderItem.getQuantity().doubleValue() * supplierOrderItem.getDiscount().doubleValue());
                                    totalSum += (supplierOrderItem.getQuantity().doubleValue() * supplierOrderItem.getPrice().doubleValue());
                                }
                            }
                        }
                        priceAvg = (priceAvgtmp / quantitySum);
                        customerOrderItem.setSupplierQuantityBought(new BigDecimal(quantitySum));
                        customerOrderItem.setSupplierPoweredPrice(new BigDecimal((priceAvg)).setScale(scalePrice, RoundingMode.HALF_UP));
                        customerOrderItem.setSupplierPoweredFreight(new BigDecimal((freightSum)).setScale(scalePrice, RoundingMode.HALF_UP));
                        customerOrderItem.setSupplierPoweredDiscount(new BigDecimal((discountSum)).setScale(scalePrice, RoundingMode.HALF_UP));
                        customerOrderItem.setSupplierPoweredTotal(new BigDecimal(totalSum).setScale(scalePrice, RoundingMode.HALF_UP));

                        customerOrderItem.setProfit(
                                new BigDecimal(
                                        //total do item vendido
                                        customerOrderItem.getQuantity().multiply(customerOrderItem.getProduct().getPrice()).doubleValue()
                                        //menos total no fornecedor
                                        - (quantitySum * priceAvg)
                                        // menos total de descontos
                                        - customerOrderItem.getTotalDiscount().doubleValue()
                                        //mais diferença do frete cobrado e o pago para o fornecedor
                                        + (customerOrderItem.getSupplierEstimatedFreight().doubleValue() - freightSum)
                                        + discountSum
                                ).setScale(scalePrice, RoundingMode.HALF_UP)
                        );

                        customerOrderItem.setProfitPercent(
                                customerOrderItem.getProfit()
                                        //lucro dividido pelo total do item no forncedor
                                        .divide(new BigDecimal(quantitySum * priceAvg), RoundingMode.HALF_UP)
                                        //multiplicado por 100
                                        .multiply(new BigDecimal(100)).setScale(scalePrice, RoundingMode.HALF_UP)
                        );

                    } else {

                        customerOrderItem.setSupplierQuantityBought(BigDecimal.ZERO);
                        customerOrderItem.setSupplierPoweredPrice(BigDecimal.ZERO);
                        customerOrderItem.setSupplierPoweredFreight(BigDecimal.ZERO);
                        customerOrderItem.setSupplierPoweredDiscount(BigDecimal.ZERO);
                        customerOrderItem.setSupplierPoweredTotal(BigDecimal.ZERO);

                        customerOrderItem.setProfit(
                                //total do item vendido
                                customerOrderItem.getQuantity().multiply(customerOrderItem.getProduct().getPrice())
                                        //menos total no fornecedor
                                        .subtract(customerOrderItem.getQuantity().multiply(customerOrderItem.getProduct().getSupplierPrice()))
                                        // menos total de descontos
                                        .subtract(customerOrderItem.getTotalDiscount())
                        );

                        customerOrderItem.setProfitPercent(
                                customerOrderItem.getProfit()
                                        //lucro dividido pelo total do item no forncedor
                                        .divide(customerOrderItem.getQuantity().multiply(customerOrderItem.getProduct().getSupplierPrice()), RoundingMode.HALF_UP)
                                        //multiplicado por 100
                                        .multiply(new BigDecimal(100))
                        );
                    }

                });

    }

    void addSupplier(String id, String supplierid, String updatedBy) {
        CustomerOrder customerOrder = customerOrderRepository.findById(id).get();
        if (customerOrder.getSuppliers().stream().filter(supplier -> supplier.getId().equals(supplierid)).count() >= 1) {
            return;
        }
        Supplier supplier = supplierService.getById(supplierid);
        customerOrder.getSuppliers().add(supplier);
        customerOrderRepository.save(customerOrder);

        customerOrder.getCustomerOrderUpdateHistorys().add(new CustomerOrderUpdateHistory(updatedBy, "Adicionado fornecedor " + supplier.getName() + "."));
    }

    void SupplierOrderAdd(String id, String supplierid, SupplierOrder supplierOrder, String updatedBy) {
        CustomerOrder customerOrder = customerOrderRepository.findById(id).get();
        Supplier supplier = supplierService.getById(supplierid);
        supplierOrder.setSupplier(supplier);

        StringBuilder sb = new StringBuilder();

        supplierOrder.getSupplierOrderItems().forEach((supplierOrderItem) -> {
            sb.append("===============").append("<br/>")
                    .append("Item: ").append(
                    customerOrder.getCustomerOrderItems().stream()
                            .filter(product -> product.getProduct().getId().equals(supplierOrderItem.getProductId()))
                            .findFirst().get().getProduct().getCode()).append(" - ");

            sb.append(
                    customerOrder.getCustomerOrderItems().stream()
                            .filter(product -> product.getProduct().getId().equals(supplierOrderItem.getProductId()))
                            .findFirst().get().getProduct().getName()).append("<br/>");

            supplierOrderItem.setTotal(supplierOrderItem.getQuantity().multiply(supplierOrderItem.getPrice()));

            supplierOrderItem.setProductCode(customerOrder.getCustomerOrderItems().stream()
                    .filter(product -> product.getProduct().getId().equals(supplierOrderItem.getProductId()))
                    .findFirst().get().getProduct().getCode());

            supplierOrderItem.setProductName(customerOrder.getCustomerOrderItems().stream()
                    .filter(product -> product.getProduct().getId().equals(supplierOrderItem.getProductId()))
                    .findFirst().get().getProduct().getName());

            sb.append("Qtd Comprada: ").append(supplierOrderItem.getQuantity()).append("<br/>");
            sb.append("Preço: ").append(supplierOrderItem.getPrice()).append("<br/>");
            sb.append("Frete: ").append(supplierOrderItem.getFreight()).append("<br/>");
            sb.append("Discount: ").append(supplierOrderItem.getDiscount()).append("<br/>");
            sb.append("Total: ").append(supplierOrderItem.getTotal()).append("<br/>");
            sb.append("Cod. Rastr: ").append(supplierOrderItem.getTrackingCode()).append("<br/>");
            sb.append("Cod. Notas: ").append(supplierOrderItem.getNotes()).append("<br/>");
        });

        customerOrder.getCustomerOrderUpdateHistorys()
                .add(new CustomerOrderUpdateHistory(updatedBy,
                        "Adicionado pedido de fornecedor " + supplierOrder.getSupplier().getName()
                        + " - " + supplierOrder.getCode() + "<br/>"
                        + " Notas do pedido: " + supplierOrder.getCode() + "<br/>"
                        + sb.toString()
                ));

        supplierOrder.setId(null);
        customerOrder.getSupplierOrders().add(supplierOrderService.createOrder(supplierOrder));
        recalculateOrder(customerOrder, updatedBy);
        customerOrderRepository.save(customerOrder);
    }

    void addVariant(String id, String productid, String name, String value, String updatedBy) {
        CustomerOrder customerOrder = customerOrderRepository.findById(id).get();
        customerOrder.getCustomerOrderItems()
                .stream()
                .filter(product -> product.getProduct().getId().equals(productid))
                .forEach((item) -> {
                    CustomerOrderItemVariant customerOrderItemVariant = new CustomerOrderItemVariant();
                    customerOrderItemVariant.setName(name);
                    customerOrderItemVariant.setValue(value);
                    item.getCustomerOrderItemVariants().add(customerOrderItemVariant);
                });
        customerOrderRepository.save(customerOrder);
    }
    void addNotes(String id, String notes, String updatedBy) {
        CustomerOrder customerOrder = customerOrderRepository.findById(id).get();
        customerOrder.setNotes(notes);
        customerOrderRepository.save(customerOrder);
    }
}
