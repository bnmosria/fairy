package com.bopera.pointofsales.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@ControllerAdvice
public class ProblemDetailControllerAdvice {
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<?> toProblemDetail(HttpClientErrorException exception) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
            exception.getStatusCode(), exception.getMessage()
        );

        problemDetail.setTitle(exception.getCause().toString());

        return getProblemDetailResponseEntity(problemDetail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        return getProblemDetailResponseEntity(exception.getBody());
    }

    private ResponseEntity<ProblemDetail> getProblemDetailResponseEntity(ProblemDetail problemDetail) {
        return ResponseEntity.status(problemDetail.getStatus())
            .contentType(MediaType.APPLICATION_JSON)
            .body(problemDetail);
    }
}
