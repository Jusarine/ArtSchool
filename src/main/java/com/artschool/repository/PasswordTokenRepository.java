package com.artschool.repository;

import com.artschool.model.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findPasswordResetTokenByToken(String token);
}
