package com.technokratos.exception.token;

import com.technokratos.exception.CpServiceException;
import org.springframework.http.HttpStatus;

public class IrrelevantTokenException extends CpServiceException {

    public IrrelevantTokenException(String token, String message) {
        super(HttpStatus.UNAUTHORIZED, String.format("Failed for [%s]: %s", token, message));
    }

}
