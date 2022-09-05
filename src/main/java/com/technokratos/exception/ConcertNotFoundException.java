package com.technokratos.exception;


import org.springframework.http.HttpStatus;

import java.util.UUID;

public class ConcertNotFoundException extends CpServiceException {

    public ConcertNotFoundException(UUID id) {
        super(HttpStatus.NOT_FOUND, "Concert not found with id:" + id);
    }
}
