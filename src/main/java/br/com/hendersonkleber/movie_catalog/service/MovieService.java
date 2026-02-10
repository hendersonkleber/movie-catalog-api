package br.com.hendersonkleber.movie_catalog.service;

import br.com.hendersonkleber.movie_catalog.domain.Movie;
import br.com.hendersonkleber.movie_catalog.dto.MovieRequest;
import br.com.hendersonkleber.movie_catalog.exception.ResourceNotFoundException;
import br.com.hendersonkleber.movie_catalog.repository.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Page<Movie> getAll(int page, int limit) {
        var pageRequest = PageRequest.of(page, limit, Sort.Direction.DESC, "createAt");
        return this.movieRepository.findAll(pageRequest);
    }

    public Movie getById(String id) {
        return this.movieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie does not exists"));
    }

    public Movie create(MovieRequest request) {
        var entity = new Movie();

        entity.setTitle(request.title());
        entity.setDescription(request.description());
        entity.setDuration(request.duration());
        entity.setGenre(request.genre());
        entity.setReleaseYear(request.releaseYear());

        return movieRepository.saveAndFlush(entity);
    }

    public Movie update(String id, MovieRequest request) {
        var entity = getById(id);

        entity.setTitle(request.title());
        entity.setDescription(request.description());
        entity.setDuration(request.duration());
        entity.setGenre(request.genre());
        entity.setReleaseYear(request.releaseYear());

        return movieRepository.saveAndFlush(entity);
    }

    public void delete(String id) {
        if (!this.movieRepository.existsById(id)) {
            throw new ResourceNotFoundException("Movie does not exists");
        }

        this.movieRepository.deleteById(id);
    }
}
