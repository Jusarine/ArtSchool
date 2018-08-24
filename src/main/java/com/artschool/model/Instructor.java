package com.artschool.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Instructor extends CustomUser{

    private Integer rating;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    private Set<Course> courses = new HashSet<>();

    public Instructor() {
    }

    public Instructor(String firstName, String lastName, Gender gender, String phoneNumber, String email, String password) {
        super(firstName, lastName, gender, phoneNumber, email, password);
        super.setRole(UserRole.ADMIN);
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
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

    @Override
    public String toString() {
        return "Instructor{" +
                "rating=" + rating +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!super.equals(o)) return false;
        Instructor that = (Instructor) o;
        if (rating != null ? !rating.equals(that.rating) : that.rating != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 31 * hash + (rating != null ? rating.hashCode() : 0);
        return hash;
    }
}
