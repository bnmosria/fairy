package com.bopera.pointofsales.exception;

import lombok.extern.slf4j.Slf4j;
import problem.ProblemDetail;
import problem.builder.ProblemDetailBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ProblemDetailControllerAdvice {
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> toProblemDetail(Throwable throwable) {
        ProblemDetail detail = new ProblemDetailBuilder(throwable).build();

        log.debug(detail.toString(), throwable);

        return ResponseEntity.status(detail.getStatus())
                .contentType(ProblemDetail.JSON_MEDIA_TYPE)
                .body(detail);
    }
}