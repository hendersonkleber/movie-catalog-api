package br.com.hendersonkleber.movie_catalog.service;

import br.com.hendersonkleber.movie_catalog.domain.Review;
import br.com.hendersonkleber.movie_catalog.dto.ReviewRequest;
import br.com.hendersonkleber.movie_catalog.exception.ResourceNotFoundException;
import br.com.hendersonkleber.movie_catalog.repository.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Page<Review> getAll(String movieId, int page, int limit) {
        var pageRequest = PageRequest.of(page, limit, Sort.Direction.DESC, "createAt");
        return this.reviewRepository.findAll(movieId, pageRequest);
    }

    public Review create(ReviewRequest request) {
        var entity = new Review();

        entity.setMovieId(request.movieId());
        entity.setCreateAt(LocalDateTime.now());
        entity.setComment(request.comment());
        entity.setRating(request.rating());

        return this.reviewRepository.save(entity);
    }

    public void delete(String id) {
        if (!this.reviewRepository.existsById(id)) {
            throw new ResourceNotFoundException("Review does not exists");
        }

        this.reviewRepository.deleteById(id);
    }
}
