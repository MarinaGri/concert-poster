package com.technokratos.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends CpServiceException {

    public UserAlreadyExistsException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
