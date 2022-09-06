package com.technokratos.exception.token;

import com.technokratos.exception.CpServiceException;
import org.springframework.http.HttpStatus;

public class TokenRefreshException extends CpServiceException {

    public TokenRefreshException(String token, String message) {
        super(HttpStatus.UNAUTHORIZED, String.format("Failed for [%s]: %s", token, message));
    }

}
