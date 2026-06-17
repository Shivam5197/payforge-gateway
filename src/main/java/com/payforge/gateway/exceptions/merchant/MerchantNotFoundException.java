package com.payforge.gateway.exceptions.merchant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MerchantNotFoundException extends RuntimeException{
    // 1. Store the status as an instance variable
    private final HttpStatus httpStatus;

    public MerchantNotFoundException(String message , HttpStatus httpStatus){
        super(message);
        this.httpStatus = httpStatus;
    }
}
