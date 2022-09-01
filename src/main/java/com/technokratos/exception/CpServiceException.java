package com.technokratos.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CpServiceException extends RuntimeException {

    private final HttpStatus httpStatus;

    public CpServiceException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
