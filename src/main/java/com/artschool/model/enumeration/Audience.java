package com.artschool.model.enumeration;

public enum Audience {
    Kids, Teens, Adults;

    @Override
    public String toString() {
        return name();
    }
}
