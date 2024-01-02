package com.bopera.pointofsales.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class EntityNotFoundException extends HttpClientErrorException {
    public EntityNotFoundException(String statusText) {
        super(HttpStatus.NOT_FOUND, statusText);
    }
}
