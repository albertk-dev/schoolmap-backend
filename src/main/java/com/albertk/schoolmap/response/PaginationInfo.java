package com.albertk.schoolmap.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaginationInfo {
    private final int currentPage;
    private final int totalPages;
    private final int pageSize;
    private final long totalElements;
    private final boolean hasNext;
    private final boolean hasPrevious;

    public PaginationInfo(int currentPage, int pageSize, long totalElements) {
        this.currentPage = currentPage;
        this.totalPages = (int) Math.ceil((double) totalElements / pageSize);
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.hasNext = currentPage < totalPages;
        this.hasPrevious = currentPage > 1;
    }
}
