package com.example.ecobookclient.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private String token;
    private Long userId;
    private String firstName;
    private String lastName;
    private String gender;
    private String role;
    private String address;
    private String phoneNumber;
    private Integer orderId;
    private Double subTotal;
    private String status;
    private String note;
}
