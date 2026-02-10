package br.com.hendersonkleber.movie_catalog.controller;

import br.com.hendersonkleber.movie_catalog.dto.*;
import br.com.hendersonkleber.movie_catalog.service.ReviewService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<ReviewResponse>> getAll(
            @RequestParam
            String movieId,

            @RequestParam(defaultValue = "0")
            @PositiveOrZero(message = "Page must be greater than or equal to zero")
            Integer page,

            @RequestParam(defaultValue = "10")
            @Min(value = 10, message = "Limit must be greater than or equal to ten")
            @Max(value = 100, message = "Limit must be less than or equal to hundred")
            Integer limit
    ) {
        var response = this.reviewService.getAll(movieId, page, limit);

        var paginated = new PaginatedResponse<>(
                response.getContent().stream().map(ReviewResponse::fromEntity).toList(),
                response.getTotalPages(),
                response.getTotalElements()
        );

        return ResponseEntity.ok(paginated);
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> create(
            @RequestBody
            @Valid
            ReviewRequest body
    ){
        var entity = this.reviewService.create(body);
        var response = ReviewResponse.fromEntity(entity);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable
            String id
    ){
        this.reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
