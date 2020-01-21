package com.dawhey.challenge.exception;

public class RequestFailureException extends RuntimeException {

    public RequestFailureException(final String message) {
        super(message);
    }
}
