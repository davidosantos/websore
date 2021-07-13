/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore.supplier;

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
@RequestMapping("/backoffice/supplier")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @RequestMapping("/edit")
    public String backofficeSupplierEditPage(
            @RequestParam(defaultValue = "") String id,
            Model model) {

        Supplier supplier;
        if (id.equals("")) {
            supplier = new Supplier();
        } else {
            supplier = supplierService.getById(id);
        }

        model.addAttribute("supplier", supplier);

        return "backoffice/suppliers";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String backofficeSupplierSavePage(
            Supplier supplier,
            Model model) {

        Supplier supplierDB;
        if (supplier.getId().equals("")) {
            supplier.setId(null);
        } else {
            supplierDB = supplierService.getById(supplier.getId());
            supplierDB.setIsActive(supplier.isIsActive());
            supplierDB.setName(supplier.getName());
            supplierDB.setNotes(supplier.getNotes());
            supplierDB.setUrl(supplier.getUrl());
            return "redirect:/backoffice/supplier/edit?id=" + supplierService.save(supplierDB).getId();
        }

        return "redirect:/backoffice/supplier/edit?id=" + supplierService.save(supplier).getId();
    }

    @RequestMapping(value = "/phone", method = RequestMethod.POST)
    public String backofficeSupplierSavePhonePage(
            String id,
            String phone,
            Model model) {
        Supplier supplier = supplierService.getById(id);
        supplier.getPhones().add(phone);
        return "redirect:/backoffice/supplier/edit?id=" + supplierService.save(supplier).getId();
    }

    @RequestMapping(value = "/email", method = RequestMethod.POST)
    public String backofficeSupplierSaveEmailPage(
            String id,
            String email,
            Model model) {
        Supplier supplier = supplierService.getById(id);
        supplier.getEmails().add(email);
        return "redirect:/backoffice/supplier/edit?id=" + supplierService.save(supplier).getId();
    }

    @RequestMapping("/select")
    public String backofficeListSelectSuppliersPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "7") int size,
            @RequestParam(defaultValue = "") String name,
            String redirectTo,
            String method,
            String id,
            Model model) {
//https://www.baeldung.com/spring-thymeleaf-pagination

        Pageable paging = PageRequest.of(page, size);
        Page supplierPage;
        if (name.equals("")) {
            supplierPage = supplierService.getAllSuppliers(paging);
        } else {
            supplierPage = supplierService.getByDefaultFilter(name, paging);
        }

        model.addAttribute("redirectTo", redirectTo);
        model.addAttribute("method", method);
        model.addAttribute("id", id);
        model.addAttribute("supplierPage", supplierPage);

        int totalPages = supplierPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages - 1)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "backoffice/suppliersselect";
    }

}
