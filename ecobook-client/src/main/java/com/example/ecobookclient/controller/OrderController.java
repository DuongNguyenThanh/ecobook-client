package com.example.ecobookclient.controller;

import com.example.ecobookclient.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping(path = "/checkout")
public class OrderController {
    private final RestTemplate restTemplate = new RestTemplate();
    @GetMapping("/")
    public String getCheckout(HttpSession session,
                              Model model){
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user != null) {
            String url = "http://localhost:8086/api/cart/update-cart";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization","Bearer " + user.getAccessToken());
            headers.setContentType(MediaType.APPLICATION_JSON);
            return "checkout";
        }

        return "redirect:/user/sign-in";
    }
}
