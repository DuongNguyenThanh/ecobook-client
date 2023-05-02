package com.example.ecobookclient.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequest {
    private Integer id;
    private Integer likeNum;
    private String context;
    private Integer productId;
    private String createdBy;
    private Date createdAt;
}
