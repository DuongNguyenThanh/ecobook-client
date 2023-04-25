package com.example.ecobookclient.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(path = "/ecobook")
public class TestController {
    @GetMapping("/")
    public String getHome(){
        return "index";
    }
    @GetMapping("/shop")
    public String getShop(){
        return "shop";
    }
    @GetMapping("/detail")
    public String getDetail(){
        return "single-product-details";
    }
    @GetMapping("/checkout")
    public String getCheckout(){
        return "checkout";
    }

}
