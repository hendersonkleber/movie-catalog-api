package br.com.hendersonkleber.movie_catalog.dto;

import br.com.hendersonkleber.movie_catalog.domain.Movie;
import br.com.hendersonkleber.movie_catalog.domain.Review;

import java.util.List;

public record MovieDetailResponse(
        String id,
        String title,
        String description,
        String genre,
        int releaseYear,
        int duration,
        List<ReviewResponse> lastReviews
) {
    public static MovieDetailResponse fromEntities(Movie entity, List<Review> reviews) {
        return new MovieDetailResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getGenre(),
                entity.getReleaseYear(),
                entity.getDuration(),
                reviews.stream().map(ReviewResponse::fromEntity).toList()
        );
    }
}
