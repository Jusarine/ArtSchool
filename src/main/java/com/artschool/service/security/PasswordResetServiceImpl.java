package com.artschool.service.security;

import com.artschool.model.entity.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    private final EmailService emailService;

    @Autowired
    public PasswordResetServiceImpl(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void sendEmail(CustomUser user, String passwordResetToken,  HttpServletRequest req) {
        String appUrl = req.getRequestURL().toString().replace(req.getRequestURI(), req.getContextPath());
        constructEmail(user.getEmail(), appUrl + "/reset_password?id=" + user.getId() + "&token=" +
                passwordResetToken);
    }

    private void constructEmail(String recipient, String resetLink) {
        SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
        passwordResetEmail.setTo(recipient);
        passwordResetEmail.setSubject("ArtSchool - Password Reset Request");
        passwordResetEmail.setText("To reset your password, click the link below:\n" + resetLink);
        emailService.sendEmail(passwordResetEmail);
    }
}
