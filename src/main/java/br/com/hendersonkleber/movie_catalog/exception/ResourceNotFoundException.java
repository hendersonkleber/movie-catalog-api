package br.com.hendersonkleber.movie_catalog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.time.LocalDateTime;

public class ResourceNotFoundException extends ApiException {
    private final String message;

    public ResourceNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var status = HttpStatus.NOT_FOUND;
        var problem = ProblemDetail.forStatus(status);

        problem.setTitle(status.getReasonPhrase());
        problem.setDetail(this.message);
        problem.setProperty("timestamp", LocalDateTime.now());

        return problem;
    }
}
