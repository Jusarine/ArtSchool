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

    public Student(String firstName, String lastName, String password, String phoneNumber, String email) {
        super(firstName, lastName, password, phoneNumber, email);
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
