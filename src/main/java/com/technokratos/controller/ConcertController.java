package com.technokratos.controller;

import com.technokratos.api.ConcertApi;
import com.technokratos.dto.request.ConcertRequest;
import com.technokratos.dto.response.ConcertResponse;
import com.technokratos.dto.response.page.ConcertPage;
import com.technokratos.service.ConcertService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class ConcertController implements ConcertApi {

    private final ConcertService concertService;

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ConcertResponse createConcert(ConcertRequest newConcert) {
        return concertService.createConcert(newConcert);
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public ConcertResponse getConcertById(UUID concertId) {
        return concertService.getConcertById(concertId);
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public ConcertPage getConcertPage(Pageable pageable) {
        return concertService.getConcertPage(pageable);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ConcertResponse updateConcertById(UUID concertId, ConcertRequest updatedConcert) {
        return concertService.updateConcert(concertId, updatedConcert);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteConcertById(UUID concertId) {
        concertService.deleteConcert(concertId);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public void addBookingInfo(UUID concertId, UUID userId) {
        concertService.addBookingInfo(concertId, userId);
    }
}
