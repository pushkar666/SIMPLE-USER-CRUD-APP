package com.user_management.user_management_spring_boot.controller;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.user_management.user_management_spring_boot.entity.AuthRequest;
import com.user_management.user_management_spring_boot.entity.UserInfo;
import com.user_management.user_management_spring_boot.entity.UserQueryParams;
import com.user_management.user_management_spring_boot.entity.UserResponse;
import com.user_management.user_management_spring_boot.service.JwtService;
import com.user_management.user_management_spring_boot.service.UserInfoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/addNewUser")
    public ResponseEntity<UserResponse> addNewUser(@Valid @RequestBody UserInfo userInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new UserResponse("Validation failed: " + errorMessage, null));
        }

        // Check for unique username
        if (service.usernameExists(userInfo.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new UserResponse("Username already exists", null));
        }

        // Save user and return response
        Integer userId = service.addUser(userInfo);

        if (userId != null) {
            return ResponseEntity.ok(new UserResponse("success", userId));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UserResponse("User creation failed", null));
        }
    }

    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile() {
        return "Welcome to User Profile";
    }

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassWord()));
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(authRequest.getUserName());
            } else {
                throw new UsernameNotFoundException("Invalid user request!");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
            throw new UsernameNotFoundException("Authentication failed: " + e.getMessage());
        }
    }

    //
    //
    @PostMapping("/queryUsers")
    public ResponseEntity<Page<UserInfo>> queryUsers(@RequestBody UserQueryParams queryParams,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            Pageable pageable) {
        if (!isAuthenticated(authHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Page<UserInfo> matchingUsers = service.queryUsers(queryParams, pageable);
        return ResponseEntity.ok(matchingUsers);
    }

    private boolean isAuthenticated(String authHeader) {
        if (authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Extract the JWT token
            return jwtService.validateToken(token); // Validate the JWT token
        } else if (authHeader.startsWith("Basic ")) {
            String encodedCredentials = authHeader.substring(6); // Extract the encoded credentials
            return isValidBasicAuthCredentials(encodedCredentials); // Validate Basic Auth credentials
        }
        return false; // Invalid authentication method
    }

    private boolean isValidBasicAuthCredentials(String encodedCredentials) {
        // Decrypt the Base64 encoded username:password and verify the credentials
        String decodedCredentials = new String(Base64.getDecoder().decode(encodedCredentials));
        String[] credentials = decodedCredentials.split(":");
        String username = credentials[0];
        String password = credentials[1];

        // You should now authenticate the user with these credentials
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return true; // Credentials are valid
        } catch (AuthenticationException e) {
            return false; // Authentication failed
        }
    }

}
