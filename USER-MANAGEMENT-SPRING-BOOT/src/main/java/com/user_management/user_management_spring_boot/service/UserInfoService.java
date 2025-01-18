package com.user_management.user_management_spring_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user_management.user_management_spring_boot.entity.UserInfo;
import com.user_management.user_management_spring_boot.entity.UserQueryParams;
import com.user_management.user_management_spring_boot.repo.UserInfoRepository;

import java.util.ArrayList;
import java.util.List;
// import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    // @Override
    // public UserDetails loadUserByUsername(String username) throws
    // UsernameNotFoundException {
    // Optional<UserInfo> userDetail = repository.findByEmail(username); // Assuming
    // 'email' is used as username

    // // Converting UserInfo to UserDetails
    // return userDetail.map(UserInfoDetails::new)
    // .orElseThrow(() -> new UsernameNotFoundException("User not found: " +
    // username));
    // }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = repository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassWord(),
                List.of(new SimpleGrantedAuthority(user.getRoles())));
    }

    public Integer addUser(UserInfo userInfo) {
        // Encode password before saving the user
        userInfo.setPassWord(encoder.encode(userInfo.getPassWord()));
        repository.save(userInfo);
        return userInfo.getId();
    }

    public boolean usernameExists(String username) {
        return repository.existsByUserName(username);
    }

    public Page<UserInfo> queryUsers(UserQueryParams queryParams, Pageable pageable) {
        return repository.findUsers(
                queryParams.getUserId(),
                queryParams.getFirstName(),
                queryParams.getLastName(),
                queryParams.getEmail(),
                queryParams.getUserName(),
                pageable
        );
    }
    
}