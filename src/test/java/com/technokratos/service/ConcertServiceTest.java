package com.technokratos.service;

import com.technokratos.config.ServiceTestConfig;
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
        ServiceTestConfig.class, ConcertServiceImpl.class, ConcertMapperImpl.class
})
public class ConcertServiceTest {

    @Autowired
    private ConcertServiceImpl concertService;

    @Autowired
    private ConcertRepository concertRepository;

    @BeforeEach
    public void setUp() {
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
    public void testSuccessfulCreateConcert() {
        ConcertResponse actualResponse = concertService.createConcert(CONCERT_REQUEST);
        assertEquals(CONCERT_RESPONSE, actualResponse);
    }

    @Test
    public void testSuccessfulGetConcert() {
        ConcertResponse actualResponse = concertService.getConcertById(CONCERT_ID);
        assertEquals(CONCERT_RESPONSE, actualResponse);
    }

    @Test
    public void testSuccessfulGetConcertPage() {
        ConcertPage actualResponse = concertService.getConcertPage(CONCERT_PAGEABLE);
        assertEquals(CONCERT_PAGE_RESPONSE, actualResponse);
    }

    @Test
    public void testSuccessfulUpdateConcert() {
        ConcertResponse actualResponse = concertService.updateConcert(CONCERT_ID, CONCERT_REQUEST);
        assertEquals(CONCERT_RESPONSE, actualResponse);
    }

    @Test
    public void testFailureDeleteConcert() {
        assertThrows(ConcertNotFoundException.class,
                () -> concertService.deleteConcert(UUID.randomUUID()));
    }
}
