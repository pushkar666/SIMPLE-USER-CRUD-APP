package com.user_management.globalException;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Slf4j
public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException() {
        super();
    }

    public DataNotFoundException(String message) {
        super(message);
        log.error("DataNotFoundException: {}", message);
    }
}
