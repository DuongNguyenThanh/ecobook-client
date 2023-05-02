package com.example.ecobookclient.controller.AdminController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/book")
    public String homeAdmin() {
        return "adminTemplates/book";
    }

    @GetMapping(value="/category")
    public String getMethodName() {
        return "adminTemplates/category";
    }
    
}
