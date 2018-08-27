package com.artschool.model;

public enum Audience {
    Kids, Teens, Adults;

    @Override
    public String toString() {
        return name();
    }
}
