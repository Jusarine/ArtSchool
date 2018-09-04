package com.artschool.model.enumeration;

public enum Audience {
    KIDS, TEENS, ADULTS;

    @Override
    public String toString() {
        return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
    }
}
