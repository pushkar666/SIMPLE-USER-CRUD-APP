package com.user_management.globalException;

public class UserCreationException extends RuntimeException {
    public UserCreationException(String message) {
        super(message);
    }
}