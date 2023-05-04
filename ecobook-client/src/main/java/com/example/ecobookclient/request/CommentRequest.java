package com.example.ecobookclient.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("product_id")
    private Integer productId;
    @JsonProperty("created_by")
    private String createdBy;
    private Date createdAt;
}
