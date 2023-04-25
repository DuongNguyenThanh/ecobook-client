package com.example.ecobookclient.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserResponse {

    @JsonProperty("body")
    private UserBody userBody;

    private String statusCode;

    private Integer statusCodeValue;

}
