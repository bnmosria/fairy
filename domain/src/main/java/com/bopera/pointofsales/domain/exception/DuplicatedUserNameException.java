package com.bopera.pointofsales.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class DuplicatedUserNameException extends HttpClientErrorException {
    public DuplicatedUserNameException(String statusText) {
        super(HttpStatus.BAD_REQUEST, statusText);
    }
}
