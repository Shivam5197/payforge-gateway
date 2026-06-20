package com.payforge.gateway.apikey.exception;

public class InvalidAPIKeyException
        extends RuntimeException {

    public InvalidAPIKeyException(
            String message) {

        super(message);
    }
}