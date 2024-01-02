package com.bopera.pointofsales.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@ControllerAdvice
public class ProblemDetailExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGlobalException(Exception exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()
        );

        return createProblemDetailResponseEntity(problemDetail);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ProblemDetail> toProblemDetail(HttpClientErrorException exception) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
            exception.getStatusCode(), exception.getStatusText()
        );

        return createProblemDetailResponseEntity(problemDetail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        return createProblemDetailResponseEntity(exception.getBody());
    }

    private ResponseEntity<ProblemDetail> createProblemDetailResponseEntity(ProblemDetail problemDetail) {
        log.error("An error occurred: {}", problemDetail.getDetail());

        return ResponseEntity.status(problemDetail.getStatus())
            .contentType(MediaType.APPLICATION_JSON)
            .body(problemDetail);
    }
}
