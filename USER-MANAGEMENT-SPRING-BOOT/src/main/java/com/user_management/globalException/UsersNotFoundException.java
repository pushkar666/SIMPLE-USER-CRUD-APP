package com.user_management.globalException;

public class UsersNotFoundException extends DataNotFoundException {
    public UsersNotFoundException(String message) {
        super(message);
    }
}
