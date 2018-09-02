package com.artschool.service.course;

import com.artschool.model.entity.*;
import com.artschool.model.form.CourseForm;

import java.util.List;
import java.util.Set;

public interface CourseService {

    Course createCourse(Course course);

    void createCourse(CourseForm form, Instructor instructor);

    Course updateCourse(Course course);

    void updateCourse(long courseId, CourseForm form);

    void saveOrUpdate(Course course);

    void enrollInCourse(Student student, Course course);

    void unenrollFromCourse(Student student, Course course);

    boolean isEnrolled(Student student, Course course);

    boolean isAuthor(Instructor instructor, Course course);

    void deleteCourse(long courseId);

    Course findCourseById(long id);

    Set<Course> findCoursesByName(String name);

    List<Course> findCourses();
}
