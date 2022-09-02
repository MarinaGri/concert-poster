package com.technokratos.controller;

import com.technokratos.config.ControllerTestConfig;
import com.technokratos.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static com.technokratos.consts.UserConst.USER_ID;
import static com.technokratos.consts.UserConst.USER_REQUEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ControllerTestConfig.class, UserController.class})
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setUp() {
        when(userService.addUser(USER_REQUEST))
                .thenReturn(USER_ID);
    }

    @Test
    public void testSuccessfulAddUser() {
        UUID actualResponse = userController.addUser(USER_REQUEST);
        assertEquals(USER_ID, actualResponse);
    }
}
