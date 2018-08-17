package com.artschool.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Instructor extends CustomUser{

    @Id
    @GeneratedValue
    @Column(name = "instructor_id")
    private long id;

    private Integer rating;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    private Set<Course> courses = new HashSet<>();

    public Instructor() {
    }

    public Instructor(String firstName, String lastName, String phoneNumber, String email, String password) {
        super(firstName, lastName, phoneNumber, email, password);
        super.setRole(UserRole.ADMIN);
    }

    public long getId() {
        return id;
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
                "id=" + id +
                ", rating=" + rating +
                "} " + super.toString();
    }
}
