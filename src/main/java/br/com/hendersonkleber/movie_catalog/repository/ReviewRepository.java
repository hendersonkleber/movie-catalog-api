package br.com.hendersonkleber.movie_catalog.repository;

import br.com.hendersonkleber.movie_catalog.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ReviewRepository extends MongoRepository<Review, String> {
    @Query(value = "{ 'movie_id': ?0 }")
    Page<Review> findAll(String movieId, Pageable pageable);
}
