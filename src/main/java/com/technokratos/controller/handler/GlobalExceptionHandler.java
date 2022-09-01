package com.technokratos.controller.handler;

import com.technokratos.dto.response.message.ExceptionMessage;
import com.technokratos.exception.CpServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionMessage> onAllExceptions(Exception exception) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionMessage.builder()
                        .message(exception.getMessage())
                        .exceptionName(exception.getClass().getSimpleName())
                        .build()
                );
    }

    @ExceptionHandler(CpServiceException.class)
    public final ResponseEntity<ExceptionMessage> onServiceExceptions(CpServiceException serviceException) {

        return ResponseEntity.status(serviceException.getHttpStatus())
                .body(ExceptionMessage.builder()
                        .message(serviceException.getMessage())
                        .exceptionName(serviceException.getClass().getSimpleName())
                        .build()
                );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<ExceptionMessage> onTypeMismatchExceptions(
            MethodArgumentTypeMismatchException exception) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionMessage.builder()
                        .message(exception.getMessage())
                        .exceptionName(exception.getClass().getSimpleName())
                        .build());
    }
}
