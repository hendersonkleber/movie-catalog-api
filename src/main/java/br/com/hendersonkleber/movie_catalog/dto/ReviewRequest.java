package br.com.hendersonkleber.movie_catalog.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReviewRequest(
        @NotBlank(message = "Comment is required")
        String comment,

        @NotNull(message = "Rating is required")
        @DecimalMin(value = "1.0", message = "Rating must be greater than or equal to one")
        Double rating
) {
}
