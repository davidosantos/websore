/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davidosantos.webstore;

import java.io.IOException;

import com.davidosantos.webstore.customers.CustomerService;
import com.davidosantos.webstore.images.ImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author david
 */
@Controller
@RequestMapping("/backoffice")
public class BackOfficeController {

    @Autowired
    CustomerService customerService;

    @Autowired
    ImageService imageService;

    @PostMapping("/images/upload")
    public String uploadImage(@RequestParam("title") String title, @RequestParam("redirectTo") String redirectTo,
            @RequestParam("image") MultipartFile image) throws IOException {
        imageService.uploadImage(title, image);
        return "redirect:" + redirectTo;
    }

    @RequestMapping(value = "")
    public String backofficePage(Model model) {
        model.addAttribute("attribute", "value");
        return "backoffice/backoffice";
    }

    @ExceptionHandler({ Exception.class })
    public String databaseError() {
        return "error-view-name";
    }

}
