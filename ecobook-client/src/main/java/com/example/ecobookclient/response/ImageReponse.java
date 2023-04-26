package com.example.ecobookclient.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageReponse {
    private Integer id;
    private String name;
    private String img;
}
