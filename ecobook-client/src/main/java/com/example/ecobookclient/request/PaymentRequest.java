package com.example.ecobookclient.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {
    private String token;
    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;
    private String gender;
    private String role;
    private String address;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("order_id")
    private Integer orderId;

    @JsonProperty("sub_total")
    private Double subTotal;
    private String status;
    private String note;
}
