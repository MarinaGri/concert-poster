package com.technokratos.consts;

import com.technokratos.dto.request.ConcertRequest;
import com.technokratos.dto.response.ConcertResponse;
import com.technokratos.model.ConcertEntity;

import java.util.UUID;

public class ConcertConst {

    public static final UUID CONCERT_ID = UUID.randomUUID();

    public static final UUID FAKE_CONCERT_ID = UUID.randomUUID();

    public static final String CONCERT_NAME = "Concert name";

    public static final String CONCERT_DESCRIPTION = "Some description";

    public static final Integer TICKETS_NUMBER = 42;

    public static final ConcertResponse CONCERT_RESPONSE = ConcertResponse.builder()
            .id(CONCERT_ID)
            .name(CONCERT_NAME)
            .description(CONCERT_DESCRIPTION)
            .ticketsNumber(TICKETS_NUMBER)
            .build();

    public static final ConcertRequest CONCERT_REQUEST = ConcertRequest.builder()
            .name(CONCERT_NAME)
            .description(CONCERT_DESCRIPTION)
            .ticketsNumber(TICKETS_NUMBER)
            .build();

    public static final ConcertEntity NEW_CONCERT_ENTITY = ConcertEntity.builder()
            .name(CONCERT_NAME)
            .description(CONCERT_DESCRIPTION)
            .ticketsNumber(TICKETS_NUMBER)
            .isDeleted(false)
            .build();

    public static final ConcertEntity CONCERT_ENTITY = ConcertEntity.builder()
            .id(CONCERT_ID)
            .name(CONCERT_NAME)
            .description(CONCERT_DESCRIPTION)
            .ticketsNumber(TICKETS_NUMBER)
            .isDeleted(false)
            .build();
}
