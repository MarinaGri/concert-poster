package com.technokratos.api;

import com.technokratos.dto.request.ConcertRequest;
import com.technokratos.dto.response.ConcertResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RequestMapping("/api/v1/concert")
public interface ConcertApi {

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    ConcertResponse createConcert(@RequestBody ConcertRequest newConcert);

    @GetMapping(value = "/{concert-id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ConcertResponse getConcertById(@PathVariable("concert-id") UUID concertId);

    @PutMapping(value = "/{concert-id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ConcertResponse updateConcertById(@PathVariable("concert-id") UUID concertId,
                                   @RequestBody ConcertRequest updatedConcert);

    @DeleteMapping(value = "/{concert-id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    void deleteConcertById(@PathVariable("concert-id") UUID concertId);

}
