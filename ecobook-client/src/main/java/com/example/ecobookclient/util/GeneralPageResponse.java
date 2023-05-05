package com.example.ecobookclient.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public class GeneralPageResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("content")
    private List<T> content;

    @JsonProperty("total_elements")
    private long totalElements;

    @JsonProperty("is_last")
    private boolean last;

    @JsonProperty("is_first")
    private boolean first;

    @JsonProperty("current_page_elements")
    private int numberOfElements;

    @JsonProperty("max_page_size")
    private int pageSize;

    @JsonProperty("current_page_number")
    private int pageNum;

    @JsonProperty("current_page_offset")
    private int offset;

    @JsonProperty("total_pages")
    private int totalPages;
}
