package br.com.hendersonkleber.movie_catalog.repository;

import br.com.hendersonkleber.movie_catalog.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, UUID> {
}
