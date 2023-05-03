package com.example.ecobookclient.controller.AdminController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Locale.Category;

import com.example.ecobookclient.request.CategoryRequest;
import com.example.ecobookclient.response.BookResponse;
import com.example.ecobookclient.response.CategoryResponse;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public String category(Model model) {
        String categoryUrl = "http://localhost:8082/api/category/";
        ResponseEntity<List<CategoryResponse>> responseCate = restTemplate.exchange(categoryUrl,HttpMethod.GET,null,
                new ParameterizedTypeReference<List<CategoryResponse>>() {});
        List<CategoryResponse> listCategory = responseCate.getBody();

        model.addAttribute("totalCategory", listCategory.size());
        model.addAttribute("listCategory",listCategory);

        return "adminTemplates/category";
    }

    @PostMapping("/category/add")
    public String addCategory(Model model) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String urlCom = "http://localhost:8082/api/category/";
        HttpEntity<CategoryRequest> entity = new HttpEntity<>(CategoryRequest
                .builder()
                .name("test name")
                .description("test description")
                .build(),
                headers);
        ResponseEntity<CategoryRequest> response = restTemplate.exchange(urlCom, HttpMethod.POST,
                entity,CategoryRequest.class);

        return "adminTemplates/category";
    }
    
    @GetMapping(value="/book_edit/{id}")
    public String getMethodName2(@PathVariable("id") Integer id, Model model) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String url = "http://localhost:8082/api/ebook/"+id;
        ResponseEntity<BookResponse> response = restTemplate.exchange(url,HttpMethod.GET,null,
                BookResponse.class);
        model.addAttribute("book", response.getBody());

        return "adminTemplates/book_edit";
    }
}
