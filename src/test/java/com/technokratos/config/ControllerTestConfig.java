package com.technokratos.config;

import com.technokratos.service.ConcertService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class ControllerTestConfig {

    @MockBean
    private ConcertService concertService;

}
