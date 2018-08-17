package com.artschool.service;

import com.artschool.model.Course;

import com.artschool.model.Instructor;
import com.artschool.model.Student;
import com.artschool.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService{

    private final CourseRepository courseRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional
    @Override
    public void createCourse(Course course) {
        course.getInstructor().addCourse(course);
        courseRepository.save(course);
    }

    @Transactional
    @Override
    public void createCourse(String name, String description, Instructor instructor) {
        Course course = new Course(name, description, instructor);
        instructor.addCourse(course);
        courseRepository.save(course);
    }

    @Transactional
    @Override
    public void enrollInCourse(Student student, Course course) {
        course.addStudent(student);
        student.addCourse(course);
        courseRepository.save(course);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Course> findCourses() {
        return courseRepository.findAll();
    }
}
