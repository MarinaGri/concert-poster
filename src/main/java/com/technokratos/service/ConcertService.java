package com.technokratos.service;

import com.technokratos.dto.request.ConcertRequest;
import com.technokratos.dto.response.ConcertResponse;

import java.util.UUID;

public interface ConcertService {

    ConcertResponse createConcert(ConcertRequest newConcert);

    ConcertResponse getConcertById(UUID concertId);

    ConcertResponse updateConcert(UUID concertId, ConcertRequest updatedConcert);

    void deleteConcert(UUID concertId);

}
