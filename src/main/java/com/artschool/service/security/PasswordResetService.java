package com.artschool.service.security;

import com.artschool.model.entity.CustomUser;

import javax.servlet.http.HttpServletRequest;

public interface PasswordResetService {

    String generateToken();

    void sendEmail(CustomUser user, String passwordResetToken, HttpServletRequest req);
}
