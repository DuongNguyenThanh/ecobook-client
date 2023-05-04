package com.example.ecobookclient.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("note")
    private String note;
    @JsonProperty("order_items")
    private List<OrderItemResponse> orderItems;
}
