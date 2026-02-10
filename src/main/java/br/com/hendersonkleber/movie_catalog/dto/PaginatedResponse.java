package br.com.hendersonkleber.movie_catalog.dto;

import java.util.List;

public record PaginatedResponse<T>(
        List<T> content,
        int totalPages,
        long totalElements
) {
}
