package com.example.auction.exception.err;

public class PasswordsNotMatchException extends RuntimeException{
    public PasswordsNotMatchException(String message) {
        super(message);
    }
}
