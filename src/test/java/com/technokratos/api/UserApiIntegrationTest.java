package com.technokratos.api;

import com.technokratos.container.PostgresTestContainer;
import com.technokratos.dto.request.UserExtendedRequest;
import com.technokratos.dto.request.UserRequest;
import com.technokratos.dto.response.TokenCoupleResponse;
import com.technokratos.dto.response.UserResponse;
import com.technokratos.dto.response.message.ExceptionMessage;
import com.technokratos.exception.UserNotFoundException;
import com.technokratos.model.UserEntity;
import com.technokratos.repository.UserRepository;
import com.technokratos.service.JwtTokenService;
import com.technokratos.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static com.technokratos.consts.SecurityConstants.BEARER;
import static com.technokratos.consts.UserConst.*;
import static com.technokratos.consts.MessageConst.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext
@Sql(scripts = "/sql/clear-account-table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserApiIntegrationTest extends PostgresTestContainer {

    @LocalServerPort
    private int serverPort;

    private String testUrl;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @BeforeEach
    public void setUp() {
        testUrl = "http://localhost:" + serverPort + "/api/v1/user";
    }

    @Test
    public void testSuccessfulAddUser() {
        HttpEntity<UserExtendedRequest> userRequest = new HttpEntity<>(USER_REQUEST);

        ResponseEntity<UUID> uuidResponse =
                restTemplate.postForEntity(testUrl, userRequest, UUID.class);

        UserEntity user = userRepository.findByEmail(USER_EMAIL)
                .orElseThrow(() -> new UserNotFoundException("Test user not found"));

        assertEquals(user.getId(), uuidResponse.getBody());
        assertEquals(HttpStatus.CREATED, uuidResponse.getStatusCode());
    }

    @Test
    public void testAddUserWithSameEmail() {
        userRepository.save(REPEATED_USER_ENTITY);

        HttpEntity<UserExtendedRequest> userRequest = new HttpEntity<>(REPEATED_USER_REQUEST);
        ResponseEntity<ExceptionMessage> actualResponse =
                restTemplate.postForEntity(testUrl, userRequest, ExceptionMessage.class);

        assertEquals(USER_ALREADY_EXISTS_MESSAGE, actualResponse.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
    }

    @Test
    public void testSuccessfulLogin() {
        userService.addUser(USER_REQUEST);

        HttpEntity<UserRequest> loginRequest = new HttpEntity<>(LOGIN_REQUEST);

        TokenCoupleResponse tokenResponse =
                restTemplate.postForObject(testUrl + "/login", loginRequest, TokenCoupleResponse.class);

        String bearerHeader = BEARER.concat(StringUtils.SPACE).concat(tokenResponse.getAccessToken());

        UserResponse userResponse = jwtTokenService.getUserInfoByHeader(bearerHeader);

        assertEquals(userResponse.getEmail(), USER_EMAIL);
        assertEquals(userResponse.getRole(), USER_ROLE);
    }

    @Test
    public void testLoginWithWrongEmail() {
        userService.addUser(USER_REQUEST);
        testFailureLogin(new HttpEntity<>(LOGIN_REQUEST_WRONG_EMAIL), WRONG_EMAIL_MESSAGE);
    }

    @Test
    public void testLoginWithWrongPassword() {
        userService.addUser(USER_REQUEST);
        testFailureLogin(new HttpEntity<>(LOGIN_REQUEST_WRONG_PASSWORD), WRONG_PASSWORD_MESSAGE);
    }

    private void testFailureLogin(HttpEntity<UserRequest> loginRequest, ExceptionMessage message) {
        ResponseEntity<ExceptionMessage> actualResponse =
                restTemplate.postForEntity(testUrl + "/login", loginRequest, ExceptionMessage.class);

        assertEquals(message, actualResponse.getBody());
        assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
    }

}
