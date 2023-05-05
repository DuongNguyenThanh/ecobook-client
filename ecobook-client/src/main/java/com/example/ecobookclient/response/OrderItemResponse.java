package com.example.ecobookclient.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemResponse {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("total_price")
    private Float totalPrice;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("book_id")
    private Integer bookId;
    @JsonProperty("book_name")
    private String bookName;
}
