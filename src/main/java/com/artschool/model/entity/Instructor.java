package com.artschool.model.entity;

import com.artschool.model.enumeration.Gender;
import com.artschool.model.enumeration.UserRole;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Instructor extends CustomUser {

    @Column(columnDefinition = "LONGTEXT")
    private String bio;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.MERGE)
    private Set<Course> courses = new HashSet<>();

    public Instructor() {
    }

    public Instructor(String firstName, String lastName, Gender gender, String phoneNumber, String email, String password, String bio) {
        super(firstName, lastName, gender, phoneNumber, email, password);
        super.setRole(UserRole.ADMIN);
        this.bio = bio;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
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
                "bio='" + bio + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!super.equals(o)) return false;
        Instructor that = (Instructor) o;
        if (bio != null ? !bio.equals(that.bio) : that.bio != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 31 * hash + (bio != null ? bio.hashCode() : 0);
        return hash;
    }
}
