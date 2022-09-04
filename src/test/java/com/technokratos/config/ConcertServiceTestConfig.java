package com.technokratos.config;

import com.technokratos.repository.ConcertRepository;
import com.technokratos.repository.UserRepository;
import com.technokratos.service.BookingService;
import com.technokratos.service.UserService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
public class ConcertServiceTestConfig {

    @MockBean
    private ConcertRepository concertRepository;

    @MockBean
    private BookingService bookingService;

    @MockBean
    private UserService userService;

}
