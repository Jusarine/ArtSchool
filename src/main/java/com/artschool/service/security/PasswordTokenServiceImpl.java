package com.artschool.service.security;

import com.artschool.model.entity.PasswordResetToken;
import com.artschool.repository.PasswordTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PasswordTokenServiceImpl implements PasswordTokenService {

    private final PasswordTokenRepository passwordTokenRepository;

    @Autowired
    public PasswordTokenServiceImpl(PasswordTokenRepository passwordTokenRepository) {
        this.passwordTokenRepository = passwordTokenRepository;
    }

    @Override
    @Transactional
    public void save(PasswordResetToken resetToken){
        passwordTokenRepository.save(resetToken);
    }

    @Override
    @Transactional(readOnly = true)
    public PasswordResetToken findByToken(String token){
        return passwordTokenRepository.findPasswordResetTokenByToken(token);
    }
}
