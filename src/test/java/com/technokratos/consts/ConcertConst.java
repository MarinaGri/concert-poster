package com.technokratos.consts;

import com.technokratos.dto.request.ConcertRequest;
import com.technokratos.dto.response.ConcertResponse;
import com.technokratos.dto.response.page.ConcertPage;
import com.technokratos.model.ConcertEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.UUID;

public class ConcertConst {

    public static final UUID CONCERT_ID = UUID.randomUUID();

    public static final UUID FAKE_CONCERT_ID = UUID.randomUUID();

    public static final String CONCERT_NAME = "Concert name";

    public static final String CONCERT_DESCRIPTION = "Some description";

    public static final Integer TICKETS_NUMBER = 42;

    public static final Integer TOTAL_PAGES = 1;

    public static final Integer CURRENT_PAGE = 0;

    public static final ConcertResponse CONCERT_RESPONSE = ConcertResponse.builder()
            .id(CONCERT_ID)
            .name(CONCERT_NAME)
            .description(CONCERT_DESCRIPTION)
            .ticketsNumber(TICKETS_NUMBER)
            .build();

    public static final ConcertPage CONCERT_PAGE_RESPONSE = ConcertPage.builder()
            .concerts(Collections.singletonList(CONCERT_RESPONSE))
            .currentPage(CURRENT_PAGE)
            .totalPages(TOTAL_PAGES)
            .build();

    public static final ConcertRequest CONCERT_REQUEST = ConcertRequest.builder()
            .name(CONCERT_NAME)
            .description(CONCERT_DESCRIPTION)
            .ticketsNumber(TICKETS_NUMBER)
            .build();

    public static final Pageable CONCERT_PAGEABLE = PageRequest.of(CURRENT_PAGE, TOTAL_PAGES);

    public static final ConcertEntity NEW_CONCERT_ENTITY = ConcertEntity.builder()
            .name(CONCERT_NAME)
            .description(CONCERT_DESCRIPTION)
            .ticketsNumber(TICKETS_NUMBER)
            .build();

    public static final ConcertEntity CONCERT_ENTITY = ConcertEntity.builder()
            .id(CONCERT_ID)
            .name(CONCERT_NAME)
            .description(CONCERT_DESCRIPTION)
            .ticketsNumber(TICKETS_NUMBER)
            .build();

    public static final Page<ConcertEntity> CONCERT_ENTITY_PAGE =
            new PageImpl<>(Collections.singletonList(CONCERT_ENTITY), CONCERT_PAGEABLE, TOTAL_PAGES);
}
