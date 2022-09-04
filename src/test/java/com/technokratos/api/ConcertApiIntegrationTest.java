package com.technokratos.api;

import com.technokratos.container.PostgresTestContainer;
import com.technokratos.dto.request.ConcertRequest;
import com.technokratos.dto.response.ConcertResponse;
import com.technokratos.dto.response.TokenCoupleResponse;
import com.technokratos.dto.response.UserResponse;
import com.technokratos.dto.response.message.ExceptionMessage;
import com.technokratos.dto.response.page.ConcertPage;
import com.technokratos.exception.ConcertNotFoundException;
import com.technokratos.model.ConcertEntity;
import com.technokratos.repository.BookingRepository;
import com.technokratos.repository.ConcertRepository;
import com.technokratos.service.JwtTokenService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import static com.technokratos.consts.SecurityConstants.BEARER;
import static com.technokratos.consts.UserConst.*;
import static com.technokratos.consts.ConcertConst.*;
import static com.technokratos.consts.MessageConst.*;
import static org.junit.jupiter.api.Assertions.*;

@Sql("/sql/add-accounts.sql")
@Sql("/sql/add-concert.sql")
@Sql(scripts = "/sql/clear-account-table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "/sql/clear-concert-table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConcertApiIntegrationTest extends PostgresTestContainer {

    @LocalServerPort
    private int serverPort;

    private String testUrl;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @BeforeEach
    public void setUp() {
        testUrl = "http://localhost:" + serverPort + "/api/v1/concert";
    }

    @Test
    public void testSuccessfulAddConcert() {
        HttpEntity<ConcertRequest> concertRequest =
                new HttpEntity<>(CONCERT_REQUEST, createAuthHeader(ADMIN_RESPONSE));

        ResponseEntity<ConcertResponse> actualResponse =
                restTemplate.exchange(testUrl, HttpMethod.POST, concertRequest, ConcertResponse.class);

        testConcertResponse(CONCERT_REQUEST, actualResponse.getBody());
    }

    @Test
    public void testAddConcertByCustomer() {
        HttpEntity<ConcertRequest> concertRequest =
                new HttpEntity<>(CONCERT_REQUEST, createAuthHeader(USER_RESPONSE));

        ResponseEntity<ExceptionMessage> actualResponse =
                restTemplate.exchange(testUrl, HttpMethod.POST, concertRequest, ExceptionMessage.class);

        assertEquals(ACCESS_DENIED_MESSAGE, actualResponse.getBody());
        assertEquals(HttpStatus.FORBIDDEN, actualResponse.getStatusCode());
    }

    @Test
    public void testSuccessfulGetConcert() {
        HttpEntity<Void> concertRequest = new HttpEntity<>(createAuthHeader(USER_RESPONSE));

        ResponseEntity<ConcertResponse> actualResponse =
                restTemplate.exchange(testUrl + "/" + CONCERT_ID, HttpMethod.GET, concertRequest, ConcertResponse.class);

        testConcertResponse(CONCERT_REQUEST, actualResponse.getBody());
    }

    @Test
    public void testSuccessfulGetConcertPage() {
        HttpEntity<Void> concertRequest = new HttpEntity<>(createAuthHeader(USER_RESPONSE));
        ResponseEntity<ConcertPage> actualResponse =
                restTemplate.exchange(testUrl, HttpMethod.GET, concertRequest, ConcertPage.class);

        assertEquals(CONCERT_PAGE_RESPONSE, actualResponse.getBody());
    }

    @Test
    public void testSuccessfulUpdateConcert() {
        HttpEntity<ConcertRequest> concertRequest
                = new HttpEntity<>(CONCERT_UPDATE_REQUEST, createAuthHeader(ADMIN_RESPONSE));

        ResponseEntity<ConcertResponse> actualResponse =
                restTemplate.exchange(testUrl + "/" + CONCERT_ID, HttpMethod.PUT, concertRequest, ConcertResponse.class);

        testConcertResponse(CONCERT_UPDATE_REQUEST, actualResponse.getBody());
    }

    @Test
    public void testSuccessfulDeleteConcert() {
        HttpEntity<Void> concertRequest = new HttpEntity<>(createAuthHeader(ADMIN_RESPONSE));
        restTemplate.exchange(testUrl + "/" + CONCERT_ID, HttpMethod.DELETE, concertRequest, void.class);

        assertTrue(concertRepository.findById(CONCERT_ID).isEmpty());
    }

    @Test
    public void testSuccessfulBooking() {
        Long countBookingBefore = bookingRepository.countBookingEntitiesByConcertId(CONCERT_ID);

        HttpEntity<Void> concertRequest = new HttpEntity<>(createAuthHeader(USER_RESPONSE));
        restTemplate.exchange(testUrl + "/" + CONCERT_ID + "/user", HttpMethod.POST, concertRequest, void.class);

        ConcertEntity concert = concertRepository.findById(CONCERT_ID)
                .orElseThrow(() -> new ConcertNotFoundException(CONCERT_ID));

        Long countBookingAfter = bookingRepository.countBookingEntitiesByConcertId(CONCERT_ID);

        assertEquals(concert.getTicketsNumber(), TICKETS_NUMBER - 1);
        assertEquals(countBookingBefore + 1, countBookingAfter);
    }

    @Test
    @Sql("/sql/add-accounts.sql")
    @Sql("/sql/add-popular-concert.sql")
    @Sql(scripts = "/sql/clear-account-table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = "/sql/clear-concert-table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testTicketsSoldOut() {
        HttpEntity<Void> concertRequest = new HttpEntity<>(createAuthHeader(USER_RESPONSE));
        ResponseEntity<ExceptionMessage> actualResponse = restTemplate.exchange(
                testUrl + "/" + POPULAR_CONCERT_ID + "/user",
                HttpMethod.POST, concertRequest, ExceptionMessage.class
        );

        assertEquals(TICKETS_SOLD_OUT_MESSAGE, actualResponse.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
    }

    private HttpHeaders createAuthHeader(UserResponse userResponse) {
        TokenCoupleResponse tokenResponse = jwtTokenService.generateTokenCouple(userResponse);
        String bearerHeader = BEARER.concat(StringUtils.SPACE).concat(tokenResponse.getAccessToken());

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, bearerHeader);
        return headers;
    }

    private void testConcertResponse(ConcertRequest request, ConcertResponse response) {
        assertNotNull(response);
        assertEquals(request.getName(), response.getName());
        assertEquals(request.getDescription(), response.getDescription());
        assertEquals(request.getTicketsNumber(), response.getTicketsNumber());
    }
}
