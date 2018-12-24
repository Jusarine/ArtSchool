package com.artschool.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Discipline {

    @Id
    @Column(name = "discipline_id")
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "disciplines", cascade = CascadeType.MERGE)
    private Set<Course> courses = new HashSet<>();

    public Discipline() {
    }

    public Discipline(String name) {
        this.name = name;
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

    public String getCapitalizedName() {
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
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

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    @Override
    public String toString() {
        return "Discipline{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discipline that = (Discipline) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int hash = (int) id;
        hash = 31 * hash + (name != null ? name.hashCode() : 0);
        return hash;
    }
}
