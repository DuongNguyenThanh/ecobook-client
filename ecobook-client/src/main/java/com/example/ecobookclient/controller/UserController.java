package com.example.ecobookclient.controller;

import com.example.ecobookclient.request.CartItemRequest;
import com.example.ecobookclient.request.LoginRequest;
import com.example.ecobookclient.request.RegisterRequest;
import com.example.ecobookclient.request.ResetPassRequest;
import com.example.ecobookclient.response.CartResponse;
import com.example.ecobookclient.response.RegisterResponse;
import com.example.ecobookclient.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.regex.Pattern;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/sign-in")
    public String getSignIn(Model model) {
        model.addAttribute("request", new LoginRequest());
        return "sign-in";
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("request", new RegisterRequest());
        return "sign-up";
    }

    @GetMapping("/reset-pass")
    public String getResetPass(Model model) {
        model.addAttribute("request", new ResetPassRequest());
        return "reset-pass";
    }

    @GetMapping("/user-profile")
    public String getProfile(){
        return "user-profile";
    }

    @PostMapping("/sign-in")
    public String loginUser(HttpSession session, LoginRequest request, RedirectAttributes redirectAttributes){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginRequest> entity = new HttpEntity<>(request, headers);
        String url = "http://localhost:8081/api/user/sign-in";
        try {
            ResponseEntity<UserResponse> us = restTemplate.exchange(url, HttpMethod.POST, entity, UserResponse.class);
            session.setAttribute("user", Objects.requireNonNull(us.getBody()));
//            log.info("login success: " + us.getBody());
            redirectAttributes.addFlashAttribute("user", us.getBody());
            //get cart info when log in success
            String urlCart = "http://localhost:8086/api/cart/active-cart";
            headers.set("Authorization","Bearer " + us.getBody().getAccessToken());
            headers.setContentType(MediaType.APPLICATION_JSON);
            CartItemRequest item = new CartItemRequest();
            HttpEntity<CartItemRequest> entityc = new HttpEntity<>(item, headers);
            ResponseEntity<CartResponse> response = restTemplate.exchange(urlCart, HttpMethod.GET, entityc, CartResponse.class);
            CartResponse cart = response.getBody();
            session.setAttribute("cart",cart);
            session.setAttribute("cic",cart.getItems().stream().count());
            return "redirect:/ecobook/";

        } catch (Exception ex) {
            log.info("wrong password or username");
            redirectAttributes.addFlashAttribute("message", "wrong password or username");
            return "redirect:/user/sign-in";
        }
    }

    @GetMapping("/sign-out")
    public String signOut(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/ecobook/";
    }

    @PostMapping("/register")
    public String checkRegister(RedirectAttributes redirectAttributes, RegisterRequest request){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RegisterRequest> entity = new HttpEntity<>(request, headers);

        String PHONE_PATTERN = "(84|0[3|5|7|8|9])+([0-9]{8})\\b";
        if(!Pattern.matches(PHONE_PATTERN, Objects.requireNonNull(entity.getBody()).getPhoneNumber())) {
            log.info("wrong phone number");
            redirectAttributes.addFlashAttribute("mess_phone", "Please fill in right phone number");
            return "redirect:/user/register";
        }
        if(!Objects.requireNonNull(entity.getBody()).getPassword().equals(Objects.requireNonNull(entity.getBody()).getConfirmPass())) {
            log.info("Confirm password doesn't match");
            redirectAttributes.addFlashAttribute("mess_pass", "Confirm password doesn't match");
            return "redirect:/user/register";
        }
        String url = "http://localhost:8081/api/user/register";

        try {
            ResponseEntity<RegisterResponse> us = restTemplate.exchange(url, HttpMethod.POST, entity, RegisterResponse.class);
            if(Objects.requireNonNull(us.getBody()).getExistPhoneNumber().compareTo(1) == 0 ||
                    Objects.requireNonNull(us.getBody()).getExistUsername().compareTo(1) == 0) {
                redirectAttributes.addFlashAttribute("exist_phone", Objects.requireNonNull(us.getBody()).getExistPhoneNumber());
                redirectAttributes.addFlashAttribute("exist_username", Objects.requireNonNull(us.getBody()).getExistUsername());
                return "redirect:/user/sign-in";
            }

            redirectAttributes.addFlashAttribute("success_message", "Register success");
            return "redirect:/user/sign-in";

        } catch(Exception ex) {
            log.info("got some error register");
            return "redirect:/user/register";
        }
    }

    @PostMapping("/reset-pass")
    public String checkResetPass(RedirectAttributes redirectAttributes, ResetPassRequest request){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ResetPassRequest> entity = new HttpEntity<>(request, headers);

        String PHONE_PATTERN = "(84|0[3|5|7|8|9])+([0-9]{8})\\b";
        if(!Pattern.matches(PHONE_PATTERN, Objects.requireNonNull(entity.getBody()).getPhoneNumber())) {
            log.info("wrong phone number");
            redirectAttributes.addFlashAttribute("mess_phone", "Please fill in right phone number");
            return "redirect:/user/reset-pass";
        }
        if(!Objects.requireNonNull(entity.getBody()).getPassword().equals(Objects.requireNonNull(entity.getBody()).getConfirmPass())) {
            log.info("Confirm password doesn't match");
            redirectAttributes.addFlashAttribute("mess_pass", "Confirm password doesn't match");
            return "redirect:/user/register";
        }
        String url = "http://localhost:8081/api/user/reset-password";

        try {
            ResponseEntity<String> us = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            log.info(us + "");
            return "redirect:/user/sign-in";

        } catch(Exception ex) {
            log.info("got some error register");
            return "redirect:/user/register";
        }
    }

}
