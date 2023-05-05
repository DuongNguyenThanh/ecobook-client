package com.example.ecobookclient.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    @JsonProperty("note")
    private String note;

    @JsonProperty("order_items")
    private List<OrderItemRequest> orderItemRequests;
}
