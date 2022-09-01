package com.technokratos.exception;

import org.springframework.http.HttpStatus;

public class CpNotFoundException extends CpServiceException {

    public CpNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
