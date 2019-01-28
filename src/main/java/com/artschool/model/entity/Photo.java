package com.artschool.model.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Photo {

    @Id
    @Column(name = "photo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "author_id")
    private CustomUser author;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "course_id")
    private Course course;

    private LocalDate date;

    public Photo() {
    }

    public Photo(String name, CustomUser author, Course course) {
        this.name = name;
        this.author = author;
        this.course = course;
        date = LocalDate.now();
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

    public CustomUser getAuthor() {
        return author;
    }

    public void setAuthor(CustomUser author) {
        this.author = author;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", author=" + author +
                ", course=" + course +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Photo)) return false;
        Photo photo = (Photo) o;

        return new EqualsBuilder()
                .append(author, photo.author)
                .append(course, photo.course)
                .append(date, photo.date)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(author)
                .append(course)
                .append(date)
                .toHashCode();
    }
}
