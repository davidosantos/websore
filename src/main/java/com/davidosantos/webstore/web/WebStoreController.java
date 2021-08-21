/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.web;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpSession;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.CreditCard;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.Transaction.Status;
import com.braintreegateway.TransactionRequest;
import com.braintreegateway.ValidationError;
import com.davidosantos.webstore.carousel.Carousel;
import com.davidosantos.webstore.carousel.CarouselService;
import com.davidosantos.webstore.checkouts.paypal.BraintreeGatewayFactory;
import com.davidosantos.webstore.customers.Customer;
import com.davidosantos.webstore.customers.CustomerAddress;
import com.davidosantos.webstore.customers.CustomerService;
import com.davidosantos.webstore.images.ImageService;
import com.davidosantos.webstore.kart.Kart;
import com.davidosantos.webstore.kart.KartItem;
import com.davidosantos.webstore.kart.KartService;
import com.davidosantos.webstore.orders.CustomerOrder;
import com.davidosantos.webstore.orders.CustomerOrderItemVariant;
import com.davidosantos.webstore.orders.CustomerOrderProductItem;
import com.davidosantos.webstore.orders.CustomerOrderService;
import com.davidosantos.webstore.orders.CustomerOrderStatus;
import com.davidosantos.webstore.products.Product;
import com.davidosantos.webstore.products.ProductCategory;
import com.davidosantos.webstore.products.ProductCategoryRepository;
import com.davidosantos.webstore.products.ProductRepository;
import com.davidosantos.webstore.products.ProductService;
import com.davidosantos.webstore.products.ProductVariant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author david
 */
@Controller
public class WebStoreController {

    @Autowired
    ImageService imageService;
    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    CarouselService carouselService;

    @Autowired
    KartService kartService;

    @Autowired
    CustomerOrderService customerOrderService;

    @Autowired
    CustomerService customerService;

    private final BraintreeGateway gateway = BraintreeGatewayFactory.fromConfigFile(new File("config.properties"));

    public boolean addKart(HttpSession session, Model model) {
        Kart kart;
        if (session.getAttribute("cartId") != null
                && kartService.countKart(session.getAttribute("cartId").toString()) > 0) {
            kart = kartService.getKart(session.getAttribute("cartId").toString());
        } else {
            kart = new Kart();
        }
        model.addAttribute("kart", kart);
        return true;
    }

    @RequestMapping("/")
    public String homePage(HttpSession session, Model model) {
        List<Product> products = productRepository.findByIsActiveAndDisplayInHome(true, true);
        List<ProductCategory> productCategories = productCategoryRepository.findByIsActive(true);
        List<Carousel> carouselItems = carouselService.getDefault();

        addKart(session, model);

        model.addAttribute("products", products);
        model.addAttribute("productCategories", productCategories);
        model.addAttribute("imageService", imageService);
        model.addAttribute("carouselItems", carouselItems);

        return "index";
    }

    @RequestMapping("/login")
    public String loginPage(){
        return "login";
    }

    @RequestMapping("/register")
    public String registerPage(){
        return "register";
    }

