package com.example.ecobookclient.controller;

import com.example.ecobookclient.request.LoginRequest;
import com.example.ecobookclient.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
    private RestTemplate restTemplate = new RestTemplate();
    @GetMapping("/sign-in")
    public String getSignIn() {
        return "sign-in";
    }
    @PostMapping("/sign-in")
    @ModelAttribute
    public String checkUser(LoginRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginRequest> entity = new HttpEntity<>(request, headers);
        String url = "http://localhost:8081/api/user/sign-in/";
        ResponseEntity<UserResponse> us = restTemplate.exchange(url, HttpMethod.POST,entity,UserResponse.class);
        HttpStatus statusCode = us.getStatusCode();
        if (statusCode == HttpStatus.NOT_FOUND){
            log.info("You do not have permission or you need to sign-in!!");
            return "redirect:/ecobook11";
        }else{

        }
        return "redirect:/ecobook11";
    }

}
