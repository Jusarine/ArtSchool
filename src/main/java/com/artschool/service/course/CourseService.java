package com.artschool.service.course;

import com.artschool.model.entity.*;
import com.artschool.model.enumeration.Audience;
import com.artschool.model.enumeration.Discipline;

import java.util.List;
import java.util.Set;

public interface CourseService {

    Course createCourse(Course course);

    void enrollInCourse(Student student, Course course);

    Course createCourse(String name, Discipline discipline, Audience audience, Integer fee, Date date, List<Day> days, String description, Instructor instructor);

    void save(Course course);

    void unenrollFromCourse(Student student, Course course);

    boolean isEnrolled(Student student, Course course);

    boolean isAuthor(Instructor instructor, Course course);

    void updateCourse(long id, String name, Discipline discipline, Audience audience, Integer fee, Date date, List<Day> days, String description);

    void deleteCourse(Course course);

    Course findCourseById(long id);

    Set<Course> findCoursesByName(String name);

    List<Course> findCourses();
}
