package com.example.ecobookclient.controller.AdminController;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.ecobookclient.request.BookRequest;
import com.example.ecobookclient.request.CategoryRequest;
import com.example.ecobookclient.request.ImageRequest;
import com.example.ecobookclient.response.BookResponse;
import com.example.ecobookclient.response.CategoryResponse;
import com.example.ecobookclient.response.UserResponse;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final RestTemplate restTemplate = new RestTemplate();

    //create header for Auth
    public HttpHeaders createHeaders(HttpSession session) {
        UserResponse user = (UserResponse) session.getAttribute("user");

        if(user == null) 
            return null;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Bearer " + user.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public List<CategoryResponse> getAllCategoryForpage(Model model, HttpEntity<String> entity) {
        String categoryUrl = "http://localhost:8082/api/category/";
        ResponseEntity<List<CategoryResponse>> responseCate = restTemplate.exchange(categoryUrl,HttpMethod.GET,entity,
                new ParameterizedTypeReference<List<CategoryResponse>>() {});
        List<CategoryResponse> listCategory = responseCate.getBody();

        return listCategory;
    }

    @GetMapping("/book")
    public String homeAdmin(Model model, HttpSession session) {
        HttpHeaders headers = createHeaders(session);
        //check header
        if(headers == null) return "redirect:/user/sign-in";
        HttpEntity<String> entity = new HttpEntity<>(headers);

        //get all catergory and set it for dropdown in page
        List<CategoryResponse> listCategory = getAllCategoryForpage(model, entity);

        model.addAttribute("categories",listCategory);
        model.addAttribute("initBook", new BookRequest());

        return "adminTemplates/book";
    }

    @GetMapping("/book_delete/{id}")
    public String deleteBook(@PathVariable("id") Integer id, HttpSession session) {
        HttpHeaders headers = createHeaders(session);
         //check header
        if(headers == null) return "redirect:/user/sign-in";
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = "http://localhost:8082/api/ebook/"+id;
        restTemplate.delete(url, entity);

        return "redirect:/admin/book";
    }

    @GetMapping("/findBook")
    public String findBook(@RequestParam("book") String name, Model model, HttpSession session) {
        HttpHeaders headers = createHeaders(session);
        //check header
        if(headers == null) return "redirect:/user/sign-in";
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = "http://localhost:8082/api/ebook/search?key=" + name + "&from=0&to=10000000";
        ResponseEntity<List<BookResponse>> response = restTemplate.exchange(url,HttpMethod.GET,entity,
                new ParameterizedTypeReference<List<BookResponse>>() {});
        List<BookResponse> listBook = response.getBody();
        List<CategoryResponse> listCategory = getAllCategoryForpage(model, entity);

        name = name.trim().toLowerCase();
        model.addAttribute("books", listBook);
        model.addAttribute("name", name);
        model.addAttribute("categories",listCategory);
        model.addAttribute("initBook", new BookRequest());

        return "adminTemplates/book";
    }

    @GetMapping(value="/category")
    public String category(Model model, HttpSession session) {
        HttpHeaders headers = createHeaders(session);
        //check header
        if(headers == null) return "redirect:/user/sign-in";
        HttpEntity<String> entity = new HttpEntity<>(headers);

        List<CategoryResponse> listCategory = getAllCategoryForpage(model, entity);

        model.addAttribute("totalCategory", listCategory.size());
        model.addAttribute("listCategory",listCategory);
        model.addAttribute("initCategory", new CategoryRequest());

        return "adminTemplates/category";
    }

    @PostMapping("/category/add")
    public String addCategory(Model model, CategoryRequest categoryRequest, HttpSession session) {
        HttpHeaders headers = createHeaders(session);
        //check header
        if(headers == null) return "redirect:/user/sign-in";
        String urlCom = "http://localhost:8082/api/category/";
        HttpEntity<CategoryRequest> entity = new HttpEntity<>(CategoryRequest
                .builder()
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .build(),
                headers);
        restTemplate.exchange(urlCom, HttpMethod.POST, entity,CategoryRequest.class);

        return "redirect:/admin/category";
    }

    @GetMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable("id") Integer id, HttpSession session) {
        // //thieu api :))
        // HttpHeaders headers = createHeaders(session);
        // //check header
        // if(headers == null) return "redirect:/user/sign-in";
        // HttpEntity<String> entity = new HttpEntity<>(headers);
        // String url = "http://localhost:8082/api/ebook/"+id;
        // restTemplate.delete(url, headers);

        return "redirect:/admin/category";
    }
    
    @GetMapping(value="/book_edit/{id}")
    public String getMethodName2(@PathVariable("id") Integer id, Model model, HttpSession session) {
        HttpHeaders headers = createHeaders(session);
        //check header
        if(headers == null) return "redirect:/user/sign-in";
        HttpEntity<String> entity = new HttpEntity<>(headers);
       
        String url = "http://localhost:8082/api/ebook/"+id;
        ResponseEntity<BookRequest> response = restTemplate.exchange(url,HttpMethod.GET,entity,
                BookRequest.class);

        List<CategoryResponse> listCategory = getAllCategoryForpage(model, entity);

        model.addAttribute("categories",listCategory);
        model.addAttribute("book", response.getBody());

        return "adminTemplates/book_edit";
    }

    //temp for action button in book_edit
    @PostMapping("/book_update")
    public String updateBook(Model model, BookRequest book, @RequestParam("category_id") String categoryId, HttpSession session) {
        HttpHeaders headers = createHeaders(session);
        //check header
        if(headers == null) return "redirect:/user/sign-in";

        String urlCom = "http://localhost:8082/api/ebook/" + book.getId();
        List<ImageRequest> tempImageList = new ArrayList<>();
        tempImageList.add(new ImageRequest(1));
        int category_id = Integer.parseInt(categoryId.replace(",", ""));

        HttpEntity<BookRequest> entity = new HttpEntity<>(BookRequest
        .builder()
        .name(book.getName())
        .author(book.getAuthor())
        .publish_year(book.getPublish_year())
        .price(book.getPrice())
        .quantity(book.getQuantity())
        .category_id(category_id)
        .images(tempImageList)
        .description(book.getDescription())
        .publisher(book.getPublisher())
        .number_sales(book.getNumber_sales())
        .build(),
        headers);
        restTemplate.exchange(urlCom, HttpMethod.PUT, entity,BookRequest.class);

        return "redirect:/admin/book";
    }

    @PostMapping(value = "/addBook")
    public String addBook(Model model, BookRequest initBook, @RequestParam("category_id") String categoryId, HttpSession session) {
        HttpHeaders headers = createHeaders(session);
        //check header
        if(headers == null) return "redirect:/user/sign-in";

        String urlCom = "http://localhost:8082/api/ebook/";
        List<ImageRequest> tempImageList = new ArrayList<>();
        tempImageList.add(new ImageRequest(1));
        int category_id = Integer.parseInt(categoryId.replace(",", ""));
        
        HttpEntity<BookRequest> entity = new HttpEntity<>(BookRequest
                .builder()
                .name(initBook.getName())
                .author(initBook.getAuthor())
                .publish_year(initBook.getPublish_year())
                .price(initBook.getPrice())
                .quantity(initBook.getQuantity())
                .category_id(category_id)
                .images(tempImageList)
                .build(),
                headers);
        restTemplate.exchange(urlCom, HttpMethod.POST, entity,BookRequest.class);
  
        return "redirect:/admin/book";
    }

}
