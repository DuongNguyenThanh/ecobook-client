package com.example.ecobookclient.controller;

import com.example.ecobookclient.request.CartItemRequest;
import com.example.ecobookclient.request.LoginRequest;
import com.example.ecobookclient.response.CartItemResponse;
import com.example.ecobookclient.response.CartResponse;
import com.example.ecobookclient.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping(path = "/cart")
public class CartController {
    private final RestTemplate restTemplate = new RestTemplate();
    @PostMapping
    public String addCart(HttpSession session,
                          Model model,
                          @RequestParam(name = "bookId") Integer bookId,
                          @RequestParam(name = "bookPrice") Float price){
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/sign-in";
        }else {
            String urlCart = "http://localhost:8086/api/cart/update-cart";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization","Bearer " + user.getAccessToken());
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<CartItemRequest> entity = new HttpEntity<>(CartItemRequest.builder()
                    .bookId(bookId)
                    .price(price)
                    .build(), headers);
            ResponseEntity<String> response = restTemplate.exchange(urlCart, HttpMethod.POST,
                    entity, String.class);
            model.addAttribute("message",response.getBody());
            log.info(response.getBody());
            String urlGetCart = "http://localhost:8086/api/cart/active-cart";
            HttpEntity<CartItemRequest> entity1 = new HttpEntity<>(new CartItemRequest(), headers);
            ResponseEntity<CartResponse> response1 = restTemplate.exchange(urlGetCart, HttpMethod.GET, entity1,
                    CartResponse.class);
            CartResponse cart = response1.getBody();
            session.setAttribute("cart",cart);
            session.setAttribute("cic",cart.getItems().stream().count());
            return "redirect:/shop/0";

        }
    }
    @GetMapping("/{itemId}")
    public String deleteCartItem(HttpSession session,
                                 Model model,
                                 @PathVariable(name = "itemId") Integer id){
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/sign-in";
        }else {
            String urlDelCart = "http://localhost:8086/api/cart-item/"+id;
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization","Bearer " + user.getAccessToken());
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<CartItemRequest> entity1 = new HttpEntity<>(new CartItemRequest(), headers);
            ResponseEntity<String> response = restTemplate.exchange(urlDelCart, HttpMethod.DELETE,entity1, String.class);
            model.addAttribute("message",response.getBody());
            log.info(response.getBody());
            String urlGetCart = "http://localhost:8086/api/cart/active-cart";

            ResponseEntity<CartResponse> response1 = restTemplate.exchange(urlGetCart, HttpMethod.GET, entity1,
                    CartResponse.class);
            CartResponse cart = response1.getBody();
            session.setAttribute("cart",cart);
            session.setAttribute("cic",cart.getItems().stream().count());
            session.setAttribute("subtotal",String.format("%.2f",
                    cart.getItems()
                            .stream()
                            .mapToDouble(CartItemResponse::getPrice)
                            .sum()));
            return "redirect:/shop/0";

        }

    }
}
