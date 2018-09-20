package com.artschool.model.entity;

import com.artschool.model.enumeration.Gender;
import com.artschool.model.enumeration.UserRole;

import javax.persistence.*;
import java.util.*;

@Entity
public class Student extends CustomUser {

    @ManyToMany(mappedBy = "students")
    private Set<Course> courses = new HashSet<>();

    @OneToMany(mappedBy = "payer", cascade = CascadeType.MERGE)
    private Set<Payment> payments = new HashSet<>();

    public Student() {
    }

    public Student(String firstName, String lastName, Gender gender, String phoneNumber, String email, String password) {
        super(firstName, lastName, gender, phoneNumber, email, password);
        super.setRole(UserRole.USER);
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course){
        courses.add(course);
    }

    public void removeCourse(Course course){
        courses.remove(course);
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public void addPayment(Payment payment){
        payments.add(payment);
    }

    public void removePayment(Payment payment){
        payments.remove(payment);
    }

    @Override
    public String toString() {
        return "Student{}" + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        if (!super.equals(o)) return false;
        Student that = (Student) o;
        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
