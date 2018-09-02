package com.artschool.repository;

import com.artschool.model.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Set<Course> findCoursesByNameContaining(String name);
}
