package com.hotel.room_service.domain.exception;

import org.springframework.http.HttpStatus;

public abstract class AppException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String userMessage;

    protected AppException(String userMessage, HttpStatus httpStatus) {
        super(userMessage);
        this.httpStatus = httpStatus;
        this.userMessage = userMessage;
    }

    protected AppException(String userMessage, HttpStatus httpStatus, Throwable cause) {
        super(userMessage, cause);
        this.httpStatus = httpStatus;
        this.userMessage = userMessage;
    }

    public HttpStatus getHttpStatus() { return httpStatus; }
    public String getUserMessage() { return userMessage; }
}
