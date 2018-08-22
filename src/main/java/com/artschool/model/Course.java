package com.artschool.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Course {

    @Id
    @GeneratedValue
    @Column(name = "course_id")
    private long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "course_student",
            joinColumns = {@JoinColumn(name = "course_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "student_id", nullable = false)}
    )
    private Set<Student> students = new HashSet<>();

    public Course() {
    }

    public Course(String name, String description, Instructor instructor) {
        this.name = name;
        this.description = description;
        this.instructor = instructor;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student){
        students.add(student);
    }

    public void removeStudent(Student student){
        students.remove(student);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course that = (Course) o;

        if (id == that.id) return false;
        if(name != null ? !name.equals(that.name) : that.name != null) return false;
        if(description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int hash = (int) id;
        hash = 31 * hash + (name != null ? name.hashCode() : 0);
        hash = 31 * hash + (description != null ? description.hashCode() : 0);
        return hash;
    }
}
