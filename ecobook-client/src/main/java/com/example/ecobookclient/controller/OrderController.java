package com.example.ecobookclient.controller;

import com.example.ecobookclient.request.OrderItemRequest;
import com.example.ecobookclient.request.OrderRequest;
import com.example.ecobookclient.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping(path = "/checkout")
public class OrderController {
    private final RestTemplate restTemplate = new RestTemplate();
    @GetMapping("/")
    public String getCheckout(HttpSession session,
                              Model model){
        UserResponse user = (UserResponse) session.getAttribute("user");
        CartResponse cart = (CartResponse) session.getAttribute("cart");
        if (user != null && cart != null) {
            if (cart.getItems() != null) {
                String url = "http://localhost:8085/api/order/create";
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + user.getAccessToken());
                headers.setContentType(MediaType.APPLICATION_JSON);
                List<OrderItemRequest> list = cart.getItems().stream()
                        .map(cartItemResponse -> {
                            OrderItemRequest o = new OrderItemRequest();
                            o.setQuantity(cartItemResponse.getQuantity());
                            o.setBookId(cartItemResponse.getBook().getId());
                            return o;
                        })
                        .collect(Collectors.toList());
                HttpEntity<OrderRequest> entity = new HttpEntity<>(OrderRequest.builder()
                        .note("nothing")
                        .orderItemRequests(list)
                        .build(),headers);
                ResponseEntity<OrderResponse> response = restTemplate.exchange(url, HttpMethod.POST,entity,OrderResponse.class);
                model.addAttribute("order",response.getBody());
                model.addAttribute("sum", String.format("%.2f",response.getBody().getOrderItems()
                        .stream()
                        .mapToDouble(OrderItemResponse::getTotalPrice)
                        .sum()));
                return "checkout";
            }else {
                log.info("your card is empty");
                model.addAttribute("message", "your cart is empty");
                return "redirect:/ecobook/";
            }
        }

        return "redirect:/user/sign-in";
    }
    @GetMapping("/status")
    public String getStatus(HttpSession session){
        session.removeAttribute("cart");
        session.removeAttribute("cic");
        session.removeAttribute("subtotal");
        session.setAttribute("cart", new CartResponse());
        session.setAttribute("cic", 0);
        session.setAttribute("subtotal",String.format("%.2f",new Double(0)));
        return "success";
    }
}
