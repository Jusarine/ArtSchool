package com.artschool;

import javax.persistence.*;

@Entity
public class Student extends CustomUser {

    @Id
    @GeneratedValue
    private long id;

    private int age;

    public Student() {
    }

    public Student(String firstName, String lastName, String phoneNumber, String email, String password) {
        super(firstName, lastName, phoneNumber, email, password);
        super.setRole(UserRole.USER);
    }

    public long getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
