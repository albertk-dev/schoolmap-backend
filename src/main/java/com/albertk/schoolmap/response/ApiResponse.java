package com.albertk.schoolmap.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL) // Ignore les champs null
public class ApiResponse<T> {
    private final boolean success;
    private final String message;
    private final T data;
    private final Boolean paginated;
    private final PaginationInfo pagination;
    private final Instant timestamp;

    // Constructeur privé pour forcer l'utilisation des méthodes statiques
    private ApiResponse(boolean success, String message, T data, Boolean paginated, PaginationInfo pagination) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.paginated = paginated;
        this.pagination = pagination;
        this.timestamp = Instant.now();
    }

    // Réponse sans pagination
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, message, data, false, null);
    }


    public static <T> ApiResponse<T> successWithPagination(T data, String message, int currentPage, int totalPages, int pageSize, long totalElements) {
        PaginationInfo pagination = new PaginationInfo(currentPage, pageSize, totalElements);
        return new ApiResponse<>(true, message, data, true, pagination);
    }


    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null, null, null);
    }

    // Getters
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public T getData() { return data; }
    public Boolean getPaginated() { return paginated; }
    public PaginationInfo getPagination() { return pagination; }
    public Instant getTimestamp() { return timestamp; }
}
