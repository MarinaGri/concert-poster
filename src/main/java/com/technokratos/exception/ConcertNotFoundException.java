package com.technokratos.exception;


import java.util.UUID;

public class ConcertNotFoundException extends CpNotFoundException {

    public ConcertNotFoundException() {
        super("Concert not found");
    }

    public ConcertNotFoundException(UUID id) {
        super("Concert not found with id:" + id);
    }
}