    @RequestMapping("/produtos")
    public String productPage(HttpSession session, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "") String categoryId,
            Model model) {
        List<ProductCategory> productCategories = productCategoryRepository.findByIsActive(true);

        addKart(session, model);

        model.addAttribute("productCategories", productCategories);

        Pageable paging = PageRequest.of(page, size);
        Page products;

        if (categoryId.equals("")) {
            products = productRepository.findByIsActive(true, paging);
        } else {
            products = productRepository.findByProductCategoryIdAndIsActive(categoryId, true, paging);
        }

        model.addAttribute("products", products);
        model.addAttribute("categoryId", categoryId);

        int totalPages = products.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages - 1).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "products";
    }

    @RequestMapping("/detalhes")
    public String productDetailsPage(HttpSession session, @RequestParam(defaultValue = "") String id, Model model) {
        List<ProductCategory> productCategories = productCategoryRepository.findByIsActive(true);

        addKart(session, model);

        model.addAttribute("productCategories", productCategories);

        Product product;
        if (id.equals("")) {
            product = productRepository.findAll().stream().findFirst().get();
        } else {
            product = productRepository.findById(id).get();
        }

        Pageable paging = PageRequest.of(0, 3);
        Page products = productRepository.findByProductCategoryIdAndIsActive(product.getProductCategory().getId(), true,
                paging);

        model.addAttribute("product", product);
        model.addAttribute("products", products);

        return "product-details";
    }

    // shopping cart
    @RequestMapping("/carrinho")
    public String shoppingCartPage(HttpSession session, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "") String categoryId,
            Model model) {

        List<ProductCategory> productCategories = productCategoryRepository.findByIsActive(true);

        addKart(session, model);

        model.addAttribute("productCategories", productCategories);

        Pageable paging = PageRequest.of(page, size);
        Page products;

        if (categoryId.equals("")) {
            products = productRepository.findByIsActive(true, paging);
        } else {
            products = productRepository.findByProductCategoryIdAndIsActive(categoryId, true, paging);
        }

        model.addAttribute("products", products);
        model.addAttribute("categoryId", categoryId);

        int totalPages = products.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages - 1).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "cart";
    }

    @RequestMapping(value = "/carrinho", method = RequestMethod.POST)
    public String shoppingCartEditPage(@CookieValue(value = "JSESSIONID", required = false) String JSESSIONID,
            @RequestParam(defaultValue = "") String operation, int itemIndex, Kart kart, int quantity, Model model) {

        if (JSESSIONID.equals("")) {
            return "redirect:/carrinho";
        }

        System.out.println("Operation: " + operation);
        if (operation.equals("reactivate")) {
            kartService.reactivateItem(kart.getId(), itemIndex);
        } else if (operation.equals("cancel")) {
            kartService.cancelItem(kart.getId(), itemIndex);
        } else if (operation.equals("alterQuantity")) {
            kartService.alterQuantity(kart.getId(), itemIndex, quantity);
        }

        return "redirect:/carrinho";
    }

    @ExceptionHandler({ Exception.class })
    public String databaseError() {
        return "error-view-name";
    }

    // images
    @GetMapping("/images/downloadDecoded/{id}")
    public ResponseEntity<byte[]> getImageDecoded(@PathVariable String id) {
        HttpHeaders headers = new HttpHeaders();

        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        return new ResponseEntity<>(Base64.getDecoder().decode(imageService.downloadImage(id).getImageData()), headers,
                HttpStatus.OK);
    }

    @RequestMapping(value = "/createShippingCart", method = RequestMethod.POST)
    public String createcreateShippingCart(HttpSession session, @RequestParam String action, Product product,
            int quantity, Model model) {

        System.out.println("Action: " + action);
        System.out.println("product: " + product);

        if (action.equals("buy")) {
            session.setAttribute("quantity", quantity);
            session.setAttribute("product", product);
            return "redirect:checkoutSingleItem";
        }

        if (session.getAttribute("cartId") != null
                && kartService.countKart(session.getAttribute("cartId").toString()) > 0) {
            Kart kart = kartService.getKart(session.getAttribute("cartId").toString());
            kart.setLastUpdateDate(new Date());
            KartItem kartItem = new KartItem();
            kartItem.setProduct(productRepository.findById(product.getId()).get());
            kartItem.setStatus("active");
            kartItem.setQuantity(quantity);

            if (kart.getItems().stream().filter(filter -> filter.getStatus().equals("active")
                    && filter.getProduct().getId().equals(product.getId())).count() <= 0) {
                kart.getItems().add(kartItem);
            } else {

                KartItem kartItemFromDB = kart.getItems().stream().filter(filter -> filter.getStatus().equals("active")
                        && filter.getProduct().getId().equals(product.getId())).findFirst().get();

                kartItemFromDB.setQuantity(kartItemFromDB.getQuantity() + quantity);
                kartItemFromDB.setProductVariants(product.getProductVariants());
            }

            kartService.save(kart);
            session.setAttribute("cartId", kart.getId());
        } else {
            Kart kart = new Kart();
            kart.setCreatedDate(new Date());
            kart.setLastUpdateDate(new Date());
            kart.setStatus("active");
            KartItem kartItem = new KartItem();
            kartItem.setProduct(productRepository.findById(product.getId()).get());
            kartItem.setStatus("active");
            kartItem.setQuantity(quantity);
            kartItem.setProductVariants(product.getProductVariants());
            kart.getItems().add(kartItem);
            kartService.save(kart);
            session.setAttribute("cartId", kart.getId());
        }

        return "redirect:/detalhes?id=" + product.getId();
    }

    // checkout 1 - quando usuário quer comprar apenas 1 item direto sem carrinho
    @RequestMapping("/checkoutSingleItem")
    public String checkoutAddressSingleItemPage(HttpSession session, Authentication auth, Model model) {

        Customer loggedCustomer = (Customer) auth.getPrincipal();
        Customer customer = customerService.getById(loggedCustomer.getId());
        CustomerAddress customerAddress = new CustomerAddress();

        List<ProductCategory> productCategories = productCategoryRepository.findByIsActive(true);

        model.addAttribute("productCategories", productCategories);

        addKart(session, model);

        model.addAttribute("customer", customer);
        model.addAttribute("customerAddress", customerAddress);
        model.addAttribute("singleItem", true);

        Product product = (Product) session.getAttribute("product");
        int quantity = Integer.valueOf(session.getAttribute("quantity").toString());

        Product productFromDB = productService.getById(product.getId());
        productFromDB.setProductVariants(product.getProductVariants());

        BigDecimal productTotal = productFromDB.getPrice().multiply(new BigDecimal(Integer.valueOf(quantity)));
        BigDecimal discountTotal = productFromDB.getDiscount().multiply(new BigDecimal(Integer.valueOf(quantity)));
        BigDecimal freightTotal = productFromDB.getFreight().multiply(new BigDecimal(Integer.valueOf(quantity)));
        BigDecimal total = productTotal.subtract(discountTotal).add(freightTotal);

        model.addAttribute("product", productFromDB);
        model.addAttribute("productTotal", productTotal);
        model.addAttribute("discountTotal", discountTotal);
        model.addAttribute("freightTotal", freightTotal);
        model.addAttribute("total", total);

        return "checkout";
    }

    // checkout 1
    @RequestMapping("/checkout")
    public String checkoutAddressPage(HttpSession session, @RequestParam(defaultValue = "") String quantity,
            Authentication auth, Model model) {
        Customer loggedCustomer = (Customer) auth.getPrincipal();
        Customer customer = customerService.getById(loggedCustomer.getId());
        CustomerAddress customerAddress = new CustomerAddress();

        List<ProductCategory> productCategories = productCategoryRepository.findByIsActive(true);

        model.addAttribute("productCategories", productCategories);

        addKart(session, model);

        model.addAttribute("customer", customer);
        model.addAttribute("customerAddress", customerAddress);

        return "checkout";
    }

    // checkout 2
    @RequestMapping("/checkout-step2")
    public String checkoutPaymentPage(HttpSession session, String customerOrderId, Authentication auth, Model model) {
        Customer loggedCustomer = (Customer) auth.getPrincipal();
        Customer customer = customerService.getById(loggedCustomer.getId());

        addKart(session, model);
        List<ProductCategory> productCategories = productCategoryRepository.findByIsActive(true);

        model.addAttribute("productCategories", productCategories);

        CustomerOrder customerOrder = customerOrderService.getByCode(customerOrderId);
        String clientToken = gateway.clientToken().generate();
        model.addAttribute("clientToken", clientToken);

        model.addAttribute("customer", customer);
        model.addAttribute("customerOrder", customerOrder);

        return "checkout-step2";
    }

    @RequestMapping(value = "/checkout-step2", method = RequestMethod.POST)
    public String checkoutPaymentPostPage(@RequestParam("payment_method_nonce") String nonce, Model model,
            final RedirectAttributes redirectAttributes, @RequestParam("customerOrderId") String customerOrderId) {

        CustomerOrder customerOrder = customerOrderService.getByCode(customerOrderId);

        BigDecimal decimalAmount;
        try {
            decimalAmount = customerOrder.getTotalAmount();
        } catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute("errorDetails", "Error: 81503: Amount is an invalid format.");
            return "redirect:/checkouts-step2?customerOrderId=" + customerOrderId;
        }

        TransactionRequest request = new TransactionRequest().amount(decimalAmount).paymentMethodNonce(nonce)
                .orderId(customerOrder.getCode()).options().submitForSettlement(true).done();

        Result<Transaction> result = gateway.transaction().sale(request);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        if (result.isSuccess()) {
            Transaction transaction = result.getTarget();
            
            customerOrderService.addPaymentStatus(customerOrder, transaction.getAmount(),true, transaction.getStatus().name(),
            new StringBuilder()
            .append("----- result -----\n")
            .append(gson.toJson(result))
            .append("\n----- transaction -----\n")
            .append(gson.toJson(transaction))
            .toString()
            , customerOrder.getCustomer().getEmail());

            customerOrderService.changeOrderStatus(customerOrder,CustomerOrderStatus.Paid,customerOrder.getCustomer().getEmail(),true);
            return "redirect:/checkouts-step3?transactionId=" + transaction.getId();
        } else if (result.getTransaction() != null) {
            Transaction transaction = result.getTransaction();

            customerOrderService.addPaymentStatus(customerOrder, transaction.getAmount(),true, transaction.getStatus().name(),
            new StringBuilder()
            .append("----- result -----\n")
            .append(gson.toJson(result))
            .append("\n----- transaction -----\n")
            .append(gson.toJson(transaction))
            .toString()
            , customerOrder.getCustomer().getEmail());

            customerOrderService.changeOrderStatus(customerOrder,CustomerOrderStatus.Paid,customerOrder.getCustomer().getEmail(),true);
            return "redirect:/checkouts-step3?transactionId=" + transaction.getId();
        } else {

            customerOrderService.addPaymentStatus(customerOrder, customerOrder.getTotalAmount(),false, "falha",
            new StringBuilder()
            .append("----- result -----\n")
            .append(gson.toJson(result))
            .toString(),
             customerOrder.getCustomer().getEmail());

            StringBuilder errorString = new StringBuilder();
            for (ValidationError error : result.getErrors().getAllDeepValidationErrors()) {
                errorString.append("Error: ").append(error.getCode()).append(": ").append(error.getMessage())
                        .append("\n");
            }
            redirectAttributes.addFlashAttribute("errorDetails", errorString.toString());
            return "redirect:/checkout-step2?customerOrderId=" + customerOrderId;
        }
    }

    @RequestMapping(value = "checkouts-step3")
    public String checkoutPaymentDonePage(HttpSession session, @RequestParam String transactionId, Model model) {
        Transaction transaction;
        CreditCard creditCard;

        List<ProductCategory> productCategories = productCategoryRepository.findByIsActive(true);

        model.addAttribute("productCategories", productCategories);

        Status[] TRANSACTION_SUCCESS_STATUSES = new Status[] { Transaction.Status.AUTHORIZED,
                Transaction.Status.AUTHORIZING, Transaction.Status.SETTLED, Transaction.Status.SETTLEMENT_CONFIRMED,
                Transaction.Status.SETTLEMENT_PENDING, Transaction.Status.SETTLING,
                Transaction.Status.SUBMITTED_FOR_SETTLEMENT };

        try {
            transaction = gateway.transaction().find(transactionId);
            creditCard = transaction.getCreditCard();

        } catch (Exception e) {
            System.out.println("Exception: " + e);
            return "redirect:/checkouts";
        }

        if (Arrays.asList(TRANSACTION_SUCCESS_STATUSES).contains(transaction.getStatus())) {
            List<Product> products = productRepository.findByIsActiveAndDisplayInHome(true, true);
            model.addAttribute("products", products);
        }

        model.addAttribute("isSuccess", Arrays.asList(TRANSACTION_SUCCESS_STATUSES).contains(transaction.getStatus()));
        model.addAttribute("transaction", transaction);
        model.addAttribute("creditCard", creditCard);

        addKart(session, model);

        return "checkout-step3";
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public String checkoutAddressPostPage(HttpSession session, @RequestParam(required = false) String addressIndex,
            @RequestParam(defaultValue = "") String singleItem, String addressAction, CustomerAddress customerAddress,
            Authentication auth, Model model) {

        Customer customer = (Customer) auth.getPrincipal();
        if (addressAction.equals("newCustomerAddress")) {
            customer.getAddresses().add(customerAddress);
            customerService.saveCustomer(customer);
        } else if (addressAction.equals("selectedCustomerAddress")) {
            customerAddress = customer.getAddresses().get(Integer.valueOf(addressIndex));
        }

        CustomerOrder customerOrder = null;

        if (!singleItem.equals("")) {

            customerOrder = customerOrderService.createOrder(customer, customerAddress, customer.getEmail());

            Product product = (Product) session.getAttribute("product");
            int quantity = Integer.valueOf(session.getAttribute("quantity").toString());
            Product productFromDB = productService.getById(product.getId());
            productFromDB.setProductVariants(product.getProductVariants());

            CustomerOrderProductItem customerOrderProductItem = new CustomerOrderProductItem();
            customerOrderProductItem.setProduct(productFromDB);
            customerOrderProductItem.setQuantity(new BigDecimal(Integer.valueOf(quantity)));
            customerOrderProductItem.setStatus("active");
            List<CustomerOrderItemVariant> customerOrderItemVariants = new ArrayList<CustomerOrderItemVariant>();

            for (ProductVariant productVariant : product.getProductVariants()) {
                CustomerOrderItemVariant customerOrderItemVariant = new CustomerOrderItemVariant();
                customerOrderItemVariant.setName(productVariant.getName());
                customerOrderItemVariant.setValue(productVariant.getProductVariantValues().get(0).getName());
                customerOrderItemVariants.add(customerOrderItemVariant);

            }

            customerOrderProductItem.setCustomerOrderItemVariants(customerOrderItemVariants);
            customerOrder.getCustomerOrderItems().add(customerOrderProductItem);
            customerOrderService.update(customerOrder, customer.getEmail());
            session.removeAttribute("product");
            session.removeAttribute("quantity");
            customerOrderService.changeOrderStatus(customerOrder,CustomerOrderStatus.PaymentWait,customerOrder.getCustomer().getEmail(),true);
        } else {

            if (kartService.countKart(session.getAttribute("cartId").toString()) > 0) {
                Kart kart = kartService.getKart(session.getAttribute("cartId").toString());

                customerOrder = customerOrderService.createOrder(customer, customerAddress, customer.getEmail());

                for (KartItem items : kart.getItems()) {
                    if (!items.getStatus().equals("active")) {
                        continue;
                    }

                    CustomerOrderProductItem customerOrderProductItem = new CustomerOrderProductItem();
                    customerOrderProductItem.setProduct(items.getProduct());
                    customerOrderProductItem.setQuantity(new BigDecimal(items.getQuantity()));
                    customerOrderProductItem.setStatus("active");
                    List<CustomerOrderItemVariant> customerOrderItemVariants = new ArrayList<CustomerOrderItemVariant>();

                    for (ProductVariant productVariant : items.getProductVariants()) {
                        CustomerOrderItemVariant customerOrderItemVariant = new CustomerOrderItemVariant();
                        customerOrderItemVariant.setName(productVariant.getName());
                        customerOrderItemVariant.setValue(productVariant.getProductVariantValues().get(0).getName());
                        customerOrderItemVariants.add(customerOrderItemVariant);

                    }

                    customerOrderProductItem.setCustomerOrderItemVariants(customerOrderItemVariants);

                    customerOrder.getCustomerOrderItems().add(customerOrderProductItem);
                    customerOrderService.update(customerOrder, customer.getEmail());
                    customerOrderService.changeOrderStatus(customerOrder,CustomerOrderStatus.PaymentWait,customerOrder.getCustomer().getEmail(),true);

                }

                kart.setStatus("finished");
                kartService.save(kart);
            }
        }

        addKart(session, model);

        return "redirect:/checkout-step2?customerOrderId=" + customerOrder.getCode();
    }

}
