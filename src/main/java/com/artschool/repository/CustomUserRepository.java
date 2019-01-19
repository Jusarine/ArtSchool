package com.artschool.repository;

import com.artschool.model.entity.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {

    CustomUser findByPasswordResetToken_Token(String passwordResetToken);

    CustomUser findByEmail(String email);
}
