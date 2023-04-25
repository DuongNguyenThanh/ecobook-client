package com.example.ecobookclient.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Set;

@Data
public class UserBody {

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refreshToken")
    private String refreshToken;

    @JsonProperty("list_role")
    private Set<String> listRole;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("expires_in")
    private Long expiresIn;
}
