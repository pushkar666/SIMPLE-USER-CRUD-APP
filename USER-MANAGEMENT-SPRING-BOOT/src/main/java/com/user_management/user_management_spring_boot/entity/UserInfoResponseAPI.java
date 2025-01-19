package com.user_management.user_management_spring_boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponseAPI {
    private Integer id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String roles;
}
