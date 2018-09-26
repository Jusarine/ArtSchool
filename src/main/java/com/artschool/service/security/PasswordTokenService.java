package com.artschool.service.security;

import com.artschool.model.entity.PasswordResetToken;

public interface PasswordTokenService {

    void save(PasswordResetToken resetToken);

    PasswordResetToken findByToken(String token);
}
