package com.example.ecobookclient.controller;

import com.example.ecobookclient.request.CartItemRequest;
import com.example.ecobookclient.response.CartResponse;
import com.example.ecobookclient.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping(path = "/cart")
public class CartController {
    private final RestTemplate restTemplate = new RestTemplate();
    @PostMapping
    public String addCart(HttpSession session,
                                Model model){
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/sign-in";
        }else {
            if (session.getAttribute("cart") == null) {
                session.setAttribute("cart",new CartResponse());
            }else {
                String urlCart = "http://localhost:8086/api/cart/active-cart";
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization","Bearer " + user.getAccessToken());
                headers.setContentType(MediaType.APPLICATION_JSON);
                CartItemRequest item = new CartItemRequest();
                HttpEntity<CartItemRequest> entity = new HttpEntity<>(item, headers);
                ResponseEntity<CartResponse> response = restTemplate.exchange(urlCart, HttpMethod.POST, entity, CartResponse.class);
                CartResponse cart = response.getBody();
                session.setAttribute("cart",cart);
                return "/";
            }
        }
        return "/";
    }
}
