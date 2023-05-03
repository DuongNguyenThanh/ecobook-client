package com.example.ecobookclient.controller.AdminController;

import java.util.List;

import com.example.ecobookclient.response.BookResponse;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/book")
    public String homeAdmin() {
        return "adminTemplates/book";
    }

    @GetMapping("/findBook")
    public String findBook(@RequestParam("book") String name, Model model) {
        name = name.trim().toLowerCase();
        model.addAttribute("name", name);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String url = "http://localhost:8082/api/ebook/search?key=" + name + "&from=0&to=10000000";
        ResponseEntity<List<BookResponse>> response = restTemplate.exchange(url,HttpMethod.GET,null,
                new ParameterizedTypeReference<List<BookResponse>>() {});
        List<BookResponse> list = response.getBody();
        model.addAttribute("books", list);

        return "adminTemplates/book";
    }

    @GetMapping(value="/category")
    public String getMethodName() {
        return "adminTemplates/category";
    }
    
    @GetMapping(value="/book_edit")
    public String getMethodName2() {
        return "adminTemplates/book_edit";
    }
}
