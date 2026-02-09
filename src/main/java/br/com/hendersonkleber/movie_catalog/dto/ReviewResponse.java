package br.com.hendersonkleber.movie_catalog.dto;

import java.util.UUID;

public record ReviewResponse(
        UUID id,
        String comment,
        Double rating
) {
}
