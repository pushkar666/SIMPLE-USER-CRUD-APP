package com.user_management.user_management_spring_boot.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.user_management.user_management_spring_boot.entity.UserInfo;

import java.util.Optional;
// import java.util.List;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByUserName(String userName); // Use 'email' if that is the correct field for login

    boolean existsByUserName(String userName); //

    @Query("SELECT u FROM UserInfo u WHERE " +
            "( :userId IS NULL OR u.id = :userId ) AND " +
            "( :firstName IS NULL OR u.firstName LIKE %:firstName% ) AND " +
            "( :lastName IS NULL OR u.lastName LIKE %:lastName% ) AND " +
            "( :email IS NULL OR u.email LIKE %:email% ) AND " +
            "( :userName IS NULL OR u.userName LIKE %:userName% )")
    Page<UserInfo> findUsers(
            @Param("userId") Integer userId,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("email") String email,
            @Param("userName") String userName,
            Pageable pageable);

}
