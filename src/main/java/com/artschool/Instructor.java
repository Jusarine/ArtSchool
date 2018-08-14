package com.artschool;

import javax.persistence.*;

@Entity
public class Instructor extends CustomUser{

    @Id
    @GeneratedValue
    private long id;

    private int rating;

    public Instructor() {
    }

    public Instructor(String firstName, String lastName, String phoneNumber, String email, String password) {
        super(firstName, lastName, phoneNumber, email, password);
        super.setRole(UserRole.ADMIN);
    }

    public long getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
