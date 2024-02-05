package com.bopera.pointofsales.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class DuplicatedRoleException extends HttpClientErrorException {
    public DuplicatedRoleException(String statusText) {
        super(HttpStatus.BAD_REQUEST, statusText);
    }
}
