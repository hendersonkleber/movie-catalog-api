package br.com.hendersonkleber.movie_catalog.service;

import br.com.hendersonkleber.movie_catalog.domain.Movie;
import br.com.hendersonkleber.movie_catalog.domain.Review;
import br.com.hendersonkleber.movie_catalog.dto.MovieRequest;
import br.com.hendersonkleber.movie_catalog.dto.ReviewRequest;
import br.com.hendersonkleber.movie_catalog.exception.ResourceNotFoundException;
import br.com.hendersonkleber.movie_catalog.repository.MovieRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {
    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @Nested
    class GetAll {
        @Test
        @DisplayName("Should return movies paginated")
        void shouldReturnMoviesPaginated() {
            // arrange
            var page = 0;
            var limit = 10;

            String id = "692e7a0b-454b-4701-b785-023d7a0ddc51";
            String title = "A Empregada";
            String description = "Em A Empregada, uma jovem começa a trabalhar na casa de um casal muito rico, mas tanto ela quanto os patrões escondem segredos sombrios.";
            String genre = "Suspense";
            int releaseYear = 2026;
            int duration = 133;
            Movie movie = new Movie(id, title, description, genre, releaseYear, duration);
            var pageRequest = PageRequest.of(page, limit, Sort.Direction.DESC, "createAt");
            var pageContent = List.of(movie);
            var pageResponse = new PageImpl<>(pageContent, pageRequest, pageContent.size());

            doReturn(pageResponse).when(movieRepository).findAll(pageRequest);

            // act
            var response = movieService.getAll(page, limit);

            // assert
            verify(movieRepository, times(1)).findAll(pageRequest);

            assertNotNull(response);
            assertEquals(pageContent.size(), response.getContent().size());
            assertEquals(1, response.getTotalElements());
            assertEquals(1, response.getTotalPages());
        }

        @Test
        @DisplayName("Should return empty content when does not exists movies")
        void shouldReturnEmptyContentWhenDoesNotExistsMovies() {
            // arrange
            var page = 0;
            var limit = 10;

            var pageRequest = PageRequest.of(page, limit, Sort.Direction.DESC, "createAt");
            var pageContent = List.of();
            var pageResponse = new PageImpl<>(pageContent, pageRequest, 0);

            doReturn(pageResponse).when(movieRepository).findAll(pageRequest);

            // act
            var response = movieService.getAll(page, limit);

            // assert
            verify(movieRepository, times(1)).findAll(pageRequest);

            assertNotNull(response);
            assertEquals(0, response.getContent().size());
            assertEquals(0, response.getTotalElements());
            assertEquals(0, response.getTotalPages());
        }
    }

    @Nested
    class GetById {
        @Test
        @DisplayName("Should return movie when exists")
        void shouldReturnMovieWhenExists() {
            // arrange
            ArgumentCaptor<Movie> captor = ArgumentCaptor.forClass(Movie.class);

            String id = "692e7a0b-454b-4701-b785-023d7a0ddc51";
            String title = "A Empregada";
            String description = "Em A Empregada, uma jovem começa a trabalhar na casa de um casal muito rico, mas tanto ela quanto os patrões escondem segredos sombrios.";
            String genre = "Suspense";
            int releaseYear = 2026;
            int duration = 133;

            Movie movie = new Movie(id, title, description, genre, releaseYear, duration);

            doReturn(Optional.of(movie)).when(movieRepository).findById(id);

            // act
            var response = movieService.getById(id);

            // assert
            verify(movieRepository, times(1)).findById(id);

            assertNotNull(response);
            assertEquals(movie.getTitle(), response.getTitle());
            assertEquals(movie.getDescription(), response.getDescription());
            assertEquals(movie.getGenre(), response.getGenre());
            assertEquals(movie.getReleaseYear(), response.getReleaseYear());
            assertEquals(movie.getDuration(), response.getDuration());
        }

        @Test
        @DisplayName("Should throw exception when movie does not exists")
        void shouldThrowExceptionWhenMovieDoesNotExists() {
            // arrange
            String id = "692e7a0b-454b-4701-b785-023d7a0ddc51";

            doReturn(Optional.empty()).when(movieRepository).findById(id);

            // act & assert
            assertThrows(ResourceNotFoundException.class, () -> movieService.update(id, any()));

            // assert
            verify(movieRepository, times(1)).findById(id);
            verify(movieRepository, times(0)).saveAndFlush(any());
        }
    }

    @Nested
    class Create {
        @Test
        @DisplayName("Should create")
        void shouldCreate() {
            // arrange
            ArgumentCaptor<Movie> captor = ArgumentCaptor.forClass(Movie.class);

            String title = "A Empregada";
            String description = "Em A Empregada, uma jovem começa a trabalhar na casa de um casal muito rico, mas tanto ela quanto os patrões escondem segredos sombrios.";
            String genre = "Suspense";
            int releaseYear = 2026;
            int duration = 133;

            MovieRequest request = new MovieRequest(title, description, genre, releaseYear, duration);

            // act
            movieService.create(request);

            // assert
            verify(movieRepository, times(1)).saveAndFlush(captor.capture());

            var response = captor.getValue();

            assertNotNull(response);
            assertEquals(request.title(), response.getTitle());
            assertEquals(request.description(), response.getDescription());
            assertEquals(request.genre(), response.getGenre());
            assertEquals(request.releaseYear(), response.getReleaseYear());
            assertEquals(request.duration(), response.getDuration());
        }
    }

    @Nested
    class Update {
        @Test
        @DisplayName("Should update when movie exists")
        void shouldUpdateWhenMovieExists() {
            // arrange
            ArgumentCaptor<Movie> captor = ArgumentCaptor.forClass(Movie.class);

            String id = "692e7a0b-454b-4701-b785-023d7a0ddc51";
            String title = "A Empregada";
            String description = "Em A Empregada, uma jovem começa a trabalhar na casa de um casal muito rico, mas tanto ela quanto os patrões escondem segredos sombrios.";
            String genre = "Suspense";
            int releaseYear = 2026;
            int duration = 133;

            MovieRequest request = new MovieRequest(title, description, genre, releaseYear, duration);
            Movie movie = new Movie(id, title, description, genre, releaseYear, duration);

            doReturn(Optional.of(movie)).when(movieRepository).findById(id);

            // act
            movieService.update(id, request);

            // assert
            verify(movieRepository, times(1)).findById(id);
            verify(movieRepository, times(1)).saveAndFlush(captor.capture());

            var response = captor.getValue();

            assertNotNull(response);
            assertEquals(request.title(), response.getTitle());
            assertEquals(request.description(), response.getDescription());
            assertEquals(request.genre(), response.getGenre());
            assertEquals(request.releaseYear(), response.getReleaseYear());
            assertEquals(request.duration(), response.getDuration());
        }

        @Test
        @DisplayName("Should throw exception when movie does not exists")
        void shouldThrowExceptionWhenMovieDoesNotExists() {
            // arrange
            String id = "692e7a0b-454b-4701-b785-023d7a0ddc51";
            MovieRequest request = new MovieRequest(
                    "A Empregada",
                    "Em A Empregada, uma jovem começa a trabalhar na casa de um casal muito rico, mas tanto ela quanto os patrões escondem segredos sombrios.",
                    "Suspense",
                    2026,
                    133
            );

            doReturn(Optional.empty()).when(movieRepository).findById(id);

            // act & assert
            assertThrows(ResourceNotFoundException.class, () -> movieService.update(id, request));

            // assert
            verify(movieRepository, times(1)).findById(id);
            verify(movieRepository, times(0)).saveAndFlush(any());
        }
    }

    @Nested
    class Delete {
        @Test
        @DisplayName("Should delete when movie exists")
        void shouldDeleteWhenMovieExists() {
            // arrange
            String id = "692e7a0b-454b-4701-b785-023d7a0ddc51";

            doReturn(true).when(movieRepository).existsById(id);

            // act & assert
            movieService.delete(id);

            // assert
            verify(movieRepository, times(1)).existsById(id);
            verify(movieRepository, times(1)).deleteById(id);
        }

        @Test
        @DisplayName("Should throw exception when movie does not exists")
        void shouldThrowExceptionWhenMovieDoesNotExists() {
            // arrange
            String id = "692e7a0b-454b-4701-b785-023d7a0ddc51";

            doReturn(false).when(movieRepository).existsById(id);

            // act & assert
            assertThrows(ResourceNotFoundException.class, () -> movieService.delete(id));

            // assert
            verify(movieRepository, times(1)).existsById(id);
            verify(movieRepository, times(0)).deleteById(id);
        }
    }
}