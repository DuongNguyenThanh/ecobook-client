package com.example.ecobookclient.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemRequest {
    @JsonProperty("book_id")
    private Integer bookId;

    @JsonProperty("price")
    private Float price;
}
