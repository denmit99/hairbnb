package com.denmit99.hairbnb.model.dto;

import lombok.Data;

@Data
public abstract class PaginationRequest {
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;
    private int page = DEFAULT_PAGE;
    private int pageSize = DEFAULT_SIZE;
}
