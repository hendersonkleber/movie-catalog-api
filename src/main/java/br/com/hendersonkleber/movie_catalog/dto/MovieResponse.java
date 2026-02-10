package br.com.hendersonkleber.movie_catalog.dto;

import br.com.hendersonkleber.movie_catalog.domain.Movie;

public record MovieResponse(
        String id,
        String title,
        String description,
        String genre,
        Integer releaseYear,
        Integer duration
) {
    public static MovieResponse fromEntity(Movie entity) {
        return new MovieResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getGenre(),
                entity.getReleaseYear(),
                entity.getDuration()
        );
    }
}
