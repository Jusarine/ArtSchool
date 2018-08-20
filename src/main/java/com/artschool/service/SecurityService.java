package com.artschool.service;

import org.springframework.security.core.userdetails.User;

public interface SecurityService {

    User getCurrentUser();

    void login(String username, String password);
}