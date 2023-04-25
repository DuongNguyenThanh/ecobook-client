package com.example.ecobookclient.controller;

import com.example.ecobookclient.request.LoginRequest;
import com.example.ecobookclient.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/sign-in")
    public String getSignIn(Model model) {
        model.addAttribute("request", new LoginRequest());
        return "sign-in";
    }

    @PostMapping("/sign-in")
    public String checkUser(HttpSession session, LoginRequest request, RedirectAttributes redirectAttributes){
        log.info("request: " + request.getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginRequest> entity = new HttpEntity<>(request, headers);
        String url = "http://localhost:8081/api/user/sign-in";
        ResponseEntity<UserResponse> us = restTemplate.exchange(url, HttpMethod.POST,entity,UserResponse.class);
        HttpStatus statusCode = us.getStatusCode();
        if (statusCode == HttpStatus.NOT_FOUND){
            log.info("wrong password or username");
            redirectAttributes.addFlashAttribute("message", "wrong password or username");
            return "redirect:/user/sign-in";
        }else{

            session.setAttribute("user",us.getBody().getUserBody());
            log.info("login success" + us.getBody().getUserBody().getFirstName());
            redirectAttributes.addFlashAttribute("user",us.getBody().getUserBody());
            return "redirect:/ecobook/";
        }

    }

}
