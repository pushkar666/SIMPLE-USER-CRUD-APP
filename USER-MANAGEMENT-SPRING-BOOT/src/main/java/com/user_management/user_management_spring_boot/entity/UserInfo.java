package com.user_management.user_management_spring_boot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_table1")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username should be between 3 and 20 characters")
    private String userName;

    @NotNull(message = "First name is required")
    private String firstName;

    @NotNull(message = "Last name is required")
    private String lastName;

    @Email(message = "Email should be valid")
    private String email;

    @JsonIgnore
    @NotNull(message = "Password is required")
    @Size(min = 8, message = "Password should be at least 8 characters long")
    @Pattern(regexp = "(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=]).*", message = "Password should have at least one uppercase letter, one number, and one special character")
    private String passWord;

    private String roles;
}
