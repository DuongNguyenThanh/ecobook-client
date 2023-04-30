package com.example.ecobookclient.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CartItemResponse {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("book")
    private BookResponse book;

    @JsonProperty("price")
    private Float price;

    @JsonProperty("quantity")
    private Integer quantity;
}
