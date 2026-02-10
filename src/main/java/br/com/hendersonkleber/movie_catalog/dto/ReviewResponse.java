package br.com.hendersonkleber.movie_catalog.dto;

import br.com.hendersonkleber.movie_catalog.domain.Review;

public record ReviewResponse(
        String id,
        String comment,
        double rating
) {
    public static ReviewResponse fromEntity(Review entity) {
        return new ReviewResponse(entity.getId(), entity.getComment(), entity.getRating());
    }
}
