package com.example.ecobookclient.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInfoResponse {
    private Integer id;
    @JsonProperty("payment_date")
    private String paymentDate;
    private Double amount;
    @JsonProperty("payment_type")
    private String paymentType;
    private String status;
    @JsonProperty("order_id")
    private Integer orderId;
    @JsonProperty("payer_id")
    private String payerId;

}
