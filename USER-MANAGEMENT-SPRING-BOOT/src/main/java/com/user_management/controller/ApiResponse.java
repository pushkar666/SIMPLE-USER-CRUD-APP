package com.user_management.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private Status status; // Enum to represent SUCCESS or FAILURE
    private T data; // Generic type for actual response data
    private String message; // Optional field for additional details

}
