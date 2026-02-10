package br.com.hendersonkleber.movie_catalog.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ProblemDetail> handleApiException(ApiException exception) {
        logger.error("ApiException", exception);

        var problem = exception.toProblemDetail();

        return ResponseEntity.status(problem.getStatus()).body(problem);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        logger.error("Validation failure", exception);

        var status = HttpStatus.BAD_REQUEST;
        var problem = ProblemDetail.forStatus(status);

        List<String> errors = new ArrayList<>();

        for (var error : exception.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }

        for (var error : exception.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        problem.setTitle(status.getReasonPhrase());
        problem.setDetail("Invalid request content");
        problem.setProperty("timestamp", LocalDateTime.now());
        problem.setProperty("errors", errors);

        return ResponseEntity.status(status).body(problem);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ProblemDetail> handleHandlerMethodValidationException(HandlerMethodValidationException exception) {
        logger.error("Parameter validation failure", exception);

        var status = HttpStatus.BAD_REQUEST;
        var problem = ProblemDetail.forStatus(status);

        List<String> errors = new ArrayList<>();

        for (var parameter : exception.getParameterValidationResults()) {
            var name = parameter.getMethodParameter().getParameterName();

            for (var error : parameter.getResolvableErrors()) {
                errors.add(name + ": " + error.getDefaultMessage());
            }
        }

        problem.setTitle(status.getReasonPhrase());
        problem.setDetail("Parameter validation failure");
        problem.setProperty("timestamp", LocalDateTime.now());
        problem.setProperty("errors", errors);

        return ResponseEntity.status(status).body(problem);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ProblemDetail> handleNoResourceFoundException(NoResourceFoundException exception) {
        logger.error("Internal error", exception);

        var status = HttpStatus.BAD_REQUEST;
        var problem = ProblemDetail.forStatus(status);

        problem.setTitle(status.getReasonPhrase());
        problem.setDetail(exception.getMessage());
        problem.setProperty("timestamp", LocalDateTime.now());

        return ResponseEntity.status(status).body(problem);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleAllException(Exception exception) {
        logger.error("Unexpected error", exception);

        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var problem = ProblemDetail.forStatus(status);

        problem.setTitle(status.getReasonPhrase());
        problem.setDetail("Unexpected internal server error");
        problem.setProperty("timestamp", LocalDateTime.now());

        return ResponseEntity.status(status).body(problem);
    }
}
