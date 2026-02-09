package br.com.hendersonkleber.movie_catalog.dto;

import jakarta.validation.constraints.*;

public record MovieRequest(
        @NotBlank(message = "Title is required")
        @Size(max = 200, message = "Title must be at most 200 characters")
        String title,

        String description,

        @NotBlank(message = "Genre is required")
        @Size(max = 60, message = "Genre must be at most 200 characters")
        String genre,

        @NotNull(message = "Release year is required")
        @Min(value = 1888, message = "Release year must be greater than or equal to 1888")
        @Max(value = 2100, message = "Release year must be less than or equal to 2100")
        Integer releaseYear,

        @NotNull(message = "Duration is required")
        @Positive(message = "Duration must be greater than zero")
        Integer duration
) {
}
