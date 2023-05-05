package com.example.ecobookclient.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class CommentResponse {
    private Integer id;
//    private Integer likeNum;
    private String context;
    private Integer productId;
    private Integer userId;
    private String createdBy;
    private Date createdAt;

}
