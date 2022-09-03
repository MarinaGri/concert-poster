package com.technokratos.exception;

import org.springframework.http.HttpStatus;

public class CpUnauthorizedException extends CpServiceException {

    public CpUnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
