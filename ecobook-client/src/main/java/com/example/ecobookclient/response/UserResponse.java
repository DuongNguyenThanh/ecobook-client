package com.example.ecobookclient.response;

import java.util.Set;

public class UserResponse {
    private Long accountId;
    private String accessToken;

    private String refreshToken;

    private Set<String> listRole;

    private String firstName;

    private String lastName;

    private Long expiresIn;
}
