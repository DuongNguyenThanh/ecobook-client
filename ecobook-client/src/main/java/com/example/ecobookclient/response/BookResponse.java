package com.example.ecobookclient.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class BookResponse {
    private Integer id;
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
    private List<ImageReponse> images;
}
