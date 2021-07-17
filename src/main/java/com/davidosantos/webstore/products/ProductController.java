/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.products;

import com.davidosantos.webstore.images.Image;
import com.davidosantos.webstore.images.ImageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author david
 */
@Controller
@RequestMapping("/backoffice")
public class ProductController {

    @Autowired
    ImageService imageService;
    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @RequestMapping("/url")
    public String page(Model model) {
        model.addAttribute("attribute", "value");
        return "view.name";
    }

    @RequestMapping("/productslist")
    public String backofficeListProductsPage(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "7") int size, @RequestParam(defaultValue = "") String code,
            @RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "") String providerName,
            Model model) {
        // https://www.baeldung.com/spring-thymeleaf-pagination

        Pageable paging = PageRequest.of(page, size);
        Page productPage;
        if (code.equals("") && name.equals("") && providerName.equals("")) {
            productPage = productRepository.findAll(paging);
        } else {
            productPage = productRepository
                    .findByCodeContainingIgnoreCaseAndNameContainingIgnoreCaseAndProviderNameContainingIgnoreCase(code,
                            name, providerName, paging);
        }

        model.addAttribute("productPage", productPage);

        int totalPages = productPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages - 1).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "backoffice/productslist";
    }

    @RequestMapping("/productselect")
    public String backofficeListSelectProductsPage(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "7") int size, @RequestParam(defaultValue = "") String code,
            @RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "") String providerName,
            String redirectTo, String method, String id, Model model) {
        // https://www.baeldung.com/spring-thymeleaf-pagination

        Pageable paging = PageRequest.of(page, size);
        Page productPage;
        if (code.equals("") && name.equals("") && providerName.equals("")) {
            productPage = productService.getAllProducts(paging);
        } else {
            productPage = productService.getByDefaultFilter(code, name, providerName, paging);
        }

        model.addAttribute("redirectTo", redirectTo);
        model.addAttribute("method", method);
        model.addAttribute("id", id);
        model.addAttribute("productPage", productPage);

        int totalPages = productPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages - 1).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "backoffice/productsselect";
    }

    @RequestMapping("/products")
    public String backofficeEditProductsPage(@RequestParam(defaultValue = "") String id, Model model) {
        Product product;
        if (id.equals("")) {
            product = new Product();
        } else {
            product = productRepository.findById(id).get();
        }

        List<ProductCategory> productCategories = productService.getProductCategories();

        ProductCategory productCategorynew = new ProductCategory();

        ProductCategory productCategory;
        if (product.getProductCategory() == null) {
            // for new category
            productCategory = new ProductCategory();
        } else {
            productCategory = product.getProductCategory();

        }

        model.addAttribute("product", product);

        model.addAttribute("productCategory", productCategory);

        model.addAttribute("productCategorynew", productCategorynew);

        model.addAttribute("productCategories", productCategories);

        return "backoffice/products";
    }

    @RequestMapping("/products/product-variation")
    public String backofficeEditProductsVariatonPage(@RequestParam(defaultValue = "") String id, Model model) {
        Product product;
        if (id.equals("")) {
            product = new Product();
        } else {
            product = productRepository.findById(id).get();
        }

        List<ProductVariant> productVariants;
        if (product.getProductVariants() != null) {
            productVariants = product.getProductVariants();
        } else {
            productVariants = new ArrayList<ProductVariant>();
        }

        model.addAttribute("product", product);

        model.addAttribute("productVariants", productVariants);

        return "backoffice/product-variation";
    }

    @RequestMapping(value = "/products/product-variation", method = RequestMethod.POST)
    public String backofficeSaveProductsVariatonPage(@RequestParam(defaultValue = "") String id, String variantName,
            Model model) {
        Product product;
        if (id.equals("")) {
            product = new Product();
        } else {
            product = productRepository.findById(id).get();
        }

        ProductVariant productVariant = new ProductVariant();
        productVariant.setIsActive(true);
        productVariant.setName(variantName);
        if (product.getProductVariants() == null) {
            product.setProductVariants(new ArrayList<ProductVariant>());
            product.getProductVariants().add(productVariant);
        } else {
            product.getProductVariants().add(productVariant);
        }

        productService.saveProduct(product);

        return "redirect:/backoffice/products/product-variation?id=" + product.getId();
    }

    @RequestMapping(value = "/products/product-variation-value", method = RequestMethod.POST)
    public String backofficeSaveProductsVariatonValuePage(@RequestParam(defaultValue = "") String id,
            @RequestParam(defaultValue = "") String variantName, @RequestParam(defaultValue = "") String variantValue,
            Model model) {
        Product product;
        if (id.equals("")) {
            product = new Product();
        } else {
            product = productRepository.findById(id).get();
        }

        ProductVariantValue productVariantValue = new ProductVariantValue();
        productVariantValue.setIsActive(true);
        productVariantValue.setName(variantValue);

        product.getProductVariants().stream().filter(variant -> variant.getName().equals(variantName)).findFirst().get()
                .getProductVariantValues().add(productVariantValue);

        productService.saveProduct(product);

        return "redirect:/backoffice/products/product-variation?id=" + product.getId();
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public String backofficeSaveProductsPage(@Validated @ModelAttribute Product product, BindingResult errors,
            Model model) {
        errors.getFieldErrors().stream().forEach((error) -> {
            System.out.println("Erro: " + error.toString());
        });

        if (product.getId().equals("")) {
            product.setId(null);
        }

        if (product.getProductCategory() != null) {
            product.setProductCategory(productCategoryRepository.findById(product.getProductCategory().getId()).get());
        }

        product = productService.saveProduct(product);
        model.addAttribute("product", product);
        return "redirect:/backoffice/products?id=" + product.getId();
    }

    @RequestMapping(value = "/products/category", method = RequestMethod.POST)
    public String backofficeSaveProductsCategoryPage(@Validated @ModelAttribute ProductCategory productCategory,
            BindingResult errors, Model model) {
        errors.getFieldErrors().stream().forEach((error) -> {
            System.out.println("Erro: " + error.toString());
        });

        if (productCategory.getId() != null && productCategory.getId().equals("")) {
            productCategory.setId(null);
        }

        productCategory = productService.saveProduct(productCategory);
        model.addAttribute("productCategory", productCategory);
        return "redirect:/backoffice/products";
    }

    @RequestMapping(value = "/products/category/delete", method = RequestMethod.POST)
    public String backofficeDeatitivateProductsCategoryPage(@RequestParam("id") String id, @RequestParam("categoryId") String categoryId) {
        ProductCategory productCategory = productCategoryRepository.findById(categoryId).get();
        productCategory.setIsActive(false);
        productCategoryRepository.save(productCategory);
        return "redirect:/backoffice/products?id=" + id;
    }

    @PostMapping("/productimage")
    public ResponseEntity<Image> uploadImage(@RequestParam("id") String id, @RequestParam("title") String title,
            @RequestParam("image") MultipartFile image) throws IOException {
        Image uploadedImage = imageService.uploadImage(title, image);
        productService.saveImage(id, uploadedImage.getId());
        uploadedImage.setImageData(null);
        return new ResponseEntity<>(
            uploadedImage,
                 HttpStatus.OK);
    }

}
