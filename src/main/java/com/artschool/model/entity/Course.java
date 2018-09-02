package com.artschool.model.entity;

import com.artschool.model.enumeration.Audience;
import com.artschool.model.enumeration.Discipline;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Entity
public class Course {

    @Id
    @Column(name = "course_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Discipline discipline;

    @Enumerated(EnumType.STRING)
    private Audience audience;

    private Integer fee;

    @OneToOne
    @JoinColumn(name = "date_id")
    private Date date;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "course_day",
            joinColumns = {@JoinColumn(name = "course_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "day_id", nullable = false)}
    )
    private List<Day> days = new LinkedList<>();

    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "course_student",
            joinColumns = {@JoinColumn(name = "course_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "student_id", nullable = false)}
    )
    private Set<Student> students = new HashSet<>();

    public Course() {
    }

    public Course(String name, Discipline discipline, Audience audience, Integer fee, Date date, List<Day> days, String description, Instructor instructor) {
        this.name = name;
        this.discipline = discipline;
        this.audience = audience;
        this.fee = fee;
        this.date = date;
        this.days = days;
        this.description = description;
        this.instructor = instructor;
    }

    public Course(long id, String name, Discipline discipline, Audience audience, Integer fee, Date date, List<Day> days, String description, Instructor instructor) {
        this.id = id;
        this.name = name;
        this.discipline = discipline;
        this.audience = audience;
        this.fee = fee;
        this.date = date;
        this.days = days;
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

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public Audience getAudience() {
        return audience;
    }

    public void setAudience(Audience audience) {
        this.audience = audience;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

    public void addDay(Day day){
        days.add(day);
    }

    public void removeDay(Day day){
        days.remove(day);
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
                ", discipline=" + discipline +
                ", audience=" + audience +
                ", fee=" + fee +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course that = (Course) o;

        if (id == that.id) return false;
        if(name != null ? !name.equals(that.name) : that.name != null) return false;
        if(discipline != null ? !discipline.equals(that.discipline) : that.discipline != null) return false;
        if(audience != null ? !audience.equals(that.audience) : that.audience != null) return false;
        if(fee != null ? !fee.equals(that.fee) : that.fee != null) return false;
        if(description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int hash = (int) id;
        hash = 31 * hash + (name != null ? name.hashCode() : 0);
        hash = 31 * hash + (discipline != null ? discipline.hashCode() : 0);
        hash = 31 * hash + (audience != null ? audience.hashCode() : 0);
        hash = 31 * hash + (fee != null ? fee.hashCode() : 0);
        hash = 31 * hash + (description != null ? description.hashCode() : 0);
        return hash;
    }
}
