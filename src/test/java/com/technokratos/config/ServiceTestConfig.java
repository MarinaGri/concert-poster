package com.technokratos.config;

import com.technokratos.repository.ConcertRepository;
import com.technokratos.repository.UserRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
public class ServiceTestConfig {

    @MockBean
    private ConcertRepository concertRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

}
