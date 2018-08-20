package com.artschool.service;

import com.artschool.model.Course;
import com.artschool.model.Instructor;
import com.artschool.model.Student;

import java.util.List;

public interface CourseService {

    Course createCourse(String name, String description, Instructor instructor);

    Course createCourse(Course course);

    void enrollInCourse(Student student, Course course);

    Course findCourseById(long id);

    List<Course> findCourses();
}
