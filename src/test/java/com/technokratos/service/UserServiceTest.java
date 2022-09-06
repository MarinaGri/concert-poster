package com.technokratos.service;

import com.technokratos.config.UserServiceTestConfig;
import com.technokratos.exception.UserAlreadyExistsException;
import com.technokratos.model.UserEntity;
import com.technokratos.repository.UserRepository;
import com.technokratos.service.impl.UserServiceImpl;
import com.technokratos.util.mapper.UserMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static com.technokratos.consts.UserConst.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        UserServiceTestConfig.class, UserServiceImpl.class, UserMapperImpl.class
})
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        when(userRepository.existsByEmail(USER_EMAIL))
                .thenReturn(false);

        when(userRepository.existsByEmail(USER_REPEATED_EMAIL))
                .thenReturn(true);

        when(userRepository.save(NEW_USER_ENTITY))
                .thenReturn(USER_ENTITY);

        when(userRepository.findById(USER_ID))
                .thenReturn(Optional.of(USER_ENTITY));

        when(userRepository.findByEmail(USER_EMAIL))
                .thenReturn(Optional.of(USER_ENTITY));

        when(passwordEncoder.encode(USER_PASSWORD))
                .thenReturn(HASH_PASSWORD);
    }

    @Test
    void testSuccessfulAddUser() {
        UUID actualResponse = userService.addUser(USER_REQUEST);
        assertEquals(USER_ID, actualResponse);
    }

    @Test
    void testSuccessfulGetUserById() {
        UserEntity actualResponse = userService.getUserById(USER_ID);
        assertEquals(USER_ENTITY, actualResponse);
    }

    @Test
    void testSuccessfulGetUserByEmail() {
        UserEntity actualResponse = userService.getUserByEmail(USER_EMAIL);
        assertEquals(USER_ENTITY, actualResponse);
    }

    @Test
    void testFailureAddUser() {
        assertThrows(UserAlreadyExistsException.class,
                () -> userService.addUser(REPEATED_USER_REQUEST));
    }
}
