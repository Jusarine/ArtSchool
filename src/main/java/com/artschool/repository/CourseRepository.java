package com.artschool.repository;

import com.artschool.model.Course;
import com.artschool.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Course findCourseById(long id);
}
