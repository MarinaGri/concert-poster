package com.technokratos.service;

import com.technokratos.config.ConcertServiceTestConfig;
import com.technokratos.dto.response.ConcertResponse;
import com.technokratos.dto.response.page.ConcertPage;
import com.technokratos.exception.ConcertNotFoundException;
import com.technokratos.repository.ConcertRepository;
import com.technokratos.service.impl.ConcertServiceImpl;
import com.technokratos.util.mapper.ConcertMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static com.technokratos.consts.ConcertConst.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        ConcertServiceTestConfig.class, ConcertServiceImpl.class, ConcertMapperImpl.class
})
class ConcertServiceTest {

    @Autowired
    private ConcertService concertService;

    @Autowired
    private ConcertRepository concertRepository;

    @BeforeEach
    void setUp() {
        when(concertRepository.save(NEW_CONCERT_ENTITY))
                .thenReturn(CONCERT_ENTITY);

        when(concertRepository.findById(CONCERT_ID))
                .thenReturn(Optional.of(CONCERT_ENTITY));

        when(concertRepository.findAll(CONCERT_PAGEABLE))
                .thenReturn(CONCERT_ENTITY_PAGE);

        when(concertRepository.existsById(FAKE_CONCERT_ID))
                .thenReturn(false);
    }

    @Test
    void testSuccessfulCreateConcert() {
        ConcertResponse actualResponse = concertService.createConcert(CONCERT_REQUEST);
        assertEquals(CONCERT_RESPONSE, actualResponse);
    }

    @Test
    void testSuccessfulGetConcert() {
        ConcertResponse actualResponse = concertService.getConcertById(CONCERT_ID);
        assertEquals(CONCERT_RESPONSE, actualResponse);
    }

    @Test
    void testSuccessfulGetConcertPage() {
        ConcertPage actualResponse = concertService.getConcertPage(CONCERT_PAGEABLE);
        assertEquals(CONCERT_PAGE_RESPONSE, actualResponse);
    }

    @Test
    void testSuccessfulUpdateConcert() {
        ConcertResponse actualResponse = concertService.updateConcert(CONCERT_ID, CONCERT_REQUEST);
        assertEquals(CONCERT_RESPONSE, actualResponse);
    }

    @Test
    void testFailureDeleteConcert() {
        UUID concertId = UUID.randomUUID();
        assertThrows(ConcertNotFoundException.class,
                () -> concertService.deleteConcert(concertId));
    }
}
