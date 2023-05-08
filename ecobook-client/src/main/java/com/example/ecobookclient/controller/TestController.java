package com.example.ecobookclient.controller;

import com.example.ecobookclient.request.CartItemRequest;
import com.example.ecobookclient.response.CartResponse;
import com.example.ecobookclient.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping(path = "/ecobook")
public class TestController {
    private final RestTemplate restTemplate = new RestTemplate();
    @GetMapping("/")
    public String getHome(){
//        UserResponse user = (UserResponse) session.getAttribute("user");
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        try {
//            session.setAttribute("user", Objects.requireNonNull(user));
//            redirectAttributes.addFlashAttribute("user", user);
//            String filePath = "D:/image/access.txt";
//            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,false))) {
//                writer.write(user.getAccessToken());
//            } catch (IOException e) {
//                // handle exception
//            }
//            //get cart info when log in success
//            String urlCart = "http://localhost:8086/api/cart/active-cart";
//            headers.set("Authorization","Bearer " + user.getAccessToken());
//            HttpEntity<CartItemRequest> entity1 = new HttpEntity<>(new CartItemRequest(), headers);
//            ResponseEntity<CartResponse> response = restTemplate.exchange(urlCart, HttpMethod.GET, entity1, CartResponse.class);
//            CartResponse cart = response.getBody();
//            session.setAttribute("cart",cart);
//            if (cart.getItems() == null) {
//                session.setAttribute("cic",new Integer(0));
//                session.setAttribute("subtotal",String.format("%.2f",new Double(0)));
//            } else {
//                session.setAttribute("cic",cart.getItems()
//                        .stream()
//                        .count());
//                session.setAttribute("subtotal",String.format("%.2f",
//                        cart.getItems()
//                                .stream()
//                                .mapToDouble(item -> item.getPrice() * item.getQuantity())
//                                .sum()));
//            }
////            ResponseEntity<String> response1 = restTemplate.exchange("http://localhost:8081/api/user/oauth2/success/", HttpMethod.GET, entity1, String.class);
////
//            return "index";
//
//        } catch (Exception ex) {
//            log.info(ex.getMessage());
//            redirectAttributes.addFlashAttribute("message", "wrong password or username");
//            return "/user/sign-in";
//        }
        return "index";

    }
}
