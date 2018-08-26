package com.artschool.service.course;

import com.artschool.model.Course;
import com.artschool.model.Instructor;
import com.artschool.model.Student;

import java.util.List;

public interface CourseService {

    Course createCourse(String name, String description, Instructor instructor);

    Course createCourse(Course course);

    void enrollInCourse(Student student, Course course);

    void unenrollFromCourse(Student student, Course course);

    boolean isEnrolled(Student student, Course course);

    boolean isAuthor(Instructor instructor, Course course);

    void updateCourse(long id, String name, String description);

    void deleteCourse(Course course);

    Course findCourseById(long id);

    List<Course> findCoursesByName(String name);

    List<Course> findCourses();
}
