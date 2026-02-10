package br.com.hendersonkleber.movie_catalog.controller;

import br.com.hendersonkleber.movie_catalog.dto.MovieRequest;
import br.com.hendersonkleber.movie_catalog.dto.MovieResponse;
import br.com.hendersonkleber.movie_catalog.dto.PaginatedResponse;
import br.com.hendersonkleber.movie_catalog.service.MovieService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<MovieResponse>> getAll(
            @RequestParam(defaultValue = "0")
            @PositiveOrZero(message = "Page must be greater than or equal to zero")
            Integer page,

            @RequestParam(defaultValue = "10")
            @Min(value = 10, message = "Limit must be greater than or equal to ten")
            @Max(value = 100, message = "Limit must be less than or equal to hundred")
            Integer limit
    ) {
        var response = this.movieService.getAll(page, limit);

        var paginated = new PaginatedResponse<>(
                response.getContent().stream().map(MovieResponse::fromEntity).toList(),
                response.getTotalPages(),
                response.getTotalElements()
        );

        return ResponseEntity.ok(paginated);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<MovieResponse> getById(
            @PathVariable
            String id
    ){
        var entity = this.movieService.getById(id);
        var response = MovieResponse.fromEntity(entity);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<MovieResponse> create(
            @RequestBody
            @Valid
            MovieRequest body
    ){
        var entity = this.movieService.create(body);
        var response = MovieResponse.fromEntity(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<MovieResponse> update(
            @PathVariable String id,

            @RequestBody
            @Valid
            MovieRequest body
    ){
        var entity = this.movieService.update(id, body);
        var response = MovieResponse.fromEntity(entity);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable String id
    ){
        this.movieService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
