package com.artschool.service.course;

import com.artschool.model.*;

import java.util.List;
import java.util.Set;

public interface CourseService {

    Course createCourse(Course course);

    Course createCourse(String name, Discipline discipline, Audience audience, String description, Instructor instructor);

    void enrollInCourse(Student student, Course course);

    void unenrollFromCourse(Student student, Course course);

    boolean isEnrolled(Student student, Course course);

    boolean isAuthor(Instructor instructor, Course course);

    void updateCourse(long id, String name, Discipline discipline, Audience audience, String description);

    void deleteCourse(Course course);

    Course findCourseById(long id);

    Set<Course> findCoursesByName(String name);

    List<Course> findCourses();
}
