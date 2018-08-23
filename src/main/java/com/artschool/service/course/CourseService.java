package com.artschool.service.course;

import com.artschool.model.Course;
import com.artschool.model.Instructor;
import com.artschool.model.Student;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CourseService {

    Course createCourse(String name, String description, Instructor instructor);

    Course createCourse(Course course);

    void enrollInCourse(Student student, Course course);

    void unenrollFromCourse(Student student, Course course);

    boolean isEnrolled(Student student, Course course);

    Course findCourseById(long id);

    @Transactional(readOnly = true)
    List<Course> findCoursesByName(String name);

    List<Course> findCourses();
}
