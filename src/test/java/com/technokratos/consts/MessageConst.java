package com.technokratos.consts;

import com.technokratos.dto.response.message.ExceptionMessage;

import static com.technokratos.consts.UserConst.*;
import static com.technokratos.consts.ConcertConst.*;

public class MessageConst {

    public static final String USER_NOT_FOUND_MESSAGE = "User not found with email: ";

    public static final ExceptionMessage USER_ALREADY_EXISTS_MESSAGE = ExceptionMessage.builder()
            .message("User already exists with email: " + USER_REPEATED_EMAIL)
            .exceptionName("UserAlreadyExistsException")
            .build();

    public static final ExceptionMessage WRONG_EMAIL_MESSAGE = ExceptionMessage.builder()
            .message(USER_NOT_FOUND_MESSAGE + USER_WRONG_EMAIL)
            .exceptionName("UserNotFoundException")
            .build();

    public static final ExceptionMessage WRONG_PASSWORD_MESSAGE = ExceptionMessage.builder()
            .message(USER_NOT_FOUND_MESSAGE + USER_EMAIL)
            .exceptionName("UserNotFoundException")
            .build();

    public static final ExceptionMessage ACCESS_DENIED_MESSAGE = ExceptionMessage.builder()
            .message("Доступ запрещен")
            .exceptionName("AccessDeniedException")
            .build();

    public static final ExceptionMessage TICKETS_SOLD_OUT_MESSAGE = ExceptionMessage.builder()
            .message("Tickets are sold out for the concert: " + POPULAR_CONCERT_NAME)
            .exceptionName("TicketsSoldOutException")
            .build();

}
