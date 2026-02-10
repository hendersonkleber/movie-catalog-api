package br.com.hendersonkleber.movie_catalog.service;

import br.com.hendersonkleber.movie_catalog.domain.Review;
import br.com.hendersonkleber.movie_catalog.dto.ReviewRequest;
import br.com.hendersonkleber.movie_catalog.exception.ResourceNotFoundException;
import br.com.hendersonkleber.movie_catalog.repository.ReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Nested
    class GetAll {
        @Test
        @DisplayName("Should return reviews paginated")
        void shouldReturnReviewsPaginated() {
            // arrange
            var movieId = "692e7a0b-454b-4701-b785-023d7a0ddc51";
            var page = 0;
            var limit = 10;

            var review = new Review("698b6f5807ce920d72cde17d", movieId, 4.0, "Good");
            var pageRequest = PageRequest.of(page, limit, Sort.Direction.DESC, "createAt");
            var pageContent = List.of(review);
            var pageResponse = new PageImpl<>(pageContent, pageRequest, pageContent.size());

            doReturn(pageResponse).when(reviewRepository).findAll(movieId, pageRequest);

            // act
            var response = reviewService.getAll(movieId, page, limit);

            // assert
            verify(reviewRepository, times(1)).findAll(movieId, pageRequest);

            assertNotNull(response);
            assertEquals(pageContent.size(), response.getContent().size());
            assertEquals(1, response.getTotalElements());
            assertEquals(1, response.getTotalPages());
        }

        @Test
        @DisplayName("Should return empty content when does not exists reviews")
        void shouldReturnEmptyContentWhenDoesNotExistsReviews() {
            // arrange
            var movieId = "692e7a0b-454b-4701-b785-023d7a0ddc51";
            var page = 0;
            var limit = 10;

            var pageRequest = PageRequest.of(page, limit, Sort.Direction.DESC, "createAt");
            var pageContent = List.of();
            var pageResponse = new PageImpl<>(pageContent, pageRequest, 0);

            doReturn(pageResponse).when(reviewRepository).findAll(movieId, pageRequest);

            // act
            var response = reviewService.getAll(movieId, page, limit);

            // assert
            verify(reviewRepository, times(1)).findAll(movieId, pageRequest);

            assertNotNull(response);
            assertEquals(0, response.getContent().size());
            assertEquals(0, response.getTotalElements());
            assertEquals(0, response.getTotalPages());
        }
    }

    @Nested
    class Create {
        @Test
        @DisplayName("Should create")
        void shouldCreate() {
            // arrange
            var captor = ArgumentCaptor.forClass(Review.class);

            var request = new ReviewRequest(
                    "692e7a0b-454b-4701-b785-023d7a0ddc51",
                    "good",
                    4.0
            );

            // act
            reviewService.create(request);

            // assert
            verify(reviewRepository, times(1)).save(captor.capture());

            var response = captor.getValue();

            assertNotNull(response);
            assertEquals(request.comment(), response.getComment());
            assertEquals(request.rating(), response.getRating());
            assertEquals(request.movieId(), response.getMovieId());
        }
    }

    @Nested
    class Delete {
        @Test
        @DisplayName("Should delete when review exists")
        void shouldDeleteWhenReviewExists() {
            // arrange
            String id = "692e7a0b-454b-4701-b785-023d7a0ddc51";

            doReturn(true).when(reviewRepository).existsById(id);

            // act
            reviewService.delete(id);

            // assert
            verify(reviewRepository, times(1)).existsById(id);
            verify(reviewRepository, times(1)).deleteById(id);
        }

        @Test
        @DisplayName("Should throw exception when review does not exists")
        void shouldThrowExceptionWhenReviewDoesNotExists() {
            // arrange
            String id = "692e7a0b-454b-4701-b785-023d7a0ddc51";

            doReturn(false).when(reviewRepository).existsById(id);

            // act & assert
            assertThrows(ResourceNotFoundException.class, () -> reviewService.delete(id));

            // assert
            verify(reviewRepository, times(1)).existsById(id);
            verify(reviewRepository, times(0)).deleteById(id);
        }
    }
}