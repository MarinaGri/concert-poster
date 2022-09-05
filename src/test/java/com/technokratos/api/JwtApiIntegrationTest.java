package com.technokratos.api;

import com.technokratos.container.PostgresTestContainer;
import com.technokratos.dto.request.TokenCoupleRequest;
import com.technokratos.dto.response.*;
import com.technokratos.dto.response.message.ExceptionMessage;
import com.technokratos.model.UserRefreshTokenEntity;
import com.technokratos.repository.RefreshTokenRepository;
import com.technokratos.service.JwtTokenService;
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


import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static com.technokratos.consts.SecurityConst.BEARER;
import static com.technokratos.consts.UserConst.*;
import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext
@Sql("/sql/add-accounts.sql")
@Sql(scripts = "/sql/clear-account-table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JwtApiIntegrationTest extends PostgresTestContainer {

    @LocalServerPort
    private int serverPort;

    private String testUrl;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @BeforeEach
    void setUp() {
        testUrl = "http://localhost:" + serverPort + "/api/v1/token";
    }

    @Test
    void testSuccessfulRefreshTokens() {
        TokenCoupleResponse tokenResponse = jwtTokenService.generateTokenCouple(USER_RESPONSE);

        HttpEntity<TokenCoupleRequest> tokenRequest = createTokenCoupleRequest(tokenResponse);

        ResponseEntity<TokenCoupleResponse> actualResponse =
                restTemplate.postForEntity(testUrl + "/refresh", tokenRequest, TokenCoupleResponse.class);

        assertNotNull(actualResponse.getBody());

        String bearerHeader = BEARER.concat(StringUtils.SPACE).concat(actualResponse.getBody().getAccessToken());
        UserResponse userResponse = jwtTokenService.getUserInfoByHeader(bearerHeader);

        assertEquals(USER_EMAIL, userResponse.getEmail());
        assertEquals(USER_ROLE, userResponse.getRole());
    }

    @Test
    void testRefreshWithExpiredToken() {
        TokenCoupleResponse tokenResponse = jwtTokenService.generateTokenCouple(USER_RESPONSE);

        Optional<UserRefreshTokenEntity> tokenCandidate =
                refreshTokenRepository.findById(UUID.fromString(tokenResponse.getRefreshToken()));
        assertTrue(tokenCandidate.isPresent());

        tokenCandidate.get().setExpiryDate(Instant.now());
        refreshTokenRepository.save(tokenCandidate.get());

        HttpEntity<TokenCoupleRequest> tokenRequest = createTokenCoupleRequest(tokenResponse);

        ResponseEntity<ExceptionMessage> actualResponse =
                restTemplate.postForEntity(testUrl + "/refresh", tokenRequest, ExceptionMessage.class);

        assertEquals(HttpStatus.UNAUTHORIZED, actualResponse.getStatusCode());
    }

    @Test
    void testGetUserByToken() {
        TokenCoupleResponse tokenResponse = jwtTokenService.generateTokenCouple(USER_RESPONSE);
        String bearerHeader = BEARER.concat(StringUtils.SPACE).concat(tokenResponse.getAccessToken());

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, bearerHeader);
        HttpEntity<Void> tokenRequest = new HttpEntity<>(headers);

        ResponseEntity<UserResponse> userResponse =
                restTemplate.exchange(testUrl + "/user", HttpMethod.GET, tokenRequest, UserResponse.class);

        assertNotNull(userResponse.getBody());
        assertEquals(USER_EMAIL, userResponse.getBody().getEmail());
        assertEquals(USER_ROLE, userResponse.getBody().getRole());
    }

    private HttpEntity<TokenCoupleRequest> createTokenCoupleRequest(TokenCoupleResponse tokens) {
        TokenCoupleRequest tokenCoupleRequest = TokenCoupleRequest.builder()
                .accessToken(tokens.getAccessToken())
                .refreshToken(tokens.getRefreshToken())
                .build();

        return new HttpEntity<>(tokenCoupleRequest);
    }

}
