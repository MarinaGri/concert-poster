package com.technokratos.api;

import com.technokratos.dto.request.ConcertRequest;
import com.technokratos.dto.response.ConcertResponse;
import com.technokratos.dto.response.page.ConcertPage;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@SecurityRequirement(name = "Authorization")
@RequestMapping("/api/v1/concert")
public interface ConcertApi {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    ConcertResponse createConcert(@RequestBody ConcertRequest newConcert);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @GetMapping(value = "/{concert-id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ConcertResponse getConcertById(@PathVariable("concert-id") UUID concertId);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ConcertPage getConcertPage(Pageable pageable);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{concert-id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ConcertResponse updateConcertById(@PathVariable("concert-id") UUID concertId,
                                   @RequestBody ConcertRequest updatedConcert);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{concert-id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    void deleteConcertById(@PathVariable("concert-id") UUID concertId);

}
