package com.artschool.model;

public enum UserRole {
    USER, ADMIN;

    @Override
    public String toString() {
        return "ROLE_" + name();
    }
}
