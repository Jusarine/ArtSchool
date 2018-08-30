package com.artschool.model.entity;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Day {

    @Id
    @Column(name = "day_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek name;

    @ManyToMany(mappedBy = "days", cascade = CascadeType.MERGE)
    private Set<Course> courses = new HashSet<>();

    public Day() {
    }

    public Day(DayOfWeek name) {
        this.name = name;
    }

    public DayOfWeek getName() {
        return name;
    }

    public void setName(DayOfWeek name) {
        this.name = name;
    }

    public String getCapitalizedName(){
        return name.toString().substring(0, 1).toUpperCase() + name.toString().substring(1).toLowerCase();
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
        return "Day{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Day that = (Day) o;

        if (id == that.id) return false;
        if(name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int hash = (int) id;
        hash = 31 * hash + (name != null ? name.hashCode() : 0);
        return hash;
    }

}
