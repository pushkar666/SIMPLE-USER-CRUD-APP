package com.user_management.user_management_spring_boot.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user_management.user_management_spring_boot.entity.JwtAudit;
import com.user_management.user_management_spring_boot.entity.UserInfo;

@Repository
public interface JwtAuditRepository extends JpaRepository<JwtAudit, Long> {
    Optional<JwtAudit> findByToken(String token);

    List<JwtAudit> findAllByUser(UserInfo user);
}
