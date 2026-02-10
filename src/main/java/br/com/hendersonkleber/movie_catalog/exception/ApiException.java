package br.com.hendersonkleber.movie_catalog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.time.LocalDateTime;

public abstract class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ProblemDetail toProblemDetail() {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var problem = ProblemDetail.forStatus(status);

        problem.setTitle(status.getReasonPhrase());
        problem.setDetail("Contact support");
        problem.setProperty("timestamp", LocalDateTime.now());

        return problem;
    }
}
