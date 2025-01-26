package com.user_management.user_management_spring_boot.service;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.user_management.user_management_spring_boot.entity.JwtAudit;
import com.user_management.user_management_spring_boot.entity.UserInfo;
import com.user_management.user_management_spring_boot.repo.JwtAuditRepository;
import com.user_management.user_management_spring_boot.repo.UserInfoRepository;

@Service
public class JwtAuditService {

    @Autowired
    private JwtAuditRepository jwtAuditRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    /**
     * Saves a generated JWT to the database for auditing.
     *
     * @param username The username associated with the token.
     * @param token    The JWT token to save.
     */
    public void saveTokenToDatabase(String username, String token) {
        UserInfo user = userInfoRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        JwtAudit jwtAudit = new JwtAudit();
        jwtAudit.setUser(user);
        jwtAudit.setToken(token);
        jwtAudit.setExpiry(new Date(System.currentTimeMillis() + 1000 * 60 * 30)); // 30-minute expiry
        jwtAuditRepository.save(jwtAudit);
    }

    public void revokeToken(String token) {
        JwtAudit jwtAudit = jwtAuditRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        jwtAudit.setIsValid(false);  // Mark the token as invalid
        jwtAuditRepository.save(jwtAudit);  // Save the updated record in the database
    }
}
