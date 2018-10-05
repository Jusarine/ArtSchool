package com.artschool.repository;

import com.artschool.model.entity.Course;
import com.artschool.model.entity.Discipline;
import com.artschool.model.entity.Instructor;
import com.artschool.model.enumeration.Audience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Set<Course> findCoursesByNameContaining(String name);

    Set<Course> findCoursesByDisciplines(Discipline discipline);

    Set<Course> findCoursesByAudience(Audience audience);

    Set<Course> findCoursesByInstructor(Instructor instructor);
}
