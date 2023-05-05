package com.example.ecobookclient.controller;

import com.example.ecobookclient.request.CartItemRequest;
import com.example.ecobookclient.request.PaymentRequest;
import com.example.ecobookclient.response.CartResponse;
import com.example.ecobookclient.response.PaymentInfoResponse;
import com.example.ecobookclient.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.*;

@Slf4j
@Controller
@RequestMapping(path = "/payment")
public class PaymentController {
    private final RestTemplate restTemplate = new RestTemplate();
    @PostMapping
    public String pay(Model model,
                      HttpSession session,
                      @RequestParam(name = "order-id") Integer id){
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:/image/cart.txt",false))) {
                CartResponse cart = (CartResponse) session.getAttribute("cart");
                writer.write(cart.getId()+"");
            } catch (IOException e) {
                // handle exception
            }
            String url = "http://localhost:8084/api/payment";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + user.getAccessToken());
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<PaymentRequest> entity = new HttpEntity<>(PaymentRequest.builder()
                    .token("smt123")
                    .note("thanh toan cho order so "+ id)
                    .role("ROLE_USER")
                    .gender("nam")
                    .status("DONE")
                    .address("Ha Noi, Ha Dong")
                    .orderId(id)
                    .userId(user.getAccountId())
                    .lastName(user.getLastName())
                    .firstName(user.getFirstName())
                    .subTotal(new Double(session.getAttribute("subtotal").toString()))
                    .phoneNumber("123456789")
                    .build(),headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,entity,String.class);
            return response.getBody();
        }else {
            return "redirect:/user/sign-in";
        }

    }
    @GetMapping("/cancel")
    public String cancelPay(Model model) {
        model.addAttribute("message","some thing wrong, please try later!!");

        return "success";
    }
    @GetMapping("success")
    public String successPay(@RequestParam("paymentId") String paymentId,
                             @RequestParam("PayerID") String payerId,
                             @RequestParam("userId") Long userId,
                             @RequestParam("orderId") Integer orderId,
                             RedirectAttributes redirectAttributes) {
//      get access token
        String filePath = "D:/image/access.txt";
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }

        } catch (IOException e) {
            // handle exception
        }
        String fileContent = content.toString();
        String url = "http://localhost:8084/api/payment/success?paymentId="+paymentId+"&PayerID="+payerId+
                "&userId="+userId+"&orderId="+orderId;
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Bearer " + fileContent);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PaymentRequest> entity = new HttpEntity<>(new PaymentRequest(), headers);
        ResponseEntity<PaymentInfoResponse> response = restTemplate.exchange(url,HttpMethod.GET,entity,PaymentInfoResponse.class);
        redirectAttributes.addFlashAttribute("payment", response.getBody());
        //get cart id
        StringBuilder content1 = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("D:/image/cart.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content1.append(line);
            }

        } catch (IOException e) {
            // handle exception
        }
        String fileContent1 = content1.toString();
        //update status cart
        String urlUpdate = "http://localhost:8086/api/cart/update-status/"+fileContent1;
        log.info(fileContent1);
        ResponseEntity<String> response1 = restTemplate.exchange(urlUpdate,HttpMethod.POST,entity,String.class);
        if(response1.getBody().equalsIgnoreCase("Update Status Cart success!")){
            return "redirect:/checkout/status";
        }
        return "redirect:/user/sign-out";
    }
}
