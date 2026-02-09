package br.com.hendersonkleber.movie_catalog.domain;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "reviews")
public class Review {
    @MongoId
    private UUID id;

    @Field(name = "create_at")
    private LocalDateTime createAt;

    @Field(name = "movie_id")
    private UUID movieId;

    @Field(name = "rating")
    private double rating;

    @Field(name = "comment")
    private String comment;

    public Review() {
    }

    public Review(UUID id, LocalDateTime createAt, UUID movieId, double rating, String comment) {
        this.id = id;
        this.createAt = createAt;
        this.movieId = movieId;
        this.rating = rating;
        this.comment = comment;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public UUID getMovieId() {
        return movieId;
    }

    public void setMovieId(UUID movieId) {
        this.movieId = movieId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
