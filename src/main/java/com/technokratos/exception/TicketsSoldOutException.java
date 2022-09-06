package com.technokratos.exception;

import org.springframework.http.HttpStatus;

public class TicketsSoldOutException extends CpServiceException {

    public TicketsSoldOutException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
