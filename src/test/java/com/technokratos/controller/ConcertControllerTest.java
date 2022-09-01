package com.technokratos.controller;

import com.technokratos.config.ControllerTestConfig;
import com.technokratos.dto.response.ConcertResponse;
import com.technokratos.dto.response.page.ConcertPage;
import com.technokratos.service.ConcertService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.technokratos.consts.ConcertConst.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ControllerTestConfig.class, ConcertController.class})
public class ConcertControllerTest {

    @Autowired
    private ConcertController concertController;

    @Autowired
    private ConcertService concertService;

    @BeforeEach
    public void setUp() {
        when(concertService.createConcert(CONCERT_REQUEST))
                .thenReturn(CONCERT_RESPONSE);

        when(concertService.getConcertById(CONCERT_ID))
                .thenReturn(CONCERT_RESPONSE);

        when(concertService.getConcertPage(CONCERT_PAGEABLE))
                .thenReturn(CONCERT_PAGE_RESPONSE);

        when(concertService.updateConcert(CONCERT_ID, CONCERT_REQUEST))
                .thenReturn(CONCERT_RESPONSE);
    }

    @Test
    public void testSuccessfulCreateConcert() {
        ConcertResponse actualResponse = concertController.createConcert(CONCERT_REQUEST);
        assertEquals(CONCERT_RESPONSE, actualResponse);
    }

    @Test
    public void testSuccessfulGetConcert() {
        ConcertResponse actualResponse = concertController.getConcertById(CONCERT_ID);
        assertEquals(CONCERT_RESPONSE, actualResponse);
    }

    @Test
    public void testSuccessfulGetConcertPage() {
        ConcertPage actualResponse = concertController.getConcertPage(CONCERT_PAGEABLE);
        assertEquals(CONCERT_PAGE_RESPONSE, actualResponse);
    }

    @Test
    public void testSuccessfulUpdateConcert() {
        ConcertResponse actualResponse = concertController.updateConcertById(CONCERT_ID, CONCERT_REQUEST);
        assertEquals(CONCERT_RESPONSE, actualResponse);
    }

    @Test
    public void testSuccessfulDeleteConcert() {
        concertController.deleteConcertById(CONCERT_ID);
        verify(concertService).deleteConcert(CONCERT_ID);
    }

}
