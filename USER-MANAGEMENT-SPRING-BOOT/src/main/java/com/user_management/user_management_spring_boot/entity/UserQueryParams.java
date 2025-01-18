package com.user_management.user_management_spring_boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserQueryParams {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
}
