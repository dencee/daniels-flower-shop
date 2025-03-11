package com.flowershop.exception;

public class UnauthorizedUserAccess extends RuntimeException {

    public UnauthorizedUserAccess(String message) {
        super(message);
    }

    public UnauthorizedUserAccess(String message, Throwable cause) {
        super(message, cause);
    }
}
