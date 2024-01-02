package com.bopera.pointofsales.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProblemDetailExceptionHandlerTest {

    private ProblemDetailExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new ProblemDetailExceptionHandler();
    }

    @Test
    void ShouldReturnResponseEntityWithProblemDetail_WhenHttpClientErrorExceptionThrown() {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Bad Request";
        HttpClientErrorException exception = new HttpClientErrorException(status, message);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, message);

        ResponseEntity<?> responseEntity = globalExceptionHandler.toProblemDetail(exception);

        assertThat(responseEntity.getStatusCode()).isEqualTo(status);
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(responseEntity.getBody()).usingRecursiveComparison().isEqualTo(problemDetail);
    }

    @Test
    void ShouldReturnResponseEntityWithProblemDetail_WhenMethodArgumentNotValidExceptionThrown() {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Validation failed";
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, message);
        when(exception.getBody()).thenReturn(problemDetail);

        ResponseEntity<?> responseEntity = globalExceptionHandler.handleMethodArgumentNotValid(exception);

        assertThat(responseEntity.getStatusCode()).isEqualTo(status);
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(responseEntity.getBody()).usingRecursiveComparison().isEqualTo(problemDetail);
    }
}
