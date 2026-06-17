package com.payforge.gateway.exceptions.merchant;

public class MerchantAlreadyExistsException
        extends RuntimeException {

    public MerchantAlreadyExistsException(
            String message) {

        super(message);
    }
}