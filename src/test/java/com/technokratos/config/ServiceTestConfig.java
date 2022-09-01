package com.technokratos.config;

import com.technokratos.repository.ConcertRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class ServiceTestConfig {

    @MockBean
    private ConcertRepository concertRepository;

}
