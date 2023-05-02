package com.example.ecobookclient.controller;

import com.example.ecobookclient.request.CommentRequest;
import com.example.ecobookclient.response.CommentResponse;
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
@RequestMapping("/comment")
public class CommentController {
    private final RestTemplate restTemplate = new RestTemplate();
    @PostMapping
    public String addComment (Model model,
                             @RequestParam(name = "message") String context,
                             @RequestParam(name = "bid") Integer bid,
                             HttpSession session){
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization","Bearer " + user.getAccessToken());
            String urlCom = "http://localhost:8083/api/book-review/";
            HttpEntity<CommentRequest> entity = new HttpEntity<>(CommentRequest
                    .builder()
                    .context(context)
                    .productId(bid)
                    .createdBy(user.getFirstName())
                    .build(),
                    headers);
            ResponseEntity<CommentResponse> response = restTemplate.exchange(urlCom, HttpMethod.POST,
                    entity,CommentResponse.class);
//            model.addAttribute("me",response.getBody());
            log.info(response.getBody().getContext());
            return "redirect:/shop/detail/"+bid;
        }
        return "redirect:/user/sign-in";
    }
    @GetMapping()
    public String deleteComment(@RequestParam(name ="id") Integer id,
                                @RequestParam(name = "bid") Integer bid,
                                HttpSession session){
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization","Bearer " + user.getAccessToken());
            String urlCom = "http://localhost:8083/api/book-review/"+id;
            HttpEntity<CommentRequest> entity = new HttpEntity<>(CommentRequest
                    .builder()
                    .context("context")
                    .productId(1)
                    .createdBy(user.getFirstName())
                    .build(),
                    headers);
            restTemplate.exchange(urlCom, HttpMethod.DELETE, entity,Void.class);
            return "redirect:/shop/detail/"+bid;
        }
        return "redirect:/user/sign-in";
    }

}
