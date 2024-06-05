package com.example.feedserver.exception;

public class BadRequestException extends BaseException {

    public BadRequestException() {
        super(400, "bad request");
    }

    public BadRequestException(String message) {
        super(400, message);
    }
}
