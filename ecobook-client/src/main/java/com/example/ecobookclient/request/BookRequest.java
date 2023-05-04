package com.example.ecobookclient.request;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequest {
    private String name;
    private String author;
    private String publisher;
    private String publish_year;
    private Float price;
    private Integer number_sales;
    private String description;
    private Integer quantity;
    private Integer category_id;
    private List<ImageRequest> images;
}
