package br.com.hendersonkleber.movie_catalog.dto;

import java.util.UUID;

public record MovieResponse(
        UUID id,
        String title,
        String description,
        String genre,
        Integer releaseYear,
        Integer duration
) {
}
