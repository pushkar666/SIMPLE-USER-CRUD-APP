package com.user_management.globalException;

public class UserNotFoundException extends DataNotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
