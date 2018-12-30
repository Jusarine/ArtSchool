package com.artschool.model.entity;

import com.artschool.model.enumeration.Gender;
import com.artschool.model.enumeration.UserRole;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Instructor extends CustomUser {

    @Lob
    private String bio;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.MERGE)
    private Set<Course> courses = new HashSet<>();

    public Instructor() {
    }

    public Instructor(String firstName, String lastName, Gender gender, String phoneNumber, String email,
                      String password, String bio) {
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

    public void addCourse(Course course) {
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
        if (!(o instanceof Instructor)) return false;
        Instructor that = (Instructor) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(bio, that.bio)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(bio)
                .toHashCode();
    }
}
