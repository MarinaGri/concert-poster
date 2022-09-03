package com.technokratos.exception.token;

import com.technokratos.exception.CpServiceException;
import org.springframework.http.HttpStatus;

public class AuthenticationHeaderException extends CpServiceException {

    public AuthenticationHeaderException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
