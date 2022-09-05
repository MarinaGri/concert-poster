package com.technokratos.exception;


import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CpServiceException {

    public UserNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

}
