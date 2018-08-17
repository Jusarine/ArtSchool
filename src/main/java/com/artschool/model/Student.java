package com.artschool.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Student extends CustomUser {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "student_id")
    private long id;

    private Integer age;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "student_courses",
            joinColumns = {@JoinColumn(name = "student_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "course_id", nullable = false)}
    )
    private Set<Course> courses = new HashSet<>();

    public Student() {
    }

    public Student(String firstName, String lastName, String phoneNumber, String email, String password) {
        super(firstName, lastName, phoneNumber, email, password);
        super.setRole(UserRole.USER);
    }

    public long getId() {
        return id;
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

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", age=" + age +
                "} " + super.toString();
    }
}
