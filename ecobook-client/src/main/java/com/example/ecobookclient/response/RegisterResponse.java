package com.example.ecobookclient.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegisterResponse {

    @JsonProperty("exist_username")
    private Integer existUsername;

    @JsonProperty("exist_phone_number")
    private Integer existPhoneNumber;

}
