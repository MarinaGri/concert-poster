package com.technokratos.service;

import com.technokratos.dto.request.ConcertRequest;
import com.technokratos.dto.response.ConcertResponse;
import com.technokratos.dto.response.page.ConcertPage;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ConcertService {

    ConcertResponse createConcert(ConcertRequest newConcert);

    ConcertResponse getConcertById(UUID concertId);

    ConcertPage getConcertPage(Pageable pageable);

    ConcertResponse updateConcert(UUID concertId, ConcertRequest updatedConcert);

    void deleteConcert(UUID concertId);

}
