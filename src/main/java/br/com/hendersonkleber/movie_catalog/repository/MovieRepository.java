package br.com.hendersonkleber.movie_catalog.repository;

import br.com.hendersonkleber.movie_catalog.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, String> {
}
