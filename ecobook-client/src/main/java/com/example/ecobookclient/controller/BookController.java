package com.example.ecobookclient.controller;

import com.example.ecobookclient.request.LoginRequest;
import com.example.ecobookclient.response.BookResponse;
import com.example.ecobookclient.response.CategoryResponse;
import com.example.ecobookclient.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping(path = "/shop")
public class BookController {
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/{cate-id}")
    public String getAllBook(Model model, @PathVariable(name = "cate-id") Integer cateId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String url = "http://localhost:8082/api/ebook/all-book";
        String categoryUrl = "http://localhost:8082/api/category/";
        try {
            ResponseEntity<List<BookResponse>> response = restTemplate.exchange(url,HttpMethod.GET,null,
                    new ParameterizedTypeReference<List<BookResponse>>() {});
            ResponseEntity<List<CategoryResponse>> responseCate = restTemplate.exchange(categoryUrl,HttpMethod.GET,null,
                    new ParameterizedTypeReference<List<CategoryResponse>>() {});
            log.info("log " + response.getBody());
            List<BookResponse> listbook = response.getBody();
            List<CategoryResponse> listCategory = responseCate.getBody();
            model.addAttribute("book", response.getBody());
            model.addAttribute("category",listCategory);
            model.addAttribute("count",listbook.stream().count());
            return "shop";

        } catch (Exception ex) {
            log.info("something wrong");
        }
        return "shop";
    }
}
