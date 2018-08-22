package com.artschool.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Student extends CustomUser {

    private Integer age;

    @ManyToMany(mappedBy = "students")
    private Set<Course> courses = new HashSet<>();

    public Student() {
    }

    public Student(String firstName, String lastName, String phoneNumber, String email, String password) {
        super(firstName, lastName, phoneNumber, email, password);
        super.setRole(UserRole.USER);
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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

    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        if (!super.equals(o)) return false;
        Student that = (Student) o;
        if (age != null ? !age.equals(that.age) : that.age != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 31 * hash + (age != null ? age.hashCode() : 0);
        return hash;
    }
}
