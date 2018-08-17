package com.artschool.service;

import com.artschool.model.Course;
import com.artschool.model.Instructor;
import com.artschool.model.Student;

import java.util.List;

public interface CourseService {

    void createCourse(String name, String description, Instructor instructor);

    void createCourse(Course course);

    void enrollInCourse(Student student, Course course);

    List<Course> findCourses();
}
