package com.artschool;

public enum UserRole {
    USER, ADMIN;

    @Override
    public String toString() {
        return "ROLE_" + name();
    }
}
