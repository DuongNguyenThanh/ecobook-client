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
public class OrderItemRequest {
    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("book_id")
    private int bookId;
}
