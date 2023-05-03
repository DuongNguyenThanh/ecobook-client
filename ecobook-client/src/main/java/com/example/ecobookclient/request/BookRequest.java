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
    @JsonProperty("publish_year")
    private String publishYear;
    private Float price;
    @JsonProperty("number_sales")
    private Integer numberSales;
    private String description;
    private Integer quantity;
    private List<ImageRequest> images;
}
