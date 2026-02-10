package br.com.hendersonkleber.movie_catalog.domain;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "reviews")
public class Review {
    @Id
    private String id;

    @Field(name = "create_at")
    private LocalDateTime createAt;

    @Field(name = "movie_id")
    private String movieId;

    @Field(name = "rating")
    private double rating;

    @Field(name = "comment")
    private String comment;

    public Review() {
    }

    public Review(String id, String movieId, double rating, String comment) {
        this.id = id;
        this.createAt = LocalDateTime.now();
        this.movieId = movieId;
        this.rating = rating;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
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
